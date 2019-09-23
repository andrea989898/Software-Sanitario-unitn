/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.manipulate;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ticket;
import java.util.*;
/**
 *
 * @author franc
 */
public class ManipulateTickets {
    
    public static ArrayList<Ticket> ScreamTicketByPaid(List<Ticket> tickets){
        ArrayList<Ticket> ticketsScream = new ArrayList<Ticket>();
        
        for(Ticket ticket:tickets){
            if(!ticket.getIsPaid()){
                ticketsScream.add(ticket);
            }
        }
        return ticketsScream;
    }
    
    
    
    
    
    
}
