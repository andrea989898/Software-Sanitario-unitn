/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.factories.jdbc;
import com.mycompany.softwaresanitario.commons.persistence.dao.DAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.dao.jdbc.JDBCDAO;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author franc
 */
public class JDBCDAOFactory implements DAOFactory{

    
    private final transient Connection CON;
    private final transient HashMap<Class, DAO> DAO_CACHE;

    private static JDBCDAOFactory instance;
    
    public static void configure(String dbUrl) throws DAOFactoryException {
        if (instance == null) {
            instance = new JDBCDAOFactory(dbUrl);
        } else {
            throw new DAOFactoryException("DAOFactory already configured. You can call configure only one time");
        }
    }

    private JDBCDAOFactory(String dbUrl) throws DAOFactoryException {
        super();

        try {
            Class.forName("org.postgresql.Driver", true, getClass().getClassLoader());
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }

        try {
            CON = DriverManager.getConnection(dbUrl);
        } catch (SQLException sqle) {
            throw new DAOFactoryException("Cannot create connection", sqle);
        }
        
        DAO_CACHE = new HashMap<>();
    }

    public static JDBCDAOFactory getInstance() throws DAOFactoryException {
        if (instance == null) {
            throw new DAOFactoryException("DAOFactory not yet configured. Call DAOFactory.configure(String dbUrl) before use the class");
        }
        return instance;
    }


    @Override
    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:postgresql:;shutdown=true");
        } catch (SQLException sqle) {
            Logger.getLogger(JDBCDAOFactory.class.getName()).info(sqle.getMessage());
        }
    }


    @Override
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException {
        DAO dao = DAO_CACHE.get(daoInterface);
        if (dao != null) {
            return (DAO_CLASS) dao;
        }
        
        Package pkg = daoInterface.getPackage();
        String prefix = pkg.getName() + ".jdbc.JDBC";
        
        try {
            Class daoClass = Class.forName(prefix + daoInterface.getSimpleName());
            
            Constructor<DAO_CLASS> constructor = daoClass.getConstructor(Connection.class);
            DAO_CLASS daoInstance = constructor.newInstance(CON);
            if (!(daoInstance instanceof JDBCDAO)) {
                throw new DAOFactoryException("The daoInterface passed as parameter doesn't extend JDBCDAO class");
            }
            DAO_CACHE.put(daoInterface, daoInstance);
            return daoInstance;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | SecurityException ex) {
            throw new DAOFactoryException("Impossible to return the DAO", ex);
        }
    }



}
