
# Courier SDK for integration

This library allows third parties to use Citibox Services for Couriers.  
Works with Courier app installed or via web view

## Content

Library and example of how to integrate with Cibitox services for deliveries

## Requirements

Contact our partners at Citibox to get your ACCESS TOKEN

It's recommended to have Couriers App installed to get all the features and maybe the best user experience

If no Couriers App is installed, this SDK will show you the web app version with all the features needed in order to deliver your parcels

## Documentation

### How to use

USe JitPack in your main `build.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency in your `build.gradle.kts`

```kotlin
dependencies {
    implementation("com.github.citibox:android-sdk-couriers:1.0.0")
}
```

### Delivery parcel

In order to start the delivery process, [check out how to use it](DELIVERY.md)

### Parcel collection

In order to start the collection process, [check out how to use it](COLLECTION.md)