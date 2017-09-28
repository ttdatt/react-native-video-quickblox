/**
 * Created by admin on 2/9/17.
 */

import React from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  LayoutAnimation,
  TouchableHighlight,
  Image
} from 'react-native';


export default class Calling extends React.Component {
  render() {
    return (
      <View style={styles.container}>
        <View style={styles.callDetails}>
          <Image style={{height:60,width:60}} source={require('./assets/avatar.jpeg')}/>
          <View style={{marginTop: 20, marginBottom: 20}}/>
          <Text style={styles.name}>Call Request</Text>
          <TouchableHighlight
            onPress={() => {

            }}
            underlayColor={'transparent'}>
            <Text>Ringing...</Text>
          </TouchableHighlight>

        </View>
        <View style={styles.callButtonContainer}>
          <TouchableHighlight
            onPress={() => {
            }}
            underlayColor={'transparent'}>
            <View style={styles.buttonContainer}>
              <View style={[styles.iconContainer, {backgroundColor: 'red'}]}>
                <Image style={styles.icon}
                       source={require('./assets/decline_call_icon.png')}/>
              </View>
              <Text style={styles.iconTitle}>Decline</Text>
            </View>
          </TouchableHighlight>
        </View>
      </View>
    )
  }
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    padding: 20,
  },
  callDetails: {
    flex: 3,
    justifyContent: 'center',
    alignItems: 'center',
  },

  avatarContainer: {
    height: 100,
    width: 100,
    borderRadius: 50,
    marginBottom: 20
  },
  avatar: {
    height: 100,
    width: 100,
    borderRadius: 50,
  },
  name: {
    fontSize: 18
  },
  callButtonContainer: {
    flex: 1,
    justifyContent: 'space-around',
    alignItems: 'center',
    flexDirection: 'row'
  },
  iconContainer: {
    height: 60,
    width: 60,
    borderRadius: 30,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 10
  },
  buttonContainer: {
    alignItems: 'center',
  },
  icon: {
    height: 30,
    width: 30
  },
  iconTitle: {
    alignItems: 'center',
    fontSize: 15,
    alignSelf: 'center'
  }
});