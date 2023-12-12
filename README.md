
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

In your `Activity` of `Fragment` declare a variable of type `DeliveryLauncher`:

```kotlin
val citiboxLauncher = DeliveryLauncher(this, ::deliveryResultFunction)
```

Create a function to handle results as a callback:

```kotlin
private fun deliveryResultFunction(result: DeliveryResult) {  
    // TODO Handle the result  
}
```

And now, when your code needs to launch Citibox delivery process, pack the data into `DeliveryParams` and just call `citiboxLauncher.launch`

```kotlin
val data = DeliveryParams(
    accessToken = "ACCESS_TOKEN_PROVIDED_BY_CITIBOX",
    tracking = "YOUR-ALPHANUMERIC-TRACKING-CODE",
    recipientPhone = "+34600600600",
    isPhoneHashed = false,
    dimensions = "10x20x30",
)
citriboxLauncher.launch(data)
```

### Entry params

The data class `DeliveryParams` is the input for launching the delivery process

| Param            | Type    | Description                                                                         |  
|------------------|---------|-------------------------------------------------------------------------------------|  
| `accessToken`    | String  | Token that identifies you as a courier                                              |  
| `tracking`       | String  | Tracking code                                                                       |  
| `isPhoneHashed`  | Boolean | Tells that the phone number will be hashed instead of using the actual phone number |  
| `recipientPhone` | String  | The recipients phone                                                                |  
| `dimensions`     | String? | Optional param to tell how big the parcel is, must follow the format [mm]x[mm]x[mm] |  


### Results
It's represented by the object `DeliveryResult` as a `sealed class` that morph into the different states.
Those states are success, failure, cancel or error, and each state has it's own descriptors

#### Success
When the delivery went well, the result will give you an instance of `DeliveryResult.Success` with information about the delivery like:

- `boxNumber`: it's the box number where the parcel were delivered
- `citiboxId`: our ID to allow you to link your delivery with our ID
- `deliveryId`: the ID of the delivery

#### Failure
When the delivery couldn't be executed for some reason related to the Box or the user, you'll receive an instance of `DeliveryResult.Failure` with the field `type` telling you what went wrong.

#### Failure codes
| Type                          | Description                                                                                              |
|-------------------------------|----------------------------------------------------------------------------------------------------------|
| `parcel_not_available`        | The package couldn’t be in the required state.                                                           |
| `max_reopens_exceed`          | The tries to open boxes has been exceeded.                                                               |
| `empty_box`                   | There isn’t a packet into box.                                                                           |
| `box_not_available`           | If in the location there are no free boxes of the proper size.                                           |
| `user_blocked`                | If the addressee is blocked.                                                                             |
| `user_autocreation_forbidden` | The user is not registered in Citibox and the carrier does not allow deliveries to non registered users. |
| `any_box_empty`               | The maximum number of opening boxes tries have been exceeded.                                            |

#### Cancel
When the delivery couldn't be done because the Courier canceled the delivery for external reasons or reasons related to the box, you'll receive an instance of `DeliveryResult.Cancel` with the field `type` with the code.

#### Cancel codes
| Type                 | Description                                                                                                                     |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------|
| `not_started`        | The courier didn’t get to scan or input the QR code of the box to start the transaction. Navigation back to the carrier app.    |
| `cant_open_boxes`    | The courier couldn’t open any of the boxes offered.                                                                             |
| `parcel_mistaken`    | The courier starts the delivery and the data inserted belongs to another package (a wrong package).                             |
| `package_in_box`     | The courier finds another package in the box where we ask him to deposit it.                                                    |
| `need_hand_delivery` | The courier sees the need to deliver the package by hand. For example, he may cross with the addressee at the Citibox location. |
| `other`              | The other specified “other” in the cancellation reason form.                                                                    |

#### Error
When there is an error in the data preventing the delivery, you'll receive an instance of `DeliveryResult.Error` with the field `errorCode` with the code that helps you to identify what is wrong in the data.

#### Error codes
| Error code                        | Description                                                                                                            |
|-----------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `tracking_missing`                | The tracking code must be provided                                                                                     |
| `access_token_missing`            | The access token must be provided                                                                                      |
| `citibox_id_missing`              | The mandatory data citibox_id wasn’t sent.                                                                             |
| `access_token_invalid`            | The access token is not valid, please contact Citibox Team                                                             |
| `access_token_permissions_denied` | The access token belongs to an user with the wrong permissions, please contact Citibox Team                            |
| `recipient_phone_missing`         | The recipient phone must be provided                                                                                   |
| `duplicated_trackings`            | You've tried to make a delivery with a tracking code already used                                                      |
| `recipient_phone_invalid`         | The recipient phone has a problem                                                                                      |
| `wrong_location`                  | The location has a problem, please contact Citibox Team                                                                |
| `arguments_missing`               | Some of the arguments are missing, check them                                                                          |
| `data_not_received`               |                                                                                                                        |
| `launching_problem`               | There were a problem launching the Courier app and the WebView, check the phone Google Play Services and WebViewClient |
