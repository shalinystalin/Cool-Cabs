/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginLogoutRegister;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.security.pkcs11.wrapper.Functions;

/**
 *
 * @author Vivek
 */
public class Logout extends HttpServlet {

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
            
            String msg = null;
            
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
            
            try{
                
                HttpSession session = request.getSession(false);
                
                if(session.getAttribute("username").equals(request.getParameter("username")))
                {
                    try{
                        //insert session details into the USERS database  
                        DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");

                        DBCursor cursor = usersColl.find(new BasicDBObject("username", session.getAttribute("username").toString()));
                        DBObject doc = cursor.one();

                        BasicDBObject sessionDetails = new BasicDBObject();
                        sessionDetails.put("startTime", session.getAttribute("startTime"));
                        sessionDetails.put("clientIP", session.getAttribute("clientIP"));
                        sessionDetails.put("endTime", session.getAttribute("endTime"));

                        doc.put("lastSession", sessionDetails);
                        doc.put("accountStatus", "loggedOut");
                        usersColl.save(doc);
                    }
                    catch(Exception e){
                        out.println("Error while updating the Database.");
                    }

                    session.invalidate();
                    
                    msg = "Logged out Successfully.";
                }
                else{
                    msg = "Error while authenticating the user.";
                }
                
            }
            catch(Exception e){
                msg = "No records found.";
            }
            
            out.println("<p>"+msg+"</p>");
            
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
        processRequest(request, response);
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
        //processRequest(request, response);
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
