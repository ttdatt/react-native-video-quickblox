//
//  QuickbloxLocalVideoView.h
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

@interface QuickbloxLocalVideoView : UIView
@property (nonatomic, weak) AVCaptureVideoPreviewLayer *previewLayer;

@end
