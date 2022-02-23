import { NativeModules, Platform, processColor } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-alert-loading' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const AlertLoading = NativeModules.AlertLoading
  ? NativeModules.AlertLoading
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

interface AlertLoadingParams {
  readonly overlayColor?: string;
  readonly color?: string;
  readonly animate?: boolean;
  readonly type: 'circleStrokeSpin' | 'ballSpinFadeLoader' | 'ballClipRotate';
}

export function showLoading(params: AlertLoadingParams) {
  return AlertLoading.showLoading({
    ...params,
    overlayColor: params.overlayColor
      ? processColor(params.overlayColor)
      : undefined,
    color: params.color ? processColor(params.color) : undefined,
  });
}

export function hideLoading(params?: { animate?: boolean }) {
  return AlertLoading.hideLoading(params);
}
