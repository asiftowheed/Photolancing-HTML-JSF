/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author Asif Towheed
 */
@ManagedBean
@RequestScoped
public class NewPhotographer {
    
    
        private int ID;
    private String username, password, repassword, fname, lname, Nationality, Address, experience, dob;
    private Boolean EducationHS, EducationBS, EducationMS;
    private Double servicecharge;

    public NewPhotographer() {
    }

//    public NewPhotographer(String username, String fname, String lname, String Nationality, String Address, String experience, Boolean EducationHS, Boolean EducationBS, Boolean EducationMS, Double servicecharge) {
//        this.username = username;
//        this.fname = fname;
//        this.lname = lname;
//        this.Nationality = Nationality;
//        this.Address = Address;
//        this.experience = experience;
//        this.EducationHS = EducationHS;
//        this.EducationBS = EducationBS;
//        this.EducationMS = EducationMS;
//        this.servicecharge = servicecharge;
//    }
    
    

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
    
    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
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
    
    
    
    
    
    public Object register(){
        
        
        if (fname.isEmpty() || lname.isEmpty()){
            System.out.println("Full name is required");
            return "registerp.xhtml";
        }
        
        CachedRowSet crs = null;
        
        if (username.isEmpty()){
            System.out.println("Please enter the username");
            return "registerp.xhtml";
        }
        else if (password.isEmpty()){
            System.out.println("Please enter a password");
            return "registerp.xhtml";
        }
        else if (!password.equals(repassword)){
            System.out.println("Passwords don't match");
            return "registerp.xhtml";
        }
        else{
            try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                    crs.setCommand("select * from photographer where username like ?");

                    crs.setString(1, username);
                    System.out.println("--- 2.3");
                    crs.execute();

                    while(crs.next()){
                        System.out.println("Username exists");
                        return "registerp.xhtml";
                    }
                    System.out.println("--- 4");


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
                    
                    
                    crs.setCommand("insert into photographer(username, password, fname, lname, dob, nationality, address, eduhs, edubs, edums, experience, rate) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                    crs.setString(1, username);
                    crs.setString(2, temphashedval);
                    crs.setString(3, fname);
                    crs.setString(4, lname);
                    crs.setString(5, dob);
                    crs.setString(6, Nationality);
                    crs.setString(7, Address);
                    crs.setBoolean(8, EducationHS);
                    crs.setBoolean(9, EducationBS);
                    crs.setBoolean(10, EducationMS);
                    crs.setString(11, experience);
                    crs.setDouble(12, servicecharge);
                    crs.execute();
                    

            }   catch (SQLException ex) {
                    Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NewPhotographer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(NewPhotographer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        return "login.xhtml";
                
    }
    
    
    
    
    
    
}
