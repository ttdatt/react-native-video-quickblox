/**
 * Created by Dat Tran on 9/27/17.
 */
import React from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  LayoutAnimation,
  TouchableOpacity,
  FlatList
} from 'react-native';
import QuickbloxManager from './QuickbloxManager';

export default class ContactList extends React.Component {

  constructor() {
    super()

    this.state = {
      user: 'null',
      users: []
    }
    this.quickbloxManager = new QuickbloxManager()
  }

  componentDidMount() {
    this.quickbloxManager.getUsers(users => {
      this.setState({users: JSON.parse(users)})
    })
  }

  renderListItem(item) {
    return <TouchableOpacity onPress={() => {
      this.quickbloxManager.callUsers([item.id], 1, 'Dat Tran', 'https://qph.ec.quoracdn.net/main-qimg-7ea75331d55c74f7e3c0815cca3e8b4a-c')
      this.props.callSuccess()
    }}>
      <View style={{flexDirection: 'row', height: 44, alignItems: 'center'}}>
        <Text>{item.id}</Text>
        <View style={{width: 40}}/>
        <Text>{item.login}</Text>
      </View>
    </TouchableOpacity>
  }

  render() {
    return <View>
      <Text>{`userId: ${this.props.currentUser}`}</Text>
      <FlatList
        keyboardShouldPersistTaps='always'
        style={{backgroundColor: 'white'}}
        data={this.state.users}
        keyExtractor={(item, index) => index}
        renderItem={({item, index}) => this.renderListItem(item, index)}/>
    </View>
  }
}
