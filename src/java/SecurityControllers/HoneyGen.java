/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SecurityControllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Vivek
 */
public class HoneyGen {

    public List<String> GenerateHoneywords(String password) {
        
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
                correctPasswordLoc = i-1;
                break;
            }
        }
        honeyWords.add(0, String.valueOf(correctPasswordLoc));
        for(int i=0; i<honeyWords.size();i++)
        {
            System.out.println(i+" > "+honeyWords.get(i));
        }
        System.out.println("Size - "+honeyWords.size());
        
        return honeyWords;
    }
}
