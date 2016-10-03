//
//  SpeechNotificationDelegate.m
//  SpeechNotificationDelegate
//
//  Created by Francis Knarfy Elopre on 05/07/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//

#import "SpeechNotificationDelegate.h"

@implementation SpeechNotificationDelegate

-(instancetype)init
{
  self = [super init];
  if (self) {
    synthesizer = [AVSpeechSynthesizer new];
    synthesizer.delegate = self;
  }

  return self;
}

- (void)speechSynthesizer:(AVSpeechSynthesizer*)synthesizer didFinishSpeechUtterance:(AVSpeechUtterance*)utterance
{
  [[AVAudioSession sharedInstance] setActive:NO withOptions:0 error:nil];
  [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback withOptions: 0 error: nil];
  [[AVAudioSession sharedInstance] setActive:YES withOptions: 0 error:nil];
}

- (void)speak:(NSDictionary*)args
{
  [[AVAudioSession sharedInstance] setActive:NO withOptions:0 error:nil];
  [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback withOptions:AVAudioSessionCategoryOptionInterruptSpokenAudioAndMixWithOthers error:nil];

  [synthesizer stopSpeakingAtBoundary:AVSpeechBoundaryWord];

  NSString *message = [args objectForKey:@"message"];
  NSString *language = [args objectForKey:@"language"];

  if (!language || (id)language == [NSNull null]) {
    language = @"en-US";
  }

  AVSpeechUtterance *utterance = [AVSpeechUtterance speechUtteranceWithString:message];
  [utterance setVoice:[AVSpeechSynthesisVoice voiceWithLanguage:language]];
  [synthesizer speakUtterance:utterance];
}

- (void)notify:(NSDictionary*)args
{
  if ([UIApplication instancesRespondToSelector:@selector(registerUserNotificationSettings:)]) {
    [[UIApplication sharedApplication] registerUserNotificationSettings:[UIUserNotificationSettings settingsForTypes:UIUserNotificationTypeAlert|UIUserNotificationTypeBadge|UIUserNotificationTypeSound categories:nil]];
  }

  [[AVAudioSession sharedInstance] setActive:NO withOptions:0 error:nil];
  [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback withOptions:AVAudioSessionCategoryOptionInterruptSpokenAudioAndMixWithOthers error:nil];

  [synthesizer stopSpeakingAtBoundary:AVSpeechBoundaryWord];

  NSString *message = [args objectForKey:@"message"];
  NSString *language = [args objectForKey:@"language"];

  if (!language || (id)language == [NSNull null]) {
    language = @"en-US";
  }

  // UIApplicationState applicationState = [[UIApplication sharedApplication] applicationState];

  UILocalNotification *notification = [[UILocalNotification alloc]init];
  [notification setAlertBody:message];

  AVSpeechUtterance *utterance = [AVSpeechUtterance speechUtteranceWithString:message];
  [utterance setVoice:[AVSpeechSynthesisVoice voiceWithLanguage:language]];

  // if (applicationState == UIApplicationStateActive) {
  //   [synthesizer speakUtterance:utterance];
  // }
  // else{
    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);
    [[UIApplication sharedApplication] setScheduledLocalNotifications:[NSArray arrayWithObject:notification]];
    [synthesizer speakUtterance:utterance];
  // }
}

@end
