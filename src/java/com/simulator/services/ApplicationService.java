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

package com.simulator.services;

import com.google.gson.JsonObject;
import com.simulator.model.Application;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class ApplicationService implements ServiceInterface {

    private final static String KEY_APP = "app",KEY_PASSWORD="password",
            KEY_URL="url",KEY_GET="get",KEY_SET="set",KEY_ACTION="action",
            KEY_STATUS="status";
    
    private final static String 
            SIMULATOR_CONFIGURATION_SUCESS_MESSAGE="Simulator configuration "
                                + "sucessfully";
    
    @Override
    public String doProcess(HttpServletRequest httpRequest) {
        
        String action = httpRequest.getParameter(KEY_ACTION);
        
        if(action.equals(KEY_GET)){
            
            JsonObject appInfo = new JsonObject();          
            appInfo.addProperty(KEY_APP, Application.getAppid());
            appInfo.addProperty(KEY_PASSWORD, Application.getPassword());
            appInfo.addProperty(KEY_URL, Application.getUrl());
            return appInfo.toString();
      
        }else if(action.equals(KEY_SET)){
        
            Application.setAppid(httpRequest.getParameter(KEY_APP));
            Application.setPassword(httpRequest.getParameter(KEY_PASSWORD)); 
            String url = httpRequest.getParameter(KEY_URL);
            
            if(url!=null)
                url = url.replaceAll(" ", "%20");
            
            Application.setUrl(url);
            
            JsonObject response = new JsonObject();
            response.addProperty(KEY_STATUS, SIMULATOR_CONFIGURATION_SUCESS_MESSAGE);
            return response.toString();
        }
        
        return null;   
    }
    
}
