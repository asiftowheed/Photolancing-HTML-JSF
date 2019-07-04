/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
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
public class NewCustomer {
    
    
        private int ID;
    private String username, password, repassword, fname, lname, Nationality, Address, dob;
    Date dobject;

    public NewCustomer() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFname() {
        return fname;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Date getDobject() {
        return dobject;
    }

    public void setDobject(Date dobject) {
        this.dobject = dobject;
    }
    
    
    
    
    
    
    public Object register(){
        
        
        if (fname.isEmpty() || lname.isEmpty()){
            System.out.println("Full name is required");
            return "register.xhtml";
        }
        
        CachedRowSet crs = null;
        
        if (username.isEmpty()){
            System.out.println("Please enter the username");
            return "register.xhtml";
        }
        else if (password.isEmpty()){
            System.out.println("Please enter a password");
            return "register.xhtml";
        }
        else if (!password.equals(repassword)){
            System.out.println("Passwords don't match");
            return "register.xhtml";
        }
        else{
            try{
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                crs=RowSetProvider.newFactory().createCachedRowSet();
                crs.setUrl(Singleton.getInstance().getDB());
                crs.setUsername(Singleton.getInstance().getUser());
                crs.setPassword(Singleton.getInstance().getPasswd());            
                crs.setCommand("select * from customer where username like ?");

                crs.setString(1, username);
                System.out.println("--- 2.3");
                crs.execute();

                while(crs.next()){
                    System.out.println("Username exists");
                    return "register.xhtml";
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

                crs.setCommand("insert into customer(username, password, fname, lname, dob, nationality, address) values(?,?,?,?,?,?,?)");
                crs.setString(1, username);
                crs.setString(2, temphashedval);
                crs.setString(3, fname);
                crs.setString(4, lname);
                crs.setString(5, dob);
                crs.setString(6, Nationality);
                crs.setString(7, Address);
                crs.execute();

            }   catch (SQLException ex) {
                    Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return "loginc.xhtml";
                
    }
    
    
    
}
