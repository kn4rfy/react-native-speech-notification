# React Native Speech Notification - iOS and Android
React Native Speech Notification is Text-to-Speech as a notification library for iOS and Android



##Documentation

-[Installation](https://github.com/kn4rfy/react-native-speech-notification#installation)
..-[Automatic Linking](https://github.com/kn4rfy/react-native-speech-notification#automatic-linking)
..-[Manual Linking](https://facebook.github.io/react-native/docs/linking-libraries-ios.html#manual-linking)
-[Usage](https://github.com/kn4rfy/react-native-speech-notification#usage)
-[About](https://github.com/kn4rfy/react-native-speech-notification#about)
-[License](https://github.com/kn4rfy/react-native-speech-notification#license)



## Installation
### Step 1
Install react-native-speech-notification
```sh
npm install https://github.com/kn4rfy/react-native-speech-notification --save
```
Note: --save or --save-dev flag is very important for this step. rnpm will link your libs based on dependencies and devDependencies in your package.json file.

### Automatic linking
### Step 2
"rnpm" is a community project that allows linking of native dependencies automatically:
Install rnpm
```sh
npm install rnpm -g
```
Note: rnpm requires node version 4.1 or higher

### Step 3
Link your native dependencies:
```sh
rnpm link
```
Done! All libraries with a native dependencies should be successfully linked to your iOS/Android project.

### Manual linking
Manual linking can be found at [React Native Docs](https://facebook.github.io/react-native/docs/linking-libraries-ios.html#manual-linking)



## Usage
```javascript
import SpeechNotification from "react-native-speech-notification";
```
Using the Text-to-Speech only
```javascript
SpeechNotification.speak({
  message: this.state.message,
  language: this.state.language
});
```
Using Text-to-Speech as notification
```javascript
SpeechNotification.notify({
  title: 'Title',
  icon: 'icon', // {icon}.png/.jpg must be present in each corresponding android/app/src/main/res/drawable-*dpi/ folders
  message: this.state.message,
  language: this.state.language
});
```

#### Reminder: icon works only for Android.
{icon}.png/.jpg must be present in each corresponding android/app/src/main/res/drawable-*dpi/ folders



## About
This library is an optimized port of [Cordova Text-to-Speech Notification Plugin](https://github.com/kn4rfy/cordova-plugin-tts-notification)



## License
The MIT License (MIT)

Copyright (c) 2016 Francis Knarfy Elopre

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
