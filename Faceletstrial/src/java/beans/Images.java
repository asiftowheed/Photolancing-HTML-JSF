/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;


/**
 *
 * @author Asif Towheed
 */

@ManagedBean
@RequestScoped
public class Images {
    
    @ManagedProperty("#{controller}")
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    ArrayList<Image> images = new ArrayList<>();
    int which_id = 0;
    CachedRowSet crs = null;

    public Images() {
        System.out.println("IMAGES CONTRUCTOR CALLED");
        //System.out.println(controller.current_c.getID());
        System.out.println("IMAGES CONTRUCTOR CALLED2");
    }

    public ArrayList<Image> getImages() {

        System.out.println("GETIMAGES CALLED");
        System.out.println(controller.current_c.getID());
        System.out.println("GETIMAGES CALLED2");
        which_id = controller.getSelected_album().getID();
        
        images.clear();
        
                    try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("select * from image where album_id=?");
                    crs.setInt(1, which_id);
                    crs.execute();
                    
                    while(crs.next()){
                        images.add(new Image(
                                crs.getInt("id"),
                                crs.getInt("album_id"),
                                crs.getString("file_name"),
                                crs.getString("dir_path")
                        ));
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
    
    
    public void deleteImage(int im_ID) {
        
        images.clear();
        
                    try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("delete from image where id = ?");
                    crs.setInt(1, im_ID);
                    crs.execute();
                    
            }   catch (SQLException ex) {
                    Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
