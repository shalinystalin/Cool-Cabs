/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

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
public class EditProfile extends HttpServlet {

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
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String province = request.getParameter("province");
            String password = request.getParameter("password");
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
            out.println("<div class=\"formtitle\" >CoolCabs - Edit Profile</div>");
            out.println("<div class=\"title\"></br>");
            
            DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
            DBCollection passwordColl = new MongoDB.MongoJDBCConnection().connect("password");
            
            //Mongo Query to check if an account with this username already exists.
            DBCursor cursor = usersColl.find(new BasicDBObject("username", username));
            DBObject doc = cursor.one();
            
            DBCursor cursorP = passwordColl.find(new BasicDBObject("username", username));
            DBObject docP = cursorP.one();
            
            String salt = doc.get("salt").toString();
            String passwordHash = new SHA256().getHashOf(password);
            String finalPasswordHash = new SHA256().getHashOf(salt+passwordHash);
            
            if(docP.get("password").equals(finalPasswordHash))
            {
                try{
                    
                    BasicDBObject updateUserDoc = new BasicDBObject();
                    updateUserDoc.put("email", email);
                    updateUserDoc.put("phone", phone);
                    updateUserDoc.put("address", address);
                    updateUserDoc.put("city", city);
                    updateUserDoc.put("province", province);

                    BasicDBObject updateQuery = new BasicDBObject();
                    updateQuery.put("$set", updateUserDoc);

                    BasicDBObject userSearchQuery = new BasicDBObject();
                    userSearchQuery.put("username", username);

                    usersColl.update(userSearchQuery, updateQuery);
                    
                    msg = "Updated Successfully.";
                }
                catch(Exception e){
                    msg = "Error while updating to Database";
                }
            }
            else{
                msg = "Incorrect Password!";
            }
            
            msg+= "<br/><br/> Click <a href='MyProfile.jsp'>here</a> to go back to your profile page.";
            
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
