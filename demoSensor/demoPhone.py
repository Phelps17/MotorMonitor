import time
import random
from pubnub import Pubnub
import sys
import os

PUBNUB_PUBLISH_KEY = "demo"
PUBNUB_SUBSCRIBE_KEY = "demo"

def main():
    sensor_access_name = "sensor"
    passcode = "12345"

    pn = Pubnub(publish_key=PUBNUB_SUBSCRIBE_KEY, subscribe_key=PUBNUB_SUBSCRIBE_KEY, ssl_on=False)
    print "Press Enter to Request"
    
    def _callback(message, channel):
        if (message != "request") :
            print message

    def _error(error):
        print(error)

    def get_input():
        input_string = raw_input()
        request_data()

    def request_data():
        print "Requesting..."
        msg = "request"
        pn.publish(channel=sensor_access_name, message=msg)

    pn.subscribe(channels=sensor_access_name, callback=_callback)

    while True:
        get_input()


if __name__ == "__main__":
    main()
        



        