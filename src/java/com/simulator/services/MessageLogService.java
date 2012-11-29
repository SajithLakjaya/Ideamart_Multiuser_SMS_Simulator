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

import com.google.gson.Gson;
import com.simulator.model.Simulator;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class MessageLogService implements ServiceInterface {
    
    private final static String KEY_TIME="time",KEY_CLEAR="clear",
                        KEY_ACTION="action";
    
    @Override
    public String doProcess(HttpServletRequest httpRequest) {
        
        Gson gson = new Gson();       
        String action = httpRequest.getParameter(KEY_ACTION);
        
        if(action!= null && action.equals(KEY_CLEAR)){
                Simulator.clearMessageLog();
                return "";
        }
        
        String time = httpRequest.getParameter(KEY_TIME);
        return gson.toJson(Simulator.getMessageLog(time));     
    }
    
}
