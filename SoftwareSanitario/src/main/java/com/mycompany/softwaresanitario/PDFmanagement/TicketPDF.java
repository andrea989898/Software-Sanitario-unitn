/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.PDFmanagement;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.utils.PDStreamUtils;
import com.mycompany.softwaresanitario.commons.persistence.dao.TicketDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.Ticket;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
/**
 *
 * @author franc
 */
public class TicketPDF {
    
    
    public static void generateTicketPDF(String id, DAOFactory daoFactory, HttpServletRequest request, HttpServletResponse response, String pdfFolder) throws IOException{
        
        //System.out.println(daoFactory);
        TicketDAO ticketDao;
        try {
            ticketDao = daoFactory.getDAO(TicketDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
        }
        
        
        Ticket ticket = null;
        try {
            ticket = ticketDao.getByPrimaryKey(Integer.parseInt(id));
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get tickets", ex));
        }
        if(ticket!=null){
            try (PDDocument doc = new PDDocument()) {
                PDPage page = new PDPage();
                doc.addPage(page);

                try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                    PDStreamUtils.write(
                        contents,
                        "Ticket Review",
                        PDType1Font.HELVETICA_BOLD,
                        26,
                        30,
                        700,
                        Color.BLUE);
                    PDStreamUtils.write(
                        contents,
                        "The following page shows the ticket of the patient " + ticket.getIdPatient() + " " + ".",
                        PDType1Font.HELVETICA_BOLD,
                        14,
                        30,
                        675,
                        Color.BLUE);

                    float margin = 30;
                    float yStartNewPage = page.getMediaBox().getHeight() - (4 * margin);
                    float tableWidth = page.getMediaBox().getWidth() - (4 * margin);
                
                    boolean drawContent = true;
                    float yStart = yStartNewPage;
                    float bottomMargin = 70;
                    float yPosition = 660;
                
                    BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);
                    Row<PDPage> header = table.createRow(20);
                    header.createCell(20, "Code");
                    header.createCell(20, "Cost");
                    header.createCell(20, "Date of emission");
                    header.createCell(20, "Expiration date");
                    header.createCell(20, "Examination Code");
                    header.createCell(10, "Exam Code");
                    table.addHeaderRow(header);
                
                    
                    Row<PDPage> row = table.createRow(12);
                    row.createCell(String.valueOf(ticket.getCode()));
                    row.createCell(String.valueOf(ticket.getCost()));
                    row.createCell(String.valueOf(ticket.getDate()));
                    row.createCell(String.valueOf(ticket.getExpirationDate()));
                    row.createCell(String.valueOf(ticket.getIdExamination()));
                    row.createCell(String.valueOf(ticket.getIDExam()));
                
                    table.draw();
                }
                //"C:\\Users\\franc\\Desktop\\Software-Sanitario-unitn\\SoftwareSanitario\\src\\main\\webapp\\pdfs"
                doc.save(new File(pdfFolder, "ticket-" + ticket.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf"));
                
                
                response.setContentType("application/pdf");
                
                response.setHeader("Content-disposition", "attachment; filename='ticket.pdf'");
                System.out.println("ticket-" + ticket.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf");
                doc.save(response.getOutputStream());   
                
                //doc.close();
                //return doc;
            }
        }
        //return null;
    }
        
    
    
    
}
