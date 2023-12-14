# Delivery

## How to use

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
    environment = WebAppEnvironment.Sandbox,
)
citriboxLauncher.launch(data)
```

### Entry params

The data class `DeliveryParams` is the input for launching the delivery process

| Param            | Type              | Description                                                                                                                                                                                                                                                                |  
|------------------|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|  
| `accessToken`    | String            | Token that identifies you as a courier                                                                                                                                                                                                                                     |  
| `tracking`       | String            | Tracking code                                                                                                                                                                                                                                                              |  
| `isPhoneHashed`  | Boolean           | Tells that the phone number will be hashed instead of using the actual phone number                                                                                                                                                                                        |  
| `recipientPhone` | String            | The recipients phone                                                                                                                                                                                                                                                       |  
| `dimensions`     | String?           | Optional param to tell how big the parcel is, must follow the format [mm]x[mm]x[mm]                                                                                                                                                                                        |  
| `environment`    | WebAppEnvironment | There are three available environments: Production, Sandbox and Test.<br />Make sure to perform all your tests using Sandbox and when building your release this settings is Production.<br /><br />⚠️ This setting only affects to the WebView and not to the Courier App |  


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

[See failure codes](CODES.md#failure-codes)

#### Cancel
When the delivery couldn't be done because the Courier canceled the delivery for external reasons or reasons related to the box, you'll receive an instance of `DeliveryResult.Cancel` with the field `type` with the code.

[See cancellation codes](CODES.md#cancel-codes)

#### Error
When there is an error in the data preventing the delivery, you'll receive an instance of `DeliveryResult.Error` with the field `errorCode` with the code that helps you to identify what is wrong in the data.

[See error codes](CODES.md#error-codes)