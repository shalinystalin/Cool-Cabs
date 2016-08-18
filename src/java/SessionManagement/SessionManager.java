/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionManagement;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Vivek
 */
public class SessionManager implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
        System.out.println("inside session destroyed");
        
        HttpSession session = se.getSession();
        String username = session.getAttribute("username").toString();
        Date dateEDT = null;
            try{
                DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
                dateEDT = formatter.parse(formatter.format(new Date()));
            }
            catch(Exception e){
                System.out.println("Date Error");
            }
        session.setAttribute("endTime", dateEDT);
        
        //insert session details into the USERS database  
        DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
        
        DBCursor cursor = usersColl.find(new BasicDBObject("username", username));
        DBObject doc = cursor.one();
        
        BasicDBObject sessionDetails = new BasicDBObject();
        sessionDetails.put("startTime", session.getAttribute("startTime"));
        sessionDetails.put("clientIP", session.getAttribute("clientIP"));
        sessionDetails.put("endTime", session.getAttribute("endTime"));

        BasicDBObject sessionDoc = new BasicDBObject();
        sessionDoc.put("lastSession", sessionDetails);

        doc.put("sessions", sessionDetails);
        usersColl.save(doc);
        
        session.invalidate();
    }
    
}
