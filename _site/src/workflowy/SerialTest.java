
package workflowy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

class Person implements Serializable{
    String name;
    
    public Person(String name){
        this.name = name;
        
    }
    
    @Override
    public String toString(){
        return "Person [name =" + name + "]";
    }
    
    //we might want to apply a toArray for our program

}


public class SerialTest {
    
    public static void main(String[] args){
        Person mike = new Person("Mike McAlexander");
        
        //writing file
        try {
            writeToFile(mike);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(SerialTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //reading file
            readFile();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(SerialTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //static allows for access
    public static void writeToFile(Person p) throws IOException{
        //binary output file
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Person.bin"));
        
        objectOutputStream.writeObject(p);
    }
    
    public static void readFile() throws IOException, ClassNotFoundException{
        //you want it to be the same file you wrote it to
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Person.bin"));
        
        //casting
        Person name = (Person) objectInputStream.readObject();
        System.out.println(name);
    }
}
