/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataRuntime;

/**
 *
 * @author Arif
 */
public class Word {
    
    
    public static class Type{
        public static int HEADING1=1;
        public static int HEADING2=2;
        public static int HEADING3=3;
        public static int SIMPLE=0;
        public static int LINK=10;
    }
    
    
    int location;
    String word;
    int type;

    public Word(int location, String word, int type) {
        this.location = location;
        this.word = word;
        this.type = type;
    }
    
    

    @Override
    public String toString() {
        return "Word{" + "location=" + location + ", word=" + word + ", type=" + type + '}';
    }
    
    
    
    
}
