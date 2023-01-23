/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  View,
  Dimensions,
  ScaledSize,
} from 'react-native';
import {StarifiedVideoView} from './src/component/StarifiedVideoView';

const windowSize: ScaledSize = Dimensions.get('window');

const defaultProps = {
  width: windowSize.width,
  height: windowSize.width * 1.25,
};

const App = () => {
  return (
    <SafeAreaView style={styles.backgroundStyle}>
      <View style={{...defaultProps}}>
        <StarifiedVideoView
          shouldSync
          source={{uri: 'origin'}}
          style={[styles.background, defaultProps]}
        />

        <StarifiedVideoView
          shouldSync
          customFilter="alphaMask"
          source={{uri: 'mask'}}
          style={[styles.background, defaultProps]}
        />
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  backgroundStyle: {
    backgroundColor: 'darkGray',
  },
  background: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
});

export default App;
