/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.PDFmanagement;

import be.quodlibet.boxable.utils.PDStreamUtils;
import com.mycompany.softwaresanitario.commons.persistence.dao.DrugDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.PrescriptionDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.Drug;
import com.mycompany.softwaresanitario.commons.persistence.entities.Prescription;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
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
public class RecipePDF {
    public static void generateRecipePDF(String id, DAOFactory daoFactory, HttpServletRequest request, HttpServletResponse response, String pdfFolder) throws IOException{
        
        //System.out.println(daoFactory);
        PrescriptionDAO prescriptionDao;
        DrugDAO drugDao;
        try {
            prescriptionDao = daoFactory.getDAO(PrescriptionDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for prescription storage system", ex));
        }
        try {
            drugDao = daoFactory.getDAO(DrugDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for drugs storage system", ex));
        }
        
        Prescription prescription = null;
        try {
            prescription = prescriptionDao.getByPrimaryKey(Integer.parseInt(id));
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get tickets", ex));
        }
        
        List<Drug> drugs = null;
        try 
        {
            drugs = drugDao.getAllDrugsByRecipe(prescription.getIdrecipe());
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get tickets", ex));
        }
        
        if(prescription!=null){
            try (PDDocument doc = new PDDocument()) {
                PDPage page = new PDPage();
                PDFont font = PDType1Font.TIMES_ROMAN;
                PDFont fontBold = PDType1Font.TIMES_BOLD;
                int fontSize = 12;
                doc.addPage(page);

                try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                    PDStreamUtils.write(
                        contents,
                        "Prescription Review",
                        PDType1Font.HELVETICA_BOLD,
                        26,
                        30,
                        700,
                        Color.BLUE);
                    PDStreamUtils.write(
                        contents,
                        "The following page shows the prescription of the patient " + prescription.getIdpatient() + " " + ".",
                        PDType1Font.HELVETICA_BOLD,
                        14,
                        30,
                        675,
                        Color.BLUE);

                    scriviPdf(doc, contents, 30, 640, Color.BLACK, "Prescription code: ", String.valueOf(prescription.getCode()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 620, Color.BLACK, "Date of prescription: ", String.valueOf(prescription.getDate()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 600, Color.BLACK, "Doctor ssd: ", String.valueOf(prescription.getIddoctor()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 580, Color.BLACK, "Analisys: ", String.valueOf(prescription.getAnalisys()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 560, Color.BLACK, "Recipe code: ", String.valueOf(prescription.getIdrecipe()), font, fontBold, fontSize);
                    int ndrug = 1;
                    int xposition = 560;
                    for(Drug d:drugs){
                        xposition -= 20;
                        scriviPdf(doc, contents, 30, xposition, Color.BLACK, "Drug " + ndrug + ": ", String.valueOf(d.getName()), font, fontBold, fontSize);
                        ndrug++;
                    }
                    
                }
                //"C:\\Users\\franc\\Desktop\\Software-Sanitario-unitn\\SoftwareSanitario\\src\\main\\webapp\\pdfs"
                doc.save(new File(pdfFolder, "prescription-" + prescription.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf"));
                
                
                response.setContentType("application/pdf");
                
                response.setHeader("Content-disposition", "attachment; filename=prescription.pdf");
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
