//
//  QuickbloxHandler.h
//  RNQuickblox
//
//  Created by dat tran on 9/29/17.
//  Copyright © 2017 Dat Tran. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Quickblox/Quickblox.h>
#import <QuickbloxWebRTC/QuickbloxWebRTC.h>
#import "RNQuickblox.h"
#import "QuickbloxLocalVideoViewManager.h"

@protocol CustomQuickbloxDelegate <NSObject>
- (void)session:(QBRTCSession *)session receivedRemoteVideoTrack:(QBRTCVideoTrack *)videoTrack fromUser:(NSNumber *)userID;
@end

@interface QuickbloxHandler : NSObject <QBRTCClientDelegate>
@property (weak, nonatomic) RNQuickblox *quickbloxClient;
@property (weak, nonatomic) id<CustomQuickbloxDelegate> quickbloxDelegate;
@property (weak, nonatomic) QuickbloxLocalVideoViewManager *localViewManager;
@property (strong, nonatomic) QBRTCSession *session;
@property (strong, nonatomic) NSNumber *caller;
@property (strong, nonatomic) QBUUser *currentUser;

+ (QuickbloxHandler *)sharedInstance;

- (void)doNothing;
- (void)startCall:(NSArray<NSNumber *> *)userIDs callRequestId:(NSNumber *)callRequestId realName:(NSString *)realName avatar:(NSString *)avatar;
@end
