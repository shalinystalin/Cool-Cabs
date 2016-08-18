/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vivek
 */
public class GetUserDetails {
    
    List<String> userDetails = new ArrayList<>();
    
    public List<String> getDetails(String username)
    {
        
        DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
        DBCursor cursor = usersColl.find(new BasicDBObject("username", username));
        DBObject doc = cursor.one();
        
        if(doc != null)
        {
            userDetails.add(username);
            userDetails.add(doc.get("firstname").toString());
            userDetails.add(doc.get("lastname").toString());
            userDetails.add(doc.get("email").toString());
            userDetails.add(doc.get("dob").toString());
            userDetails.add(doc.get("phone").toString());
            userDetails.add(doc.get("address").toString());
            userDetails.add(doc.get("city").toString());
            userDetails.add(doc.get("province").toString());
        }
        else
        {
            return null;
        }
        
        return userDetails;
    }
}
