# AndroidAppUpdateLibrary
Android App Update Library can be used for downloading the Apk from a link , Save it to External or Internal Storage, Than automatic installing the apk. Remember to add provider xml and android runtime permission before using this library. See sample use in app folder.

[![](https://jitpack.io/v/Piashsarker/AndroidAppUpdateLibrary.svg)](https://jitpack.io/#Piashsarker/AndroidAppUpdateLibrary)
## How To Use 
#### Step 1. Add the JitPack repository to your build file 

Add it in your root build.gradle at the end of repositories: </br> 


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
#### Step 2. Add the dependency

	dependencies {
	        compile 'com.github.Piashsarker:AndroidAppUpdateLibrary:1.0.0'
	}
  
  
#### Step 3. Add Runtime Permission 

Remember to add below  permission in `Manifest.xml file` . And Also add runtime permission for (Version => Marshmallow ). See the sample app. 

     <uses-permission android:name="android.permission.INTERNET"/>
  
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 
 ####  Step 4. Add Provider XML 
 Create a folder called `xml` in `res` folder called `provider_paths`. Paste below code: 
 
    <?xml version="1.0" encoding="utf-8"?>
      <paths xmlns:android="http://schemas.android.com/apk/res/android">
        <external-path name="external_files" path="."/>
      </paths>
 
 <b> You are Good To Go. Happy Coding </b> 
 
