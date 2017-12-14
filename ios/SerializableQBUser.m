//
//  SerializableQBUser.m
//  RNQuickblox
//
//  Created by dat tran on 9/29/17.
//  Copyright © 2017 Dat Tran. All rights reserved.
//

#import "SerializableQBUser.h"

@implementation SerializableQBUser

- (instancetype)initWithQBUUser:(QBUUser *)user {
    self = [super init];
    if(self != nil) {
        self.ID = user.ID;
        self.email = user.email;
        self.fullName = user.fullName;
        self.login = user.login;
    }
    return self;
}

+ (NSDictionary *)JSONKeyPathsByPropertyKey {
    return @{@"ID": @"id",
             @"email": @"email",
             @"login": @"login",
             @"fullName": @"fullName"
             };
}

@end
