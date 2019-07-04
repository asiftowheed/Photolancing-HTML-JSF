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
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author Asif Towheed
 */
@ManagedBean
public class ReservationList {
    
    private ArrayList<Reservation> reservations = null;
    private CachedRowSet crs=null;

    public ReservationList() {
    }

    public ArrayList<Reservation> getReservations() {
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
            crs.setCommand("select * from titles");
            
            crs.execute();
                    while(crs.next()){
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

            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservationList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReservationList.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    
    
    
    
    
}
