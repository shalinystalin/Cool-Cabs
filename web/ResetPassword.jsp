<%-- 
    Document   : ResetPassword
    Created on : 15 Apr, 2016, 4:11:25 PM
    Author     : Vivek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CoolCabs - Reset Password</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>
        <link href="css/login-registration-style.css" rel="stylesheet" type="text/css" media="all" />        
    </head>
    
    <body>
    <!----------start sign_up----------->
    <div class="sign_up">
            <!----------start form----------->
            <form class="sign" action="ResetPassword" method="post" >
                    <div class="formtitle" >Reset Password</div>
                    <!----------start top_section----------->
                    <div class="top_section">
                            <div class="section">
                                    <div class="input-sign details">
                                        <input type="text" name="username" id="username" placeholder="User Name" required/> 
                                    </div>
                                    
                            </div>
                            
                    </div>
                    <!----------end top_section----------->
                    <!----------start bottom-section----------->
                    <div class="bottom-section">
                            <div class="submit section">
                                <input class="bluebutton submitbotton" type="submit" id="reset" onclick="showSecurityQuestions()" value="Reset" />
                            </div>
                    </div>
            </form>
        <!----------end form----------->
        </div>
    </b
</html>
