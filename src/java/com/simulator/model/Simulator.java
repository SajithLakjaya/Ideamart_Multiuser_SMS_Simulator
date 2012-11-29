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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class Simulator {
    
    public static final String ALL_LIST = "0";
    private static final Map<String, Mobile> mobilePhones = new HashMap<String, Mobile>();
    private static final List<MtSmsReq> messageLog = new ArrayList<MtSmsReq>();
    
    public static void addMobile(Mobile mobile){
        synchronized(mobilePhones){
            mobilePhones.put(mobile.getAddress(), mobile);
        }
    }
    
    public static Mobile getMobile(String address){
        synchronized(mobilePhones){
            return mobilePhones.get(address);
        }
    }
    
    public static void removeMobile(String address){
        synchronized(mobilePhones){
            mobilePhones.remove(address);
        }
    }
    
    public static void addMessageLog(MtSmsReq mtSmsReq){
        synchronized(messageLog){
            messageLog.add(mtSmsReq);
        }
    }

    public static List<MtSmsReq> getMessageLog() {  
        synchronized(messageLog){
            return messageLog;
        }
    }
    
    public static List<MtSmsReq> getMessageLog(String time){       
        
        List<MtSmsReq> requestMtSmsReq = new ArrayList<MtSmsReq>();
        
        if(time.equals(ALL_LIST))
                return getMessageLog();
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            synchronized(messageLog){
                for(MtSmsReq mtSmsReq : messageLog ){
                    try {
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
    
    public static void clearMessageLog(){
        synchronized(messageLog){
            messageLog.clear();
        }
    }
    
    public static List<String> getAllMobiles()
    {
        List<String> addressList = new ArrayList<String>();
        Set<String> addresses = mobilePhones.keySet();
        Iterator<String> iterator = addresses.iterator();
        
        while(iterator.hasNext()){
            addressList.add(iterator.next());
        }
      
        return addressList;
    }
    
}
