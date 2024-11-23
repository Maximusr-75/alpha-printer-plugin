package com.printer.pos;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class POSPrinterPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("print")) {
            String ip = args.getString(0);
            int port = args.getInt(1);
            String data = args.getString(2);
            this.print(ip, port, data, callbackContext);
            return true;
        }
        return false;
    }

    private void print(final String ip, final int port, final String data, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket(ip, port);
                    OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());
                    outputStream.write(data);
                    outputStream.flush();
                    socket.close();
                    callbackContext.success("Print successful");
                } catch (Exception e) {
                    callbackContext.error("Print failed: " + e.getMessage());
                }
            }
        });
    }
}
