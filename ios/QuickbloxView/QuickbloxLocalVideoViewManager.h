//
//  QuickbloxLocalVideoViewManager.h
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import <UIKit/UIKit.h>
#import <React/RCTViewManager.h>

#import <SystemConfiguration/SystemConfiguration.h>
#import <MobileCoreServices/MobileCoreServices.h>
#import <Quickblox/Quickblox.h>
#import <QuickbloxWebRTC/QuickbloxWebRTC.h>

@interface QuickbloxLocalVideoViewManager : RCTViewManager
@property (strong, nonatomic) QBRTCCameraCapture *videoCapture;
- (void)attachLocalCameraStream:(QBRTCSession *)session;
@end
