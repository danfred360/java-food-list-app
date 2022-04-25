/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psu.edu;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;

/**
 *
 * @author kzb535
 */
public class ApiHandler {
    private static HttpURLConnection connection;
    private static FileWriter file;
    
    public static void main(String[] args) {
        
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        
        try {
            // Method 1 java.net.HttpURLConnection
            URL url = new URL("https://api.nal.usda.gov/fdc/v1/foods/list?api_key=tTK3PTAvTYpVnez116Vqcd1WqmhELS0MQf2oUfeL");
            //key tTK3PTAvTYpVnez116Vqcd1WqmhELS0MQf2oUfeL
            connection = (HttpURLConnection) url.openConnection();
            
            //Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int status = connection.getResponseCode();
            //System.out.println(status);
            
            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            String response = responseContent.toString();
            System.out.println(response);    
            JSONArray obj = new JSONArray(response);
            System.out.println("JSON " + obj.toString().length());
            
            //Writes JSON to File
            try{
                file = new FileWriter("D:\\Classes\\IST411\\outputJSON.txt");
                file.write(obj.toString());
                System.out.println("Successfully Wrote");
            } catch(IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    file.flush();
                    file.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            
        } catch (MalformedURLException ex) {
            //Logger.getLogger(ApiHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(ApiHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            connection.disconnect();
        }
        
        
        
    }
    
    public static String parse(String responseBody){
        return "";
    }
}
