package BoatClubManagement.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable {
    private static final long serialVersionUID = 765191115952174300L;

    private String name;
    private String lastName;
    private String socialSecurityNumber;
    private final int id;
    private String phoneNumber;

    private final ArrayList<Boat> b_arrayList = new ArrayList<>();


    public Member(String name, String lastName,String socialSecurityNumber, String phoneNumber,int id) {
        this.name = name;
        this.socialSecurityNumber = socialSecurityNumber;
        this.id = id;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) throws Exception {
        if (newName.matches("[a-zA-Z]+") || newName.length() > 20) {
            name = newName;
        } else {
            throw new IllegalArgumentException("Wrong input");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        if (!phoneNumber.matches("[0-9]+")||phoneNumber.length()>10) {
            throw new IllegalArgumentException("Number is Invalid");
        } else {
            //only contain numbers
            this.phoneNumber = phoneNumber;
        }
    }

    public int getId() {
        return id;
    }

    public int getBoatListSize() {
        return b_arrayList.size();
    }

    public Boat getBoatPosition(int position)  {
        return b_arrayList.get(position);
    }

    public Boat[] getBoatArray() {
        Boat[] b_array = new Boat[b_arrayList.size()];
        return b_arrayList.toArray(b_array);
    }

    public void registerBoat(Boat.TYPE type, double length) {
        Boat boat = new Boat(type, length);
        b_arrayList.add(boat);
    }

    public void removeBoatPosition(int position)  {
        b_arrayList.remove(position);
    }

}
