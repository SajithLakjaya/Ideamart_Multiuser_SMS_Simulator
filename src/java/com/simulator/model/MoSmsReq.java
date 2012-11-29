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

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class MoSmsReq {
    
    private String time;
    private String version;
    private String applicationId;
    private String sourceAddress;
    private String message;
    private String requestId;
    private String encoding;
    private String deliveryStatusRequest;
    
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }
    
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getDeliveryStatusRequest() {
        return deliveryStatusRequest;
    }

    public void setDeliveryStatusRequest(String deliveryStatusRequest) {
        this.deliveryStatusRequest = deliveryStatusRequest;
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    @Override
    public String toString()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
}
