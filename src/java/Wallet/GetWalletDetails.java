/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wallet;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 *
 * @author Vivek
 */
public class GetWalletDetails {
    
    String amount = null;
    
    public String getAmount(String username)
    {
        DBCollection usersColl = new MongoDB.MongoJDBCConnection().connect("users");
        DBCursor cursor = usersColl.find(new BasicDBObject("username", username));
        DBObject doc = cursor.one();
        
        if(doc != null)
        {
            amount = doc.get("walletAmount").toString();
        }
        
        cursor.close();
        return amount;
    }
}
