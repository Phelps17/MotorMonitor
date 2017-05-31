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

    def _callback(message, channel):
        if (message == "request"):
            send_data()

    def _error(error):
        print(error)

    def get_input():
        input_string = raw_input()

    def send_data():
        msg = ""
        print "Sending data..."
        for x in range(0,100) :
            date = time.strftime("%d/%m/%Y")
            curTime = time.strftime("%H:%M:%S")
            vibration = random.uniform(1.00, 10.00)
            temp = random.uniform(80.00, 100.00)
            current = random.uniform(1000.00, 1500.00)
            msg += """{"access_name" : "ACCESS_NAME", "date":"%s", "time":"%s","vibration":"%d","temp":"%d","current":"%d"}\n""" % (date, curTime, vibration, temp, current)
        pn.publish(channel=sensor_access_name, message=msg)
        print msg

    pn.subscribe(channels=sensor_access_name, callback=_callback)

    while True:
        get_input()


if __name__ == "__main__":
    main()
        



        