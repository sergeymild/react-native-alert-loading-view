import * as React from 'react';

import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { showAlertLoading, hideAlertLoading } from 'react-native-alert-loading';
import { useEffect, useState } from 'react';

export default function App() {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (!visible) return;
    setVisible(false);
    setTimeout(() => {
      hideAlertLoading({ animate: true });
    }, 100);
  }, [visible]);

  return (
    <View style={styles.container}>
      <TouchableOpacity
        onPress={() => {
          setVisible(true);
          showAlertLoading({
            overlayColor: 'rgba(0, 0, 0, 0.3)',
            color: 'green',
            type: 'circleStrokeSpin',
            animate: true,
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
