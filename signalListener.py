from pubnub import Pubnub

PUBNUB_PUBLISH_KEY = "pub-c-169dbe41-99f9-4a69-b358-337bd5a0d24e"
PUBNUB_SUBSCRIBE_KEY = "sub-c-e32c8950-1351-11e7-9093-0619f8945a4f"

CHANNEL = "eLockServer"

if __name__ == "__main__":
    pubnub = Pubnub(publish_key=PUBNUB_PUBLISH_KEY,
                    subscribe_key=PUBNUB_SUBSCRIBE_KEY,
                    cipher_key='',
                    ssl_on=False
                    )

    def callback(message, channel):
        print "Channel %s: (%s)" % (channel, message)

    print "Listening on Channel %s" % CHANNEL
    pubnub.subscribe(CHANNEL, callback)