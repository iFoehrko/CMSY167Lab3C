/**
 * @author Ingrid Sobieski Foehrkolb
 * This file contains the Class definition of the Customer class.  It contains 
 * only String value representing the name, address, city, state, zip code, and
 * phone number of each customer.
 *  
*/
package lab3c;

public class Customer {
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    
    Customer(){}
    Customer(String name, String address, String city, String state, String zip, String phone){
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
    }
    
    //setters 
    public void setName(String name){this.name = name;}
    public void setAddress(String stAdd){this.address = stAdd;}
    public void setCity(String city){this.city = city;}
    public void setState(String state){this.state = state;}
    public void setZip(String zip){this.zip = zip;}
    public void setPhone(String ph){this.phone = ph;}
    //getters
    public String getName(){return name;}
    public String getAddress(){return address;}
    public String getCity(){return city;}
    public String getState(){return state;}
    public String getZip(){return zip;}
    public String getPhone(){return phone;}
    
    public String toString(){
        return String.format("%-21s%-20s%-20s%-10s%-10s%s", this.getName(),
                    this.getAddress(), this.getCity(), this.getState(), this.getZip(),
                    this.getPhone());
    }
    
}
