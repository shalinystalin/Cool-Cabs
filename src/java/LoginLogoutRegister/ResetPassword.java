/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginLogoutRegister;

import HashingAlgorithm.SHA256;
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
public class ResetPassword extends HttpServlet {

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
            
            out.println("<html>");
            out.println("<title>CoolCabs - Login</title>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
            out.println("<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>");
            out.println("<link href=\"css/login-registration-style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />");
            
            String username = request.getParameter("username");
            String question1;
            String answer1;
            String question2;
            String answer2;
            
            DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
            
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
            
            if(usernameExists == true)
            {
                question1 = doc.get("question1").toString();
                answer1 = doc.get("answer1").toString();
                
                question2 = doc.get("question2").toString();
                answer2 = doc.get("answer2").toString();
                
                out.println("<div class=\"sign_up\">");
                out.println("<form class=\"sign\" action=\"ValidateNewPassword\" method=\"post\">");
                out.println("<div class=\"formtitle\">Reset Password</div>");
                out.println("<div class=\"top_section section\">");
                out.println("<p style='padding:15px'>Please answer the below questions</p>");
                out.println("</div>");
                out.println("<div style='padding:15px' class=\"top_section section\">");
                out.println("<input type=\"text\" name=\"answer1\" id=\"answer1\" style='width:100%;padding:10px' required placeholder=\""+question1+"\"/>");
                out.println("</div>");
                out.println("<div style='padding:15px' class=\"top_section section\">");
                out.println("<input type=\"text\" name=\"answer2\" id=\"answer2\" style='width:100%;padding:10px' required placeholder=\""+question2+"\"/>");
                out.println("</div>");
                out.println("<div class=\"top_section section\">");
                out.println("<p style='padding:15px'>New Password</p>");
                out.println("</div>");
                out.println("<div style='padding:15px' class=\"top_section section\">");
                out.println("<input type=\"password\" name=\"newPassword1\" id=\"newPassword1\" style='width:100%;padding:10px' required placeholder=\"Enter New Password\" pattern=\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\" \n" +
"                                               title=\"Password must contain at least eight characters including at least one lowercase letter, uppercase letter and a number\"/>");
                out.println("</div>");
                out.println("<div style='padding:15px' class=\"top_section section\">");
                out.println("<input type=\"password\" name=\"newPassword2\" id=\"newPassword2\" style='width:100%;padding:10px' required placeholder=\"Re-Enter New Password\" pattern=\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\" \n" +
"                                               title=\"Password must contain at least eight characters including at least one lowercase letter, uppercase letter and a number\"/>");
                out.println("</div>");
                out.println("<div class=\"bottom_section submit\">");
                out.println("<input class=\"bluebutton submitbotton\" type=\"submit\" id=\"submit\" value=\"Reset\" />");
                out.println("<input type=\"hidden\" name=\"username\" id=\"username\" value=\""+ username +"\" />");
                out.println("</div>");
                
            }
            else
            {
                out.println("<div class=\"bottom_section submit\">");
                out.println("An account with user name '"+username+"' does not exists.");
                out.println("</div>");
            }
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
