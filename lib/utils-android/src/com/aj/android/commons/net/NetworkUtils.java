package com.aj.android.commons.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.aj.android.commons.io.StreamUtils;
import com.aj.android.commons.java.StringUtils;

/**
 * Common network related methods to validate URL,  download bitmap, GET-ting and POST-ing data to server.
 * 
 * @author Akhil Jain.
 *
 */
public class NetworkUtils {

	/***
	 * Check if the given {@link String} url is {@link URL} or not.
	 *
	 * @param stringUrl -{@link String}.
	 * @return boolean - returns true if string is a valid url, else false. 
	 **/
	public static boolean isValidURL(String stringUrl) {
		if (StringUtils.isEmpty(stringUrl)) {
			return false;
		}
		URL url = null;
		try {
			url = new URL(stringUrl);  
		} catch (MalformedURLException e) {  
			return false;  
		}  
		try {  
			url.toURI();
		} catch (URISyntaxException e) {  
			return false;  
		}
		return true;  
	}
	
	/**
	 * Gets the {@link Bitmap} from the provided {@link String} url.
	 * <br>
	 * Calls a get request to download bitmap.  
	 * 
	 * @param url -{@link String}.
	 * @return {@link Bitmap} -the downloaded bitmap, else <code>null</code>.
	 * @throws Exception exception are not filtered, need to see stack trace for exact cause.
	 */
	public static Bitmap downloadBitmap(String url) throws Exception 
	{
        DefaultHttpClient client = new DefaultHttpClient();
        Bitmap downloadedBitmap=null;        
        HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            	downloadedBitmap=null;;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    downloadedBitmap = BitmapFactory.decodeStream(inputStream);
                } finally {
                    StreamUtils.closeQuietly(inputStream);
                }
            }
        } catch (Exception e) {
            getRequest.abort();
            throw e;
        }finally{
        	if(client!=null){
        		client.getConnectionManager().closeExpiredConnections();
        		client.getConnectionManager().shutdown();
        	}
        }
        return downloadedBitmap;
    }
	
	
	/**
     * Queries the given URL with a list of parameters via POST call.
     *
     * @param url -{@link String}.
     * @param parameters -{@link List}<{@link NameValuePair}>.
     * @return {@link JSONObject} -the response json object, else <code>null</code>.
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws Exception various other kind of exception can be thrown. 
     */
    public static JSONObject getJSONFromUrlViaPOST(String url, List<NameValuePair> parameters) throws IOException, JSONException {

        InputStream is = null;
        JSONObject jObj = null;
        String json = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
          throw e;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
           StreamUtils.closeQuietly(is);
            json = sb.toString();
        } catch (Exception e) {
            throw e;
        }
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            throw e;
        }
        return jObj;

    }

    /**
     * Queries the given URL via GET call.
     *
     * @param url -{@link String}.
     * @return {@link JSONObject} -the response json object, else <code>null</code>.
     * @throws IOException 
     * @throws JSONException 
     */
    public static JSONObject getJSONFromUrlViaGet(String url) throws IOException, JSONException {
    	JSONObject jObj = null;
    	StringBuilder builder = new StringBuilder();
    	HttpClient client = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet(url);
    	try {
    		HttpResponse response = client.execute(httpGet);
    		StatusLine statusLine = response.getStatusLine();
    		int statusCode = statusLine.getStatusCode();
    		if (statusCode == 200) {
    			HttpEntity entity = response.getEntity();
    			InputStream content = entity.getContent();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    			String line;
    			while ((line = reader.readLine()) != null) {
    				builder.append(line);
    			}
    			jObj = new JSONObject(builder.toString());
    		}
    	} catch (ClientProtocolException e) {
    		throw e;
    	} catch (IOException e) {
    		throw e;
    	} catch (JSONException e) {
    		throw e;
    	}
    	return jObj;
    }


}
