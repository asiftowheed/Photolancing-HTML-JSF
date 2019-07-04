/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Asif Towheed
 */
@ManagedBean
@SessionScoped
public class testbean {
    
    private String test;

    public testbean() {
    }

    public String getTest() {
        return "hello";
    }

    public void setTest(String test) {
        this.test = test;
    }
    
    
    
}
