# Result codes

## Failure codes
| Type                          | Description                                                                                              |
|-------------------------------|----------------------------------------------------------------------------------------------------------|
| `parcel_not_available`        | The package couldn’t be in the required state.                                                           |
| `max_reopens_exceed`          | The tries to open boxes has been exceeded.                                                               |
| `empty_box`                   | There isn’t a packet into box.                                                                           |
| `box_not_available`           | If in the location there are no free boxes of the proper size.                                           |
| `user_blocked`                | If the addressee is blocked.                                                                             |
| `user_autocreation_forbidden` | The user is not registered in Citibox and the carrier does not allow deliveries to non registered users. |
| `any_box_empty`               | The maximum number of opening boxes tries have been exceeded.                                            |

## Cancel codes
| Type                 | Description                                                                                                                     |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------|
| `not_started`        | The courier didn’t get to scan or input the QR code of the box to start the transaction. Navigation back to the carrier app.    |
| `cant_open_boxes`    | The courier couldn’t open any of the boxes offered.                                                                             |
| `parcel_mistaken`    | The courier starts the delivery and the data inserted belongs to another package (a wrong package).                             |
| `package_in_box`     | The courier finds another package in the box where we ask him to deposit it.                                                    |
| `need_hand_delivery` | The courier sees the need to deliver the package by hand. For example, he may cross with the addressee at the Citibox location. |
| `other`              | The other specified “other” in the cancellation reason form.                                                                    |

## Error codes
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
