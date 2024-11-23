package com.alphaprinter;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.AsyncTask;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class PrinterPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("printData")) {
            String ip = args.getString(0);
            String port = args.getString(1);
            String data = args.getString(2);
            this.printData(ip, port, data, callbackContext);
            return true;
        }
        else if (action.equals("testPrinter")) {
            String ip = args.getString(0);
            String port = args.getString(1);
            this.testPrinter(ip, port, callbackContext);
            return true;
        }
        return false;
    }

    private void printData(final String ip, final String port, final String data, final CallbackContext callbackContext) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Socket socket = new Socket(ip, Integer.parseInt(port));
                    OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());
                    outputStream.write(data);
                    outputStream.flush();
                    socket.close();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    callbackContext.success("Print successful");
                } else {
                    callbackContext.error("Print failed");
                }
            }
        }.execute();
    }

    private void testPrinter(final String ip, final String port, final CallbackContext callbackContext) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Socket socket = new Socket(ip, Integer.parseInt(port));
                    socket.close();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    callbackContext.success("Printer connected");
                } else {
                    callbackContext.error("Printer connection failed");
                }
            }
        }.execute();
    }
}
