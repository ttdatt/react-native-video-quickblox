//
//  QuickbloxHandler.m
//  RNQuickblox
//
//  Created by dat tran on 9/29/17.
//  Copyright © 2017 Dat Tran. All rights reserved.
//

#import "QuickbloxHandler.h"
#import <React/RCTLog.h>
#import "QMSoundManager.h"
#import "QBAVCallPermissions.h"

@implementation QuickbloxHandler
- (instancetype)init
{
    self = [super init];
    if (self) {
        //    [QBRTCConfig setAnswerTimeInterval:60];
        //    [QBRTCConfig setDialingTimeInterval:5];
        //    [QBRTCConfig setStatsReportTimeInterval:1.f];
        
        // Initialize Quickblox WebRTC
        [QBRTCClient initializeRTC];
        
        //    [QBRTCConfig setMediaStreamConfiguration:[QBRTCMediaStreamConfiguration defaultConfiguration]];
        
        [[QBRTCClient instance] addDelegate:self];
        [QBSettings setAutoReconnectEnabled:YES];
        
        // Use bottom microphone if possible
        AVAudioSessionPortDescription *port = [AVAudioSession sharedInstance].availableInputs[0];
        for (AVAudioSessionDataSourceDescription *source in port.dataSources) {
            if ([source.dataSourceName isEqualToString:@"Bottom"])
                [port setPreferredDataSource:source error:nil];
        }
        
        [QBAVCallPermissions checkPermissionsWithConferenceType:QBRTCConferenceTypeVideo completion:^(BOOL granted) {
        }];
        
        // Use device speaker
        [QBRTCAudioSession.instance initialize];
        QBRTCAudioSession.instance.currentAudioDevice = QBRTCAudioDeviceSpeaker;
        //    [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback error:nil];
    }
    return self;
}

+ (id)sharedInstance {
    static QuickbloxHandler *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[self alloc] init];
    });
    return sharedInstance;
}

- (void)doNothing {
    
}

- (void)startCall:(NSArray<NSNumber *> *)userIDs callRequestId:(NSNumber *)callRequestId realName:(NSString *)realName avatar:(NSString *)avatar {
    //  NSArray *opponentsIDs = @[@22606048, @22644894];
    RCTLogInfo(@"start call user %@ %@", userIDs, realName);
    QBRTCSession *newSession = [QBRTCClient.instance createNewSessionWithOpponents:userIDs
                                                                withConferenceType:QBRTCConferenceTypeVideo];
    self.session = newSession;
    RCTLogInfo(@"start call with session %@", self.session);
    [self.localViewManager attachLocalCameraStream:self.session];
    NSDictionary *userInfo = @{ @"callRequestId": callRequestId,
                                @"sessionId": self.session.ID,
                                @"realName": realName,
                                @"avatar": avatar};
    [newSession startCall:userInfo];
    
    [QMSoundManager playCallingSound];
}

#pragma mark - Quickblox Delegate

- (void)didReceiveNewSession:(QBRTCSession *)session userInfo:(NSDictionary<NSString *,NSString *> *)userInfo {
    RCTLogInfo(@"did Receive New Session");
    NSDictionary *userInfo1 = @{ @"key" : @"value" };
    if (self.session && [session.ID isEqualToString:self.session.ID]) {
        [session rejectCall:userInfo1];
        return;
    }
    self.session = session;
    RCTLogInfo(@"receive call with session %@", self.session);
    //  self.session.localMediaStream.videoTrack.enabled = 1;
    [self.localViewManager attachLocalCameraStream:self.session];
    
    [self.quickbloxClient receiveCallSession:session userId:@([userInfo[@"userId"] integerValue])];
}

- (void)session:(QBRTCSession *)session didChangeState:(QBRTCSessionState)state {
    
}

- (void)session:(QBRTCSession *)session receivedRemoteVideoTrack:(QBRTCVideoTrack *)videoTrack fromUser:(NSNumber *)userID {
    [self.quickbloxDelegate session:session receivedRemoteVideoTrack:videoTrack fromUser:userID];
    self.caller = userID;
}

- (void)session:(QBRTCSession *)session startedConnectingToUser:(NSNumber *)userID {
    RCTLogInfo(@"startedConnectingToUser %@", userID);
}

- (void)session:(QBRTCSession *)session connectedToUser:(NSNumber *)userID {
    RCTLogInfo(@"connectedToUser %@", userID);
}

- (void)session:(QBRTCSession *)session disconnectedFromUser:(NSNumber *)userID {
    RCTLogInfo(@"disconnectedFromUser %@", userID);
}

- (void)session:(QBRTCSession *)session connectionClosedForUser:(NSNumber *)userID {
    RCTLogInfo(@"connectionClosedForUser %@", userID);
}

- (void)session:(QBRTCSession *)session userDidNotRespond:(NSNumber *)userID {
    RCTLogInfo(@"userDidNotRespond %@", userID);
}

- (void)session:(QBRTCSession *)session connectionFailedForUser:(NSNumber *)userID {
    RCTLogInfo(@"connectionFailedForUser %@", userID);
}

- (void)sessionDidClose:(QBRTCSession *)session {
    [self.quickbloxClient sessionDidClose:session];
    self.session = nil;
}

- (void)session:(QBRTCSession *)session acceptedByUser:(NSNumber *)userID userInfo:(NSDictionary<NSString *,NSString *> *)userInfo {
    [self.quickbloxClient userAcceptCall:userID];
}

- (void)session:(QBRTCSession *)session rejectedByUser:(NSNumber *)userID userInfo:(NSDictionary<NSString *,NSString *> *)userInfo {
    [self.quickbloxClient userRejectCall:userID];
}

- (void)session:(QBRTCSession *)session hungUpByUser:(NSNumber *)userID userInfo:(NSDictionary<NSString *,NSString *> *)userInfo {
    [self.quickbloxClient userHungUp:userID];
}
@end
