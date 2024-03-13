package Classversion;

import java.util.Scanner;

class Customer {
    private String firstName;
    private String lastName;
    private int id;
    private int pizzasRequired;

    public Customer(String firstName, String lastName, int id, int pizzasRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.pizzasRequired = pizzasRequired;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getId() {
        return id;
    }

    public int getPizzasRequired() {
        return pizzasRequired;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "fullName='" + getFullName() + '\'' +
                ", id=" + id +
                ", pizzasRequired=" + pizzasRequired +
                '}';
    }
}
