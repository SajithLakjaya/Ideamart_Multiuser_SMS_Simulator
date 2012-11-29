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

public class MtSmsResp {
    
    private String version;
    private String requestId;
    private String statusCode;
    private String statusDetail;
    private List<DestinationResponse> destinationResponses;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public List<DestinationResponse> getDestinationResponses() {
        return destinationResponses;
    }

    public void setDestinationResponses(List<DestinationResponse> destinationResponses) {
        this.destinationResponses = destinationResponses;
    }
    
    @Override
    public String toString()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
}
