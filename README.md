
# react-native-video-quickblox
Only work on Android, will implement on iOS in near future

## Getting started

`$ npm install react-native-video-quickblox --save`

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
Do nothing


## Usage
This [example](https://github.com/ttdat89/react-native-video-quickblox/tree/master/example/TestLibQuickblox) is a little bit messy, but it can help you (I think)
  
