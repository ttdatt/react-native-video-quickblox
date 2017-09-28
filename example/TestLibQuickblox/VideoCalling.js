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
  Image,
  Platform
} from 'react-native';
import {QuickbloxLocalVideoView, QuickbloxRemoteVideoView} from 'react-native-video-quickblox'

export default class VideoCalling extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      bottom: 1
    }
  }

  render() {
    return (
      <View style={styles.container}>
        {/*<Text style={styles.iconTitle}>{setTime(this.props.callingTime)}</Text>*/}
        <QuickbloxRemoteVideoView style={styles.callDetails}>
          {Platform.OS === 'android' ? <QuickbloxLocalVideoView
              style={[styles.userVideo, {bottom: this.state.bottom}]}
              onRendered={() => this.setState({bottom: 0})}/>
            : <QuickbloxLocalVideoView style={styles.userVideo}/>
          }
        </QuickbloxRemoteVideoView>
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
              {/*<Text style={styles.iconTitle}>Decline</Text>*/}
            </View>
          </TouchableHighlight>
        </View>
      </View>
    )
  }
}

function setTime(totalSeconds) {
  let sec = pad(totalSeconds % 60);
  let min = pad(parseInt(totalSeconds / 60));
  return min + ' : ' + sec
}

function pad(val) {
  let valString = val + '';
  if (valString.length < 2) {
    return '0' + valString;
  }
  else {
    return valString;
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    marginTop: 0,
  },
  callDetails: {
    flex: 3,
    backgroundColor: '#6969'
  },
  userVideo: {
    position: 'absolute',
    bottom: 1,
    right: 0,
    width: 80,
    height: 120,
    backgroundColor: 'green',
    overflow: 'hidden'
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