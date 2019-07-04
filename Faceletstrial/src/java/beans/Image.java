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
public class Image {
    
    private int ID, albumID;
    private String filename, directorypath;

    public Image() {
    }

    public Image(int ID, int albumID, String filename, String directorypath) {
        this.ID = ID;
        this.albumID = albumID;
        this.filename = filename;
        this.directorypath = directorypath;
    }
    
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDirectorypath() {
        return directorypath;
    }

    public void setDirectorypath(String directorypath) {
        this.directorypath = directorypath;
    }
    
}
