/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beans.Album;
import beans.Customer;
import beans.Photographer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Asif Towheed
 */
@ManagedBean(name="controller")
@SessionScoped
public class Controller {
    
    public Photographer current_p;
    public Customer current_c;
    public Album selected_album;
    public Boolean logged_in_as_customer;
    public Boolean logged_in = false;
    public boolean logged_in_bean_prop;

    public Controller() {
        logged_in = false;
    }

    public Photographer getCurrent_p() {
        return current_p;
    }

    public void setCurrent_p(Photographer current_p) {
        this.current_p = current_p;
    }

    public Customer getCurrent_c() {
        return current_c;
    }

    public void setCurrent_c(Customer current_c) {
        this.current_c = current_c;
    }

    public Album getSelected_album() {
        return selected_album;
    }

    public void setSelected_album(Album selected_album) {
        this.selected_album = selected_album;
    }

    public Boolean getLogged_in_as_customer() {
        return logged_in_as_customer;
    }

    public void setLogged_in_as_customer(Boolean logged_in_as_customer) {
        this.logged_in_as_customer = logged_in_as_customer;
    }

    public Boolean getLogged_in() {
        return logged_in;
    }

    public void setLogged_in(Boolean logged_in) {
        this.logged_in = logged_in;
    }
    
    public void Set_loggedin_false(){
        logged_in = false;
    }

    public boolean getLogged_in_bean_prop() {
        logged_in_bean_prop = logged_in;
        
        System.out.println("----------------- returning: " + logged_in_bean_prop);
        return logged_in_bean_prop;
    }

    public void setLogged_in_bean_prop(boolean logged_in_bean_prop) {
        this.logged_in_bean_prop = logged_in_bean_prop;
    }
    
    
    
    
    
}
