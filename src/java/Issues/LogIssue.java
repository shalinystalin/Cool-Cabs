/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Issues;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Vivek
 */
public class LogIssue {
    
    public void NewIssue(String username, String desc, String clientIP, String accountStatus)
    {
      
        Date dateEDT = null;
        try{
            DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
            dateEDT = formatter.parse(formatter.format(new Date()));
        }
        catch(Exception e)
        {
            System.out.println("Date error");
        }
        DBCollection issueColl = new MongoDB.MongoJDBCConnection().connect("issues");
        
        BasicDBObject issueDoc = new BasicDBObject();
        issueDoc.put("username", username);
        issueDoc.put("status", "open");
        issueDoc.put("desc", desc);
        issueDoc.put("clientIP", clientIP);
        issueDoc.put("date", dateEDT);
        
        issueColl.insert(issueDoc);
    }
    
}
