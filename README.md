
# react-native-quickblox

## Getting started

`$ npm install react-native-quickblox --save`

### Mostly automatic installation

`$ react-native link react-native-quickblox`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-quickblox` and add `RNQuickblox.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNQuickblox.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.sts.RNQuickblox.RNQuickbloxPackage;` to the imports at the top of the file
  - Add `new RNQuickbloxPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-quickblox'
  	project(':react-native-quickblox').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-quickblox/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-quickblox')
  	```


## Usage
```javascript
import RNQuickblox from 'react-native-quickblox';

// TODO: What to do with the module?
RNQuickblox;
```
  