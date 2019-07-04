/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Asif Towheed
 */
public class Album {
    
    private int ID, photographerID;
    private String albumname;

    public Album() {
    }

    public Album(int ID, int photographerID, String albumname) {
        this.ID = ID;
        this.photographerID = photographerID;
        this.albumname = albumname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPhotographerID() {
        return photographerID;
    }

    public void setPhotographerID(int photographerID) {
        this.photographerID = photographerID;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }
    
    
    
}
