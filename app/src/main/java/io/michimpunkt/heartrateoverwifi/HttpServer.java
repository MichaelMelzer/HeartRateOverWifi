package io.michimpunkt.heartrateoverwifi;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class HttpServer extends NanoHTTPD {

    private ReadHeartRateActivity parent;

    public HttpServer(ReadHeartRateActivity parent, String hostname, int port) throws IOException {
        super(hostname, port);
        this.parent = parent;
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        TextView tvIP = parent.findViewById(R.id.tvIP);
        tvIP.setText(getHostname()+":"+getListeningPort());
        Log.i(getClass().getSimpleName(), "NanoHTTPD started on "+getHostname()+":"+getListeningPort());
    }

    @Override
    public Response serve(IHTTPSession session) {
        Log.i(getClass().getSimpleName(), "New http session!");
        try {
            InputStream is = parent.getAssets().open("defaultSite.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            String line;
            while ((line = br.readLine()) != null) {
                ret += line;
            }
            Log.i(getClass().getSimpleName(), "Serving session...");
            return newFixedLengthResponse(Response.Status.OK, "text/html", ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(getClass().getSimpleName(), "Could not open asset file");
        return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "", "Could not open asset file");
    }

}
