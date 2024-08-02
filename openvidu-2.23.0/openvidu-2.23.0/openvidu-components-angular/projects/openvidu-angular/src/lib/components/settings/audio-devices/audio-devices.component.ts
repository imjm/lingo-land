import { EventEmitter, Component, OnDestroy, OnInit, Output } from '@angular/core';
import { PublisherProperties } from 'openvidu-browser';
import { DeviceService } from '../../../services/device/device.service';
import { OpenViduService } from '../../../services/openvidu/openvidu.service';
import { StorageService } from '../../../services/storage/storage.service';
import { CustomDevice } from '../../../models/device.model';
import { ParticipantAbstractModel } from '../../../models/participant.model';
import { ParticipantService } from '../../../services/participant/participant.service';
import { Subscription } from 'rxjs';
import { VideoType } from '../../../models/video-type.model';

/**
 * @internal
 */
@Component({
	selector: 'ov-audio-devices-select',
	templateUrl: './audio-devices.component.html',
	styleUrls: ['./audio-devices.component.css']
})
export class AudioDevicesComponent implements OnInit, OnDestroy {
	@Output() onDeviceSelectorClicked = new EventEmitter<void>();
	hasAudioDevices: boolean;
	isAudioMuted: boolean;
	microphoneSelected: CustomDevice;
	microphones: CustomDevice[] = [];
	private localParticipantSubscription: Subscription;

	constructor(
		private openviduService: OpenViduService,
		private deviceSrv: DeviceService,
		private storageSrv: StorageService,
		private participantService: ParticipantService
	) {}

	async ngOnInit() {
		this.subscribeToParticipantMediaProperties();
		if (this.openviduService.isSessionConnected()) {
			// Updating devices only with session connected
			await this.deviceSrv.initializeDevices();
		}
		this.hasAudioDevices = this.deviceSrv.hasAudioDeviceAvailable();
		this.microphones = this.deviceSrv.getMicrophones();
		this.microphoneSelected = this.deviceSrv.getMicrophoneSelected();
		this.isAudioMuted = this.deviceSrv.isAudioMuted();
		if (this.openviduService.isSessionConnected()) {
			this.isAudioMuted = !this.participantService.isMyAudioActive();
		} else {
			this.isAudioMuted = this.deviceSrv.isAudioMuted();
		}
	}

	ngOnDestroy() {
		if (this.localParticipantSubscription) this.localParticipantSubscription.unsubscribe();
	}

	toggleMic() {
		const publish = this.isAudioMuted;
		this.openviduService.publishAudio(publish);
	}

	async onMicrophoneSelected(event: any) {
		const audioSource = event?.value;
		if (this.deviceSrv.needUpdateAudioTrack(audioSource)) {
			const pp: PublisherProperties = { audioSource, videoSource: false };
			await this.openviduService.replaceTrack(VideoType.CAMERA, pp);
			this.deviceSrv.setMicSelected(audioSource);
			this.microphoneSelected = this.deviceSrv.getMicrophoneSelected();
		}
	}

	private subscribeToParticipantMediaProperties() {
		this.localParticipantSubscription = this.participantService.localParticipantObs.subscribe((p: ParticipantAbstractModel) => {
			if (p) {
				this.isAudioMuted = !p.hasAudioActive();
				this.storageSrv.setAudioMuted(this.isAudioMuted);
			}
		});
	}
}
