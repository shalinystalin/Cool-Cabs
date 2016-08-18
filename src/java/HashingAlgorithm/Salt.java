/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HashingAlgorithm;

import java.security.SecureRandom;

/**
 *
 * @author Vivek
 */
public final class Salt {

    public String generateSalt() {
        
        //inbult random byte generator
        SecureRandom random = new SecureRandom();
        byte saltBytes[] = new byte[10];
        random.nextBytes(saltBytes);
        
        //to convert from byte to string
        final StringBuilder builder = new StringBuilder();
        for(byte b : saltBytes) {
            builder.append(String.format("%02x", b));
        }
        
        //return the salt string
        return builder.toString();

    }
    
    
}
