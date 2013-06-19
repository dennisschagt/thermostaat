package nl.tuestudent.thermostaat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class CommunicationClass {
	final private String SERVER_URL = "http://192.168.1.201:1234/";
	//final private String SERVER_URL = "http://wwwis.win.tue.nl:10030/";
	private String content;
	private String function;
	private String method;
	SubmitResult SR;
	
	public interface SubmitResult {
		public void submitResult(String function, String method, String contents);
	}
	
	CommunicationClass(SubmitResult event, String func, String methode) {
		this(event, func, methode, "");
	}
	
	CommunicationClass(SubmitResult event, String func, String methode, String putData) {
		function = func;
		method = methode;
		SR = event;
		String url = SERVER_URL + function;
		new DownloadWebpageTask().execute(url, method, putData);
	}
	
	// Uses AsyncTask to create a task away from the main UI thread. This task takes a 
	// URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
    	@Override
    	protected String doInBackground(String... params) {
    		// params comes from the execute() call: params[0] is the url.
    		// params[1] is the method (GET|PUT)
    		// params[2] contains the data when using the method PUT, an empty string otherwise
    		try {
    			return downloadUrl(params[0], params[1], params[2]);
    		} catch (IOException e) {
    			return "Error"; // Unable to retrieve webpage
    		}
    	}
    	// onPostExecute displays the results of the AsyncTask.
    	@Override
    	protected void onPostExecute(String result) {
    		content = result;
    		SR.submitResult(function, method, content);
    	}
    }
	
	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	private String downloadUrl(String myurl, String method, String putData) throws IOException {
		InputStream is = null;
		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod(method);
			conn.setDoInput(true);
			if(method.equals("PUT")) {
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				out.write(putData);
				out.close();
				conn.getInputStream();
				return "PUT ENDED";
			} else if (method.equals("GET")) {
				conn.connect();
				int response = conn.getResponseCode();
				is = conn.getInputStream();
				// Convert the InputStream into a string
				String contentAsString = convertStreamToString(is);
				return contentAsString;
			} else {
				return "Error"; // Method not suported
			}
			
		// Makes sure that the InputStream is closed after the app is
		// finished using it.
		} finally {
			if (is != null) {
				is.close();
			} 
		}
	}
	
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}