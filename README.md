
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

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.sts.RNQuickblox.RNQuickbloxPackage;` to the imports at the top of the file
  - Add `new RNQuickbloxPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-video-quickblox'
  	project(':react-native-video-quickblox').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-video-quickblox/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-video-quickblox')
  	```


## Usage
This [example](https://github.com/ttdat89/react-native-video-quickblox/tree/master/example/TestLibQuickblox) is a little bit messy, but it can help you (I think)
  
