package com.ap.datagrid.spring.core.client;

import java.io.Serializable;

/**
 * Main domain object.
 *
 *@author anand.prakash
 */
public class Client implements Serializable{

    private int id;
    private String firstName;
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String lastName;
    private String favoriteCoffee;
    private int numberOfOrders;

    public Client(int id, String firstName, String lastName, String favoriteCoffee, int numberOfOrders) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.favoriteCoffee = favoriteCoffee;
        this.numberOfOrders = numberOfOrders;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFavoriteCoffee() {
        return favoriteCoffee;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }
}
