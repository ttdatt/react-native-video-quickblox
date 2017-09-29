//
//  RNQuickblox.h
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import <Foundation/Foundation.h>

#import <React/RCTBridgeModule.h>
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTEventEmitter.h>

#import <Quickblox/Quickblox.h>
#import <QuickbloxWebRTC/QuickbloxWebRTC.h>

#import "SerializableQBUser.h"

#define DID_RECEIVE_CALL_SESSION @"DID_RECEIVE_CALL_SESSION"
#define USER_ACCEPT_CALL @"USER_ACCEPT_CALL"
#define USER_REJECT_CALL @"USER_REJECT_CALL"
#define USER_HUNG_UP @"USER_HUNG_UP"
#define SESSION_DID_CLOSE @"SESSION_DID_CLOSE"

@interface RNQuickblox : RCTEventEmitter <RCTBridgeModule>
- (void)receiveCallSession:(QBRTCSession *)session userId:(NSNumber *)userId;
- (void)userAcceptCall:(NSNumber *)userId;
- (void)userRejectCall:(NSNumber *)userId;
- (void)userHungUp:(NSNumber *)userId;
- (void)sessionDidClose:(QBRTCSession *)session;
@end
  
