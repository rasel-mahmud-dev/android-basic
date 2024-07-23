



## Build

```shell
cd android
./gradlew clean
./gradlew assembleRelease

```

Now find this apk on
```shell
/android/app/build/outputs/apk/release/app-release.apk
```

## Clear cache
```shell
npx react-native start --reset-cache

cd android && ./gradlew clean
```


```shell
emulator -list-avds
emulator -avd Pixel_3a_XL_API_31
emulator -avd Pixel_7_API_33
emulator -avd Pixel_7_API_31
```





./gradlew assembleRelease


sudo apt-get install android-tools-adb

cd TurboModuleDemo/DeviceName/android


adb install ./android/app/build/outputs/apk/release/app-release.apk

adb install -r ./android/app/build/outputs/apk/release/app-release.apk

If you get an error saying the APK is already installed, you can add the -r flag to replace the existing installation:



adb shell am start -n com.devicename/.MainActivity