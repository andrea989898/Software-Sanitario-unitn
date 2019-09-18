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
public class JDBCTicketDAO extends JDBCDAO<Ticket, String> implements TicketDAO{

    public JDBCTicketDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ticket getByPrimaryKey(String primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ticket> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList <Ticket> getTickets(Connection conn, String patient) throws SQLException{
                String myGet = "select tt.code, tt.cost, tt.date, tt.expirationdate, e.argument, tt.idexamination, pat.ssd, tt.ispaid\n" +
                                "from tickets tt inner join examinations e\n" +
                                "on tt.idexamination = e.idexamination\n" +
                                "inner join patients pat\n" +
                                "on pat.ssd = tt.idpatient\n" +
                                "where pat.ssd = " + patient ;
        PreparedStatement stm = CON.prepareStatement(myGet);
        ResultSet rst = stm.executeQuery();
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        while (rst.next()) {
            Ticket ticket = new Ticket();
            ticket.setCode(rst.getInt("code"));
            ticket.setCost(rst.getInt("cost"));
            ticket.setDate(rst.getDate("date"));
            ticket.setExpirationDate(rst.getDate("expirationdate"));
            ticket.setIdExamination(rst.getInt("idexamination"));
            ticket.setIdPatient(rst.getString("ssd"));
            ticket.setIsPaid(rst.getBoolean("ispaid"));
            tickets.add(ticket); 
            //System.out.println(ticket.code + " " + ticket.isPaid +" "+ ticket.expirationDate);
        }
        stm.close();
        return tickets;
    }
    
}
