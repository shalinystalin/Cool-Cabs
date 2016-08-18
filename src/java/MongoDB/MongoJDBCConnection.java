/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 *
 * @author Vivek
 */
public final class MongoJDBCConnection {

    public DBCollection connect(String collectionName)
    {
        DBCollection collection = null;
        DB db = null;
        MongoClient mongoClient = null;
        
        try{
            //connect to mongo server
            mongoClient = new MongoClient("localhost", 27017);
            
            //connect to database
            db = mongoClient.getDB("coolcabs");

            collection = db.getCollection(collectionName);
            System.out.println("Now connected to "+collectionName+" collection");
                        
        }
        catch(Exception e)
        {
            System.out.println("MongoJDBCConnection -> connect ->"+e);
        }
        return collection;
    }
}
