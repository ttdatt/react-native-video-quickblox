
import { NativeModules } from 'react-native';
import QuickbloxLocalVideoView from './QuickbloxLocalVideoView'
import QuickbloxRemoteVideoView from './QuickbloxRemoteVideoView'

export {
  QuickbloxLocalVideoView,
  QuickbloxRemoteVideoView
}

const { RNQuickblox } = NativeModules;

export default RNQuickblox;
