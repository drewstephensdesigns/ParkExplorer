<div align="center">
  <a href="">
    <img src="artwork/national-park.png" alt="Logo" width="200 height="200">
  </a>
</div>

<h2 align="center">Park Explorer (Unofficial)</h2>
<p align="center">Sample Android project utilizing the developer API from the National Park Service</p>

### Getting Started
This project uses the Gradle build system. To build this project, use the ```gradlew build``` command or use ```Import Project``` in Android Studio.

To run tests, run ```gradlew test```

### Prerequisites
To learn more about Android accessibility, visit the Android accessibility page. 
To learn more about developer facing aspects of Android accessibility, read the accessibility developer guide.

### Installation
Clone the repo
   ```sh
   git clone https://github.com/drewstephensdesigns/ParkExplorer.git
   ```

### Purpose
Allows users to view a list of parks by varying topics.  Currently defaults to ```Laborer and Worker```.  [FloatingActionButton](https://developer.android.com/reference/com/google/android/material/floatingactionbutton/FloatingActionButton) allows the user to select a different park topic.  The JSON response is then returned with National Park Service properties matching the criteria.  For the purpose of this app, only the first 40 topics are shown.  The user can click on a park detail: this will open a web browser to the corresponding park page of the National Park Service.

### Developer Access
Get you API Key at [National Park Service Developer Resources](https://www.nps.gov/subjects/developer/get-started.htm).  
Set up your own API key to "key" in ```local.properties``` file

### Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Sheets](https://github.com/maxkeppeler/sheets) - Sleek dialogs and bottom-sheets for quick use in your app
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable 
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

### App Permissions
The following permissions are utilized in the app, nothing more.
```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
- android.permission.INTERNET is required for network operations 
- android.permission.ACCESS_WIFI_STATE allows applications to access information about Wi-Fi networks

Additional information for these permissions can be found at https://developer.android.com/reference/android/Manifest.permission

### Attribution &amp; Acknowledgement
- Mountain Icon by [FlatIcon](https://www.flaticon.com/)
- Thank you to the [U.S National Park Service](https://www.nps.gov/index.htm) for having this publicly available API.  Learn more about their mission by clicking the link!
