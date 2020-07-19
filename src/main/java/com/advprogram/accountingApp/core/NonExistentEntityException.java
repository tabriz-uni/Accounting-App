/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.advprogram.accountingApp.core;

public class NonExistentEntityException extends Throwable {

    private static final long serialVersionUID = -3760558819369784286L;

    public NonExistentEntityException(String message) {
        super(message);
    }
}