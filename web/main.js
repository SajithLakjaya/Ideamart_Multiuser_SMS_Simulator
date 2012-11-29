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


/******************************************************
MAIN
******************************************************/

$(document).ready(function(){
    var $tabs = $("#tabs").tabs();
    displayMTURL();
     $("#tabs").tabs( {
        "show": function(event, ui) {
                //adjuest column size in message log table
                $("#messageLogTable").dataTable().fnAdjustColumnSizing();
            }
        }
     );
});
//display simulator run url on web page
function displayMTURL() {
	var url = location.href
        if (url.substr(url.length - 1, url.length) == "#")
            url = url.substr(0,url.length - 1);
	if (url.substr(url.length - 1, url.length) != "/")
		url += "/"
			url += "service"
	$('#mtMsgURL').attr('value', url);
}

/******************************************************
SIMULATOR CONFIGURATION
******************************************************/

$(document).ready(function(){   
    
    var $appid = $("#appid");
    var $password = $("#password");
    var $url = $("#url");
    
    //loading configuration data
    function loadConfiguration(){
        $.get('service?service=application&action=get',function(resp)
        {
            $("#appid").attr('value',resp.app);
            $("#password").attr('value',resp.password);
            $("#url").attr('value',resp.url);
        });
    }
    
    //initalize simulator configuration dialog
    var $dialog = $( "#configdialog" ).dialog({
        autoOpen: false,
        modal: true,         
        width: 480,
        buttons: {
            OK: function() {
                //set configuration data
                setApplicationData($appid.val(),$password.val(),$url.val());
                $( this ).dialog( "close" );
                },
            Cancel: function() {
                $( this ).dialog( "close" );
                }
            },
            open: function() {
                $appid.focus();
                }
    });
    
    //set configuration data in service
    function setApplicationData(appid,password,url)
    {
        $.get('service?service=application&action=set&app='+appid+'&password='+password+'&url='+url,function(resp)
        {
            if(resp['status'])
                //display status
                alert(resp['status']);
        });
    }

    //configuration button
    $("#configSimulatorButton")
        .button()
        .click(function() {
            loadConfiguration();
            $dialog.dialog( "open" );
        });
    
});

/******************************************************
RECEIVED MESSAGE LOG CODES
******************************************************/

var timeOutMessageLog
var maxLogTime=0;

$(document).ready(function() {
    
    //initalize message log table
    $('#messageLogTable').dataTable( {
        "sScrollY": "300px",
        "bAutoWidth": false,
        "bScrollCollapse": true,
        "bPaginate": false,
        "oLanguage": {
            "sEmptyTable": "No messages"
                     },
        "bJQueryUI": true,
        "aoColumns": [{"sWidth": "23%"}, {"sWidth": "10%"}, {"sWidth": "67%"}],
        "aaSorting": [[ 0, "desc" ]]
    } );
    
    //load message log
    setLog();
    
    //clear message log  
    $("#clearMessageLog")
        .button()
        .click(function(){
            $.get('service?service=messagelog&action=clear');
            clearMessageLogTable();
    });
            
});

//clear message log table
function clearMessageLogTable()
{
    $('#messageLogTable').dataTable().fnClearTable();
}

//intialize message log table
function initializeMessageLogTable()
{
    clearTimeout(timeOutMessageLog);
    clearMessageLogTable();
    maxLogTime=0;
    setLog();
}

//add sms details into message log table
function addSMSMessageLog(date,number,message)
{
    $('#messageLogTable').dataTable().fnAddData( [
        date,
        number,
        escapeHTML(message)
    ] );
}

//set the message log table               
function setLog()
{
    $.get('service?service=messagelog&time='+maxLogTime,function(resp)
    {
        for (index in resp)
        {
            var sms=resp[index];
            maxLogTime=sms.time;
            addSMSMessageLog(sms.time,sms.destinationAddresses,sms.message);
        }
    });
timeOutMessageLog = setTimeout('setLog();', 1000);
}
    
/*****************************************************
MOBILE CODES
******************************************************/
var maxInboxTime=[]
var maxOutboxTime=[]
var timeOutInbox=[]
var timeOutOutbox=[]
var mobilePort="77000"

