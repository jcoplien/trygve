package info.fulloo.trygve.editor;
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

/*
 * Trygve IDE 4.3
 *   Copyright (c)2023 James O. Coplien, jcoplien@gmail.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */


import java.net.*;
import java.io.*;

// EXPERIMENTAL
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
// END EXPERIMENTAL

/**
*
* @author pawel
*/
public class URLGet {
	// EXPERIMENTAL
	 public String getSite2(final String urlArg)  // throws IOException, InterruptedException
			 {
		 
		    String url = urlArg;
		    if (url.startsWith("http") && url.endsWith(".k")) {
		    	url = url.substring(0, url.length() - 2) + ".html";
		    }
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET() // GET is default
	                .build();

	        HttpResponse<String> response = null;
			try {
				response = client.send(request,
				        HttpResponse.BodyHandlers.ofString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // System.out.println(response.body());
	        return response.body();
	    }
	// END EXPERIMENTAL

   public String getSite(final String url) {

       StringBuilder sb = new StringBuilder();

       try {
           final URLConnection connection = (new URL(url)).openConnection();
           final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream() , "UTF-8"));

           while (reader.ready()) {
               sb.append(reader.readLine() + "\n");
           }

           reader.close();
       }
       catch (final MalformedURLException me) {
           sb.append("malformed url");
       }
       catch (IOException ioe) {
           sb.append("I/O error");
       }

       return sb.toString();
   }

   public static void main(String[] args) throws UnsupportedEncodingException, IOException {
	   assert false;
       URLGet test = new URLGet();
       BufferedWriter writer = 
           new BufferedWriter(
               new OutputStreamWriter(
                   new FileOutputStream("labcast.xml"), "UTF-8"
               )
           );
       writer.write(test.getSite("http://feeds.media.mit.edu/labcast?format=xml"));
       writer.close();

   }
}