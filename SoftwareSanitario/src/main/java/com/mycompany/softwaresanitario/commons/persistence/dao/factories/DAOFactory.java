/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.factories;
import com.mycompany.softwaresanitario.commons.persistence.dao.DAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;


/**
 *
 * @author franc
 */
public interface DAOFactory {
    
    
     public void shutdown();
     
     
     public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException;
    
}
