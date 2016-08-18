/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maps;

import java.util.Random;

/**
 *
 * @author Vivek
 */
public class AvailableCabs {
    
    private int totalCabs = 0;
    private int miniCabs = 0;
    private int plusCabs = 0;
    private int poolCabs = 0;

    public AvailableCabs() {
        
        this.miniCabs = new Random().nextInt(5);
        this.plusCabs = new Random().nextInt(4);
        this.poolCabs = new Random().nextInt(6);
        this.totalCabs = this.miniCabs+this.plusCabs+this.poolCabs;
    }

    public int getTotalCabs() {
        return totalCabs;
    }

    public int getMiniCabs() {
        return miniCabs;
    }

    public int getPlusCabs() {
        return plusCabs;
    }

    public int getPoolCabs() {
        return poolCabs;
    }

    
    
}
