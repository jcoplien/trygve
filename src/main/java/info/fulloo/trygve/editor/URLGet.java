package info.fulloo.trygve.editor;
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

/*
 * Trygve IDE
 *   Copyright ©2015 James O. Coplien
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

/**
*
* @author pawel
*/
public class URLGet {

   public String getSite(String url) {

       StringBuilder sb = new StringBuilder();

       try {
           URLConnection connection = (new URL(url)).openConnection();
           BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

           while (reader.ready()) {
               sb.append(reader.readLine() + "\n");
           }

           reader.close();

       }
       catch (MalformedURLException me) {
           sb.append("malformed url");
       }
       catch (IOException ioe) {
           sb.append("I/O error");
       }

       return sb.toString();
   }

   public static void main(String[] args) throws UnsupportedEncodingException, IOException {

       URLGet test = new URLGet();
       BufferedWriter writer = 
           new BufferedWriter(
               new OutputStreamWriter(
                   new FileOutputStream("labcast.xml")
               )
           );
       writer.write(test.getSite("http://feeds.media.mit.edu/labcast?format=xml"));
       writer.close();

   }
}