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
import javax.faces.context.FacesContext;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;




/**
 *
 * @author Asif Towheed
 */
@ManagedBean
@RequestScoped
public class Albums {
    
    @ManagedProperty("#{controller}")
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    ArrayList<Album> albums = new ArrayList<>();
    int which_id = 0;
    CachedRowSet crs = null;
    public Album newAlbum = null;
    public String newAlbumName = null;

    public Albums() {
    }

    public ArrayList<Album> getAlbums() {
        
        albums.clear();
        
        if(controller.current_p != null){

        which_id = controller.current_p.getID();
        
            System.out.println("HELLLLOOO------------------------------111111");
        
        
//            System.out.println("Called from getalbums() --> current_p's ID: " + Integer.toString(controller.current_p.getID()));
//            System.out.println("Called from getalbums() --> which_id: " + Integer.toString(which_id));
            System.out.println("HELLLLOOO------------------------------222222");
        
                    try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("select * from album where photographer_id=?");
                    crs.setInt(1, which_id);
                    crs.execute();
                    
                    while(crs.next()){
                        albums.add(new Album(
                                crs.getInt("id"),
                                crs.getInt("photographer_id"),
                                crs.getString("name")
                        ));
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            
        }


        
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public Album getNewAlbum() {
        return newAlbum;
    }

    public void setNewAlbum(Album newAlbum) {
        this.newAlbum = newAlbum;
    }

    public String getNewAlbumName() {
        return newAlbumName;
    }

    public void setNewAlbumName(String newAlbumName) {
        this.newAlbumName = newAlbumName;
    }

    public int getWhich_id() {
        return which_id;
    }

    public void setWhich_id(int which_id) {
        this.which_id = which_id;
    }
    
    
    
    
    public void addAlbum(){
        if (newAlbumName == null || newAlbumName.equals("")){
            System.out.println("NOT ADDED");
            return;
        }
        else{
            
        which_id = controller.current_p.getID();
            
            
            try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("insert into album(name, photographer_id) values (?,?)");
                    crs.setString(1, newAlbumName);
                    crs.setInt(2, which_id);
                    crs.execute();
                    
                    while(crs.next()){
                        albums.add(new Album(
                                crs.getInt("id"),
                                crs.getInt("photographer_id"),
                                crs.getString("name")
                        ));
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            newAlbumName = "";
            
            System.out.println("NEW ALBUM BEING ADDED");
        }
    }
    
    
    
    
    public Object testclick(Album a){
        System.out.println("testclick!!!");
        System.out.println("clicked " + a.getAlbumname());
        controller.selected_album = a;
        System.out.println("clicked2 " + a.getAlbumname());
        if (controller.logged_in_as_customer){
            return "viewalbum_for_customer.xhtml";
        }
        return "viewalbum.xhtml";
    }
/*    public void dtestclick(Album a){
        System.out.println("d - clicked");
    }*/
    
    
    
}
