# DIT212-Alpha - LOST
This is an Android mobile application meant to enable and encourage motivated students to find other study partners. The application allows for users to create Broadcasts, "study sessions" in which the user can select a course they are studying at a certain place. The broadcast also contains a description where the user can specify additional information about the session, for example, group-room, floor, members etc. 
The interface is built on top of a Google maps API where the broadcast locations are represented with markers on the map. 

# Motivation
This project was created as part of a course (Object oriented programming project TDA367 / DIT212) taught at Chalmers univeristy in collaboration with the University of Gothenburg.
The motivation behind the specific application was to learn the Android development framework for mobile applications. 

# Build status
For Scrumboard see [Scrumboard](https://github.com/DIT212-Alpha/DIT212-Alpha/projects/1)

# Code style


# Screenshots
<!-- ![Alt text](/relative/path/to/img.jpg?raw=true "Optional Title")-->

# Frameworks used
- The application is written in Java using [android studio](https://developer.android.com/studio)
- For handling dependencies [Gradle](https://gradle.org/) has been used.
- The application is centered around the [google maps API](https://developers.google.com/maps/documentation/android-sdk/start)
- [Jetpack Navigation](https://developer.android.com/guide/navigation), a framework used to easily transition between different UI:s
- Info window
- [JUnit 5](https://junit.org/junit5/), Framework for testing
- [Espresso](https://developer.android.com/training/testing/espresso), Framework for android UI tests
- [JaCoCo](https://www.eclemma.org/jacoco/), Java Code Coverage Library a tool for Java which generates a code coverage report.
- [STAN4j](http://stan4j.com/), Structure analysis framework to generate dependency graphs


# Code Example

# Installation
To run the application
1. Download android studio
2. Download an android virtual device (recommended Pixel 2 API 29), or use an actual android hooked up to the computer 
3. Do a Gradle build and run

# API Reference
- Java version: 
- Virtual device: Pixel 2 API 29 (OBS! API 30 not supported). Install this device in the android studio AVD manager. 
- Android gradle plugin version: 4.0.1
- Gradle version: 6.6.1

# Tests
The tests are equipped with starter comments. In some cases there are preconditions that must be obliged.
There is a mix of both JUnit tests and Espresso tests.

# How to use?
Begin by registering an account. You can choose to either use a google account or an email. 
Login using the defined credentials.
(Allow the application to know your location)
You should now be greeted with a map. 
The screen also contains a button in the lower left hand corner titled "Broadcast". Pressing this button will take you to the AddBroadcast screen where you can specify a specific course and set a description and then press add. The application will automatically attach your currrent location to the Broadcast and create a red pin on the map assigned to the broadcast you just created.
You can also pan around the map to see other broadcasts by searching for active broadcasts tied to a coursecode in the search bar at the top.
If you find a broadcast you can press its pin to interact with it and see its description. If you are the creator you can also choose to either modify or delete it. The application will also detect if you stray too far from the original position and deactivate the broadcast automatically.  

# Credits
- android studio
- google maps
- firebase
- info window

# Team members
**Usernames  - Real name**
- [git.nuclear](https://github.com/Bashar3) - Bashar Oumari
- [Sannholm](https://github.com/Sannholm) - Benjamin Sannholm
- [drage1994](https://github.com/drage1994) - Mattias
- [sophiapham](https://github.com/sophiapham) - Sophia Pham
- [Pontare25](https://github.com/Pontare25)  - Pontus Nellgård 
