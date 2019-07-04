/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;



import java.security.NoSuchAlgorithmException;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Asif Towheed
 */

@ManagedBean
@SessionScoped
public class Photographer {
    
    @ManagedProperty("#{controller}")
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private int ID;
    private String username, password, fname, lname, Nationality, Address, experience, dob;
    private Boolean EducationHS, EducationBS, EducationMS;
    private Double servicecharge;
    CachedRowSet crs = null;

    public Photographer() {
    }

    public Photographer(int ID, String username, String fname, String lname, String Nationality, String Address, String experience, String dob, Boolean EducationHS, Boolean EducationBS, Boolean EducationMS, Double servicecharge) {
        this.ID = ID;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.Nationality = Nationality;
        this.Address = Address;
        this.experience = experience;
        this.dob = dob;
        this.EducationHS = EducationHS;
        this.EducationBS = EducationBS;
        this.EducationMS = EducationMS;
        this.servicecharge = servicecharge;
    }


    
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Boolean getEducationHS() {
        return EducationHS;
    }

    public void setEducationHS(Boolean EducationHS) {
        this.EducationHS = EducationHS;
    }

    public Boolean getEducationBS() {
        return EducationBS;
    }

    public void setEducationBS(Boolean EducationBS) {
        this.EducationBS = EducationBS;
    }

    public Boolean getEducationMS() {
        return EducationMS;
    }

    public void setEducationMS(Boolean EducationMS) {
        this.EducationMS = EducationMS;
    }

    public Double getServicecharge() {
        return servicecharge;
    }

    public void setServicecharge(Double servicecharge) {
        this.servicecharge = servicecharge;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    
    
    
    
    
        public Object update(){
        
                try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());      
            
            
                // code for hashing passwords
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] sign = md.digest(password.getBytes());
                
                System.out.println("md5--------------");
                String temphashedval = "";
                for (int i = 0; i < sign.length; i++){
                    temphashedval += String.format("%02X",sign[i]);
                }
                System.out.println(temphashedval);
                System.out.println("/md5--------------");
                // /code for hashing passwords

            
            
                    crs.setCommand("update photographer set fname = ?,"
                            + "lname = ?,"
                            + "password = ?,"
                            + "nationality = ?,"
                            + "address = ?,"
                            + "eduhs = ?,"
                            + "edubs = ?,"
                            + "edums = ?,"
                            + "experience = ?,"
                            + "rate = ?,"
                            + "dob = ?"
                            + " where id = ?");
                    crs.setString(1, fname);
                    crs.setString(2, lname);
                    crs.setString(3, temphashedval);
                    crs.setString(4, Nationality);
                    crs.setString(5, Address);
                    crs.setBoolean(6, EducationHS);
                    crs.setBoolean(7, EducationBS);
                    crs.setBoolean(8, EducationMS);
                    crs.setString(9, experience);
                    crs.setDouble(10, servicecharge);
                    crs.setString(11, dob);
                    crs.setInt(12, ID);
                    crs.execute();

            }   catch (SQLException ex) {
                    Logger.getLogger(Albums.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Photographer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Photographer.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                return "portfolio.xhtml";

    }
    
    
    
    
    public Object login(){
        
        System.out.println("--- 1");
        
        String user = this.getUsername();
        String pass = this.getPassword();
        System.out.println(user);
        System.out.println(pass);
        int which = 0;
        int whicherr = 0;
        
        ArrayList<String> returned = new ArrayList<>();
            try {
//                out.println("a");
//                out.println("b");

//                out.println("c");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                crs.setCommand("select * from photographer where username like ?");

                // code for hashing passwords
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] sign = md.digest(pass.getBytes());
                
                System.out.println("md5--------------");
                String temphashedval = "";
                for (int i = 0; i < sign.length; i++){
                    temphashedval += String.format("%02X",sign[i]);
                }
                System.out.println(temphashedval);
                System.out.println("/md5--------------");
                // /code for hashing passwords

                
                crs.setString(1, user);
                crs.execute();
                whicherr += 1;

                while(crs.next()){
                    returned.add(crs.getString("password"));
                }

                if (returned.size() < 1){
                    which = 0;                    
                }
                else{
//                    out.println("k");
//                    out.println(returned.get(0).equals(new String(sign, "UTF-8")));
                    which = 1;
                    if (returned.get(0).equals(temphashedval)) which = 2;
                System.out.println("--- 5");
//                    out.println("l");
//                    out.println(new String(sign, "UTF-8"));
//                    out.println("m");
                }


            } catch (SQLException ex) {
                switch(whicherr){
                    case 0:
                        return "Username Error";
                    case 1:
                        return "Password Error";
                }
            } catch (Exception e){
                return "Some Error";
            }

        
            switch(which){
                case 0:
                    return "Username Error";
                case 1:
                    return "Password Error";
            }
            
            try{
                crs.previous();
                this.setID(crs.getInt("id"));
                this.setFname(crs.getString("fname"));
                this.setLname(crs.getString("lname"));
                this.setNationality(crs.getString("nationality"));
                this.setAddress(crs.getString("address"));
                this.setEducationHS(crs.getBoolean("eduhs"));
                this.setEducationBS(crs.getBoolean("edubs"));
                this.setEducationMS(crs.getBoolean("edums"));
                this.setExperience(crs.getString("experience"));
                this.setServicecharge(crs.getDouble("rate"));
                this.setDob(crs.getString("dob"));
            } catch (SQLException ex) {
            Logger.getLogger(Photographer.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println(this.fname + " " + this.lname);
            
            controller.current_p = this;
            System.out.println("Photographer logged in with id: " + Integer.toString(this.ID));
            System.out.println("current_p's ID: " + Integer.toString(controller.current_p.getID()));

            controller.logged_in = true;
            controller.logged_in_as_customer = false;
            return "Passed";

        
    }
    
    
        public Object logout(){
            
            controller.logged_in = false;
//            logged_in_as_customer = false;
            return "index.xhtml";
        }
    
}
