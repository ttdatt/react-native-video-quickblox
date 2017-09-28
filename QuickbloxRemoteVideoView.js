/**
 * Created by Dat Tran on 2/8/17.
 */
import React from 'react';
import PropTypes from 'prop-types'
import {requireNativeComponent} from 'react-native';

export default class QuickbloxRemoteVideoView extends React.Component {

  constructor(props) {
    super(props);
    this.onRendered = this.onRendered.bind(this);
  }

  onRendered(event) {
    if (this.props.onRendered)
      this.props.onRendered()
  }

  render() {
    return <NativeQuickbloxRemoteVideoView
      {...this.props}
      ref={ci => this.myRef = ci}
      onChange={this.onRendered}
    />;
  }
}

QuickbloxRemoteVideoView.propTypes = {
  onRendered: PropTypes.func
}

const NativeQuickbloxRemoteVideoView = requireNativeComponent('QuickbloxRemoteVideoView', null);