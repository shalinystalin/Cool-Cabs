/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginLogoutRegister;

import HashingAlgorithm.SHA256;
import SecurityControllers.GetClientIPAddress;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vivek
 */
public class ValidateLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CoolCabs - Login</title>"); 
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
            out.println("<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>");
            out.println("<link href=\"css/login-registration-style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"sign_up\">");
            out.println("<div class=\"formtitle\" >CoolCabs - Login</div>");
            out.println("<div class=\"title\"></br>");
            
            String username;
            String password;
            String salt;
            String passwordHash;
            String saltwithpasswordHash;
            String finalPasswordHash; //Hash of the entered password
            String msg = "";
            String clientIP = new GetClientIPAddress().getIP(request);
            String accountStatus;
            
            
            username = request.getParameter("username");
            password = request.getParameter("password");
            
            DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
            DBCollection passwordColl = new MongoDB.MongoJDBCConnection().connect("password");
            DBCollection honeyCheckColl = new MongoDB.MongoJDBCConnection().connect("honeycheck");
            DBCollection honeywordsColl = new MongoDB.MongoJDBCConnection().connect("honeywords");
            
            //Mongo Query to check if an account with this username already exists.
            DBCursor cursor = usersColl.find(new BasicDBObject("username", username));

            while(cursor.hasNext())
            {
                DBObject doc = cursor.next();
                
                if(doc.get("username").equals(username))
                {
                    accountStatus = doc.get("accountStatus").toString();
                    
                    salt = doc.get("salt").toString();
                    passwordHash = new SHA256().getHashOf(password);
                    saltwithpasswordHash = salt + passwordHash;
                    finalPasswordHash = new SHA256().getHashOf(saltwithpasswordHash);
                    
                    //retrieve password that is stored in the database
                    DBCursor cursorP = passwordColl.find(new BasicDBObject("username", username));
                    DBObject docP = cursorP.one();                   
                    
                    //retrieve the correct password index from the database
                    DBCursor cursorHC = honeyCheckColl.find(new BasicDBObject("username", username));
                    DBObject docHC = cursorHC.one();
                    int correctPasswordIndex = Integer.valueOf(docHC.get("index").toString())+1;
                    List<String> honeywords = (List<String>) docP.get("password");
                    boolean honeywordUsed = false;
                    String correctPassword = null;
                    
                    //check for login attempt with honeyword. If yes block the user.
                    for(int i=0; i<honeywords.size(); i++)
                    {
                        if(i == correctPasswordIndex)
                        {
                            //get the correct password
                            correctPassword = honeywords.get(i);
                            honeywordUsed = false;
                            continue;
                        }
                        
                        else if(honeywords.get(i).equals(finalPasswordHash))
                        {
                            
                            honeywordUsed = true;
                            String desc = "Login attempt with a Honeyword.";
                            new Issues.LogIssue().NewIssue(username, desc, clientIP, "blocked");
                            doc.put("accountStatus", "blocked");
                            usersColl.save(doc);
                            out.println("honeywordUsed" + honeywordUsed);
                        }
                    }
                    
                    //check if account is blocked or if honeyword is used
                    if(honeywordUsed)
                    {
                        msg = "<br/>HoneyWord-This account is blocked for security reasons.<br/>"
                                + "Please contact the Administrator. (coolcabsamerica@gmail.com)<br/><br/>";
                        msg+= "Your IP Address ("+clientIP+") is being tracked for security purpose.";
                    }
                    else if(correctPassword.equals(finalPasswordHash))
                    {       
                        if(doc.get("accountStatus").equals("blocked"))
                        {
                            msg = "Blocked-This account is blocked for security reasons.<br/>"
                                    + "Please contact the Administrator. (coolcabsamerica@gmail.com)<br/><br/>";
                            msg+= "Your IP Address ("+clientIP+") is being tracked for security purpose.";
                        }
                        if( accountStatus.equals("active") || accountStatus.equals("loggedOut") )
                        {       
                            HttpSession session = request.getSession(true);
                            
                            String sessionUsername = "";
                            try{
                                sessionUsername = session.getAttribute("username").toString();
                            }
                            catch(Exception e)
                            {
                                System.out.println(e);
                            }
                            
                            if(sessionUsername.equals(""))
                            {
                                session.setAttribute("username", doc.get("username"));
                                session.setAttribute("firstname", doc.get("firstname"));
                                session.setAttribute("loggedin", "true");
                                session.setAttribute("clientIP", clientIP);
                                Date dateEDT = null;
                                try{
                                    DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                                    formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
                                    dateEDT = formatter.parse(formatter.format(new Date()));
                                }
                                catch(Exception e){System.out.println(e);}
                                session.setAttribute("startTime", dateEDT);
                                
                                //insert session details into the USERS database
                                BasicDBObject sessionDetails = new BasicDBObject();
                                sessionDetails.put("startTime", session.getAttribute("startTime"));
                                sessionDetails.put("clientIP", session.getAttribute("clientIP"));
                                
                                BasicDBObject sessionDoc = new BasicDBObject();
                                sessionDoc.put("lastSession", sessionDetails);
                                
                                doc.put("accountStatus", "loggedIn");
                                doc.put("lastSession", sessionDetails);
                                usersColl.save(doc);
                                
                                if(username.equals("admin"))
                                {
                                    response.sendRedirect("IssueLog.jsp");
                                }
                                else
                                {
                                    response.sendRedirect("Home.jsp");
                                }
                            }
                        }
                        else if(accountStatus.equals("loggedIn"))
                        {
                            String desc = "Login attempt when already logged in.";
                            new Issues.LogIssue().NewIssue(username, desc, clientIP, accountStatus);
                            msg = "This account is already logged in.<br/><br/>";
                            msg+= "Your IP Address ("+clientIP+") is being tracked for security purpose.";
                        }
                        else if(accountStatus.equals("created"))
                        {
                            msg = "Your account is not active. Please activate it using the link that was emailed to your registered email address.";
                        }
                        cursorP.close();
                    }
                }
            }
            
            cursor.close();
            
            if(msg.equals(""))
                msg = "The username and password do not match. Please try again. [<a href='Login.jsp'>Login</a>]";
            out.println(msg);
            
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
