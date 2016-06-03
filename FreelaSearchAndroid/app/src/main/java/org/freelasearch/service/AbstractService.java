package org.freelasearch.service;

import org.freelasearch.utils.ExceptionFreelaSearch;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractService<T> {

    protected Object sendObject(T object, String uri) throws IOException {
        HttpURLConnection urlConnection = getHttpURLConnection(uri);
        urlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");

        ObjectOutputStream oos = new ObjectOutputStream(urlConnection.getOutputStream());
        oos.writeObject(object);
        oos.close();
        int codigoResposta = urlConnection.getResponseCode();
        if (codigoResposta != HttpURLConnection.HTTP_OK) {
            if (codigoResposta == HttpURLConnection.HTTP_BAD_REQUEST) {
                throw new ExceptionFreelaSearch(urlConnection.getResponseMessage());
            } else {
                throw new IOException(urlConnection.getResponseMessage());
            }
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(urlConnection.getInputStream());
            return ois.readObject();
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    protected T retrieveObject(Map<String, ?> params, String uri) throws IOException {
        HttpURLConnection urlConnection = getHttpURLConnection(uri);

        if (params != null && !params.isEmpty()) {
            PrintWriter pw = new PrintWriter(urlConnection.getOutputStream());

            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String paramName = it.next();
                pw.print(paramName + "=" + params.get(paramName));

                if (it.hasNext()) {
                    pw.print("&");
                }
            }

            pw.close();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(urlConnection.getInputStream());
            return (T) ois.readObject();
        } catch (ClassNotFoundException ex) {
            throw new IOException("Falha ao recuperar objeto com os parametros " + params + ". Exception=" + ex.getLocalizedMessage());
        } catch (Exception ex) {
            int codigoResposta = urlConnection.getResponseCode();
            if (codigoResposta != HttpURLConnection.HTTP_OK) {
                if (codigoResposta == HttpURLConnection.HTTP_BAD_REQUEST) {
                    throw new ExceptionFreelaSearch(urlConnection.getResponseMessage());
                } else {
                    throw new IOException(urlConnection.getResponseMessage());
                }
            }
            throw new IOException(ex.getMessage());
        }
    }

    protected List<T> retrieveListObject(Map<String, ?> params, String uri) throws IOException {
        HttpURLConnection urlConnection = getHttpURLConnection(uri);

        if (params != null && !params.isEmpty()) {
            PrintWriter pw = new PrintWriter(urlConnection.getOutputStream());

            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String paramName = it.next();
                if (params.get(paramName) != null) {
                    pw.print(paramName + "=" + params.get(paramName));
                    if (it.hasNext()) {
                        pw.print("&");
                    }
                }
            }

            pw.close();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(urlConnection.getInputStream());
            return (List<T>) ois.readObject();
        } catch (ClassNotFoundException ex) {
            throw new IOException("Falha ao recuperar objeto com os parametros " + params + ". Exception=" + ex.getLocalizedMessage());
        } catch (Exception ex) {
            int codigoResposta = urlConnection.getResponseCode();
            if (codigoResposta != HttpURLConnection.HTTP_OK) {
                if (codigoResposta == HttpURLConnection.HTTP_BAD_REQUEST) {
                    throw new ExceptionFreelaSearch(urlConnection.getResponseMessage());
                } else {
                    throw new IOException(urlConnection.getResponseMessage());
                }
            }
            throw new IOException(ex.getMessage());
        }
    }

    private HttpURLConnection getHttpURLConnection(String uri) throws IOException {
        //URL url = new URL("http://10.0.2.2:8080/FreelaSearchServer/" + uri);
        //URL url = new URL("http://192.168.25.4:8080/FreelaSearchServer/" + uri);
        URL url = new URL("http://freelasearchserver.sa-east-1.elasticbeanstalk.com/" + uri);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");

        return urlConnection;
    }

}
