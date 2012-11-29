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

package com.simulator.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.simulator.model.util.PostHttp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class Mobile {
    
    private final String KEY_STATUS_DETAIL="statusDetail";
    private final String SUCESS_RESPONSE_CODE="S1000";
    private String address;
    private final List<MtSmsReq> inbox = new ArrayList<MtSmsReq>();
    private final List<MoSmsReq> outbox = new ArrayList<MoSmsReq>();
    
    public Mobile(String address){
        this.address=address;
    }

    public String getAddress() {
        return address;
    }

    public List<MtSmsReq> getInbox(){
        synchronized(inbox){
            return inbox;
        }
    }
    
    public List<MtSmsReq> getInbox(String time){
        
        List<MtSmsReq> requestMtSmsReq = new ArrayList<MtSmsReq>();
        
        if(time.equals(Simulator.ALL_LIST))
                return getInbox();
        else{
            synchronized(inbox){    
                for(MtSmsReq mtSmsReq : inbox ){
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date filterTime = dateFormat.parse(time);
                        Date smsTime = dateFormat.parse(mtSmsReq.getTime());
                        if(smsTime.after(filterTime))
                            requestMtSmsReq.add(mtSmsReq);
                    } catch (ParseException ex) {
                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }      
        return requestMtSmsReq;
    }
    
    public void clearInbox(){
        synchronized(inbox){
            inbox.clear();
        }
    }

    public List<MoSmsReq> getOutbox() {
        synchronized(outbox){
            return outbox;
        }
    }
    
    public List<MoSmsReq> getOutbox(String time){
        
        List<MoSmsReq> requestMoSmsReq = new ArrayList<MoSmsReq>();
        
        if(time.equals(Simulator.ALL_LIST))
            return getOutbox();
        else{
            synchronized(outbox){
                for(MoSmsReq moSmsReq : outbox ){
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date filterTime = dateFormat.parse(time);
                        Date smsTime = dateFormat.parse(moSmsReq.getTime());
                        if(smsTime.after(filterTime))
                            requestMoSmsReq.add(moSmsReq);
                    } catch (ParseException ex) {
                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return requestMoSmsReq;        
    }
    
    public void clearOutbox(){
        synchronized(outbox){
            outbox.clear();
        }
    }
    
    public void addMessage(MtSmsReq mtSmsReq){
        synchronized(inbox){
            this.inbox.add(mtSmsReq);
        }
    }
    
    public void addMessage(MoSmsReq moSmsReq){
        synchronized(outbox){
            this.outbox.add(moSmsReq);
        }
    }
    
    public String sendMessage(String message){
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String responseString;
        Gson gson = new Gson();
        MoSmsReq sendMessage = new MoSmsReq();
        sendMessage.setMessage(message);
        sendMessage.setVersion(Application.getVersion());
        sendMessage.setApplicationId(Application.getAppid());
        sendMessage.setTime(dateFormat.format(new Date()));
        sendMessage.setSourceAddress(getAddress());
        
        try{      
            
            responseString = PostHttp.sendRequest(Application.getUrl(), gson.toJson(sendMessage));
            MoSmsResp response = gson.fromJson(responseString, MoSmsResp.class);
            
            if(response.getStatusCode().equals(SUCESS_RESPONSE_CODE))
                addMessage(sendMessage);
				
        }catch(RuntimeException ex){
            
            JsonObject res = new JsonObject();
            res.addProperty(KEY_STATUS_DETAIL, ex.getMessage());
            responseString = res.toString(); 

        }
        return responseString;
    }
    
    
}
