/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.exceptions;

/**
 *
 * @author franc
 */
public class DAOFactoryException extends Exception{
    
    
     public DAOFactoryException() {
        super();
    }

     public DAOFactoryException(String message) {
        super(message);
    }

    public DAOFactoryException(Throwable cause) {
        super(cause);
    }

    public DAOFactoryException(String message, Throwable cause) {
        super(message, cause);
    }


}
