Simple 'invisible' app that turns the screen on uppon reception of a SMS.
This app does not support any other kind of text messaging at the moment, but could
be updated if request is made.

This is mostly a personal app (since my phone doesn't have a LED to warn me
of incoming texts, and the screen stays off all the time)

#How to use
Start the app, enable the 'screen on' option.
To stop the screen from turning on uppong reception of a message, 
go in the app, and disable the 'screen on' option.

#How to test
Using the terminal:
adb shell am broadcast -a android.provider.Telephony.SMS_RECEIVED
