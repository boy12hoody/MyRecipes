<h1 align="center">My Recipes App</h1>
<p align="center">
A beautiful Food Recipes app based on MVVM architecture and Material UI design.<br>
A fully functional, modern, neat-looking app that let's you search for thousands of recipes. 
Remote data source is popular <b>Spoonacular API</b> and you can store the data locally with the help of <b>Room</b> library as well.
The project uses latest methods and best practices as much as possible from architecture to UI design patterns.
</p>

<p align="center">
  <a href="https://kotlinlang.org/docs/releases.html#release-details"><img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-1.5.+-green.svg?style=flat&logo=kotlin"/></a>
  <a href="https://developer.android.com/studio/releases/gradle-plugin#7-0-0"><img alt="Gradle Version" src="https://img.shields.io/badge/Gradle-7.0.0-yellowgreen.svg?style=flat&logo=gradle"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/boy12hoody/MyRecipes/actions/workflows/android.yml"><img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/boy12hoody/MyRecipes/Build%20&%20Publish%20Debug%20APK"></a>
  <a href="https://github.com/boy12hoody/MyRecipes/blob/main/LICENSE"><img alt="License" src="https://img.shields.io/github/license/boy12hoody/MyRecipes?style=flat"></a>
  <a href="https://t.me/boywonder"><img alt="Telegram" src="https://img.shields.io/badge/Telegram-@BoyWonder-blue.svg?style=flat&logo=telegram"/></a>
</p>

### Screenshots
<p align="center">
<img src="/preview/1.gif" width="30%"/>
<img src="/preview/2.gif" width="30%"/>
<img src="/preview/3.gif" width="30%"/>
<img src="/preview/4.gif" width="30%"/>
<img src="/preview/5.gif" width="30%"/>
</p>

## Status
The project is currently **under development**: Splash Screen, Motion Layout, Dark Mode and many more yet to come..

## Download
Go to the [Releases](https://github.com/boy12hoody/MyRecipes/releases) to download the latest APK (Demo Api Key included).

## Build And Run

1. Get a free API Key at [Spoonacular.com](https://spoonacular.com/food-api)
2. Clone the repo
   ```sh
   git clone https://github.com/boy12hoody/MyRecipes.git
   ```
3. From Android Studio, select *Import Project*, then select the root folder of the cloned repository.
4. Click *Make Project* to build the app and download all the required dependencies.
5. Replace API key with yours in `util/Constants.kt`
   ```JS
   const val API_KEY = "Paste_Your_Key_Here"
   ```
6. Click *Run app* to install the app on your device or emulator.


## 🛠 Tech stack & Open-source libraries
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Parcelize](https://developer.android.com/kotlin/parcelize) - Parcelable implementation generator.
- [Spoonacular API](https://spoonacular.com/food-api) - Search through hundreds of thousands of recipes using JSON API.
- [Retrofit 2](https://github.com/square/retrofit) - A type-safe HTTP client for Android and the JVM.
- [Moshi + Codegen](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Chucker](https://github.com/ChuckerTeam/chucker) - An HTTP inspector for Android & OkHTTP.
- [Jsoup](https://github.com/jhy/jsoup/) - Java HTML Parser.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [Jetpack](https://developer.android.com/jetpack) -
        - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
        - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - New data storage solution which is the replacement of SharedPreferences.
        - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - the library helps us load and display pages of data from a larger dataset from local storage or over network.
        - [Navigation](https://developer.android.com/guide/navigation) - Navigation component designed for apps that have one main activity with multiple fragment destinations.
        - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
        - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Handling lifecycles with lifecycle-aware components.
        - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
        - [AndroidViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/AndroidViewModel) - Application context aware `ViewModel`
        - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
    - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
    - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Material Components for Android](https://material.io/develop/android/docs/getting-started/) - Modular and customizable Material Design UI components for Android.
- [Shimmer Effect](https://facebook.github.io/shimmer-android/) - An easy way to add a shimmer effect to any view.

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.
![](https://user-images.githubusercontent.com/70273198/128603663-0c51a103-296e-433f-9da0-6a013b82a969.png)

## Package Structure

    uz.boywonder.myrecipes          # Root Package
    .
    ├── adapters                    # Adapters for RecyclerView and Paging3
    |
    ├── data                        # Data sources and repositories.
    │   ├── database                # Local Persistence Database. Room (SQLite) database
    │   └── network                 # Remote Data Handler (API)
    │
    ├── models                      # Model classes
    |
    ├── di                          # Dependency Injection
    |
    ├── ui                          # Activity/View layer
    │   └── fragments               # Fragments
    │       ├── favorites           # Favorites Screen
    │       ├── ingredients         # Ingredients Screen
    │       ├── instructions        # Instructions Screen   
    │       ├── overview            # Overview Screen   
    │       └── recipes             # Recipes Screen
    │           └── bottomsheet     # Filter Bottom Sheet Dialog
    │
    ├── utils                       # Utility Classes / Kotlin extensions
    |
    └── viewmodels                  # ViewModels

## MAD Score

<img src="https://user-images.githubusercontent.com/70273198/128637424-231a8f0e-64ed-4108-8020-b8aa6be73aa6.png" width="50%"/> 
<img src="https://user-images.githubusercontent.com/70273198/128637425-717729ab-a7b2-402e-aa5d-0adcf0a9027a.png" width="50%"/>


## Contact
If you have questions or need any help, contact me on
[![Telegram](https://img.shields.io/badge/Telegram-@BoyWonder-blue.svg?style=flat&logo=telegram)](https://t.me/boywonder)

# License
```
MIT License

    Copyright (c) 2021 Ismatov Xurshid

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
```
