//
//  SerializableQBUser.h
//  RNQuickblox
//
//  Created by dat tran on 9/29/17.
//  Copyright © 2017 Dat Tran. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Mantle/Mantle.h>
#import <Quickblox/Quickblox.h>

@interface SerializableQBUser : MTLModel <MTLJSONSerializing>
@property (assign, nonatomic) NSUInteger ID;
@property (strong, nonatomic) NSString *email;
@property (strong, nonatomic) NSString *fullName;
@property (strong, nonatomic) NSString *login;

- (instancetype)initWithQBUUser:(QBUUser *)user;
@end
