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
import org.apache.pdfbox.pdmodel.font.PDFont;
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
                PDFont font = PDType1Font.TIMES_ROMAN;
                PDFont fontBold = PDType1Font.TIMES_BOLD;
                int fontSize = 12;
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

                    scriviPdf(doc, contents, 30, 640, Color.BLACK, "Ticket code: ", String.valueOf(ticket.getCode()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 620, Color.BLACK, "Cost: ", String.valueOf(ticket.getCost()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 600, Color.BLACK, "Date of emission: ", String.valueOf(ticket.getDate()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 580, Color.BLACK, "Expiration date: ", String.valueOf(ticket.getExpirationDate()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 560, Color.BLACK, "Examination Code: ", String.valueOf(ticket.getIdExamination()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 540, Color.BLACK, "Exam Code: ", String.valueOf(ticket.getIDExam()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 520, Color.BLACK, "The ticket has been paid: ", String.valueOf(ticket.getIsPaid()), font, fontBold, fontSize);
                }
                //"C:\\Users\\franc\\Desktop\\Software-Sanitario-unitn\\SoftwareSanitario\\src\\main\\webapp\\pdfs"
                doc.save(new File(pdfFolder, "ticket-" + ticket.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf"));
                
                
                response.setContentType("application/pdf");
                
                response.setHeader("Content-disposition", "attachment; filename=ticket.pdf");
                System.out.println("ticket-" + ticket.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf");
                doc.save(response.getOutputStream());   
                
                //doc.close();
                //return doc;
            }
        }
        //return null;
    }
        
    private static void scriviPdf(PDDocument document, PDPageContentStream contentStream, int x, int y, Color color, String stringa1, String stringa2, PDFont font, PDFont fontBold, int fontSize) {
        try {
            contentStream.beginText();
            contentStream.setFont(fontBold, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(stringa1);
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.showText(stringa2);
            contentStream.endText();
        } catch (IOException ex) {
            System.err.println("Unable to generatePDF a Prescription Exam PDF");
        }
    }
    
    
    
}
