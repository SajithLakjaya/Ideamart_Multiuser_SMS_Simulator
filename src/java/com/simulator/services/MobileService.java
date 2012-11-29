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
import com.simulator.model.MoSmsReq;
import com.simulator.model.Mobile;
import com.simulator.model.MtSmsReq;
import com.simulator.model.Simulator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class MobileService implements ServiceInterface{
    
    private final static String KEY_MOBILE_NO="mobileno",KEY_ACTION="action",
            KEY_CREATE="create",KEY_GET="get",KEY_INBOX="inbox",
            KEY_OUTBOX="outbox",KEY_TYPE="type",KEY_TIME="time",
            KEY_CLEAR="clear",KEY_SEND="send",KEY_MESSAGE="message",
            KEY_REMOVE="remove";

    @Override
    public String doProcess(HttpServletRequest httpRequest) {
        
        Gson gson = new Gson();
        
        String mobileNumber = httpRequest.getParameter(KEY_MOBILE_NO);
        String action = httpRequest.getParameter(KEY_ACTION);       
                
        if(action.equals(KEY_CREATE)){
            
            if(Simulator.getMobile(mobileNumber)==null){               
                Mobile mobile = new Mobile(mobileNumber);          
                Simulator.addMobile(mobile);           
            }
     
        }else if(action.equals(KEY_GET)){
            
            String type = httpRequest.getParameter(KEY_TYPE);
            String time = httpRequest.getParameter(KEY_TIME);
        
            Mobile requestMobile = Simulator.getMobile(mobileNumber);
        
            if(requestMobile==null)
                return "";

            if(type.equals(KEY_INBOX)){
                
                List<MtSmsReq> requestList = requestMobile.getInbox(time);
                return(requestList==null?"":gson.toJson(requestList));
                
            }else if(type.equals(KEY_OUTBOX)){
                
                List<MoSmsReq> requestList = requestMobile.getOutbox(time);
                return(requestList==null?"":gson.toJson(requestList)); 
                
            }
            
        }else if(action.equals(KEY_CLEAR)){
            
            String type = httpRequest.getParameter(KEY_TYPE);           
            Mobile requestMobile = Simulator.getMobile(mobileNumber);
            
            if(requestMobile==null)
                return "";
            
            if(type.equals(KEY_INBOX)) 
                requestMobile.clearInbox();          
            else if(type.equals(KEY_OUTBOX))
                requestMobile.clearOutbox();
                   
        }else if(action.equals(KEY_SEND)){
            
            Mobile requestMobile = Simulator.getMobile(mobileNumber);
            String message = httpRequest.getParameter(KEY_MESSAGE);
            
            if(requestMobile==null)
                return "";
            
            return(requestMobile.sendMessage(message));
            
        }else if(action.equals(KEY_REMOVE)){
            
            if(Simulator.getMobile(mobileNumber)!=null)                     
                Simulator.removeMobile(mobileNumber);
            
        }
        return null; 
    }
   
}
