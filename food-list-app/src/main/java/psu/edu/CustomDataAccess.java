/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psu.edu;


import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author dpfrederick
 */
public class CustomDataAccess implements DataAccess {
    private Connection connection;
    
    private PreparedStatement sql_get_items_in_list;
    private PreparedStatement sql_add_item_to_list;
    private PreparedStatement sql_remove_item_from_list;
    private PreparedStatement sql_insert_list;
    private PreparedStatement sql_update_list;
    private PreparedStatement sql_delete_list;
    private PreparedStatement sql_get_lists;
    private PreparedStatement sql_list_id;
    
    private PreparedStatement sql_check_item_in_list;
    
    public CustomDataAccess(String strDBName) throws Exception {
        connectToSQLiteDB(strDBName);
                
        sql_get_items_in_list = connection.preparedStatement(
        "SELECT items.FDCID, items.name " +
        "FROM items " +
        "INNER JOIN items_in_list ON items.FDCID=items_in_list.FDCID " +
        "WHERE items_in_list.listID=?"
        );
        
        sql_add_item_to_list = connection.preparedStatement(
        "INSERT INTO items_in_list (FDCID, listID) " +
        "VALUES (?, ?)" // TODO make method that handles multiple items for speed
        );
        
        sql_remove_item_from_list = connection.preparedStatement(
        "DELETE FROM items_in_list " +
        "WHERE FDCID IN (?)" //TODO method
        );
        
        sql_insert_list = connection.preparedStatement(
        "INSERT INTO lists (name, description) " +
        "VALUES (?, ?)"
        );
        
        sql_update_list = connection.preparedStatement(
        "UPDATE lists " +
        "SET name = ?, description = ? " +
        "WHERE id = ?"
        );
        
        sql_delete_list = connection.preparedStatement(
        "DELETE FROM lists WHERE ID = ?"
        );
        
        sql_get_lists = connection.preparedStatement(
        "SELECT * FROM lists"
        );
        
        sql_list_id = connection.preparedStatement(
        "SELECT max(id) from lists"
        );
        
        sql_check_item_in_list = connection.preparedStatement(
        "SELECT * " +
        "FROM items_in_list " +
        "WHERE FDCID = ? AND " +
        "listID = ?"
        );
    }
    
