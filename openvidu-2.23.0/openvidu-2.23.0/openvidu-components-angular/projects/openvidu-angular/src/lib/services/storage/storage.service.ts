import { Injectable } from '@angular/core';
import { ILogger } from '../../models/logger.model';
import { LoggerService } from '../logger/logger.service';
import { Storage } from '../../models/storage.model';

/**
 * @internal
 */
@Injectable({
	providedIn: 'root'
})
export class StorageService {
	public storage = window.localStorage;
	public log: ILogger;

	constructor(private loggerSrv: LoggerService) {
		this.log = this.loggerSrv.get('StorageService');
	}

	getNickname(): string {
		return this.get(Storage.USER_NICKNAME);
	}

	setNickname(name: string){
		this.set(Storage.USER_NICKNAME, name);
	}
	getVideoDevice() {
		return this.get(Storage.VIDEO_DEVICE);
	}

	setVideoDevice(device: any) {
		this.set(Storage.VIDEO_DEVICE, device);
	}

	getAudioDevice() {
		return this.get(Storage.AUDIO_DEVICE);
	}

	setAudioDevice(device: any) {
		this.set(Storage.AUDIO_DEVICE, device);
	}
	isVideoMuted(): boolean {
		return this.get(Storage.VIDEO_MUTED) === 'true';
	}
	setVideoMuted(muted: boolean) {
		this.set(Storage.VIDEO_MUTED, `${muted}`);
	}

	isAudioMuted(): boolean {
		return this.get(Storage.AUDIO_MUTED) === 'true';
	}

	setAudioMuted(muted: boolean) {
		this.set(Storage.AUDIO_MUTED, `${muted}`);
	}

	setLang(lang: string){
		this.set(Storage.LANG, lang);
	}

	getLang(): string {
		return this.get(Storage.LANG);
	}

	private set(key: string, item: any) {
		const value = JSON.stringify({ item: item });
		// this.log.d('Storing on localStorage "' + key + '" with value "' + value + '"');
		this.storage.setItem(key, value);
	}
	private get(key: string): any {
		const value = JSON.parse(this.storage.getItem(key));
		return value?.item ? value.item : null;
	}
	public clear() {
		this.log.d('Clearing localStorage');
		this.storage.clear();
	}
}