$(document).ready(function() {
     
    var $mobile = $( "#mobiles").tabs({
        tabTemplate: "<li><a href='#{href}'>#{label}</a><span class='ui-icon ui-icon-close'>Remove Tab</span></li>"
    });    
    
    var $mobileNumberInput = $("#mobile_number");
    
    $("#mobiles").tabs( { 
        show: function(event, ui) {    
            adjustTableColumnSizing('inbox_'+ui.panel.id);
            adjustTableColumnSizing('outbox_'+ui.panel.id);
            $('#sendMessage_'+ui.panel.id).focus();
        },
        remove: function(event, ui){
            var tabDiv = document.getElementById(ui.panel.id);
            clearTimeout(timeOutInbox[ui.panel.id]);
            clearTimeout(timeOutOutbox[ui.panel.id]);
            $.get('service?service=mobile&action=remove&mobileno=tel:'+ui.panel.id);
            tabDiv.html('');
       }
     })
    
    //create mobile tab
    function createMobile(mobileNumber){
        createMobileView(mobileNumber);
        $mobile.tabs( "add", "#"+mobileNumber, mobileNumber );
        $.get('service?service=mobile&action=create&mobileno=tel:'+mobileNumber);
    }
    
    //create mobile tab view
    function createMobileView(mobileNumber)
    {
        $mobileDiv = '<div id="' + mobileNumber + '"></div>';
        $messageDataFieldSet = '<fieldset id="field_' + mobileNumber + '">/<fieldset>'
        $textAreaDiv = '<div style="width:400px;height:50px;" id="textArea_'+mobileNumber+'"></div>';
        $sendTextArea = '</p><textarea id="sendMessage_' + mobileNumber + '" class="ui-widget-content ui-corner-all" style="width:80%;height:99%;"></textarea>';
        $sendButton='</p><button id="sendButton_'+ mobileNumber +'">Send</button>';
        $clearInboxButton='</p><button id="clearInbox_'+ mobileNumber +'">Clear All</button>';
        $clearOutboxButton='</p><button id="clearOutbox_'+ mobileNumber +'">Clear All</button>';
        $inboxTable = '<table cellpadding="0" cellspacing="0" border="0" id="inbox_' + mobileNumber + '" class="display"><thead><tr><th>Received Date</th><th>Number</th><th>Message</th></tr></thead><tbody></tbody></table>';
        $outboxTable = '<table cellpadding="0" cellspacing="0" border="0" id="outbox_' + mobileNumber + '" class="display"><thead><tr><th>Send Date</th><th>Number</th><th>Message</th></tr></thead><tbody></tbody></table>';
        $messageLabel='</p><label for="sendMessage_' + mobileNumber + '">Message :</label>';
        $toNumberTextBox = '<input readonly="readonly" id="toNumber_' + mobileNumber + '" class="ui-widget-content ui-corner-all" value="77000" style="height:19px;"></>';
        $toNumberLabel='<label for="toNumber_' + mobileNumber + '">To Number :</label>'
        $("#mobiles").append($mobileDiv);
        $('#'+mobileNumber).append($messageLabel);
        $('#'+mobileNumber).append($textAreaDiv);
        $('#textArea_'+mobileNumber).append($sendTextArea);
        $('#'+mobileNumber).append($sendButton);
        $('#sendButton_'+mobileNumber)
            .button()
            .click(function(){
               var messageText = $('#sendMessage_'+mobileNumber); 
               var message = messageText.attr('value');
               $.get('service?service=mobile&action=send&mobileno=tel:'+mobileNumber+'&message='+message,function(resp)
                {
                    alert(resp['statusDetail']);
                    messageText.attr('value','');
                    messageText.focus();
                });
            });
        $('#'+mobileNumber).append('<h3>Inbox</h3>');
        $('#'+mobileNumber).append($inboxTable);
        tableCreate('inbox_' + mobileNumber);
        $('#'+mobileNumber).append($clearInboxButton);
        $('#clearInbox_'+mobileNumber)
            .button()
            .click(function(){
                $.get('service?service=mobile&action=clear&type=inbox&mobileno=tel:'+mobileNumber);
                $('#inbox_'+mobileNumber).dataTable().fnClearTable();
                initializeMessageLogTable();
            });           
        $('#'+mobileNumber).append('<h3>Outbox</h3>');
        $('#'+mobileNumber).append($outboxTable);
        tableCreate('outbox_' + mobileNumber);
        $('#'+mobileNumber).append($clearOutboxButton);
        $('#clearOutbox_'+mobileNumber)
            .button()
            .click(function(){
                $.get('service?service=mobile&action=clear&type=outbox&mobileno=tel:'+mobileNumber);
                $('#outbox_'+mobileNumber).dataTable().fnClearTable();
                initializeMessageLogTable();
            });        
        maxOutboxTime[mobileNumber]=0;
        setOutbox(mobileNumber);
        maxInboxTime[mobileNumber]=0;
        setInbox(mobileNumber);
    }
       
    //intialize table on mobile view
    function tableCreate(tableId)
    {
        $('#'+tableId).dataTable( {
        "sScrollY": "200px",
        "bAutoWidth": false,
        "bScrollCollapse": true,
        "bPaginate": false,
        "bInfo": false,
        "oLanguage": {
            "sEmptyTable": "No messages"
                     },
        "bJQueryUI": true,
        "aoColumns": [{"sWidth": "23%"}, {"sWidth": "10%"}, {"sWidth": "67%"}],
        "aaSorting": [[ 0, "desc" ]]
    })
        adjustTableColumnSizing(tableId);
    }
    
    //adjuest column size in table
    function adjustTableColumnSizing(tableId)
    {
        $('#'+tableId).dataTable().fnAdjustColumnSizing();
    }
    
    //mobile button
    $( "#create_mobile" )
        .button()
        .click(function(){
            if($mobileNumberInput.val().trim()==""){
                alert("Please enter the Mobile Number");
                $mobileNumberInput.focus();
                return;
            }
            if(document.getElementById($mobileNumberInput.val().trim())){
                alert("Mobile Number already exists.");
                $mobileNumberInput.attr('value','');
                $mobileNumberInput.focus();
                return;
            }
            createMobile($mobileNumberInput.val().trim());
            $mobileNumberInput.attr('value','');
        });
            
    // mobile tab close icon
    $( "#mobiles span.ui-icon-close" ).live( "click", function() {
        var index = $( "li", $mobile ).index( $( this ).parent() );
        $mobile.tabs( "remove", index );
    });
});

