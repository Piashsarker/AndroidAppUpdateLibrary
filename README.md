# AndroidAppUpdateLibrary
Android App Update Library can be used for downloading the Apk from a link , Save it to External or Internal Storage, Than automatic installing the apk. Remember to add provider xml and android runtime permission before using this library. See sample use in app folder.

### Changelog 
    Version: 1.0.4
    - Updated dependencies (upgrade to AndroidX) (df0ce1a)
    - Fixed failed install on lower Android versions (4603d10)
    - Rewrite to Kotlin (8d32d31)
    - Fixed calcualtion of download percentage (42e4dbb)
    - Made installation possible on higher Android versions (8a9be2e)
    - New optional file name parameter (fe4c1ab)
  
   Thanks to @cyb3rko 

    Version : 1.0.3
    
    Update Target SDK Version To 27 ( Android Oreo)
    Added Permission For Package Install For Android Oreo.




[![](https://jitpack.io/v/Piashsarker/AndroidAppUpdateLibrary.svg)](https://jitpack.io/#Piashsarker/AndroidAppUpdateLibrary)

![AndroidAppUpdateLibrary](https://github.com/Piashsarker/AndroidAppUpdateLibrary/blob/master/AppUpdateLibrary.gif)

## Usage 

#### Step 1. Add the JitPack repository to your build file 

Add it in your project level root build.gradle at the end of repositories: </br> 


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
#### Step 2. Add the dependency in your app build.gradle file 

	dependencies {
	        implementation 'com.github.Piashsarker:AndroidAppUpdateLibrary:1.0.4'
	}
  
  
#### Step 3. Add Runtime Permission 

Remember to add below  permission in `Manifest.xml file` . And Also add runtime permission for (Version => Marshmallow ). See the sample app. 

     <uses-permission android:name="android.permission.INTERNET" />
  
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     
     <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" /> 
     
 
 ####  Step 4. Add Provider XML 
 Create a folder called `xml` in `res` folder. Create a xml file and named it `provider_paths`. Paste below code: 
 
    <?xml version="1.0" encoding="utf-8"?>
    <paths xmlns:android="http://schemas.android.com/apk/res/android">
    	<external-path name="external_files" path="."/>
    </paths>
      
 Add below code in your `AndroidManifest.xml` file.    
 

    <application
      
	..............
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths"/>
        </provider>


    </application>

 
 #### Step 5. For downloading and installing the apk automatically.
 
 After setting up all neccessary files it's just two line of code to download and install the apk updates in your device. 
  	
	String url = "https://github.com/Piashsarker/AndroidAppUpdateLibrary/raw/master/app-debug.apk";
	
	DownloadApk downloadApk = new DownloadApk(MainActivity.this);
       	
	// With standard fileName 'App Update.apk'
	downloadApk.startDownloadingApk(url);
	
	// With custom fileName, e.g. 'Update 2.0'
 	downloadApk.startDownloadingApk(url, "Update 2.0");
 
 
 <b> You are Good To Go. Happy Coding </b> 
 
 ### MIT License

#### Copyright (c) 2018 Piash Sarker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
