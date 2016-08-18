<%-- 
    Document   : Home
    Created on : 10 Apr, 2016, 6:35:32 PM
    Author     : Vivek
--%>

<%@page import="Maps.AvailableCabs"%>
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
        <title>Cool Cabs</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script type="text/javascript">
            function validateForm()
            {
                if(document.getElementById("selectCab").value==="null")
                {
                  alert("Please select a cab from the drop down menu.");
                  document.getElementById("selectCab").focus();
                  return false;
                }
                if(document.getElementById("start").value==="")
                {
                  alert("A starting address is required.");
                  document.getElementById("start").focus();
                  return false;
                }
                if(document.getElementById("end").value==="")
                {
                  alert("A destination address is required.");
                  document.getElementById("end").focus();
                  return false;
                }
            }
        </script>
    </head>
    
    <body>
        <!-- begin template -->
        <div class="navbar navbar-custom navbar-fixed-top">
            <div class="navbar-header"><a class="navbar-brand" href="Home.jsp">Cool Cabs</a>
                <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                </a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#">Hello <%= firstname %>!</a></li>
                    <li><a href="MyProfile.jsp">My Profile</a></li>
                    <li><a href="Wallet.jsp">Wallet</a></li>
                    <li><a href="#">About this Project</a></li>
                    <li style="float:right"><a href='Logout?username=<%= username %>'>Logout</a></li>
                    <li>&nbsp;</li>
                </ul>
            </div>
        </div>
        
        <div id="map-canvas"></div>
        <div class="container-fluid" id="main">
            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-9" id="left">
                        <!-- item list -->
                        <div id="map"></div>
                        <hr/>
                        <div class="panel panel-default">
                          <div class="panel-heading"><a href="">Item heading</a></div>
                        </div>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis pharetra varius quam sit amet vulputate. 
                          Quisque mauris augue, molestie tincidunt condimentum vitae, gravida a libero. Aenean sit amet felis 
                          dolor, in sagittis nisi. Sed ac orci quis tortor imperdiet venenatis. Duis elementum auctor accumsan. 
                          Aliquam in felis sit amet augue.</p>     

                    </div>
                    <form action="BookRide" method="post" onSubmit="return validateForm()">
                        <div class="col-xs-3">
                            <h3>Search Cabs</h3>
                            <hr/>                            
                            <input type="button" id="locateButton" class="center-block btn" value="Use My Current Location">
                            <br/>
                            <b>Start:</b>
                            <input type="text" id="start" name="start" class="form-control" value="">
                            <b>End: </b>
                            <input type="text" id="end" name="end" class="form-control" value="Cyber Towers, Hyderabad">
                            <br/>
                            <input type="button" id="searchButton" class="center-block btn btn-primary" value="Search"> 

                            <div>
                                <table>
                                    <tr>
                                        <th>Total Available Cabs:</th>
                                      <th><p id="availableCabsTotal" ></th>
                                    </tr>
                                    <tr>
                                      <td>Mini Cabs:</td>
                                      <td><p id="availableCabsMini" ></td>
                                    </tr>
                                    <tr>
                                      <td>Plus Cabs:</td>
                                      <td><p id="availableCabsPlus" ></td>
                                    </tr>
                                    <tr>
                                      <td>Pool Cabs:</td>
                                      <td><p id="availableCabsPool" ></td>
                                    </tr>
                                </table>
                            </div>
                            
                            <br/>
                            <select id="selectCab" name="selectCab" style="width:100%;padding:5px;">
                                <option value="null">Select a Cab</option>
                            </select>
                            
                            <br/>
                            <input type="hidden" id="bookCabButton" class="center-block btn btn-primary" style="margin-top:10px;" value="Ride Now">

                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- end template -->
        
        <!-- script references -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/map.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA4BU2soNpZXnAzCw3avmKO61o0Si4Ys-8&libraries=places&callback=initMap"
                async defer></script>
         
    </body>
</html>
    
    

