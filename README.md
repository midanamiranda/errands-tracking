# Errands Tracking App

Simple ̷T̷o̷-̷d̷o̷  Errands Tracking application focused on REST API consuming using Retrofit2, Hilt, Coroutines and MockWebServer, all through Test Driven Development approach.

[![CI Actions Status](https://github.com/midanamiranda/errands-tracking/workflows/CI/badge.svg)](https://github.com/midanamiranda/errands-tracking/actions)

## Introduction

This Errands Tracking Application is for learning purposes, as it works with dummy data to show REST API consuming using _soa_ Android development tools.
If you either a student or profissional, and want to learn how to consume diferent REST API definitions from your _Kotlin_ code, this repo takes a step further and does it applying TDD approach, assuring test code coverage.

## Technologies

The data used in this repo provides from [JSONPlaceholder](https://jsonplaceholder.typicode.com/).

If you want to catch up with the resources used in the application:
* [Retrofit2](https://square.github.io/retrofit/)
* [OkHttp3](https://square.github.io/okhttp/)
* [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
* [Hilt DI](https://developer.android.com/training/dependency-injection/hilt-android)
* [AssertJ](https://assertj.github.io/doc/)
* [Coroutines](https://developer.android.com/kotlin/coroutineshttps://developer.android.com/kotlin/coroutines)

## Features

### Implemented
* Dependency Injection through Hilt
** ApiModule to Retrofit related injections
* Retrofit
** Custom OkHttpClient
** MediaTypeInterceptor
** RetrofitBuilder
** ErrandsTrackingService with GET and POST requests
** ErrandsTrackingServiceHelper
* Testing
** TestUtils
** Unit Tests on all services and helpers methods
** MockWebServer mock responses
* Globals
** Result class to pass resources through layers
** Custom Exception

### Planned
* Architecture
** Add ViewModel layer
* View
** Create list view to present errands
** Create view to add and edit errands
* Retrofit
** Custom GsonConverter
** Add PUT and DELETE methods
* View model injection
* Data storage using DataStore
* Testing
** Add tests for viewmodel
** Add tests for new services call


## Thank You

Trynig to provide a nice and clean implementation for Android development using Kotlin and applying Google best practices.
Hope my vision suits your needs and if so, please join me in the journey.
