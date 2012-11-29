/*   
   Copyright 2012 Sajith Lakjaya

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.simulator.model.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class PostHttp {
    
    public static String sendRequest(String requestUrl,String entity) { 
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
 
            OutputStream os = conn.getOutputStream();
            os.write(entity.getBytes());
            os.flush();
 
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Message send failed : HTTP error code : "
                                        + conn.getResponseCode());
            }
 
            BufferedReader br = new BufferedReader(new InputStreamReader(
                                                    (conn.getInputStream())));
 
            StringBuilder response = new StringBuilder();
            String content;
            while ((content = br.readLine()) != null) {
                response.append(content);
            }
        
            conn.disconnect();
                
            return response.toString();
                
        } catch (MalformedURLException e) {
        throw new RuntimeException("Message send failed"
                        + (e.getMessage()==null? "":" : " + e.getMessage()));
      
        } catch (IOException e) {      
        throw new RuntimeException("Message send failed"
                        + (e.getMessage()==null? "":" : " + e.getMessage()));      
        }       
    }
}
