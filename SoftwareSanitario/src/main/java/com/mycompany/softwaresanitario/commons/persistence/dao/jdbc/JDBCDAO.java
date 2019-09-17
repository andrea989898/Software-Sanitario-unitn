/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.DAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import java.sql.Connection;
import java.util.HashMap;


/**
 *
 * @author franc
 */
public abstract class JDBCDAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> implements DAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {
    
     protected final Connection CON;
     
     protected final HashMap<Class, DAO> FRIEND_DAOS;
     
     
     protected JDBCDAO(Connection con) {
        super();
        this.CON = con;
        FRIEND_DAOS = new HashMap<>();
    }

     
    @Override
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoClass) throws DAOFactoryException {
        return (DAO_CLASS) FRIEND_DAOS.get(daoClass);
    }

    
    
}
