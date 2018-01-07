/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataIndexed;

import java.io.Serializable;

/**
 *
 * @author Arif
 */
public class LocationAndType implements Serializable{
    int location;
    int type;

    public LocationAndType() {
    }
    
    
    

    public LocationAndType(int location, int type) {
        this.location = location;
        this.type = type;
    }

    public int getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "LocationAndType{" + "location=" + location + ", type=" + type + '}';
    }
    
    
}
