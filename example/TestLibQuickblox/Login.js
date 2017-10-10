/**
 * Created by Dat Tran on 9/28/17.
 */
import React, {Component} from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text, TouchableHighlight, Button,
  View, Platform, TextInput
} from 'react-native';
import QuickbloxManager from './QuickbloxManager';
import ContactList from './ContactList';
import IndicatorDialog from './IndicatorDialog'

export default class Login extends React.Component {

  constructor() {
    super()
    this.state = {
      userName: '',
      password: '12345678',
      user: null,
      waiting: false
    }

    this.quickbloxManager = new QuickbloxManager()
  }

  login = () => {
    this.setState({waiting: true})
    this.quickbloxManager.login(this.state.userName, this.state.password, (qbId) => {
      this.setState({waiting: false, user: qbId})
    })
  }

  render() {
    return !this.state.user ? <View style={{paddingTop: 20}}>
        <Text>login name: dat.tran or phuc.tran - password: 12345678</Text>
        <TextInput
          placeholder='Login name'
          underlineColorAndroid='transparent'
          onChangeText={(text) => this.setState({userName: text})}/>
        <TextInput
          placeholder='Password'
          underlineColorAndroid='transparent'
          onChangeText={(text) => this.setState({password: text})}/>
        <Button onPress={() => this.login()}
                title="Login"/>
        {this.state.waiting && <IndicatorDialog message='Please wait'/>}
      </View>
      : <ContactList currentUser={this.state.user}
                     callSuccess={this.props.callSuccess}/>
  }
}