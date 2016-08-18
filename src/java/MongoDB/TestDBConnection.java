/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MongoDB;

import HashingAlgorithm.Salt;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.eclipse.jdt.internal.compiler.batch.Main;

/**
 *
 * @author Vivek
 */
public class TestDBConnection {
    
    public static void main(String[] args) {
          
        String password = "Admin1234";
        
        List<String> honeyWords = new ArrayList<>();
        honeyWords = GenerateHoneywords(password);

    }

    private static List<String> GenerateHoneywords(String password) {
        
        List<String> honeyWords = new ArrayList<>();
        
        List<Integer> digitLocation = new ArrayList<>();
        int digitCount = 0;
        
        for(int i=0; i<password.length(); i++)
        {
            char c = password.charAt(i);
            if(Character.isDigit(c))
            {
                digitLocation.add(i);
                digitCount++;
            }
        }
        if(digitCount>2)
            digitCount = 2;
        for(int i=0; i<digitCount; i++)
        {
            int loc = digitLocation.get(i);
            for(int j=0; j<10; j++)
            {
                StringBuilder newHoneyWord = new StringBuilder(password);
                newHoneyWord.deleteCharAt(loc);
                newHoneyWord.insert(loc, j);
                if(newHoneyWord.toString().equals(password))
                {
                    continue;
                }
                honeyWords.add(newHoneyWord.toString());
            }
        }
        honeyWords.add(password);
        System.out.println("Size - "+honeyWords.size());
        Collections.shuffle(honeyWords);
        int correctPasswordLoc = 0;
        for(int i=0; i<honeyWords.size();i++)
        {
            if(honeyWords.get(i).equals(password))
            {
                correctPasswordLoc = i;
                break;
            }
        }
        honeyWords.add(0, String.valueOf(correctPasswordLoc));
        for(int i=0; i<honeyWords.size();i++)
        {
            if(honeyWords.get(i).equals(password))
                {
                    System.out.println("Password at "+i);
                }
            System.out.println(i+" > "+honeyWords.get(i));
        }
        System.out.println("Size - "+honeyWords.size());
        
        return honeyWords;
    }
}
