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
        saveAction = new SaveAction();
        saveAction.setEnabled(false); // disabled by default
        deleteAction = new DeleteAction();
        deleteAction.setEnabled(false);
        searchAction = new SearchAction();
        exitAction = new ExitAction();
        
        //adds actions to tool bar
        toolBar.add(openDBAction);
        toolBar.add(newAction);
        toolBar.add(saveAction);
        toolBar.add(deleteAction);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(searchAction);
        
        //adds actions to the file menu
        fileMenu.add(openDBAction);
        fileMenu.add(newAction);
        fileMenu.add(saveAction);
        fileMenu.add(deleteAction);
        fileMenu.addSeparator();
        fileMenu.add(searchAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        
        //set up menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        //set up desktop
        desktop = new JDesktopPane();
        
        //get the content pane to set up GUI
        Container c = getContentPane;
        c.add(toolBar, BorderLayout.NORTH);
        c.add(desktop, BorderLayout.CENTER);
        
        //if the user doesn't exit correctly
        addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent event){
                    shutDown();
                }
            }
        );
        
        //set window size and display window
        Toolkit toolkit = getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        
        //center window on screen
        setBounds(100, 100, dimension.width - 200, dimension.height - 200);
        setVisible(true);
        
    }
    
    String getDBFileName(String strInFile){
        fileChooser = new JFileChooser(strInFile);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File fileSelected = fileChooser.getSelectedFile();
            strInFile = fileSelected.getPath();
        }
        return strInFile;
    }
    
    private void shutDown(){
        database.close();
        System.exit(0);
    }
    
    private EntryFrame createEntryFrame(){
        EntryFrame frame = new EntryFrame();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.addInternalFrameLister(
            new InternalFrameAdapter(){
                public void internalFrameActivated(InternalFrameEvent event){
                    saveAction.setEnabled(true);
                    deleteAction.setEnabled(true);
                }
                
                public void internalFrameDeactivated(InternalFrameEvent event){
                    saveAction.setEnabled(false);
                    deleteAction.setEnabled(false);
                }
            }
        );
        return frame;
    }
    
    public static void main(String args[]){
        new App();
    }

    private class NewAction extends AbstractAction {
        public NewAction(){
            putValue(NAME, "New");
            putValue(SHORT_DESCRIPTION, "New");
            putValue(LONG_DESCRIPTION, "Add a new food list");
            putValue(MNEMONIC_KEY, new Integer('N'));
        }
        
        public void actionPerformed(ActionEvent e){
            EntryFrame entryFrame = createEntryFrame();
            
            entryFrame.setAddressBookEntry(
                new AddressBookEntry());
            
            desktop.add(entryFrame);
            entryFrame.setVisible(true);
        }
    }
    
    private class OpenDBAction extends AbstractAction {
        public OpenDBAction(){
            putValue( NAME, "Open");
            putValue( SMALL_ICON, new ImageIcon(
            getClass().getResource( "images/OpenDB24.png" ) ) );
            putValue( SHORT_DESCRIPTION, "Open DB" );
            putValue( LONG_DESCRIPTION, 
                "Open a SQLite Database" );
            putValue( MNEMONIC_KEY, new Integer( 'O' ) );
        }
        
        public void actionPerformed(ActionEvent e){
            fileChooser = new JFileChooser(strDBName);
            if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(desktop.getParent())){
                File fileSelected = fileChooser.getSelectedFile();
                strDBName = fileSelected.getPath();
                
                database = new CloudscapeDataAccess(strDBName);
                
            }
        }
    }
    
    private class SaveAction extends AbstractAction {
        public SaveAction(){
        putValue( NAME, "Save" );
        putValue( SMALL_ICON, new ImageIcon(
                getClass().getResource( "images/Save24.png" ) ) );
        putValue( SHORT_DESCRIPTION, "Save" );
        putValue( LONG_DESCRIPTION, 
            "Save record" );
        putValue( MNEMONIC_KEY, new Integer( 'S' ) );
        }
    
    
        public void actionPerformed(ActionEvent e){
            EntryFrame currentFrame = (EntryFrame) desktop.getSelectedFrame();

            currentFrame.getEntry();

            try{
                int foodID = food.getFoodID();

                String operation = (foodID == 0) ? "Insertion" : "Update";

                if (foodID == 0){
                    database.newFood(food);
                }
                else{
                    database.saveFood(food);
                }

                JOptionPane.showMessageDialog(desktop, operation + " successful");
            }

            catch(DataAccessException e){
                JOptionPane.showMessageDialog(desktop, e, "DataAccessException",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

            currentFrame.dispose();
        }
    }
    
    private class DeleteAction extends AbstractAction{
        public DeleteAction(){
            putValue( NAME, "Delete" );
            putValue( SMALL_ICON, new ImageIcon(
                getClass().getResource( "images/Delete24.png" ) ) );
            putValue( SHORT_DESCRIPTION, "Delete" );
            putValue( LONG_DESCRIPTION, 
                "Delete an address book entry" );
            putValue( MNEMONIC_KEY, new Integer( 'D' ) );
        }
        
        public void actionPerformed(ActionEvent e){
            EntryFrame currentFrame = (EntryFrame) desktop.getSelectedFrame();
            
            Entry food = currentFrame.getAddressBookEntry();
            
            if(person.getPersonID() == 0){
                JOptionPane.showMessageDialog(desktop, "New entries must be saved before they can be " +
               "deleted. \nTo cancel a new entry, simply " + 
               "close the window containing the entry" );
                return;
            }
            
            try{
                database.deleteFood(food);
                
                JOptionPane.showMessageDialog(desktop, "Deletion successful");
            }
            
            catch (DataAccessException exception){
                JOptionPane.showMessageDialog(desktop, exception,
                        "Deletion failed", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
            
            currentFrame.dispose();
        }
    }
    
    private class SearchAction extends AbstractAction{
        public SearchAction(){
            putValue( NAME, "Search" );
            putValue( SMALL_ICON, new ImageIcon(
                getClass().getResource( "images/Find24.png" ) ) );
            putValue( SHORT_DESCRIPTION, "Search" );
            putValue( LONG_DESCRIPTION, 
                "Search for an food list entry" );
            putValue( MNEMONIC_KEY, new Integer( 'r' ) );
        }
        
        public void actionPerformed(ActionEvent e){
            String foodName = JOptionPane.showInputDialog(desktop, "Enter food name");
            
            if(foodName != null){
                Entry food = database.findFood(foodName);
                
                if(food != null) {
                    EntryFrame entryFrame = createEntryFrame();
                    
                    entryFrame.setEntry(food);
                    
                    desktop.add(entryFrame);
                    entryFrame.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(desktop, "Entry with food "
                    + food + " not found in address book");
                }
                
            }
        }
    }
    
    private class ExitAction extends AbstractAction {
        public ExitAction(){
        putValue( NAME, "Exit" );
        putValue( SHORT_DESCRIPTION, "Exit" );
        putValue( LONG_DESCRIPTION, "Terminate the program" );
        putValue( MNEMONIC_KEY, new Integer( 'x' ) );
        }
        
        public void actionPerformed(ActionEvent e){
            shutDown();
        }
    }
}