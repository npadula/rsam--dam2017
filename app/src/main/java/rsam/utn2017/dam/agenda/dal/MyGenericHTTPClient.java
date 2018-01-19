package rsam.utn2017.dam.agenda.dal;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by mdominguez on 26/10/17.
 */

public class MyGenericHTTPClient {

    private String serverAddress;

    public MyGenericHTTPClient(String address){
        this.serverAddress=address;
    }

    public String post(String recurso,String jsonDataObject) {
        StringBuilder sb = new StringBuilder();

        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
        try {
            URL url = new URL(this.serverAddress+"/"+recurso);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            printout = new DataOutputStream(urlConnection.getOutputStream());
            Log.d("TEST-ARR",jsonDataObject.toString());
            Log.d("TEST-ARR", URLEncoder.encode(jsonDataObject.toString(),"UTF-8"));
            String str = jsonDataObject.toString();
            byte[] jsonData=str.getBytes("UTF-8");
            printout.write(jsonData);
//          printout.writeBytes(URLEncoder.encode(pedidoJSON.toString(),"UTF-8"));
            printout.flush();

            // leer las respuestas
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
            Log.d("TEST-ARR",sb.toString());

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printout!=null) try {
                printout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(urlConnection !=null)urlConnection.disconnect();
        }
        return sb.toString();
    }

    public String put(String recurso, String json){
        StringBuilder sb = new StringBuilder();

        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
        try {
            URL url = new URL(this.serverAddress+"/"+recurso);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setUseCaches(false);
            printout = new DataOutputStream(urlConnection.getOutputStream());
            Log.d("TEST-ARR",json.toString());
            Log.d("TEST-ARR", URLEncoder.encode(json.toString(),"UTF-8"));
            String str = json.toString();
            byte[] jsonData=str.getBytes("UTF-8");
            printout.write(jsonData);
//          printout.writeBytes(URLEncoder.encode(pedidoJSON.toString(),"UTF-8"));
            printout.flush();

            // leer las respuestas
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }
            Log.d("TEST-ARR",sb.toString());

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printout!=null) try {
                printout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(urlConnection !=null)urlConnection.disconnect();
        }
        return sb.toString();
    }


    public String getAll(String recurso) {
        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(this.serverAddress+"/"+recurso);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);


            // leer las respuestas
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printout!=null) try {
                printout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(urlConnection !=null)urlConnection.disconnect();
        }
        return sb.toString();
    }


    public String getById(String recurso,Integer id) {
        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(this.serverAddress+"/"+recurso+"/"+id);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setChunkedStreamingMode(0);

            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);

            InputStream stream = urlConnection.getInputStream();
            // leer las respuestas
            InputStream in = new BufferedInputStream(stream);
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                sb.append(current);
                data = isw.read();
            }

        } catch (ProtocolException e) {
            Log.d("12a3", "getById: protocolasd");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("45a6", "getById: io");
            e.printStackTrace();
        }
        catch(Exception ex){
            Log.d("45a6", "getById: ex");
            ex.printStackTrace();
        }
        finally {
            if(printout!=null) try {
                printout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(urlConnection !=null)urlConnection.disconnect();
        }
        return sb.toString();
    }


    public void delete(String recurso, Integer id){
        //StringBuilder sb = new StringBuilder();

        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
        try {
            URL url = new URL(this.serverAddress+"/"+recurso+"/"+id);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setUseCaches(false);

            int response = urlConnection.getResponseCode();



        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printout!=null) try {
                printout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(urlConnection !=null)urlConnection.disconnect();
        }

    }


}
