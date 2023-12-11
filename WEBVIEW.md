# Communication

Documentation to communicate between Android App and Web Javascript.
Two communication mechanisms have been implemented:
- Javascript Object
- URL navigation

## Javascript Object

There is an object exposed to Javascript called `CitiboxCourierSDK` with four methods:

- `onSuccess(boxNumber: String, citiboxId: String, deliveryId: String)`
- `onFail(failureCode: String)`
- `onError(errorCode: String)`
- `onCancel(cancelCode: String)`

These methods are processed by the application to tell the result status. The web view will be
closed and the status sent to the SDK consumer.

Example for success:

    function success(boxNumber, citiboxId, deliveryId){
        if (CitiboxCourierSDK) {
            CitiboxCourierSDK.onSuccess(boxNumber, citiboxId, deliveryId)
        }
    }


## URL navigation

In addition to the Javascript Object implementation, there is another way to tell the status to the
Android SDK using navigation:

| When         | URL destination                                                    |
|--------------|--------------------------------------------------------------------|
| Success      | `XX/finish/success?boxNumber=23%2054&citiboxId=123&deliveryId=456` |
| Error        | `XX/finish/error?code=access_token_missing`                        |
| Failure      | `XX/finish/fail?code=max_reopens_exceed`                           |
| Cancellation | `XX/finish/cancel?code=need_hand_delivery`                         |

The codes of error, failure and cancellation must match the ones in the [README.md](README.md)