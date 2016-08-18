<%-- 
    Document   : MyProfile
    Created on : 18 Apr, 2016, 5:42:23 PM
    Author     : Vivek
--%>

<%@page import="Profile.GetUserDetails"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Cool Cabs - My Profile</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/login-registration-style.css" rel="stylesheet" type="text/css" media="all" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    
    <body>
        <!-- begin template -->
        <div class="navbar navbar-custom navbar-fixed-top">
            <div class="navbar-header"><a class="navbar-brand" href="MyProfile.jsp">My Profile</a></div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#">Hello <%= firstname %>!</a></li>
                    <li><a href="Home.jsp">Home</a></li>
                    <li><a href="#">Wallet</a></li>
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
                            List<String> userDetails = new ArrayList<>();
                            userDetails = new GetUserDetails().getDetails(username);                            
                        %>
                        
                        <!-- Form -->
                        <form action="EditProfile" method="post">
                        
                                <br/>
                                <div class="panel panel-default">
                                    <div class="panel-heading"><h4>Profile</h4></div>
                                    <div class="panel-body">
                                        <div class="input-sign details" style="color:#9d9d9d">First Name</div>
                                        <div class="input-sign details1" style="color:#9d9d9d;border-left:0px">Last Name</div>
                                        <div class="panel-title details1" style="border-left:0px"><%=userDetails.get(1)%></div>
                                        <div class="panel-title details1" style="border-left:0px"><%=userDetails.get(2)%></div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="input-sign details" style="color:#9d9d9d">Email</div>
                                        <div class="input-sign details1" style="color:#9d9d9d;border-left:0px">Date of Birth</div>
                                        <div class="panel-title details" style="border-left:0px" id="oldemail"><%=userDetails.get(3)%></div>
                                        <div class="input-sign details"><input type="hidden" id="email" name="email" value="<%=userDetails.get(3)%>"></div>
                                        <div class="panel-title details1" style="border-left:0px"><%=userDetails.get(4)%></div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="input-sign details" style="color:#9d9d9d">Phone</div>
                                        <div class="input-sign details1" style="color:#9d9d9d;border-left:0px">Address</div>
                                        <div class="panel-title details" style="border-left:0px" id="oldphone"><%=userDetails.get(5)%></div>
                                        <div class="input-sign details"><input type="hidden" id="phone" name="phone" value="<%=userDetails.get(5)%>"></div>
                                        <div class="panel-title details1" style="border-left:0px" id="oldaddress"><%=userDetails.get(6)%></div>
                                        <div class="input-sign details1"><input type="hidden" id="address" name="address" value="<%=userDetails.get(6)%>"></div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="input-sign details" style="color:#9d9d9d">City</div>
                                        <div class="input-sign details1" style="color:#9d9d9d;border-left:0px">Province</div>
                                        <div class="panel-title details" style="border-left:0px" id="oldcity"><%=userDetails.get(7)%></div>
                                        <div class="input-sign details"><input type="hidden" id="city" name="city" value="<%=userDetails.get(7)%>"></div>
                                        <div class="panel-title details1" style="border-left:0px" id="oldprovince"><%=userDetails.get(8)%></div>
                                        <div class="input-sign details1"><input type="hidden" id="province" name="province" value="<%=userDetails.get(8)%>"></div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="input-sign details">
                                            <br/><input type="button" id="editProfileButton" class="submitbotton" style="width:40%;margin-left:30%" value="Edit Profile"/>
                                        </div>
                                        <div class="input-sign details1" style="border-left:0px">
                                            <br/><input type="button" id="changePasswordButton" class="submitbotton" style="width:40%;margin-left:30%" value="Change Password" onclick="location.href = 'ResetPassword.jsp'"/>
                                        </div>
                                        <div class="input-sign details">
                                            <br/><input type="hidden" id="editProfilePassword" name="password" style="width:40%;margin-left:30%" placeholder="Enter your Password" required/>
                                        </div>
                                        <div class="input-sign details1">
                                            <br/><input type="hidden" id="saveProfileButton" class="center-block btn btn-primary" style="width:40%;margin-left:30%" value="Save Profile"/>
                                        </div>
                                        <input type="hidden" id="username" name="username" value="<%=userDetails.get(0)%>">
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
    
    


