/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author Asif Towheed
 */
@ManagedBean
@RequestScoped
public class SelectedAlbum {
    
    @ManagedProperty("#{controller}")
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    Album selected;

    public SelectedAlbum() {
    }

    public Album getSelected() {
        selected = controller.selected_album;
        return selected;
    }

    public void setSelected(Album selected) {
        this.selected = selected;
    }
    
    
    
}
