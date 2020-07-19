/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.advprogram.accountingApp.core;


public class NonExistentEmployeeException extends NonExistentEntityException {

    private static final long serialVersionUID = 8633588908169766368L;

    public NonExistentEmployeeException() {
        super("Employee does not exist");
    }

}