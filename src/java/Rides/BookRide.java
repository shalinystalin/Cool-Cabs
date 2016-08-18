/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rides;

import Mails.RideBookedMail;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vivek
 */
public class BookRide extends HttpServlet {

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
            
            String username = null;
            String firstname = null;
            
            String msg = null;
            String mailMsg = null;

            //get the current seesion from HTTP Request
            HttpSession session = request.getSession(false);
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
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CoolCabs - Book Ride</title>"); 
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
            out.println("<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>");
            out.println("<link href=\"css/login-registration-style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"sign_up\">");
            out.println("<div class=\"formtitle\" >CoolCabs - Book Ride</div>");
            out.println("<div class=\"title\">Dear "+firstname+",</br></br>"); 
            
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
            
            
            DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
            //Mongo Query to check if an account with this username already exists.
            DBCursor cursor = usersColl.find(new BasicDBObject("username", username));
            DBObject doc = cursor.one();
            
            String email = doc.get("email").toString();
            
            BasicDBObject rideDocument = new BasicDBObject();
            rideDocument.put("bookedTime", dateEDT);
            rideDocument.put("from", request.getParameter("start"));
            rideDocument.put("to", request.getParameter("end"));
            rideDocument.put("cabType", request.getParameter("selectCab"));
            rideDocument.put("cabNumber", "XXX-XXXXX");
            rideDocument.put("licenseNumber", "XXX-XXX-XXXXX");
            rideDocument.put("driverName", "XXXXX XXXXX");
            
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("username", username);
            BasicDBObject pushQuery = new BasicDBObject();
            pushQuery.put("$push", new BasicDBObject().append("rides", rideDocument));
            usersColl.update(searchQuery, pushQuery);
            
            msg = "Your ride has been booked.<br/><br/>";
            msg+= "Booked Time : "+dateEDT+"<br/><br/>";
            msg+= "From : "+request.getParameter("start")+"<br/><br/>";
            msg+= "To : "+request.getParameter("end")+"<br/><br/>";
            msg+= "Cab Type : "+request.getParameter("selectCab")+"<br/><br/>";
            msg+= "Cab Number : "+"XXX-XXXXX"+"<br/><br/>";
            msg+= "License Number : "+"XXX-XXX-XXXXX"+"<br/><br/>";
            msg+= "Driver's Name : "+"XXXXX XXXXX"+"<br/><br/>";
            msg+= "Driver's Phone : "+"XXX-XXX-XXXX"+"<br/><br/>";
            
            out.println(msg);
            out.println("<br/><br/><a href='Home.jsp'>Click here to go back to Home Page.</a>");
            
            mailMsg = "Dear "+firstname+",\n\n";
            mailMsg+= "Your ride has been booked. Below are the ride details.\n\n";
            mailMsg+= "Booked Time : "+dateEDT+"\n";
            mailMsg+= "From : "+request.getParameter("start")+"\n";
            mailMsg+= "To : "+request.getParameter("end")+"\n";
            mailMsg+= "Cab Type : "+request.getParameter("selectCab")+"\n";
            mailMsg+= "Cab Number : "+"XXX-XXXXX"+"\n";
            mailMsg+= "License Number : "+"XXX-XXX-XXXXX"+"\n";
            mailMsg+= "Driver's Name : "+"XXXXX XXXXX"+"\n";
            mailMsg+= "Driver's Phone : "+"XXX-XXX-XXXX"+"\n\n";
            mailMsg+= "If you have any queries feel free to email us - coolcabsamerica@gmail.com\n\n";
            
            new RideBookedMail().Send(firstname, email, mailMsg);
            
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
