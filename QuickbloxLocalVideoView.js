/**
 * Created by Dat Tran on 2/8/17.
 */
import React from 'react';
import PropTypes from 'prop-types'
import {requireNativeComponent} from 'react-native';

export default class QuickbloxLocalVideoView extends React.Component {

  constructor(props) {
    super(props);
    this.onRendered = this.onRendered.bind(this);
  }

  onRendered(event) {
    if (this.props.onRendered)
      this.props.onRendered()
  }

  stopCamera() {
    this.myRef.setNativeProps({stopCamera: true})
  }

  render() {
    return <NativeQuickbloxLocalVideoView
      {...this.props}
      ref={ci => this.myRef = ci}
      onChange={() => {
        this.setState({bottom: 0})
        this.onRendered()
      }}
    />;
  }
}

QuickbloxLocalVideoView.propTypes = {
  onRendered: PropTypes.func
}

const NativeQuickbloxLocalVideoView = requireNativeComponent('QuickbloxLocalVideoView', null);