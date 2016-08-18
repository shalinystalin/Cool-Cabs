<%-- 
    Document   : Login
    Created on : 15 Apr, 2016, 1:12:24 AM
    Author     : Vivek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <title>CoolCabs - Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>
    <link href="css/login-registration-style.css" rel="stylesheet" type="text/css" media="all" />
    <body background="images/taxi-bg.jpg">
    <!----------start sign_up----------->
    <div class="sign_up">
            <!----------start form----------->
            <form class="sign" action="ValidateLogin" method="post">
                    <div class="formtitle" >Login</div>
                    <!----------start top_section----------->
                    <div class="top_section">
                            <div class="section">
                                    <div class="input-sign username">
                                        <input type="text" name="username" id="username" placeholder="User Name"/> 
                                    </div>
                            </div>
                            <div class="section">
                                    <div class="input-sign password">
                                        <input type="password" name="password" id="password" placeholder="Password"/>
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                            
                    </div>
                    <!----------end top_section----------->
                    <!----------start bottom-section----------->
                    <div class="bottom-section">
                            <div class="submit">
                                <input class="bluebutton submitbotton" type="submit" id="submit" value="Login" />
                            </div>
                            <div class="section details">
                                <p style="font-size: 14px; text-align: center; padding-bottom:20px;">
                                    New user? <a href="Register.jsp" style="font-weight:bold">Register here</a> 
                                </p>
                            </div>
                            <div class="section details1">
                                <p style="font-size: 14px; text-align: center; padding-bottom:20px;">
                                    Forgot Password? <a href="ResetPassword.jsp" style="font-weight:bold">Reset here</a> 
                                </p>
                            </div>
                    </div>
                    <!----------end bottom-section----------->
            </form>
    <!----------end form----------->
    </div>
</html>
