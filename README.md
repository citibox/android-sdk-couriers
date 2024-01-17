# Courier SDK for integration

This library allows third parties to use Citibox Services for Couriers.  
Works with Courier app installed or via web view

## Content 📋

Library and example of how to integrate with Cibitox services for deliveries

## Requirements 👀

Contact our partners at Citibox to get your ACCESS TOKEN

It's recommended to have Couriers App installed to get all the features and maybe the best user
experience

If no Couriers App is installed, this SDK will show you the web app version with all the features
needed in order to deliver your parcels

## Documentation 📚

### Install 🏗️

Use JitPack in your main `build.gradle.kts`

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

### How to use it 🔨

#### Delivery parcel

[How to start the delivery process](DELIVERY.md)

#### Parcel retrieval

[How to start the retrieval process](RETRIEVAL.md)

### Environments

What are they? They are separated environments and endpoints where you operate, these are:
production, sandbox, local and test. Each one has it's own DB and admin panel.

| Environment | Purpose                                                                                        | Panel                                                                                                                                                                                        |
|-------------|------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Production  | ⚠️ pick this environment only when you want to send and receive parcels with real users        | [Production panel](https://accounts.citibox.com/?client=v3POyEsp3bhdotZRPs5hWweXJj0tkS8hQdElCZ46&callbackUrl=https%3A%2F%2Fadmin.citibox.com%2Farea.php)                                     |
| Sandbox     | Testing and checking if the integration works properly                                         | [Sandbox panel](https://accounts.citibox-sandbox.com/?client=v3POyEsp3bhdotZRPs5hWweXJj0tkS8hQdElCZ46&callbackUrl=https%3A%2F%2Fcitibox-app-dot-citibox-sandbox.ey.r.appspot.com%2Farea.php) |
| Local       | Pick this environment to test locally when you have a local server listening in localhost:8080 | N/A                                                                                                                                                                                          |
| Test        | Semi-environment to test if the communications between APP and Webapp works correctly          | N/A                                                                                                                                                                                          |