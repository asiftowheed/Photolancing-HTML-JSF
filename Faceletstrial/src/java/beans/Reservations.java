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
public class Reservations {
    
    @ManagedProperty("#{controller}")
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    ArrayList<Reservation> reservations = new ArrayList<>();
    int which_id = 0;
    CachedRowSet crs = null;

    public Reservations() {
        reservations = new ArrayList<>();
    }

    public ArrayList<Reservation> getReservations() {
        
        System.out.println("ENTER1");
        reservations.clear();
        
        System.out.println("ENTER2");
        if (controller.logged_in_as_customer){
            System.out.println("ENTER3");
            which_id = controller.current_c.getID();            
        }
        else{
            System.out.println("ENTER4");
            which_id = controller.current_p.getID();            
        }
        
        

//        which_id = current_p.getID();
        
            System.out.println("ENTER5");
            System.out.println("ENTER5.1");
        
//            System.out.println("Called from getalbums() --> current_p's ID: " + Integer.toString(current_p.getID()));
            System.out.println("ENTER5.2");
            System.out.println("Called from getreservations() --> which_id: " + Integer.toString(which_id));
            System.out.println("ENTER5.3");
        
                    try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
            System.out.println("ENTER6");
                    if(controller.logged_in_as_customer)
                        crs.setCommand("select * from reservation where customer_id=?");
                    else
                        crs.setCommand("select * from reservation where photographer_id=?");
                    crs.setInt(1, which_id);
                    crs.execute();
            System.out.println("ENTER7");
                    
                    while(crs.next()){
            System.out.println("ENTER8");
                        reservations.add(new Reservation(
                                crs.getInt("id"),
                                crs.getInt("customer_id"),
                                crs.getInt("photographer_id"),
                                crs.getString("venue"),
                                crs.getString("status"),
                                crs.getDouble("proposed_amount"),
                                crs.getString("date_reserved"),
                                crs.getString("time_reserved")
                        ));
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reservations.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("ENTER9");
        
        
        return reservations;
        
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;        
    }
    
    
    public void accept(int res_id){

            try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("update reservation set status = 'Accepted' where id = ?");
                    crs.setInt(1, res_id);
                    crs.execute();

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reservations.class.getName()).log(Level.SEVERE, null, ex);
        }


        System.out.println(res_id + " is being set to Accepted");
        
    }
    public void decline(int res_id){

            try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("update reservation set status = 'Declined' where id = ?");
                    crs.setInt(1, res_id);
                    crs.execute();

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reservations.class.getName()).log(Level.SEVERE, null, ex);
        }


        System.out.println(res_id + " is being set to Declined");
    }
    
    
    
}
