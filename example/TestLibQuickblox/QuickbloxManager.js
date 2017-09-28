/**
 * Created by Dat Tran on 2/10/17.
 */
import React from 'react'
import {NativeModules, NativeEventEmitter} from 'react-native'

export const DID_RECEIVE_CALL_SESSION = 'DID_RECEIVE_CALL_SESSION'
export const USER_ACCEPT_CALL = 'USER_ACCEPT_CALL'
export const USER_REJECT_CALL = 'USER_REJECT_CALL'
export const USER_HUNG_UP = 'USER_HUNG_UP'
export const SESSION_DID_CLOSE = 'SESSION_DID_CLOSE'

import RNQuickblox from 'react-native-video-quickblox'

const QuickbloxModule = new NativeEventEmitter(RNQuickblox);

let instance = null;

export default class QuickbloxManager {
  constructor(subscriber) {
    if (!instance) {
      instance = this;
      this.subscriber = subscriber
      this.registerEvents()
    }

    return instance;
  }

  // static get instance() {
  //     return new QuickbloxManager();
  // }

  init() {

  }

  getUsers(complete) {
    RNQuickblox.getUsers(complete)
  }

  login(userName, password, complete) {
    RNQuickblox.connectUser(userName, password, qbId => {
      if (complete)
        complete(qbId)
    })
  }

  signUp(userName, password, realName, email, complete) {
    RNQuickblox.signUp(userName, password, realName, email, () => {
      if (complete)
        complete({userName, password})
    })
  }

  hangUp() {
    RNQuickblox.hangup()
  }

  callUsers(userIds: Array, callId, realName, avatar) {
    RNQuickblox.callToUsers(userIds, callId, realName, avatar)
  }

  registerEvents() {
    QuickbloxModule.addListener(
      RNQuickblox.DID_RECEIVE_CALL_SESSION, this.receiveCall.bind(this));
    QuickbloxModule.addListener(
      RNQuickblox.USER_ACCEPT_CALL, this.userAcceptCall.bind(this));
    QuickbloxModule.addListener(
      RNQuickblox.USER_REJECT_CALL, this.userRejectCall.bind(this));
    QuickbloxModule.addListener(
      RNQuickblox.SESSION_DID_CLOSE, this.sessionDidClose.bind(this));
    QuickbloxModule.addListener(
      RNQuickblox.USER_HUNG_UP, this.hungUp.bind(this));
  }

  receiveCall() {
    this.subscriber.receiveCall()
    RNQuickblox.acceptCall()
    // store.dispatch({type: SET_IN_COMING_CALL_MODAL})
  }

  userAcceptCall() {
    console.log('userAcceptCall')
    // this.store.dispatch({type: USER_ACCEPT_CALL})
  }

  userRejectCall() {
    console.log('userRejectCall')
    // this.store.dispatch({type: USER_REJECT_CALL})
  }

  sessionDidClose() {
    console.log('sessionDidClose')
    // this.store.dispatch({type: SESSION_DID_CLOSE})
  }

  hungUp() {
    console.log('hungUp')
    // this.store.dispatch({type: USER_HUNG_UP})
  }
}