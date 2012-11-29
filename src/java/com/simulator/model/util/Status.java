/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simulator.model.util;

/**
 *
 * @author Sajith Lakjaya
 */

public enum Status{
    
    S1000("S1000","Success."),
    E1312("E1312","Request is Invalid. Refer the SDP NBL API "
            + "Developer Guide for the mandatory fields and "
            + "correct format of the request."),
    E1313("E1313","Authentication failed. No such active application "
            + "with applicationId, or no active service provider or "
            + "the given password in the request is invalid."),
    E1601("E1601","System experienced an unexpected error."),
    P1001("P1001","Request processed successfully, but delivery to "
            + "some destinations have failed. Please check the "
            + "individual status for more details."),
    E1331("E1331","%s is not allowed.");
                          
    private String statusCode;
    private String status;
        
    Status(String statusCode,String status)
    {
        this.statusCode=statusCode;
        this.status=status;
    }
            
    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }
                    
};