//set inbox table
function setInbox(number)
{
    $.get('service?service=mobile&action=get&type=inbox&mobileno=tel:'+number+'&time='+maxInboxTime[number],function(resp)
    {
        for (index in resp)
        {
            var sms=resp[index];
            maxInboxTime[number]=sms.time;
            if(sms.sourceAddress)
                addInboxSMS(sms.time,number,sms.message,sms.sourceAddress);
            else
                addInboxSMS(sms.time,number,sms.message,mobilePort);
        }
    });
timeOutInbox[number]=setTimeout('setInbox("'+number+'");', 1000);
}

//set outbox table
function setOutbox(number)
{
    $.get('service?service=mobile&action=get&type=outbox&mobileno=tel:'+number+'&time='+maxOutboxTime[number],function(resp)
    {
        for (index in resp)
        {
            var sms=resp[index];
            maxOutboxTime[number]=sms.time;
            addOutboxSMS(sms.time,number,sms.message,mobilePort);
        }
    });
timeOutOutbox[number]=setTimeout('setOutbox("'+number+'");', 1000);
}
 
//add sms details into inbox table 
function addInboxSMS(date,number,message,sourceNumber)
{
    if(document.getElementById(number))
    {
        $('#inbox_'+number).dataTable().fnAddData( [
            date,
            sourceNumber,
            escapeHTML(message)
        ] );
    }
}

//add sms details into outbox table
function addOutboxSMS(date,number,message,sourceAddress)
{
    if(document.getElementById(number))
    {
        $('#outbox_'+number).dataTable().fnAddData( [
        date,
        sourceAddress,
        escapeHTML(message)
        ] );
    }
}

/******************************************************
Utility Functions
******************************************************/

function escapeHTML(string)
{
    return jQuery('<pre>').text(string).html();
}