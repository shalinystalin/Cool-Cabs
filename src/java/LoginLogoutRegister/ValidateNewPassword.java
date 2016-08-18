/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginLogoutRegister;

import HashingAlgorithm.SHA256;
import HashingAlgorithm.Salt;
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

/**
 *
 * @author Vivek
 */
public class ValidateNewPassword extends HttpServlet {

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
            
            String username = request.getParameter("username");
            String answer1 = request.getParameter("answer1");
            String answer2 = request.getParameter("answer2");
            String newPassword1 = request.getParameter("newPassword1");
            String newPassword2 = request.getParameter("newPassword2");
            String msg = null;
            
            String salt;
            String passwordHash;
            String saltwithpasswordHash;
            String finalPasswordHash;
            
            out.println("<html>");
            out.println("<title>CoolCabs - Login</title>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
            out.println("<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>");
            out.println("<link href=\"css/login-registration-style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />");

            DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
            DBCollection passwordColl = new MongoDB.MongoJDBCConnection().connect("password");
            
            //Mongo Query to check if an account with this username already exists.
            DBCursor cursor = usersColl.find(new BasicDBObject("username", username));
            DBObject doc = cursor.one();
            
            boolean usernameExists = false;
            
            if(doc == null)
            {
                usernameExists = false;
            }
            else{
                
                if((doc.get("username").equals(username)))
                {
                    usernameExists = true;
                }
            }
            
            if(usernameExists)
            {
                if( (doc.get("answer1").equals(answer1)) && (doc.get("answer2").equals(answer2)) )
                {
                    if(newPassword1.equals(newPassword2))
                    {                                           
                        
                        
                        //generate a new salt and passwordHash of new password
                        salt = new Salt().generateSalt();
                        passwordHash = new SHA256().getHashOf(newPassword1);
                        saltwithpasswordHash = salt + passwordHash;
                        finalPasswordHash = new SHA256().getHashOf(saltwithpasswordHash);
                        
                        //update USERS collection with new salt 
                        BasicDBObject newUserDocument = new BasicDBObject();
                        newUserDocument.put("$set", new BasicDBObject("salt", salt));
                        BasicDBObject userSearchQuery = new BasicDBObject();
                        userSearchQuery.put("username", username);
                        usersColl.update(userSearchQuery, newUserDocument);
                        
                        
                        
                        //update PASSWORD collection with newPasswordHash
                        BasicDBObject newPasswordDocument = new BasicDBObject();
                        newUserDocument.put("$set", new BasicDBObject("password", finalPasswordHash));
                        BasicDBObject passwordSearchQuery = new BasicDBObject();
                        passwordSearchQuery.put("username", username);
                        passwordColl.update(passwordSearchQuery, newPasswordDocument);

                        msg = "Your Password has been updated.";
                        
                        out.println("old cursor: "+cursor);
                        out.println("<br/><br/>old doc: "+doc);
                        cursor.close();
                        
                        DBCursor cursor1 = passwordColl.find(new BasicDBObject("username", username));
                        DBObject doc1 = cursor.one();
                        out.println("<br/><br/>new cursor: "+cursor1);
                        out.println("<br/><br/>new doc"+doc1);
                        
                        //fetch name and email to send an email notification to the user about the password update.
                        String firstname = doc.get("firstname").toString();
                        String email = doc.get("email").toString();
                        
                        new Mails.PasswordUpdateMail().Send(firstname, email, newPassword1);
                        
                    }
                    else
                    {
                        msg = "Passwords do not match. Please try again.";
                    }
                }
                else
                {
                    msg = "The answers do not match our records. Please try again.";
                }
            }
            else
            {
                msg = "An account with this username does not exists.";
            }
            
            out.println("<div class=\"bottom_section submit\"> " + msg + " </div>");
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