    private void connectToSQLiteDB(String strDBName) throws Exception {
        String strDBClass = "org.sqlite.JDBC";
        String strJDBCString = "jdbc:sqlite:";
        
        try {
            System.out.println("About to connect to " + strDBName);
            Class.forName(strDBClass);
            connection = DriverManager.getConnection(strJDBCString + strDBName);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        
        connection.setAutoCommit(false);
        
        System.out.println("Connected to product: " + connection.getMetaData.getDatabaseProductName());
    }
    
    // intended to return all food lists in db for gui
    public ArrayList<FoodList> get_lists() {
        try {
            ResultSet result_set = sql_get_lists.executeQuery();
            
            if (!result_set.next())
                return null;
            ArrayList<FoodList> food_lists = new ArrayList<>();
            
            while (result_set.next()) {
                FoodList new_list = new FoodList(
                result_set.getInt(1),
                result_set.getString(2),
                result_set.getString(3)
                );
                
                food_lists.add(new_list);
            }
            
            return food_lists;
        } catch (SQLException sqlException) {
            return null;
        }
    }
    
    public ArrayList<FoodItem> get_items_in_list(int listID) {
        ArrayList<FoodItem> food_items = new ArrayList<FoodItem>();
        try {
            sql_get_items_in_list.setInt(1, listID);
            ResultSet result_set = sql_get_items_in_list.executeQuery();
            
            if (!result_set.next())
                return null;
            
            while (result_set.next()){
                FoodItem food_item = new FoodItem(
                result_set.getInt(1),
                result_set.getString(2)
                );
            
                food_items.add(food_item);
            }
            
            return food_items;
        } catch (SQLException sqlException) {
            return null;
        }
    }
    
    // intended to be called in the gui when an existing list is edited and saved
    public boolean save_list(FoodList food_list, ArrayList<FoodItem> food_items) throws DataAccessException {
        try {
            int result;
            
            sql_update_list.setString(1, food_list.getName());
            sql_update_list.setString(2, food_list.getDescription());
            sql_update_list.setString(3, food_list.getId());
            
            result = sql_update_list.executeUpdate();
            
            if (result == 0) {
                connection.rollback();
                return false; // update unsuccessful
            }
            
            ArrayList<FoodItem> existing_items_in_list = get_items_in_list(food_list.getId());
            
            for (FoodItem food_item : food_items) {
                try {
                    int check_item_result;
                    
                    sql_check_item_in_list.setInt(1, food_item.getFDCID());
                    sql_check_item_in_list.setInt(2, food_list.getId());
                    check_item_result = sql_check_item_in_list.executeQuery();
                    if (check_item_result == 0) {
                        int add_item_result;
                        // item is not already in list
                        sql_add_item_to_list.setInt(1, food_item.getFDCID());
                        sql_add_item_to_list.setInt(2, food_list.getId());
                        add_item_result = sql_add_item_to_list.executeUpdate();
                        if (add_item_result == 0) {
                            connection.rollback();
                            return false;
                        }
                    }
                } catch(SQLException sqlException) {
                    try {
                        connection.rollback();
                        return false;
                    } catch(SQLException exception) {
                        throw new DataAccessException(exception);
                    }
                }
            }
            
            for (FoodItem food_item : existing_items_in_list) {
                if (food_items.contains(food_item) == false) {
                    try {
                        int remove_item_result;
                        sql_remove_item_from_list.setInt(1, food_item.getFDCID());
                        sql_remove_item_from_list.setInt(2, food_list.getId());
                        remove_item_result = sql_remove_item_from_list.executeUpdate();
                        if (remove_item_result == 0) {
                            connection.rollback();
                            return false;
                        }
                    } catch(SQLException sqlException) {
                        try {
                            connection.rollback();
                            return false;
                        } catch(SQLException exception) {
                            throw new DataAccessException(exception);
                        }
                    }
                }
            }
            
            connection.commit();
            return true;
        } catch(SQLException sqlException) {
            try {
                connection.rollback();
                return false;
            } catch(SQLException exception) {
                throw new DataAccessException(exception);
            }
        }
    }
    
    // intended to be called in the gui when a new list is created
    public boolean new_list(FoodList food_list, ArrayList<FoodItem> food_items) throws DataAccessException {
        try {
            int result;
            
            sql_insert_list.setString(1, food_list.getName());
            sql_insert_list.setString(2, food_list.getDescription());
            result = sql_insert_list.executeUpdate();
            if (result == 0) {
                connection.rollback();
                return false; // insert unsuccessful
            }
            
            ResultSet result_list_id = sql_list_id.executeQuery(); // TODO finish
            
            if (result_list_id.next()) {
                int list_id = result_list_id.getInt(1);
                
                for (FoodItem food_item : food_items) {
                    try {
                        int check_item_result;

                        sql_check_item_in_list.setInt(1, food_item.getFDCID());
                        sql_check_item_in_list.setInt(2, list_id);
                        check_item_result = sql_check_item_in_list.executeQuery();
                        if (check_item_result == 0) {
                            int add_item_result;
                            // item is not already in list
                            sql_add_item_to_list.setInt(1, food_item.getFDCID());
                            sql_add_item_to_list.setInt(2, list_id);
                            add_item_result = sql_add_item_to_list.executeUpdate();
                            if (add_item_result == 0) {
                                connection.rollback();
                                return false;
                            }
                        }
                    } catch(SQLException sqlException) {
                        try {
                            connection.rollback();
                            return false;
                        } catch(SQLException exception) {
                            throw new DataAccessException(exception);
                        }
                    }
                }
                
                connection.commit();
                return true;
            } else
                return false;
        } catch (SQLException sqlException) {
            try {
                connection.rollback();
                return false; // update unsuccessful
            } catch (SQLException exception) {
                // handle exception rolling back transaction
                throw new DataAccessException(exception);
            }
            
        }
    }
    
    public boolean delete_list(FoodList food_list, ArrayList<FoodItem> food_items) throws DataAccessException {
        try {
            int result;
            
            sql_delete_list.setInt(1, food_list.getId());
            result = sql_delete_list.executeUpdate();
            
            if (result == 0) {
                connection.rollback();
                return false;
            }
            
            // items_in_lists should correct due to cascading delete
            
            connection.commit();
            return true;
        } catch (SQLException sqlException) {
            try {
                connection.rollback();
                return false;
            } catch (SQLException exception) {
                throw new DataAccessException(exception);
            }
        }
    }
    
    @Override
    public void close() {
        // close db connection
        try {
            sql_get_items_in_list.close();
            sql_add_item_to_list.close();
            sql_remove_item_from_list.close();
            sql_insert_list.close();
            sql_update_list.close();
            sql_delete_list.close();
            sql_get_lists.close();
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
