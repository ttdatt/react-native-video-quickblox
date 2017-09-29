//
//  QuickbloxLocalVideoViewManager.m
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import "QuickbloxLocalVideoViewManager.h"
#import "QuickbloxLocalVideoView.h"
#import "QuickbloxHandler.h"

#import <React/RCTLog.h>

@implementation QuickbloxLocalVideoViewManager
RCT_EXPORT_MODULE()

- (instancetype)init {
  self = [super init];
  if (self) {
    [QuickbloxHandler sharedInstance].localViewManager = self;
  }
  return self;
}

#pragma mark - React Native Properties
RCT_CUSTOM_VIEW_PROPERTY(stopCamera, BOOL, QuickbloxLocalVideoView) {
  [self.videoCapture stopSession:^{
    RCTLogInfo(@"stop camera neeeeeeeeeee");
  }];
}

- (UIView *)view {
  QuickbloxLocalVideoView *localVideoView = [[QuickbloxLocalVideoView alloc] initWithFrame:CGRectMake(0, 0, 0, 0)];
  
  QBRTCVideoFormat *videoFormat = [[QBRTCVideoFormat alloc] init];
  videoFormat.frameRate = 30;
  videoFormat.pixelFormat = QBRTCPixelFormat420f;
  videoFormat.width = 640;
  videoFormat.height = 480;
  
  // QBRTCCameraCapture class used to capture frames using AVFoundation APIs
  self.videoCapture = [[QBRTCCameraCapture alloc] initWithVideoFormat:videoFormat position:AVCaptureDevicePositionFront]; // or AVCaptureDevicePositionBack
  
  // add video capture to session's local media stream
  // from version 2.3 you no longer need to wait for 'initializedLocalMediaStream:' delegate to do it
  
  if (QuickbloxHandler.sharedInstance.session)
    QuickbloxHandler.sharedInstance.session.localMediaStream.videoTrack.videoCapture = self.videoCapture;
  
  self.videoCapture.previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill;
  self.videoCapture.previewLayer.frame = localVideoView.bounds;
  [self.videoCapture startSession:nil];
  
  [localVideoView.layer insertSublayer:self.videoCapture.previewLayer atIndex:0];
  localVideoView.previewLayer = self.videoCapture.previewLayer;
  
  return localVideoView;
}

- (void)attachLocalCameraStream:(QBRTCSession *)session {
  session.localMediaStream.videoTrack.videoCapture = self.videoCapture;
}

@end
