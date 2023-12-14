# Retrieval

## How to use

⚠️ Only works if Courier App is installed

In your `Activity` of `Fragment` declare a variable of type `DeliveryLauncher`:

```kotlin
val citiboxLauncher = RetrievalLauncher(this, ::retrievalResultFunction)
```

Create a function to handle results as a callback:

```kotlin
private fun retrievalResultFunction(result: RetrievalResult) {  
    // TODO Handle the result  
}
```

And now, when your code needs to launch Citibox retrieval process, pack the data into `RetrievalParams` and just call `citiboxLauncher.launch`

```kotlin
val data = RetrievalParams(
    accessToken = "ACCESS_TOKEN_PROVIDED_BY_CITIBOX",
    citiboxId = "CITIBOX_ID",
    environment = WebAppEnvironment.Sandbox,
)
citriboxLauncher.launch(data)
```

### Entry params

The data class `RetrievalParams` is the input for launching the delivery process

| Param         | Type              | Description                                                                                                                                                                                                                                                                |  
|---------------|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|  
| `accessToken` | String            | Token that identifies you as a courier                                                                                                                                                                                                                                     |  
| `citiboxId`   | String            | Citibox ID                                                                                                                                                                                                                                                                 |
| `environment` | WebAppEnvironment | There are three available environments: Production, Sandbox and Test.<br />Make sure to perform all your tests using Sandbox and when building your release this settings is Production.<br /><br />⚠️ This setting only affects to the WebView and not to the Courier App |  


### Results
It's represented by the object `RetrievalResult` as a `sealed class` that morph into the different states.
Those states are success, failure, cancel or error, and each state has it's own descriptors

#### Success
When the delivery went well, the result will give you an instance of `RetrievalResult.Success` with information about the delivery like:

- `boxNumber`: it's the box number where the parcel were delivered
- `citiboxId`: our ID to allow you to link your delivery with our ID

#### Failure
When the delivery couldn't be executed for some reason related to the Box or the user, you'll receive an instance of `RetrievalResult.Failure` with the field `type` telling you what went wrong.

[See failure codes](CODES.md#failure-codes)

#### Cancel
When the delivery couldn't be done because the Courier canceled the delivery for external reasons or reasons related to the box, you'll receive an instance of `RetrievalResult.Cancel` with the field `type` with the code.

[See cancellation codes](CODES.md#cancel-codes)

#### Error
When there is an error in the data preventing the delivery, you'll receive an instance of `RetrievalResult.Error` with the field `errorCode` with the code that helps you to identify what is wrong in the data.

[See error codes](CODES.md#error-codes)