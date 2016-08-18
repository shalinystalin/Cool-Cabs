/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wallet;

import HashingAlgorithm.SHA256;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

/**
 *
 * @author Vivek
 */
public class TransferFund extends HttpServlet {

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
            String recipientUsername = request.getParameter("recipientUsername");
            String amount = request.getParameter("amount");
            
            String msg = "";
            
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
            out.println("<div class=\"formtitle\" >CoolCabs - Transfer</div>");
            out.println("<div class=\"title\"></br>");
            
            try{
                
                DBCollection ftColl = new MongoDB.MongoJDBCConnection().connect("fundTransfer");
                DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
                
                //check if the recipient username exists of not
                DBObject userCheckDoc = null;
                DBCursor cursorCheck = usersColl.find(new BasicDBObject("username", recipientUsername));
                userCheckDoc = cursorCheck.one();
                
                if(userCheckDoc != null)
                {
                    String dateEDT = null;
                    try{
                        Date date = new Date();
                        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                        formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
                        dateEDT = (formatter.format(date));
                    }
                    catch(Exception e)
                    {
                        System.out.println("Date Error " + e);
                    }

                    String key = new SHA256().getHashOf(username+recipientUsername+dateEDT);

                    BasicDBObject ftDoc = new BasicDBObject();
                    ftDoc.put("from", username);
                    ftDoc.put("to", recipientUsername);
                    ftDoc.put("amount", amount);
                    ftDoc.put("status", "initiated");
                    ftDoc.put("initiatedTime", dateEDT);
                    ftDoc.put("completedTime", "");
                    ftDoc.put("key", key);

                    ftColl.insert(ftDoc);

                    DBCursor cursor = usersColl.find(new BasicDBObject("username", username));
                    DBObject doc = cursor.one();

                    String fromName = doc.get("firstname").toString();
                    String fromEmail = doc.get("email").toString();

                    DBCursor cursorFT = ftColl.find(new BasicDBObject("key", key));
                    DBObject idDoc = cursorFT.one();

                    String id = idDoc.get("_id").toString();

                    new Mails.FundTransferInitiatedMail().Send(fromName, fromEmail, recipientUsername, amount, dateEDT, id, key);

                    msg = "Transfer is initiated. Please check your email and confirm to complete.";
                }
                else{
                    msg = "No such username found.";
                }
            }
            catch(Exception e)
            {
                msg = "Error while Processing.";
            }

            msg+= "<br/><br/> Click <a href='Wallet.jsp'>here</a> to go back to Wallet page.";
            
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
