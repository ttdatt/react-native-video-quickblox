//
//  QuickbloxRemoteVideoView.h
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import <QuickbloxWebRTC/QuickbloxWebRTC.h>
#import <React/RCTComponent.h>

@interface QuickbloxRemoteVideoView : QBRTCRemoteVideoView
@property (nonatomic, copy) RCTBubblingEventBlock onReceivedVideoTrack;
@end
