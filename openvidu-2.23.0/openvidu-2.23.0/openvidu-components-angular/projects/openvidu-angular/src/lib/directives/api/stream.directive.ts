import { AfterViewInit, Directive, ElementRef, Input, OnDestroy } from '@angular/core';
import { OpenViduAngularConfigService } from '../../services/config/openvidu-angular.config.service';

/**
 * The **displayParticipantName** directive allows show/hide the participants name in stream component.
 *
 * Default: `true`
 *
 * It can be used in the parent element {@link VideoconferenceComponent} specifying the name of the `stream` component:
 *
 * @example
 * <ov-videoconference [streamDisplayParticipantName]="false"></ov-videoconference>
 *
 * \
 * And it also can be used in the {@link StreamComponent}.
 * @example
 * <ov-stream [displayParticipantName]="false"></ov-stream>
 */
@Directive({
	selector: 'ov-videoconference[streamDisplayParticipantName], ov-stream[displayParticipantName]'
})
export class StreamDisplayParticipantNameDirective implements AfterViewInit, OnDestroy {
	@Input() set streamDisplayParticipantName(value: boolean) {
		this.displayParticipantNameValue = value;
		this.update(this.displayParticipantNameValue);
	}
	@Input() set displayParticipantName(value: boolean) {
		this.displayParticipantNameValue = value;
		this.update(this.displayParticipantNameValue);
	}

	displayParticipantNameValue: boolean;

	constructor(public elementRef: ElementRef, private libService: OpenViduAngularConfigService) {}

	ngOnDestroy(): void {
		this.clear();
	}

	ngAfterViewInit() {
		this.update(this.displayParticipantNameValue);
	}

	update(value: boolean) {
		if (this.libService.displayParticipantName.getValue() !== value) {
			this.libService.displayParticipantName.next(value);
		}
	}

	clear() {
		this.update(true);
	}
}

/**
 * The **displayAudioDetection** directive allows show/hide the participants audio detection in stream component.
 *
 * Default: `true`
 *
 * It can be used in the parent element {@link VideoconferenceComponent} specifying the name of the `stream` component:
 *
 * @example
 * <ov-videoconference [streamDisplayAudioDetection]="false"></ov-videoconference>
 *
 * \
 * And it also can be used in the {@link StreamComponent}.
 * @example
 * <ov-stream [displayAudioDetection]="false"></ov-stream>
 */
@Directive({
	selector: 'ov-videoconference[streamDisplayAudioDetection], ov-stream[displayAudioDetection]'
})
export class StreamDisplayAudioDetectionDirective implements AfterViewInit, OnDestroy {
	@Input() set streamDisplayAudioDetection(value: boolean) {
		this.displayAudioDetectionValue = value;
		this.update(this.displayAudioDetectionValue);
	}
	@Input() set displayAudioDetection(value: boolean) {
		this.displayAudioDetectionValue = value;
		this.update(this.displayAudioDetectionValue);
	}

	displayAudioDetectionValue: boolean;

	constructor(public elementRef: ElementRef, private libService: OpenViduAngularConfigService) {}

	ngAfterViewInit() {
		this.update(this.displayAudioDetectionValue);
	}
	ngOnDestroy(): void {
		this.clear();
	}

	update(value: boolean) {
		if (this.libService.displayAudioDetection.getValue() !== value) {
			this.libService.displayAudioDetection.next(value);
		}
	}
	clear() {
		this.update(true);
	}
}

/**
 * The **settingsButton** directive allows show/hide the participants settings button in stream component.
 *
 * Default: `true`
 *
 * It can be used in the parent element {@link VideoconferenceComponent} specifying the name of the `stream` component:
 *
 * @example
 * <ov-videoconference [streamSettingsButton]="false"></ov-videoconference>
 *
 * \
 * And it also can be used in the {@link StreamComponent}.
 * @example
 * <ov-stream [settingsButton]="false"></ov-stream>
 */
@Directive({
	selector: 'ov-videoconference[streamSettingsButton], ov-stream[settingsButton]'
})
export class StreamSettingsButtonDirective implements AfterViewInit, OnDestroy {
	@Input() set streamSettingsButton(value: boolean) {
		this.settingsValue = value;
		this.update(this.settingsValue);
	}
	@Input() set settingsButton(value: boolean) {
		this.settingsValue = value;
		this.update(this.settingsValue);
	}

	settingsValue: boolean;

	constructor(public elementRef: ElementRef, private libService: OpenViduAngularConfigService) {}

	ngAfterViewInit() {
		this.update(this.settingsValue);
	}

	ngOnDestroy(): void {
		this.clear();
	}

	update(value: boolean) {
		if (this.libService.streamSettingsButton.getValue() !== value) {
			this.libService.streamSettingsButton.next(value);
		}
	}

	clear() {
		this.update(true);
	}
}
