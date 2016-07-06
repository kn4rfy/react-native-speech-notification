//
//  RCTSpeechNotification.m
//  RCTSpeechNotification
//
//  Created by Francis Knarfy Elopre on 05/07/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//

#import "RCTSpeechNotification.h"
#import "RCTLog.h"
#import "RCTBridge.h"
#import "RCTEventDispatcher.h"
#import "RCTConvert.h"

@implementation RCTSpeechNotification

@synthesize bridge = _bridge;
@synthesize speechDelegate;

RCT_EXPORT_MODULE();


-(instancetype)init
{
  self = [super init];
  if (self) {
    speechDelegate = [[SpeechNotificationDelegate alloc] init];
  }

  return self;
}

RCT_EXPORT_METHOD(speak:(NSDictionary *)params)
{
  RCTLogInfo(@"RCTSpeechNotification #speak");
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    [speechDelegate speak:params];
  });
}

@end
