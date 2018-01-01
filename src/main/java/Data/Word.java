/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Arif
 */
public class Word {
    
    
    public static  final class Type{
        public static  final int TITLE=1000;
        public static  final int H1=1;
        public static  final int H2=2;
        public static  final int H3=3;
        public static  final int SIMPLE=0;
        public static  final int LINK=10;        
        public static  final int BOLD=100;
    }
    
    
    int location;
    String word;
    int type;

    public Word(int location, String word, int type) {
        this.location = location;
        this.word = word;
        this.type = type;
    }

    public int getLocation() {
        return location;
    }

    public String getWord() {
        return word;
    }

    public int getType() {
        return type;
    }
    
    
    
    

    @Override
    public String toString() {
        return "Word{" + "location=" + location + ", word=" + word + ", type=" + type + '}';
    }
    
    
    
    
}
