# Communication

## Delivery

The app will open a web view loading the URL

    [URL]/deeplink-delivery?access_token=[ACCESS_TOKEN]&tracking=[TRACKING]&recipient_phone=[RECIPIENT_PHONE]&recipient_hash=[RECIPIENT_HASH]&dimensions=[DIMENSIONS]&booking_id=[BOOKING_ID]

Parameters:

| Param                                 | Type                   |
|---------------------------------------|------------------------|
| `access_token`                        | `String`               |
| `tracking`                            | `String`               |
| `recipient_phone` or `recipient_hash` | `String`               |
| `dimensions`                          | `String`<br />Optional |
| `booking_id`                          | `String`<br />Optional |

## Retrieval 

The app will open a web view loading the URL

    [URL]/retrieval?access_token=[ACCESS_TOKEN]&citibox_id=[CITIBOX_ID]

| Param          | Type     |
|----------------|----------|
| `access_token` | `String` |
| `citibox_id`   | `String` |

## Getting results from web app

How to communicate between Android App and Web Javascript.
Two communication mechanisms have been implemented:
- Javascript Object
- URL navigation

### Javascript Object

There is an object exposed to Javascript called `CitiboxCourierSDK` with four methods:

- `onSuccess(boxNumber: Int, citiboxId: Int, deliveryId: String)` (for Delivery success)
- `onSuccess(boxNumber: Int, citiboxId: Int)` (for Retrieval success)
- `onFail(failureCode: String)`
- `onError(errorCode: String)`
- `onCancel(cancelCode: String)`

These methods are processed by the application to tell the result status. The web view will be
closed and the status sent to the SDK consumer.

Example for success:

```js
    function success(boxNumber, citiboxId, deliveryId){
        if (window.CitiboxCourierSDK) {
            window.CitiboxCourierSDK.onSuccess(boxNumber, citiboxId, deliveryId)
        }
    }
```

### URL navigation

In addition to the Javascript Object implementation, there is another way to tell the status to the
Android SDK using navigation:

| When         | URL destination                                                    |
|--------------|--------------------------------------------------------------------|
| Success      | `XX/finish/success?boxNumber=23%2054&citiboxId=123&deliveryId=456` |
| Error        | `XX/finish/error?code=access_token_missing`                        |
| Failure      | `XX/finish/fail?code=max_reopens_exceed`                           |
| Cancellation | `XX/finish/cancel?code=need_hand_delivery`                         |

The codes of error, failure and cancellation must match the ones in the [README.md](README.md)