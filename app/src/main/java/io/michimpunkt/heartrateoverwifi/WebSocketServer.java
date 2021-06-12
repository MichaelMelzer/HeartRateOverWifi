package io.michimpunkt.heartrateoverwifi;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public class WebSocketServer extends NanoWSD {

    public List<WebSocket> getConnections() {
        return connections;
    }

    private List<WebSocket> connections = new ArrayList<>();

    public WebSocketServer(String hostname, int port) throws IOException {
        super(hostname, port);
        start(0);
    }

    @Override
    protected WebSocket openWebSocket(IHTTPSession handshake) {
        return new WebSocket(handshake) {
            @Override
            protected void onOpen() {
                connections.add(this);
                Log.i(getClass().getSimpleName(), "onOpen()");
            }

            @Override
            protected void onClose(WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) {
                connections.remove(this);
                Log.i(getClass().getSimpleName(), "onClose() "+code.name()+" "+reason+" "+initiatedByRemote);
            }

            @Override
            protected void onMessage(WebSocketFrame message) {
                Log.i(getClass().getSimpleName(), "onMessage() "+message.getTextPayload());
            }

            @Override
            protected void onPong(WebSocketFrame pong) {
                Log.i(getClass().getSimpleName(), "onPong() "+pong.getTextPayload());
            }

            @Override
            protected void onException(IOException exception) {
                Log.e(getClass().getSimpleName(), "onException()", exception);
            }
        };
    }

}
