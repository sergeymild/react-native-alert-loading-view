import * as React from 'react';

import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { showLoading, hideLoading } from 'react-native-alert-loading';
import { useEffect, useRef, useState } from 'react';

export default function App() {
  const[visible, setVisible] = useState(false)

  useEffect(() => {
    if (!visible) return
    setTimeout(() => {
      setVisible(false)
      hideLoading({animate: true})
    }, 2000)
  }, [visible])

  return (
    <View style={styles.container}>
      <TouchableOpacity
        onPress={() => {
          setVisible(true);
          showLoading({
            overlayColor: 'rgba(0, 0, 0, 0.3)',
            color: 'green',
            type: 'ballSpinFadeLoader',
            animate: true
          });
        }}
      >
        <Text>Toggle</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
