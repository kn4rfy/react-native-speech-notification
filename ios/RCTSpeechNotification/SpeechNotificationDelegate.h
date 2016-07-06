//
//  SpeechNotificationDelegate.h
//  SpeechNotificationDelegate
//
//  Created by Francis Knarfy Elopre on 05/07/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>
#import <AudioToolbox/AudioServices.h>

@interface SpeechNotificationDelegate : NSObject <AVSpeechSynthesizerDelegate>
{
  AVSpeechSynthesizer* synthesizer;
}

- (void)speak:(NSDictionary*)params;
@end
