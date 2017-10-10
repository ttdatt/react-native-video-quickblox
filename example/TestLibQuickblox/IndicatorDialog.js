/**
 * Created by Dat Tran on 1/25/17.
 */
import React from 'react'
import {View, Text, StyleSheet, Modal, TouchableOpacity} from 'react-native'
import Spinner from 'react-native-spinkit'
import PropTypes from 'prop-types'

export default class IndicatorDialog extends React.Component {
  static propTypes = {
    message: PropTypes.string.isRequired,
  }

  constructor(props) {
    super(props);
    this.state = {
      modalVisible: true
    }
  }

  render() {
    const {message, showGlobalIndicator} = this.props;

    return (
      <View>
        <Modal animationType='fade'
               transparent={true}
               visible={true}
               onRequestClose={() => {
                 console.log('android click back')
               }}>
          <View style={[styles.container, {backgroundColor: 'rgba(0, 0, 0, 0.2)'}]}>
            <View style={[styles.innerContainer]}>
              <Spinner isVisible={true}
                       size={60}
                       type='ThreeBounce'
                       color='#4286f4'/>
              <Text>{message}</Text>
            </View>
          </View>
        </Modal>
      </View>
    );
  }
}

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  innerContainer: {
    borderRadius: 10,
    alignItems: 'center',
    backgroundColor: '#fff',
    paddingBottom: 20,
    width: 280
  },
  row: {
    alignItems: 'center',
    flex: 1,
    flexDirection: 'row',
    marginBottom: 20,
  },
  rowTitle: {
    flex: 1,
    fontWeight: 'bold',
  },
  button: {
    borderRadius: 5,
    flex: 1,
    height: 44,
    alignSelf: 'stretch',
    justifyContent: 'center',
    overflow: 'hidden',
  },
  buttonText: {
    fontSize: 18,
    margin: 5,
    textAlign: 'center',
    color: 'white'
  },
  modalButton: {
    borderRadius: 4,
    marginTop: 10,
    padding: 4,
    backgroundColor: 'orange'
  },
});