/*
  React Native Speech Notification Plugin
  https://github.com/kn4rfy/react-native-speech-notification

  Created by FRANCIS KNARFY ELOPRE
  https://github.com/kn4rfy

  MIT License
*/

'use strict';

var { DeviceEventEmitter, NativeModules } = require('react-native');
const RNSpeechNotification = NativeModules.SpeechNotification;

var SpeechNotification = {
  speak: function(params) {
    RNSpeechNotification.speak(params);
  },
  notify: function(params) {
    RNSpeechNotification.notify(params);
  }
};

module.exports = SpeechNotification;
