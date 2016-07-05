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
  speak: function(params, onfulfilled, onrejected) {
      var options = {};

      if (typeof params == 'string') {
          options.params = params;
      } else {
          options = params;
      }

      RCTSpeechNotification.speak(successFn, errorFn, [options]);
  },

  notify: function(params, onfulfilled, onrejected) {
      var options = {};

      if (typeof params == 'string') {
          options.params = params;
      } else {
          options = params;
      }

      RCTSpeechNotification.notify(successFn, errorFn, [options]);
  }
};

module.exports = SpeechNotification;
