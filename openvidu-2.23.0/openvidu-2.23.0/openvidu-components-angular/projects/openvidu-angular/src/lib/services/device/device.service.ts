import { Injectable } from '@angular/core';
import { Device, OpenVidu, OpenViduError, OpenViduErrorName } from 'openvidu-browser';

import { CameraType, DeviceType, CustomDevice } from '../../models/device.model';
import { ILogger } from '../../models/logger.model';
import { OpenViduAngularConfigService } from '../config/openvidu-angular.config.service';

import { LoggerService } from '../logger/logger.service';
import { PlatformService } from '../platform/platform.service';
import { StorageService } from '../storage/storage.service';

/**
 * @internal
 */
@Injectable({
	providedIn: 'root'
})
export class DeviceService {
	private OV: OpenVidu | null = null;
	private devices: Device[];
	private cameras: CustomDevice[] = [];
	private microphones: CustomDevice[] = [];
	private cameraSelected: CustomDevice | null;
	private microphoneSelected: CustomDevice | null;
	private log: ILogger;
	private videoDevicesDisabled: boolean;
	private audioDevicesDisabled: boolean;

	// Initialized with Storage.VIDEO_MUTED info saved on storage
	private _isVideoMuted: boolean;
	// Initialized with Storage.AUDIO_MUTED info saved on storage
	private _isAudioMuted: boolean;
	private deviceAccessDeniedError: boolean = false;

	constructor(
		private loggerSrv: LoggerService,
		private platformSrv: PlatformService,
		private storageSrv: StorageService,
		private libSrv: OpenViduAngularConfigService
	) {
		this.log = this.loggerSrv.get('DevicesService');
	}

	/**
	 * Initialize media devices and devices selected checking in local storage (if exists) or
	 * first devices found by default
	 */
	async forceInitDevices() {
		this.clear();
		try {
			this.OV = new OpenVidu();
			this.devices = await this.OV.getDevices();
			if(this.devices?.some(device => !device.deviceId || !device.label)){
				// Forcing media permissions request.
				// Sometimes, browser doesn't request the media permissions.
				await this.OV.getUserMedia({ audioSource: undefined, videoSource: undefined });
			}

			await this.initializeDevices();
			this.updateAudioDeviceSelected();
			this.updateVideoDeviceSelected();

			this._isVideoMuted = this.storageSrv.isVideoMuted() || this.libSrv.videoMuted.getValue();
			this._isAudioMuted = this.storageSrv.isAudioMuted() || this.libSrv.audioMuted.getValue();

			this.log.d('Media devices', this.cameras, this.microphones);
		} catch (error) {
			this.deviceAccessDeniedError = (<OpenViduError>error).name === OpenViduErrorName.DEVICE_ACCESS_DENIED;
		}
	}

	/**
	 * Initialize only the media devices available
	 */
	async initializeDevices() {
		if(this.devices?.some(device => !device.deviceId || !device.label)){
			this.devices = await this.OV?.getDevices() as Device [];
		}
		this.initializeCustomDevices();
	}

	private initializeCustomDevices(updateSelected: boolean = true): void {
		const FIRST_POSITION = 0;
		const defaultMicrophones: Device[] = this.devices.filter((device) => device.kind === DeviceType.AUDIO_INPUT);
		const defaultCameras: Device[] = this.devices.filter((device) => device.kind === DeviceType.VIDEO_INPUT);

		if (defaultMicrophones.length > 0) {
			this.microphones = [];
			defaultMicrophones.forEach((device: Device) => {
				this.microphones.push({ label: device.label, device: device.deviceId });
			});
		}

		if (defaultCameras.length > 0) {
			this.cameras = [];
			defaultCameras.forEach((device: Device, index: number) => {
				const myDevice: CustomDevice = {
					label: device.label,
					device: device.deviceId,
					type: CameraType.BACK
				};
				if (this.platformSrv.isMobile()) {
					// We assume front video device has 'front' in its label in Mobile devices
					if (myDevice.label.toLowerCase().includes(CameraType.FRONT.toLowerCase())) {
						myDevice.type = CameraType.FRONT;
					}
				} else {
					// We assume first device is web camera in Browser Desktop
					if (index === FIRST_POSITION) {
						myDevice.type = CameraType.FRONT;
					}
				}
				this.cameras.push(myDevice);
			});
		}
	}

