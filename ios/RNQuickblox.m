//
//  RNQuickblox.h
//  RNQuickblox
//
//  Created by dat tran on 1/11/17.
//  Copyright © 2017 Dat Tran. All rights reserved.

#import "RNQuickblox.h"
#import "QuickbloxHandler.h"

@implementation RNQuickblox

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

- (instancetype)init {
    self = [super init];
    if (self) {
        [QuickbloxHandler sharedInstance].quickbloxClient = self;
        //    [QBSettings setAutoReconnectEnabled:YES];
    }
    return self;
}

- (NSArray<NSString *> *)supportedEvents {
    return @[DID_RECEIVE_CALL_SESSION,
             USER_ACCEPT_CALL,
             USER_REJECT_CALL,
             USER_HUNG_UP,
             SESSION_DID_CLOSE];
}

- (NSDictionary *)constantsToExport {
    return @{DID_RECEIVE_CALL_SESSION: DID_RECEIVE_CALL_SESSION,
             USER_ACCEPT_CALL: USER_ACCEPT_CALL,
             USER_REJECT_CALL: USER_REJECT_CALL,
             USER_HUNG_UP: USER_HUNG_UP,
             SESSION_DID_CLOSE: SESSION_DID_CLOSE};
}

#pragma mark - React Export Method

RCT_EXPORT_METHOD(setupQuickblox:(NSString *)appId authKey:(NSString* )authKey authSecret:(NSString *)authSecret accountKey:(NSString *)accountKey) {
    [QBSettings setApplicationID:[appId integerValue]];
    [QBSettings setAuthKey:authKey];
    [QBSettings setAuthSecret:authSecret];
    [QBSettings setAccountKey:accountKey];
}

RCT_EXPORT_METHOD(connectUser:(NSString *)userID password:(NSString *)pwd completion:(RCTResponseSenderBlock)callback) {
    [self login:userID pwd:pwd completion:callback];
}

RCT_EXPORT_METHOD(signUp:(NSString *)userName pwd:(NSString *)password realName:(NSString *)realName email:(NSString *)email completion:(RCTResponseSenderBlock)completion) {
    QBUUser *newUser = [QBUUser user];
    newUser.login = userName;
    newUser.password = password;
    newUser.email = email;
    newUser.fullName = realName;
    
    [QBRequest signUp:newUser successBlock:^(QBResponse * _Nonnull response, QBUUser * _Nullable user) {
        QuickbloxHandler.sharedInstance.currentUser = user;
        [self login:userName pwd:password completion:completion];
    } errorBlock:^(QBResponse * _Nonnull response) {
        if (response.status == QBResponseStatusCodeValidationFailed) {
            [self login:userName pwd:password completion:completion];
        }
        else {
            NSError *error = response.error.error;
            completion(@[error ? error : [NSNull null]]);
        }
    }];
}

RCT_EXPORT_METHOD(callToUsers:(NSArray<NSNumber *> *)userIDs callRequestId:(nonnull NSNumber *)callRequestId realName:(NSString *)realName avatar:(NSString *)avatar) {
    [[QuickbloxHandler sharedInstance] startCall:userIDs callRequestId:callRequestId realName:realName avatar:avatar];
}

RCT_EXPORT_METHOD(acceptCall) {
    NSDictionary *userInfo = @{ @"key" : @"value" };
    [[QuickbloxHandler sharedInstance].session acceptCall:userInfo];
}

RCT_EXPORT_METHOD(hangUp) {
    NSDictionary *userInfo = @{ @"key" : @"value" };
    [[QuickbloxHandler sharedInstance].session hangUp:userInfo];
    [QuickbloxHandler sharedInstance].session = nil;
}

RCT_EXPORT_METHOD(rejectCall) {
    NSDictionary *userInfo = @{ @"key" : @"value" };
    [[QuickbloxHandler sharedInstance].session rejectCall:userInfo];
    [QuickbloxHandler sharedInstance].session = nil;
}

- (void)receiveCallSession:(QBRTCSession *)session userId:(NSNumber *)userId {
    [self sendEventWithName:DID_RECEIVE_CALL_SESSION body:@{@"userId": userId}];
}

- (void)userAcceptCall:(NSNumber *)userId {
    [self sendEventWithName:USER_ACCEPT_CALL body:@{@"": @""}];
}

- (void)userRejectCall:(NSNumber *)userId {
    [self sendEventWithName:USER_REJECT_CALL body:@{@"": @""}];
}

- (void)userHungUp:(NSNumber *)userId {
    [self sendEventWithName:USER_HUNG_UP body:@{@"": @""}];
}

- (void)sessionDidClose:(QBRTCSession *)session {
    [self sendEventWithName:SESSION_DID_CLOSE body:@{@"": @""}];
}

- (void)login:(NSString *)userName pwd:(NSString *)pwd completion:(RCTResponseSenderBlock)completion {
    [QBRequest logInWithUserLogin:userName password:pwd successBlock:^(QBResponse * _Nonnull response, QBUUser * _Nullable user) {
        user.password = pwd;
        [[QBChat instance] connectWithUser:user completion:^(NSError * _Nullable error) {
            if (!error) {
                QuickbloxHandler.sharedInstance.currentUser = user;
            }
            completion(@[error ? error : @(user.ID)]);
        }];
    } errorBlock:^(QBResponse * _Nonnull response) {
        NSError *error = response.error.error;
        completion(@[error ? error : [NSNull null]]);
    }];
}

RCT_EXPORT_METHOD(getUsers:(RCTResponseSenderBlock)complete) {
    QBGeneralResponsePage *pageRequest = [QBGeneralResponsePage responsePageWithCurrentPage:1 perPage:50];
    [QBRequest usersForPage:pageRequest successBlock:^(QBResponse * _Nonnull response, QBGeneralResponsePage * _Nonnull page, NSArray<QBUUser *> * _Nonnull users) {
        NSMutableArray *arr = [NSMutableArray arrayWithCapacity:users.count];
        NSError *error = nil;
        for (int i = 0; i<users.count; i++) {
            SerializableQBUser *u = [[SerializableQBUser alloc] initWithQBUUser:users[i]];
            [arr addObject:u];
        }
        NSArray *result = [MTLJSONAdapter JSONArrayFromModels:arr error:&error];
        complete(@[result]);
    } errorBlock:^(QBResponse * _Nonnull response) {
        
    }];
}

@end
  
