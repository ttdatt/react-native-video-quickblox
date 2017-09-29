//
//  QuickbloxRemoteVideoViewManager.h
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import <Foundation/Foundation.h>
#import <React/RCTViewManager.h>

#import <Quickblox/Quickblox.h>
#import <QuickbloxWebRTC/QuickbloxWebRTC.h>

#import "QuickbloxHandler.h"
#import "QuickbloxRemoteVideoView.h"

@interface QuickbloxRemoteVideoViewManager : RCTViewManager <CustomQuickbloxDelegate>
@property (weak, nonatomic) QuickbloxRemoteVideoView *videoView;
//@property (weak, nonatomic) QBRTCRemoteVideoView *remoteView;
@property (strong, nonatomic) QBRTCVideoTrack *videoTrack;
@end