	private updateAudioDeviceSelected() {
		// Setting microphone selected
		if (this.microphones.length > 0) {
			const storageMicrophone = this.getMicrophoneFromStogare();
			if (!!storageMicrophone) {
				this.microphoneSelected = storageMicrophone;
			} else if (this.microphones.length > 0) {
				if (this.deviceAccessDeniedError && this.microphones.length > 1) {
					// We assume that the default device is already in use
					// Assign an alternative device with the aim of avoiding the DEVICE_ALREADY_IN_USE error
					this.microphoneSelected = this.microphones[1];
				} else {
					this.microphoneSelected = this.microphones[0];
				}
			}
		}
	}

	private updateVideoDeviceSelected() {
		// Setting camera selected
		if (this.cameras.length > 0) {
			const storageCamera = this.getCameraFromStorage();
			if (!!storageCamera) {
				this.cameraSelected = storageCamera;
			} else if (this.cameras.length > 0) {
				if (this.deviceAccessDeniedError && this.cameras.length > 1) {
					// We assume that the default device is already in use
					// Assign an alternative device with the aim of avoiding the DEVICE_ALREADY_IN_USE error
					this.cameraSelected = this.cameras[1];
				} else {
					this.cameraSelected = this.cameras[0];
				}
			}
		}
	}

	isVideoMuted(): boolean {
		return this.hasVideoDeviceAvailable() && this._isVideoMuted;
	}

	isAudioMuted(): boolean {
		return this.hasAudioDeviceAvailable() && this._isAudioMuted;
	}

	getCameraSelected(): CustomDevice | null {
		return this.cameraSelected;
	}

	getMicrophoneSelected(): CustomDevice | null {
		return this.microphoneSelected;
	}

	setCameraSelected(deviceField: any) {
		this.cameraSelected = this.getCameraByDeviceField(deviceField);
		this.saveCameraToStorage(this.cameraSelected);
	}

	setMicSelected(deviceField: any) {
		this.microphoneSelected = this.getMicrophoneByDeviceField(deviceField);
		this.saveMicrophoneToStorage(this.microphoneSelected);
	}

	needUpdateVideoTrack(newVideoSource: string): boolean {
		return this.cameraSelected?.device !== newVideoSource;
	}

	needUpdateAudioTrack(newAudioSource: string): boolean {
		return this.microphoneSelected?.device !== newAudioSource;
	}

	getCameras(): CustomDevice[] {
		return this.cameras;
	}

	getMicrophones(): CustomDevice[] {
		return this.microphones;
	}

	hasVideoDeviceAvailable(): boolean {
		return !this.videoDevicesDisabled && this.cameras.length > 0;
	}

	hasAudioDeviceAvailable(): boolean {
		return !this.audioDevicesDisabled && this.microphones.length > 0;
	}

	cameraNeedsMirror(deviceField: string): boolean {
		return this.getCameraByDeviceField(deviceField)?.type === CameraType.FRONT;
	}

	areEmptyLabels(): boolean {
		return !!this.cameras.find((device) => device.label === '') || !!this.microphones.find((device) => device.label === '');
	}

	disableVideoDevices() {
		this.videoDevicesDisabled = true;
	}

	disableAudioDevices() {
		this.audioDevicesDisabled = true;
	}

	clear() {
		this.OV = null;
		this.devices = [];
		this.cameras = [];
		this.microphones = [];
		this.cameraSelected = null;
		this.microphoneSelected = null;
		this.videoDevicesDisabled = false;
		this.audioDevicesDisabled = false;
	}

	private getCameraByDeviceField(deviceField: any): CustomDevice {
		return <CustomDevice>this.cameras.find((opt: CustomDevice) => opt.device === deviceField || opt.label === deviceField);
	}

	private getMicrophoneByDeviceField(deviceField: any): CustomDevice {
		return <CustomDevice>this.microphones.find((opt: CustomDevice) => opt.device === deviceField || opt.label === deviceField);
	}

	private getMicrophoneFromStogare(): CustomDevice | undefined {
		const storageDevice: CustomDevice = this.storageSrv.getAudioDevice();
		if (!!storageDevice && this.microphones.some((device) => device.device === storageDevice.device)) {
			return storageDevice;
		}
	}

	private getCameraFromStorage() {
		const storageDevice: CustomDevice = this.storageSrv.getVideoDevice();
		if (!!storageDevice && this.cameras.some((device) => device.device === storageDevice.device)) {
			return storageDevice;
		}
	}

	private saveCameraToStorage(cam: CustomDevice) {
		this.storageSrv.setVideoDevice(cam);
	}

	private saveMicrophoneToStorage(mic: CustomDevice) {
		this.storageSrv.setAudioDevice(mic);
	}
}
