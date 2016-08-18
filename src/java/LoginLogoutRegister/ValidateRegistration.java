/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginLogoutRegister;

import HashingAlgorithm.SHA256;
import HashingAlgorithm.Salt;
import Mails.FundTransferCompletedCreditMail;
import Mails.RegistrationConfirmationMail;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vivek
 */
public class ValidateRegistration extends HttpServlet {

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
            
            String username;
            String password;
            String firstname;
            String lastname;
            String email;
            String dob;
            String phone;
            String address;
            String city;
            String province;
            String question1;
            String answer1;
            String question2;
            String answer2;
            String accountStatus;
            String accountActivationKey = null;
            Date dateEDT = null;
            String salt;
            String passwordHash;
            String saltwithpasswordHash;
            String finalPasswordHash;
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>New Registration</title>"); 
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
            out.println("<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>");
            out.println("<link href=\"css/login-registration-style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"sign_up\">");
            out.println("<div class=\"formtitle\" >New Registration</div>");
            out.println("<div class=\"title\">Dear User,</br></br>");
            
            DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
            DBCollection passwordColl = new MongoDB.MongoJDBCConnection().connect("password");
            DBCollection honeyCheckColl = new MongoDB.MongoJDBCConnection().connect("honeycheck");
            DBCollection honeywordsColl = new MongoDB.MongoJDBCConnection().connect("honeywords");
            
            username = request.getParameter("username");
            password = request.getParameter("password");
            
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
                out.println("An account with user name '"+username+"' already exists.");
                //getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
            }
            else
            {
                salt = new Salt().generateSalt();
                passwordHash = new SHA256().getHashOf(password);
                saltwithpasswordHash = salt + passwordHash;
                finalPasswordHash = new SHA256().getHashOf(saltwithpasswordHash);
                
                try{
                    Date date = new Date();
                    DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
                    dateEDT = formatter.parse(formatter.format(date));
                }
                catch(Exception e)
                {
                    out.println("Created Date Error " + e);
                }

                //insert to USERS Collection
                BasicDBObject userDocument = new BasicDBObject();
                
                userDocument.put("username", username);
                userDocument.put("createdDate", dateEDT);
                
                accountStatus = "created";
                accountActivationKey = new SHA256().getHashOf(salt+username);
                
                userDocument.put("accountStatus", accountStatus);
                userDocument.put("accountActivationKey", accountActivationKey);
                userDocument.put("salt", salt);
                
                firstname = request.getParameter("firstname");
                lastname = request.getParameter("lastname");
                userDocument.put("firstname", firstname);
                userDocument.put("lastname", lastname);

                email = request.getParameter("email");
                dob = request.getParameter("dob");
                userDocument.put("email", email);
                userDocument.put("dob", dob);
                
                phone = request.getParameter("phone");
                address = request.getParameter("address");
                userDocument.put("phone", phone);
                userDocument.put("address", address);
                
                city = request.getParameter("city");
                province = request.getParameter("province");
                userDocument.put("city", city);
                userDocument.put("province", province);
                
                question1 = request.getParameter("question1");
                answer1 = request.getParameter("answer1");
                userDocument.put("question1", question1);
                userDocument.put("answer1", answer1);

                question2 = request.getParameter("question2");
                answer2 = request.getParameter("answer2");
                userDocument.put("question2", question2);
                userDocument.put("answer2", answer2);
                
                userDocument.put("walletAmount", new Float(10));
                
                List<BasicDBObject> userDocumentRidesDetail = new ArrayList<>();
                userDocument.put("rides", userDocumentRidesDetail);
                
                List<BasicDBObject> userDocumentSessionsDetail = new ArrayList<>();
                userDocument.put("lastSession", userDocumentSessionsDetail);

                usersColl.insert(userDocument);
                
                //inserting to PASSWORD collection
                
                List<String> userHoneyWords = new SecurityControllers.HoneyGen().GenerateHoneywords(password);
                List<String> hashedHoneyWords = new ArrayList<>();
                
                //Hashing and inserting the honeywords into the database
                for(int i=0; i<userHoneyWords.size(); i++)
                {
                    //First element is the index where the correct password is stored
                    if(i == 0)
                    {
                        String index = userHoneyWords.get(i);
                        BasicDBObject honeycheckDoc = new BasicDBObject();
                        honeycheckDoc.put("username", username);
                        honeycheckDoc.put("index", index);
                        honeyCheckColl.insert(honeycheckDoc);
                        continue;
                    }
                    String honeyword = userHoneyWords.get(i);
                    String honeywordHash = new SHA256().getHashOf(honeyword);
                    String saltWithHoneywordHash = new SHA256().getHashOf(salt + honeywordHash);
                    hashedHoneyWords.add(saltWithHoneywordHash);
                }
                
                //Remove the first element i.e., the 'index' from the userHoneyWords and insert into the database
                userHoneyWords.remove(0);
                BasicDBObject honeywordsDoc = new BasicDBObject();
                honeywordsDoc.put("username", username);
                honeywordsDoc.put("honeywords", userHoneyWords);
                honeywordsColl.insert(honeywordsDoc);
                
                BasicDBObject passwordDocument = new BasicDBObject();
                passwordDocument.put("username", username);
                passwordDocument.put("password", hashedHoneyWords);
                
                passwordColl.insert(passwordDocument);
                
                new RegistrationConfirmationMail().Send(request,accountActivationKey);
                new FundTransferCompletedCreditMail().Send(firstname, email, "10", "CoolCabs America", dateEDT.toString(), "10");
                out.println("Registration is under process. Once complete we will email you the details.</br>");
                out.println("Please check your email for more information.</br></br>");
            }

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
