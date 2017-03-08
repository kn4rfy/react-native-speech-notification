//
//  RCTSpeechNotification.m
//  RCTSpeechNotification
//
//  Created by Francis Knarfy Elopre on 05/07/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//

#import "RCTSpeechNotification.h"
#import <React/RCTLog.h>

@implementation RCTSpeechNotification

@synthesize speechNotificationDelegate;

RCT_EXPORT_MODULE();

-(instancetype)init
{
  self = [super init];
  if (self) {
    speechNotificationDelegate = [[SpeechNotificationDelegate alloc] init];
  }

  return self;
}

RCT_EXPORT_METHOD(speak:(NSDictionary *)args)
{
  RCTLogInfo(@"RCTSpeechNotification #speak");
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    [speechNotificationDelegate speak:args];
  });
}

RCT_EXPORT_METHOD(notify:(NSDictionary *)args)
{
  RCTLogInfo(@"RCTSpeechNotification #notify");
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    [speechNotificationDelegate notify:args];
  });
}

@end
