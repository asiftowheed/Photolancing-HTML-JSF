/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;


/**
 *
 * @author Asif Towheed
 */

@ManagedBean
@SessionScoped
public class Reservation {
    
    @ManagedProperty("#{controller}")
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private int ID, customerID, photographerID;
    private String photographername = "", customername = "";
    private String venue, status;
    private Double proposedAmount;
    private String date_of_shoot;
    private Date final_date;
    private String time_of_shoot;
    CachedRowSet crs = null;
    //private Time final_time;
    
    private Photographer whichphotographer = null;
    private Customer whichcustomer = null;
    

    public Reservation() {
    }

//    public Reservation(int ID, int customerID, int photographerID, String venue, String status, Double proposedAmount) {
//        this.ID = ID;
//        this.customerID = customerID;
//        this.photographerID = photographerID;
//        this.venue = venue;
//        this.status = status;
//        this.proposedAmount = proposedAmount;
//    }

    public Reservation(int ID, int customerID, int photographerID, String venue, String status, Double proposedAmount, String date_of_shoot, String time_of_shoot) {
        this.ID = ID;
        this.customerID = customerID;
        this.photographerID = photographerID;
        this.venue = venue;
        this.status = status;
        this.proposedAmount = proposedAmount;
        this.date_of_shoot = date_of_shoot;
        this.time_of_shoot = time_of_shoot;
    }
    
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPhotographerID() {
        return photographerID;
    }

    public void setPhotographerID(int photographerID) {
        this.photographerID = photographerID;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProposedAmount() {
        return proposedAmount;
    }

    public void setProposedAmount(Double proposedAmount) {
        this.proposedAmount = proposedAmount;
    }

    public String getDate_of_shoot() {
        System.out.println("CALLED GETDATE");
        return date_of_shoot;
    }

    public void setDate_of_shoot(String date_of_shoot) {
        System.out.println("CALLED SETDATE");
        this.date_of_shoot = date_of_shoot;
    }

    public Date getFinal_date() {
        return final_date;
    }

    public void setFinal_date(Date final_date) {
        this.final_date = final_date;
    }

    public String getTime_of_shoot() {
        return time_of_shoot;
    }

    public void setTime_of_shoot(String time_of_shoot) {
        this.time_of_shoot = time_of_shoot;
    }

    public Photographer getWhichphotographer() {
        return whichphotographer;
    }

    public void setWhichphotographer(Photographer whichphotographer) {
        this.whichphotographer = whichphotographer;
    }

    public Customer getWhichcustomer() {
        return whichcustomer;
    }

    public void setWhichcustomer(Customer whichcustomer) {
        this.whichcustomer = whichcustomer;
    }

    public String getPhotographername(int p_id) {
        
            try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("select * from photographer where id=?");
                    crs.setInt(1, p_id);
                    crs.execute();
                    
                    while(crs.next()){
                        return crs.getString("fname") + " " + crs.getString("lname");
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return photographername;
    }

    public void setPhotographername(String photographername) {
        this.photographername = photographername;
    }

    public String getCustomername(int c_id) {

        
                    try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("select * from customer where id=?");
                    crs.setInt(1, c_id);
                    crs.execute();
                    
                    while(crs.next()){
                        return crs.getString("fname") + " " + crs.getString("lname");
                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
        }


        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }
    
    
    
    
    
    
    
    
    public Object beginReservation(Photographer p){
        
        System.out.println("Begin reservation called");
        System.out.println("IDDD: " + p.getID());
        whichphotographer = p;
        System.out.println("IDDD2: " + whichphotographer.getID());
        System.out.println("IDDD: " + controller.current_c.getID());
        whichcustomer = controller.current_c;        
        System.out.println("IDDD2: " + whichcustomer.getID());
        return "newreservation.xhtml";
        
    }
    
    public Object createReservation(){
        
        
                    try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
//                    crs.setCommand("insert into album(name, photographer_id) values (?,?)");
                    crs.setCommand("insert into reservation(customer_id, photographer_id, date_reserved, time_reserved, venue, proposed_amount, status)"+ 
                            " values (?,?,?,?,?,?,?)");
                    
                        System.out.println(whichcustomer.getID());
                        System.out.println(whichphotographer.getID());
                        System.out.println(date_of_shoot);
                        System.out.println(time_of_shoot);
                        System.out.println(venue);
                        System.out.println(proposedAmount);
                        System.out.println("Requested");
                    crs.setInt(1, whichcustomer.getID());
                    crs.setInt(2, whichphotographer.getID());
                    crs.setString(3, date_of_shoot);
                    crs.setString(4, time_of_shoot);
                    crs.setString(5, venue);
                    crs.setDouble(6, proposedAmount);
                    crs.setString(7, "Requested");
                    crs.execute();
                    
//                    while(crs.next()){
//                        albums.add(new Album(
//                                crs.getInt("id"),
//                                crs.getInt("photographer_id"),
//                                crs.getString("name")
//                        ));
//                    }

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
        }


        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("DOS "+date_of_shoot);
        System.out.println("");
        
        this.setDate_of_shoot("");
        this.setTime_of_shoot("");
        this.setVenue("");
        this.proposedAmount = null;
        
        return "profile.xhtml";
    }
    
    
    
}
