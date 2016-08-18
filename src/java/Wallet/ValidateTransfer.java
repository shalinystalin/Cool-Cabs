/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wallet;

import HashingAlgorithm.SHA256;
import com.mongodb.BasicDBList;
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

/**
 *
 * @author Vivek
 */
public class ValidateTransfer extends HttpServlet {

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

            String amount;
            
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
            out.println("<div class=\"title\">Dear User,</br>");
            
            try{
                
                String id = request.getParameter("id");
                String key = request.getParameter("key");    
                String fromEmail = "";
                String toEmail = "";
                String fromName = "";
                String toName = "";
                String fromBalance = "";
                String toBalance = "";
                
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
                
                DBCollection ftColl = new MongoDB.MongoJDBCConnection().connect("fundTransfer");

                DBCursor cursorFT = ftColl.find(new BasicDBObject("key", key));
                DBObject ftDoc = cursorFT.one();
                
                amount = ftDoc.get("amount").toString();

                if((ftDoc.get("key").equals(key)) && ftDoc.get("status").equals("initiated"))
                {
                    
                    String from = ftDoc.get("from").toString();
                    String to = ftDoc.get("to").toString();

                    DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");

                    //update the TO user's wallet amount
                    DBCursor cursorToUser = usersColl.find(new BasicDBObject("username", to));
                    DBObject tuDoc = cursorToUser.one();
                    
                    float tuPreviousAmount = Float.parseFloat(tuDoc.get("walletAmount").toString());
                    float tuPresentAmount = tuPreviousAmount + Float.parseFloat(amount);
                    
                    toName = tuDoc.get("firstname").toString();
                    toEmail = tuDoc.get("email").toString();
                    toBalance = Float.toString(tuPresentAmount);

                    tuDoc.put("walletAmount", tuPresentAmount);
                    usersColl.save(tuDoc);

                    //update the FROM user's wallet amount
                    DBCursor cursorFromUser = usersColl.find(new BasicDBObject("username", from));
                    DBObject fuDoc = cursorFromUser.one();

                    float fuPreviousAmount = Float.parseFloat(fuDoc.get("walletAmount").toString());
                    float fuPresentAmount = fuPreviousAmount - Float.parseFloat(amount);
                    
                    fromName = fuDoc.get("firstname").toString();
                    fromEmail = fuDoc.get("email").toString();
                    fromBalance = Float.toString(fuPresentAmount);

                    fuDoc.put("walletAmount", fuPresentAmount);
                    usersColl.save(fuDoc);

                    ftDoc.put("status", "completed");
                    ftColl.save(ftDoc);

                    msg = "Transfer is Complete.";
                    msg+= "<br/><br/>$ "+amount+" has been credited to "+to+".";
                    msg+= "<br/><br/>Your available balance is $"+fuPresentAmount+".";
                    
                    new Mails.FundTransferCompletedDebitMail().Send(fromName, fromEmail, fromBalance, toName, dateEDT, amount);
                    new Mails.FundTransferCompletedCreditMail().Send(toName, toEmail, toBalance, fromName, dateEDT, amount);
                
                }
                else{
                    msg = "Incorrect Link";
                }
            }
            catch(Exception e)
            {
                msg = "Error while Processing. ";
                msg+= e.getStackTrace().toString();
            }
            
            out.println("<br/>");
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
