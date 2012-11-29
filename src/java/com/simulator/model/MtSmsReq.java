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
import java.util.List;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class MtSmsReq {
    
    private String time;
    private String applicationId;
    private String password;
    private String version;
    private List<String> destinationAddresses;
    private String message;
    private String sourceAddress;
    private String deliveryStatusRequest;
    private String encoding;
    private String chargingAmount;
    private String binaryHeader;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDeliveryStatusRequest() {
        return deliveryStatusRequest;
    }

    public void setDeliveryStatusRequest(String deliveryStatusRequest) {
        this.deliveryStatusRequest = deliveryStatusRequest;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getChargingAmount() {
        return chargingAmount;
    }

    public void setChargingAmount(String chargingAmount) {
        this.chargingAmount = chargingAmount;
    }

    public String getBinaryHeader() {
        return binaryHeader;
    }

    public void setBinaryHeader(String binaryHeader) {
        this.binaryHeader = binaryHeader;
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
