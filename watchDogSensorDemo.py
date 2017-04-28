import time
import random
from pubnub import Pubnub

PUBNUB_PUBLISH_KEY = "pub-c-169dbe41-99f9-4a69-b358-337bd5a0d24e"
PUBNUB_SUBSCRIBE_KEY = "sub-c-e32c8950-1351-11e7-9093-0619f8945a4f"
CHANNEL = "watchdogServer"

if __name__ == "__main__":
    pubnub = Pubnub(publish_key=PUBNUB_PUBLISH_KEY,
                    subscribe_key=PUBNUB_SUBSCRIBE_KEY,
                    cipher_key='',
                    ssl_on=False
                    )

    while (1) :
    	print ">",
    	input_string = raw_input()
    	date = datetime.date
    	for x in range(0,100) :
			date = time.strftime("%d/%m/%Y")
			curTime = time.strftime("%H:%M:%S")
			vibration = random.uniform(1.00, 10.00)
			temp = random.uniform(80.00, 100.00)
			current = random.uniform(1000.00, 1500.00)
			json = """{"access_name" : "ACCESS_NAME", "date":"%s", "time":"%s","vibration":"%d","temp":"%d","current":"%d"}""" % (date, curTime, vibration, temp, current)
			pubnub.publish(CHANNEL, json)



    	