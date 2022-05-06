/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psu.edu;

import java.util.ArrayList;

/**
 *
 * @author dpfrederick
 */
public class FoodList {
    private int id;
    private String name;
    private String description;
    private ArrayList<FoodItem> items;
    
    public FoodList() {}
    
    public FoodList(int intId, String strName) {
        id = intId;
        name = strName;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String strName) {
        name = strName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String strDesc) {
        description = strDesc;
    }
    
}
