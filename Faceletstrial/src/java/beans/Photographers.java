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
import javax.faces.bean.ViewScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author Asif Towheed
 */

@ManagedBean
@RequestScoped
public class Photographers {
    
    @ManagedProperty("#{controller}")
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    ArrayList<Photographer> photographers = new ArrayList<>();
    //int which_id = current_p.getID();
    CachedRowSet crs = null;
    private String with_fname;
    private String with_address;
    private Double with_upperlimit;

    public Photographers() {
        this.with_fname = "";
        this.with_address = "";
    }

    public String getWith_fname() {
        return with_fname;
    }

    public void setWith_fname(String with_fname) {
        this.with_fname = with_fname;
    }

    public String getWith_address() {
        return with_address;
    }

    public void setWith_address(String with_address) {
        this.with_address = with_address;
    }

    public Double getWith_upperlimit() {
        return with_upperlimit;
    }

    public void setWith_upperlimit(Double with_upperlimit) {
        this.with_upperlimit = with_upperlimit;
    }
    
    
    

    public ArrayList<Photographer> getPhotographers() {
        photographers.clear();
        
                    try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());
                    
            if (with_upperlimit == null){
                
                    if (with_fname.equals("") && with_address.equals("")){
                        crs.setCommand("select * from photographer");
                    }
                    else if (with_address.equals("")){
                        crs.setCommand("select * from photographer where fname=?");
                        crs.setString(1, with_fname);
                    }
                    else if (with_fname.equals("")){
                        crs.setCommand("select * from photographer where address=?");
                        crs.setString(1, with_address);                        
                    }
                    else{
                        crs.setCommand("select * from photographer where fname=? and address=?");
                        crs.setString(1, with_fname);                        
                        crs.setString(2, with_address);                        
                    }
                
            }
            else{
                
                    if (with_fname.equals("") && with_address.equals("")){
                        crs.setCommand("select * from photographer where rate<=?");
                        crs.setDouble(1, with_upperlimit);
                    }
                    else if (with_address.equals("")){
                        crs.setCommand("select * from photographer where fname=? and rate>=?");
                        crs.setString(1, with_fname);
                        crs.setDouble(2, with_upperlimit);
                    }
                    else if (with_fname.equals("")){
                        crs.setCommand("select * from photographer where address=? and rate>=?");
                        crs.setString(1, with_address);                        
                        crs.setDouble(2, with_upperlimit);
                    }
                    else{
                        crs.setCommand("select * from photographer where fname=? and address=? and rate>=?");
                        crs.setString(1, with_fname);                        
                        crs.setString(2, with_address);                        
                        crs.setDouble(3, with_upperlimit);
                    }
                
                
            }
                    
                    crs.execute();
                    
                    while(crs.next()){
                        photographers.add(new Photographer(
                                crs.getInt("id"),
                                crs.getString("username"),
                                crs.getString("fname"),
                                crs.getString("lname"),
                                crs.getString("nationality"),
                                crs.getString("address"),
                                crs.getString("experience"),
                                crs.getString("dob"),
                                crs.getBoolean("eduhs"),
                                crs.getBoolean("edubs"),
                                crs.getBoolean("edums"),
                                crs.getDouble("rate")
                        ));
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {       
            Logger.getLogger(Photographers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return photographers;
    }

    public void setPhotographers(ArrayList<Photographer> photographers) {
        this.photographers = photographers;
    }
    
    
    public Object viewalbums(Photographer p){
        controller.current_p = p;
        System.out.println("view albums called");
        return "viewalbums_for_customer.xhtml";
    }
    
    public String getName(int ID){
        
        String test = "test";
        
                            try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("select * from photographer where id=?");
                    crs.setInt(1, ID);
                    crs.execute();
                    
                    while(crs.next()){
                        test = crs.getString("fname") + " " + crs.getString("lname");
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {       
            Logger.getLogger(Photographers.class.getName()).log(Level.SEVERE, null, ex);
        }

        return test;
    }

    
    
}
