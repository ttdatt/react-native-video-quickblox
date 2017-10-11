
# react-native-video-quickblox [![npm version](https://badge.fury.io/js/react-native-video-quickblox.svg)](https://badge.fury.io/js/react-native-video-quickblox)
Quickblox video call for React Native

## Getting started

`$ npm install react-native-video-quickblox --save` or `yarn add react-native-video-quickblox`

### Mostly automatic installation

`$ react-native link react-native-video-quickblox`

### Configure Project


#### iOS

1. Add Quickblox and Mantle
Add a `Podfile` to your ios directory with the following content. Then run `pod install` and open the generated .xcworkspace from now on in xcode.
```
source 'https://github.com/CocoaPods/Specs.git'
platform :ios, '9.0'

target 'YourProject' do
pod 'Quickblox-WebRTC', '~> 2.6.1'
pod 'QuickBlox', '~> 2.12'
pod 'Mantle', '~> 2.1.0'
end
```

2. Click on Project → Select Target of interest → Choose **Build Phases** tab → **Link Binary With Libraries** → At the bottom of this list hit + to add libraries.

Here is the list of required Apple library frameworks:
```
libicucore.dylib
libc++.dylib
libresolv.dylib
libxml2.dylib
libz.dylib
CFNetwork.framework
GLKit.framework
MobileCoreServices.framework
SystemConfiguration.framework
VideoToolbox.framework
Accelerate.framework
```

#### Android
Copy folder [jniLibs](https://github.com/ttdat89/react-native-video-quickblox/tree/master/example/TestLibQuickblox/android/app/src/main/jniLibs) in [https://github.com/ttdat89/react-native-video-quickblox/tree/master/example/TestLibQuickblox/android/app/src/main/](https://github.com/ttdat89/react-native-video-quickblox/tree/master/example/TestLibQuickblox/android/app/src/main/) to your Android project


## Usage
This [example](https://github.com/ttdat89/react-native-video-quickblox/tree/master/example/TestLibQuickblox) is a little bit messy, but it can help you (I think)
  
