/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
import psu.edu.Entry;
/**
 *
 * @author Kevin
 */
public class EntryFrame extends JInternalFrame {
    private HashMap fields;
    private Entry food;
    private JPanel leftPanel, rightPanel;
    private static int xOffset = 0, yOffset = 0;
    
    public EntryFrame(){
        super("Entry", true, true);
        
        fields = new HashMap();
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(9, 1, 0, 5));
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout (9,1,0,5));
        
        //create rows here
        
        Container container = getContentPane();
    }
    
}
