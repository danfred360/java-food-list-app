package psu.edu;

// Java core packages
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
// import java.sql.*;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// Java extension packages
import javax.swing.*;
import javax.swing.event.*;

/**
 * JavaFX App
 */
public class App extends JFrame {

    private String strDBName = "";
    
    private JDesktopPane desktop;
    
    private DataAccess database;
    
    Action newAction, saveAction, deleteAction, searchAction, exitAction, openDBAction;
    
    JFileChooser fileChooser;
    
    public App() {
        super(" Food List ");
        try {
            strDBName = getDBFileName(strDBName);
            database = new CustomDataAccess(strDBName);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        // db access successfull
        
        JToolBar toolBar = new JToolBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        
        newAction = new NewAction();
        openDBAction = new OpenDBAction();
        openDBAction.setEnabled(true);
        saveAction = newSaveAction();
        saveAction.setEnabled(false); // disabled by default
        //deleteAction = new DeleteAction
    }

    public static void main(String[] args) {
        //launch();
    }

}