package psu.edu;

import psu.edu.FoodItem;
import psu.edu.FoodList;
import java.sql.*;
import java.util.ArrayList;

public interface DataAccess {
    public ArrayList<FoodList> get_lists();
    
    public ArrayList<FoodItem> get_items_in_list(int listID);
    
    public boolean save_list(
            FoodList food_list, ArrayList<FoodItem> food_items) 
            throws DataAccessException;
    
    public boolean new_list(
            FoodList food_list, ArrayList<FoodItem> food_items) 
            throws DataAccessException;
    
    public boolean delete_list(
            FoodList food_list, ArrayList<FoodItem> food_items) 
            throws DataAccessException;
    
    public void close();
    
}
