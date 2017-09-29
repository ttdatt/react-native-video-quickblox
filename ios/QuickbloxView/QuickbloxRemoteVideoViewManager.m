//
//  QuickbloxRemoteVideoViewManager.m
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import "QuickbloxRemoteVideoViewManager.h"

@implementation QuickbloxRemoteVideoViewManager
RCT_EXPORT_MODULE()

#pragma mark - React Native Properties
RCT_EXPORT_VIEW_PROPERTY(onReceivedVideoTrack, RCTBubblingEventBlock)

- (UIView *)view {
  QuickbloxRemoteVideoView *view = [[QuickbloxRemoteVideoView alloc] initWithFrame:CGRectMake(0, 0, 200, 200)];
  view.videoGravity = AVLayerVideoGravityResizeAspectFill;
  [QuickbloxHandler sharedInstance].quickbloxDelegate = self;
  
  self.videoView = view;
  if (QuickbloxHandler.sharedInstance.caller) {
    RCTLogInfo(@"Set Remote Video Track with %@ when create view", QuickbloxHandler.sharedInstance.caller);
    QBRTCVideoTrack *track = [QuickbloxHandler.sharedInstance.session remoteVideoTrackWithUserID:QuickbloxHandler.sharedInstance.caller];
    [self.videoView setVideoTrack:track];
  }
  return view;
}

- (void)session:(QBRTCSession *)session receivedRemoteVideoTrack:(QBRTCVideoTrack *)videoTrack fromUser:(NSNumber *)userID {
  self.videoTrack = [session remoteVideoTrackWithUserID:userID];
  
  dispatch_async(dispatch_get_main_queue(), ^{
    RCTLogInfo(@"Set Remote Video Track with %@ when received video track", QuickbloxHandler.sharedInstance.caller);
    [self.videoView setVideoTrack:videoTrack];
  });
  
  if (self.videoView.onReceivedVideoTrack) {
    self.videoView.onReceivedVideoTrack(nil);
  }
}

@end
