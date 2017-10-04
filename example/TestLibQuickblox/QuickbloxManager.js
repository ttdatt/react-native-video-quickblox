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
  constructor() {
    if (!instance) {
      instance = this;
      this.subscriber = []
      this.registerEvents()
    }

    return instance;
  }

  init() {
    // RNQuickblox.setupQuickblox(APP_ID, AUTH_KEY, AUTH_SECRET, ACCOUNT_KEY)
    RNQuickblox.setupQuickblox("44519","YqHTqrJPDkAzht3", "fgYy8K3hL6LKHaS","6XDmKdXBfwPuJsWv9Fxp")
  }

  addSubscriber(subscriber) {
    this.subscriber.push(subscriber)
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
      RNQuickblox.USER_HUNG_UP, this.userHungUp.bind(this));
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
    RNQuickblox.hangUp()
  }

  callUsers(userIds, callId, realName, avatar) {
    RNQuickblox.callToUsers(userIds, callId, realName, avatar)
  }

  acceptCall() {
    RNQuickblox.acceptCall()
  }

  receiveCall() {
    this.subscriber.forEach(sub => sub.receiveCall())

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

  userHungUp() {
    console.log('hangup')
    // this.store.dispatch({type: USER_HUNG_UP})
  }
}