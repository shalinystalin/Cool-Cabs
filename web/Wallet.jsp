<%-- 
    Document   : Wallet
    Created on : 19 Apr, 2016, 6:13:22 PM
    Author     : Vivek
--%>

<%@page import="Wallet.GetWalletDetails"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<%
  
    String username = null;
    String firstname = null;
    
    if(session != null)
    {
        if(session.getAttribute("username") != null)
        {
            username = session.getAttribute("username").toString();
            firstname = session.getAttribute("firstname").toString();
        }
        else
        {
            response.sendRedirect("Login.jsp");
        }
    }

%>

<!DOCTYPE html>
<html>    
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Cool Cabs - Wallet</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/login-registration-style.css" rel="stylesheet" type="text/css" media="all" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script>
            function validateTransferAmount(){
                
                var walletAmount = document.getElementById("walletAmount").value;
                var transferAmount = document.getElementById("amount").value;
                
                if(transferAmount > walletAmount)
                {
                    alert("Enter a amount less than or equal to the available Wallet amount.");
                    document.getElementById("amount").focus();
                    return false;
                }
                
                if(transferAmount <= 0)
                {
                    alert("Enter a amount that is greater than zero.");
                    document.getElementById("amount").focus();
                    return false;
                }
            }
        </script>
    </head>
    
    <body>
        <!-- begin template -->
        <div class="navbar navbar-custom navbar-fixed-top">
            <div class="navbar-header"><a class="navbar-brand" href="MyProfile.jsp">My Profile</a></div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#">Hello <%= firstname %>!</a></li>
                    <li><a href="Home.jsp">Home</a></li>
                    <li><a href="MyProfile.jsp">My Profile</a></li>
                    <li><a href="#">About this Project</a></li>
                    <li style="float:right"><a href='Logout?username=<%= username %>'>Logout</a></li>
                    <li>&nbsp;</li>
                </ul>
            </div>
        </div>
        <div class="container-fluid" id="main">
            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-1"></div>
                    <div class="col-xs-10">
                        
                        <!-- Profile -->
                        
                        <%@page import="Profile.GetUserDetails" %>
                        <%
                            String walletAmount = new GetWalletDetails().getAmount(username);
                        %>
                        
                        <!-- Form -->
                        <form action="TransferFund" method="post" onsubmit="return validateTransferAmount()">
                        
                                <br/>
                                <div class="panel panel-default">
                                    <div class="panel-heading"><h4>Wallet</h4></div>
                                    <div class="panel-body">
                                        <div class="input-sign details" style="color:#9d9d9d">Amount</div>
                                        <div class="panel-title details1" style="border-left:0px">$ <%=walletAmount%></div>
                                        <input type="hidden" id="walletAmount" value="<%=walletAmount%>">
                                    </div>
                                </div>
                                <div class="panel panel-default"> 
                                    <div class="panel-heading"><h4>Transfer Fund</h4></div>
                                    <div class="panel-body">
                                        <div class="input-sign details">
                                            <input type="hidden" id="username" name="username" value="<%=username%>">
                                            <input type="text" id="username" name="recipientUsername" style="width:60%" value="" placeholder="Recipient's Username">
                                            <input type="text" id="amount" name="amount" style="width:25%" value="" placeholder="Amount">
                                        </div>
                                        <div class="input-sign details">
                                            <input type="submit" class="center-block btn btn-primary" style="width:40%;margin-top: 1%" value="Transfer">
                                        </div>
                                    </div>
                                </div>
                        
                        </form>

                    </div>
                </div>
            </div>
        </div>
        <!-- end template -->
        
        <!-- script references -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/profile.js"></script>
         
    </body>
</html>
    
    



