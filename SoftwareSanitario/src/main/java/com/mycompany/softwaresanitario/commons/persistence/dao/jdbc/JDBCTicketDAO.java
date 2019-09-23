/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.commons.persistence.dao.jdbc;

import com.mycompany.softwaresanitario.commons.persistence.dao.TicketDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC Andrea
 */
public class JDBCTicketDAO extends JDBCDAO<Ticket, Integer> implements TicketDAO{

    public JDBCTicketDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ticket getByPrimaryKey(Integer primaryKey) throws DAOException {
        Ticket ticket = new Ticket();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM tickets t WHERE t.code = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {
               // System.out.println(rs.next());
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same code! WHY???");
                    }
                    
                    ticket.setCode(rs.getInt("code"));
                    ticket.setCost(rs.getInt("cost"));
                    ticket.setDate(rs.getDate("date"));
                    ticket.setExpirationDate(rs.getDate("expirationdate"));
                    ticket.setIdExamination(rs.getInt("idexamination"));
                    ticket.setIDExam(rs.getInt("idexam"));
                    ticket.setIdPatient(rs.getString("idpatient"));
                    ticket.setIsPaid(rs.getBoolean("ispaid"));
                    
                    
                    
                    
                    
                    return ticket;
                }

                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find the ticket", ex);
        }
    }

    @Override
    public List<Ticket> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList <Ticket> getTickets(String patient) throws DAOException{
        String myGet = "select *\n" +
                                "from tickets tt "+
                                "where tt.idpatient = ?";
        try(PreparedStatement stm = CON.prepareStatement(myGet)){
            stm.setString(1, patient);
            try(ResultSet rst = stm.executeQuery()){
                ArrayList<Ticket> tickets = new ArrayList<Ticket>();
                //System.out.println("Sono qui");
                while (rst.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setCode(rst.getInt("code"));
                    ticket.setCost(rst.getInt("cost"));
                    ticket.setDate(rst.getDate("date"));
                    ticket.setExpirationDate(rst.getDate("expirationdate"));
                    ticket.setIdExamination(rst.getInt("idexamination"));
                    ticket.setIDExam(rst.getInt("idexam"));
                    ticket.setIdPatient(rst.getString("idpatient"));
                    ticket.setIsPaid(rst.getBoolean("ispaid"));
                    
                    
                    tickets.add(ticket); 
                    //System.out.println(ticket.code + " " + ticket.isPaid +" "+ ticket.expirationDate);
                }
                
                
                return tickets;
            }
        }catch (SQLException ex) {
            throw new DAOException("Impossible to find the user", ex);
        }
    }
    
}


