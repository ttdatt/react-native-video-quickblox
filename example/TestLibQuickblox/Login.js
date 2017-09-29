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

export default class Login extends React.Component {

  constructor() {
    super()
    this.state = {
      userName: '',
      password: '',
      user: null
    }

    this.quickbloxManager = new QuickbloxManager()
  }

  login = () => {
    this.quickbloxManager.login(this.state.userName, this.state.password, (qbId) => {
      this.setState({user: qbId})
    })
  }

  render() {
    return !this.state.user ? <View style={{paddingTop: 20}}>
        <TextInput
          placeholder='Login name'
          underlineColorAndroid='transparent'
          onChangeText={(text) => this.setState({userName: text})}/>
        <TextInput
          placeholder='Password'
          underlineColorAndroid='transparent'
          onChangeText={(text) => this.setState({password: text})}/>
        <Button onPress={() => this.login()}
                title="Learn More"/>
      </View>
      : <ContactList currentUser={this.state.user}
                     callSuccess={this.props.callSuccess}/>
  }
}