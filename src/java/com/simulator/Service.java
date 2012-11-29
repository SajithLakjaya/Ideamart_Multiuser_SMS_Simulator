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

package com.simulator;

import com.simulator.services.SmsReqProcessor;
import com.simulator.services.ApplicationService;
import com.simulator.services.MessageLogService;
import com.simulator.services.MobileService;
import com.simulator.services.ServiceInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class Service extends HttpServlet{
    
    private final static String SERVICE_MESSAGE_LOG="messagelog",
                                SERVICE_MOBILE="mobile",
                                SERVICE_APPLICATION="application",
                                SERVICE_KEY="service";
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            
        ServiceInterface requestService=null;
        String service = req.getParameter(SERVICE_KEY);
        
        if(service.equals(SERVICE_MESSAGE_LOG)){
            requestService = new MessageLogService(); 
        }else if(service.equals(SERVICE_MOBILE)){
            requestService = new MobileService();
        }else if(service.equals(SERVICE_APPLICATION)){
            requestService = new ApplicationService();
        }
        
        if(requestService!=null){
            resp.setContentType("application/json");
            resp.getWriter().print(requestService.doProcess(req));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {      
        
        StringBuilder jsonString = new StringBuilder();
        String content;
        while ((content = req.getReader().readLine()) != null) {
            jsonString.append(content);
        }
        
        SmsReqProcessor mtSmsReqProcessor = new SmsReqProcessor(jsonString.toString());
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(mtSmsReqProcessor.doProcess());
       
    }
       
}
