package com.tylerphelps.motormonitor;

import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.*;
import java.util.ArrayList;

/**
 * Created by TylerPhelps on 3/27/17.
 */

public class NetworkController {
    private final String PUB_KEY = "demo";
    private final String SUB_KEY = "demo";

    private PubNub pubnub;
    private SensorModule sm;
    public ArrayList<String> responses;

    public NetworkController(SensorModule sm) {
        this.sm = sm;
        this.responses = new ArrayList<>();
        connect();
    }

    public String requestData() {
        sendMessage("request", this.sm.getAccess_name());

        return null;
    }

    public void sendMessage(String message, String channel) {
        this.pubnub.publish()
                .message(message)
                .channel(channel)
                .shouldStore(true)
                .usePOST(true)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        if (status.isError()) {
                            //TODO handle??
                        }
                        System.out.println(result.toString());
                    }
                });
    }

    public ArrayList<SensorDataEntry> getDataForSensorModule(SensorModule module) {
        return null;
    }

    private void connect() {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(PUB_KEY);
        pnConfiguration.setPublishKey(SUB_KEY);
        this.pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                responses.add(message.getMessage().toString());
                System.out.println(1);
            }

            @Override
            public void status(PubNub pubnub, PNStatus status) {
                //None
                System.out.println(2);
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                //None
                System.out.println(3);
            }
        });

        ArrayList<String> channels = new ArrayList<>();
        channels.add(sm.getAccess_name());
        pubnub.subscribe().channels(channels);
    }

    private void disconnect() {
        ArrayList<String> channels = new ArrayList<>();
        channels.add(sm.getAccess_name());

        try {
            pubnub.unsubscribe().channels(channels).execute();
            this.pubnub.disconnect();
        }
        catch (Exception e) {

        }
    }
}
