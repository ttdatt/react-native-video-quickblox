//
//  QuickbloxLocalVideoView.m
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import "QuickbloxLocalVideoView.h"

@implementation QuickbloxLocalVideoView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (void)layoutSubviews {
  [super layoutSubviews];
  
  self.previewLayer.frame = self.bounds;
}

@end
