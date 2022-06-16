/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StyleSheet,
  requireNativeComponent,
} from 'react-native';
import {Header} from 'react-native/Libraries/NewAppScreen';
const FilterVideoView = requireNativeComponent('FilterVideo');

const App = () => {
  return (
    <SafeAreaView>
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <Header />
        <FilterVideoView 
          style={{
            backgroundColor: 'red',
            width: 200,
            height: 200
          }}        
        />
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
});

export default App;
