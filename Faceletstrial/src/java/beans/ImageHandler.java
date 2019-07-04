/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;



/**
 *
 * @author Asif Towheed
 */
@ManagedBean
@SessionScoped
public class ImageHandler {
 
    @ManagedProperty("#{controller}")
    private Controller controller;

    private Part file;
    private String fileName;    
    private String extension;
    private String itemName = null;
    CachedRowSet crs = null;

    
    public ImageHandler() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    
    
    
    private String getExtension(Part file) {
        String name = file.getSubmittedFileName();        
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }

        return name.substring(lastIndexOf);
    }

    
    
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        try {
            InputStream in = null;
            this.file = file;
            in = file.getInputStream();
            extension = getExtension(this.file).trim();
            if(extension.equals(".png")||extension.equals(".jpg")||extension.equals(".jpeg")){
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss.SSS");
                fileName = "IMG_"+sdf.format(new Date()) + extension;
                String destName = "C:\\Users\\Asif Towheed\\Documents\\NetBeansProjects\\Faceletstrial\\web\\resources\\images\\";
                Files.copy(in, new File(destName+fileName).toPath());
                Path currentRelativePath = Paths.get(".\\");
                String s = currentRelativePath.toAbsolutePath().toString();
                System.out.println("Current relative path is: " + s);
            } else{
                System.out.println(extension);     
            }
            } catch (IOException ex) {
            Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.file = file;
    }

    
    public Object submit(){
        if(extension.equals(".png")||extension.equals(".jpg")||extension.equals(".jpeg")){
            
            try{

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("insert into image (file_name, dir_path, album_id) values (?, ?, ?)");
                    crs.setString(1, fileName);
                    crs.setString(2, "C:\\Users\\Asif Towheed\\Documents\\NetBeansProjects\\Faceletstrial\\web\\resources\\images\\");
                    crs.setInt(3, controller.selected_album.getID());
                    crs.execute();
                    
                    
                    
            } catch (SQLException ex) {
                Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            return "failed";
        }
        return "viewalbum.xhtml";
    }
    
    
}
