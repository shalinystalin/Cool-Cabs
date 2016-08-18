<%-- 
    Document   : AdminPage
    Created on : 21 Apr, 2016, 10:55:52 PM
    Author     : Vivek
--%>

<%@page import="com.mongodb.DBObject"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.DBCursor"%>
<%@page import="com.mongodb.DBCollection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<%
  
    String username = null;
    String firstname = null;
    
    if(session != null)
    {
        if(session.getAttribute("username").equals("admin"))
        {
            username = session.getAttribute("username").toString();
            firstname = session.getAttribute("firstname").toString();
        }
        else
        {
            username = session.getAttribute("username").toString();
            firstname = session.getAttribute("firstname").toString();
            response.sendRedirect("Home.jsp");
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
            <div class="navbar-header"><a class="navbar-brand" href="IssueLog.jsp">Issue Log</a></div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#">Hello Admin!</a></li>
                    <li><a href="Home.jsp">Home</a></li>
                    <li><a href="#">Wallet</a></li>
                    <li><a href="#">About this Project</a></li>
                    <li><a href="IssueLog.jsp">Issue Log</a></li>
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
                            <br/>
                            <div class="panel panel-default">
                                <div class="panel-heading"><h4>Issues</h4></div>
                                <div class="panel-body">
                                    <table>
                                        <tr>
                                            <th>_ID</th>
                                            <th>USER NAME</th>
                                            <th>STATUS</th>
                                            <th>DESCRIPTION</th>
                                            <th>CLIENT IP</th>
                                            <th>TIME</th>
                                        </tr>
                                        <%
                                            try{
                                                DBCollection issueColl = new MongoDB.MongoJDBCConnection().connect("issues");
                                                DBCursor cursor = issueColl.find();
                                                while(cursor.hasNext())
                                                {
                                                    DBObject doc = cursor.next();
                                        %>
                                        <tr>
                                            <td><%=doc.get("_id")%></td>
                                            <td><%=doc.get("username")%></td>
                                            <td><%=doc.get("status")%></td>
                                            <td><%=doc.get("desc")%></td>
                                            <td><%=doc.get("clientIP")%></td>
                                            <td><%=doc.get("date")%></td>
                                        </tr>
                                        <%
                                                }
                                            }
                                            catch(Exception e)
                                            {
                                                System.out.println("Database Connectivity error.");
                                            }
                                        %>
                                        
                                    </table>
                                </div>
                            </div>
                            
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
    
    



