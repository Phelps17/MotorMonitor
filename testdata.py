import time
import random

for x in range(0,100) :
	date = time.strftime("%d/%m/%Y")
	curTime = time.strftime("%H:%M:%S")
	vibration = random.uniform(1.00, 10.00)
	temp = random.uniform(80.00, 100.00)
	current = random.uniform(1000.00, 1500.00)
	json = """{"access_name" : "ACCESS_NAME", "date":"%s", "time":"%s","vibration":"%d","temp":"%d","current":"%d"}""" % (date, curTime, vibration, temp, current)
	print json



    #