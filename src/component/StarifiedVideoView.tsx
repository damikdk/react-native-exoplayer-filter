import * as React from 'react';
import { requireNativeComponent, Platform } from 'react-native';

import Video, { VideoProperties } from 'react-native-video';

const FilterVideoView = requireNativeComponent('FilterVideo');

export const StarifiedVideoView = (props: StarifiedVideoViewProps) => {
  if (Platform.OS === 'android') {
    return (
      <FilterVideoView
        ref={props.videoRef}
        filter={props.customFilter}
        {...props}
      />
    );
  }

  return (
    <Video ref={props.videoRef} muted repeat playWhenInactive {...props} />
  );
};

interface StarifiedVideoViewProps extends VideoProperties {
  shouldSync?: boolean;
  customFilter?: string;
  videoRef?: any;
}
