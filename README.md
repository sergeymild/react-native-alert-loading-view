# react-native-alert-loading

Native

## Installation

```sh
"react-native-alert-loading": "sergeymild/react-native-alert-loading-view"
yarn
```

## Usage

```js
import { showAlertLoading, hideAlertLoading } from 'react-native-alert-loading';

export interface AlertLoadingShowParams {
  readonly overlayColor?: string;
  readonly color?: string;
  readonly animate?: boolean;
  readonly type: 'circleStrokeSpin' | 'ballSpinFadeLoader' | 'ballClipRotate';
}

export interface AlertLoadingHideParams {
  readonly animate?: boolean;
}

showAlertLoading(params: AlertLoadingShowParams);
hideAlertLoading(params?: AlertLoadingHideParams);
// ...
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
