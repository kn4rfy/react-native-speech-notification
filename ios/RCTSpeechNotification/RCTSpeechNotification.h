//
//  RCTSpeechNotification.h
//  RCTSpeechNotification
//
//  Created by Francis Knarfy Elopre on 05/07/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//


#import "RCTBridgeModule.h"
#import "SpeechNotificationDelegate.h"

@interface RCTSpeechNotification : NSObject <RCTBridgeModule>

@property (nonatomic, strong) SpeechNotificationDelegate* speechDelegate;

@end
