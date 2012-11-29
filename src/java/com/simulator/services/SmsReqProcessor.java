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
import com.google.gson.JsonSyntaxException;
import com.simulator.model.*;
import com.simulator.model.util.Status;
import java.text.DateFormat;
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

public class SmsReqProcessor {
    
    private MtSmsReq mtSmsReq;
    private Status status;
    private String requestId;
    private final String SEND_ALL_NUMBER = "tel:all";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
      
    public SmsReqProcessor(String jsonString)
    {
        
        Gson gson = new Gson();
        try{
            
            mtSmsReq = gson.fromJson(jsonString, MtSmsReq.class);
            mtSmsReq.setTime(dateFormat.format(new Date()));
            
        }catch(JsonSyntaxException ex){       
            
            Logger.getLogger(SmsReqProcessor.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
     
            setStatus();
            generateRequestId();
            
        }
    }
    
    private void generateRequestId() {     
	requestId = "" + ((int) (Math.random() * 1000000000L));
    }
    
    private void setStatus(){        
        if(mtSmsReq==null){           
            status = Status.E1312;
        }else if(!Application.checkAuthentication(mtSmsReq.getApplicationId(), mtSmsReq.getPassword())){
            status = Status.E1313;
        }else
            status = Status.S1000;
    }
    
    
    public String doProcess(){
        
        List<String> addresses = mtSmsReq.getDestinationAddresses();        
        List<DestinationResponse> destinationResponses = new ArrayList<DestinationResponse>();
        
        boolean containAll = true;
        
        MtSmsResp mtSmsResp = new MtSmsResp();
        Gson gson = new Gson();
     
        if(addresses!=null&&addresses.get(0).equals(SEND_ALL_NUMBER))    
            addresses = Simulator.getAllMobiles();
        
        boolean respStatus = status.equals(Status.S1000);
        
        for(String address:addresses){
                        
            Mobile mobile = Simulator.getMobile(address);
            DestinationResponse desResponse = new DestinationResponse();
            
            if(mobile!=null&&respStatus){      
                mobile.addMessage(mtSmsReq);
            }else
                containAll=false;
            
            desResponse.setAddress(address); 
            desResponse.setStatusCode(mobile!=null?status.getStatusCode()
                                    :respStatus?Status.E1331.getStatusCode()
                                    :status.getStatusCode());          
            desResponse.setStatusDetail(mobile!=null?status.getStatus()
                                    :respStatus?
                                    String.format(Status.E1331.getStatus(),address)
                                    :status.getStatus());
            desResponse.setTimeStamp(mtSmsReq.getTime());
            desResponse.setRequestId(requestId);
            
            destinationResponses.add(desResponse);  
        }
        
        if(respStatus)
            Simulator.addMessageLog(mtSmsReq);
        
        mtSmsResp.setDestinationResponses(destinationResponses);
        mtSmsResp.setRequestId(requestId);
        mtSmsResp.setVersion(Application.getVersion());
        mtSmsResp.setStatusCode(containAll?status.getStatusCode()
                                :respStatus?Status.P1001.getStatusCode():
                                status.getStatusCode());      
        mtSmsResp.setStatusDetail(containAll?status.getStatus()
                                 :respStatus?Status.P1001.getStatus():
                                 status.getStatus());       
        mtSmsResp.setVersion(mtSmsReq.getVersion());
        
        return gson.toJson(mtSmsResp);
    }
               
}
