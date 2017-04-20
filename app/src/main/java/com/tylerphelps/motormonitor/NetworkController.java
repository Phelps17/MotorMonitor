package com.tylerphelps.motormonitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import java.util.ArrayList;

/**
 * Created by TylerPhelps on 3/27/17.
 */

public class NetworkController {
    private final String CHANNEL = "channel";
    private final String PUB_KEY = "pub-c-169dbe41-99f9-4a69-b358-337bd5a0d24e";
    private final String SUB_KEY = "sub-c-e32c8950-1351-11e7-9093-0619f8945a4f";

    private PubNub pubnub;

    public NetworkController() {
        connect();
    }

    public void sendMessage(String message) {
        this.pubnub.publish()
                .message(message)
                .channel(CHANNEL)
                .shouldStore(true)
                .usePOST(true)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        if (status.isError()) {
                            //TODO handle
                        }
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
    }

    private void disconnect() {
        try {
            this.pubnub.disconnect();
        }
        catch (Exception e) {

        }
    }
}
