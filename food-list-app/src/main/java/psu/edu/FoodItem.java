/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psu.edu;

/**
 *
 * @author dpfrederick
 */
public class FoodItem {
    private String name = "";
    private int FDCID;
    
    public FoodItem() {}
    
    public FoodItem( int intId, String strName ) {
        FDCID = intId;
        name = strName;
    }
    
    public void setName(String strName) {
        name = strName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setFDCID(int intId) {
        FDCID = intId;
    }
    
    public int getFDCID() {
        return FDCID;
    }
    
}
