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

/**
 *
 * @author Sajith Lakjaya
 * @email slakjaya@gmail.com
 */

public class Application {
    
    public static String appid;
    public static String password;
    public static String url;
    public static final String version="1.0";
    
    public static boolean checkAuthentication(String appid,String password){
        return(appid.equals(Application.appid)&&password.equals(Application.password));
    }
     
    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String aAppid) {
        appid = aAppid;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String aUrl) {
        url = aUrl;
    }

    public static String getVersion() {
        return version;
    }
        
}
