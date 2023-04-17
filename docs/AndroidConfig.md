# Android Configure Part

## Checkout settings.gradle

> your project/android/settings.gradle

```
include ':app', 'jverification-react-native, 'jcore-react-native'
project(':jverification-react-native').projectDir = new File(rootProject.projectDir, '../node_modules/jverification-react-native/android')
project(':jcore-react-native').projectDir = new File(rootProject.projectDir, '../node_modules/jcore-react-native/android')
```

## Checkout module's build.gradle, add configuration

> your project/android/app/build.gradle

```
android {
  ...
  defaultConfig {
    applicationId "your application id"
    ...
    manifestPlaceholders = [
      JPUSH_APPKEY: "your app key",	//在此替换你的APPKey
      JPUSH_CHANNEL: "developer-default",		//应用渠道号, 默认即可
    ]
  }
}
...
dependencies {
  implementation project(':jverification-react-native')
  implementation project(':jcore-react-native')
}
```

​




