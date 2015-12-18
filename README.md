# Checkpoint 3 (Currency Calculator)

Many times, when People travel outside their Country, they have difficulties deciding what to purchase, a lot of People prefer converting the price of an Item they would like to buy first to their local currency, after which they make their purchase decision. Checkpoint 3 offers a solution to the above mentioned use case. It deals with the development of a Currency calculator that can also second as a Basic calculator.

### Features

**•	Basic Calculator:** The Currency calculator has a basic calculator layout in addition to the source and destination currency symbols. It works perfectly as a simple calculator with no advanced operators. The operators allowed are addition, subtraction, multiplication and division. The calculator also handles basic sub expressions within expressions, i.e expressions in parenthesis.

**•	Currency Converter:** The major aim of coming up with a currency calculator is to be able to convert from one currency to another while also doing basic mathematical expressions with currencies. Some top 30 currencies have been selected to be used in this currency calculator. Also this currency calculator allows user to mix currencies for conversion. Whenever a source currency is not selected, it implies the user’s source currency is the same with the destination currency. E..g if destination currency equals NGN, and *23+5USD-7GBP+6* is type, this evaluates to *23NGN+5USD-7GBP+6NGN*. Also the conversion is done in real time, and when the destination currency changes after the user enters expression, the result gets updated immediately.

**•	Error Validation:** The currency calculator has a robust error validation. This prevents users from typing an invalid expression. E.g *2.6.8/+8USDEUR*. This error handling will be seen when the calculator is used.

**•	Orientation:** Unlike other apps that restrict the user only to a portrait view, this currency calculator app can be operated in both portrait and landscape view.

###User Guide

There is no hard and fast rule in using this app. It is very easy to use as it works exactly the way a basic calculator works except for the way currencies are selected.

* Simple calculations can be performed by pressing the buttons; the expression a user types will be displayed on the screen with a   real time result of the expression.
* The C button on the top leftmost corner of the keypad is used for clearing the work area and the result area.
* The ← button on the top rightmost corner of the keypad is used for clearing the last character of the expression in the computation area. This result area is automatically updated when this is done.
* The source currency is located on the bottom leftmost corner of the keypad. When the button is clicked, it displays a list of currencies that the user can select from. Once a currency is selected, the button text gets updated with the selected currency.
* The destination currency is located on the bottom rightmost corner of the keypad. When the button is clicked, it displays a list of currencies that the user can select from. Once a currency is selected, the button test gets updated with the selected currency.

####Getting Started (As a Developer)

To have a feel of this app, clone the *url https://github.com/andela-bomotoso/checkpoint3.git*

####Running The App

* Open the cloned project with android studio
* Plug the android phone you want to install the app on, ensure that USB debugging is enabled on the phone
* Run the app in android studio
* After a short while, the device picker dialog pops up
* Select your device name and click ok. The calculator should be launched shortly.

####Tools and Requirements
* Android Studio 1.4
* API 10: Android 2.3.3 (Gingerbread)
* Target SDK: 23

####Limitations
* The currency calculator supports only 30 currencies that the user can pick from. Users cannot use a currency other than the specified one.
* Users need to connect to internet to get most up-to date rates.
* This currency calculator cannot effectively handle nested parenthesis.

