/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.PDFmanagement;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.utils.PDStreamUtils;
import com.alibaba.fastjson.JSON;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.PrescriptionDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.Exam;
import com.mycompany.softwaresanitario.commons.persistence.entities.Prescription;
import com.mycompany.softwaresanitario.image.ImageUtil;
import com.mycompany.softwaresanitario.image.QRCode;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author PC Andrea
 */
public class ExamPDF {
    public static void generateExamPDF(String id, DAOFactory daoFactory, HttpServletRequest request, HttpServletResponse response, String pdfFolder) throws IOException{
        
        //System.out.println(daoFactory);
        ExamDAO examDao;
        PrescriptionDAO prescriptionDao;
        try {
            examDao = daoFactory.getDAO(ExamDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for exams storage system", ex));
        }
        
        try {
            prescriptionDao = daoFactory.getDAO(PrescriptionDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for prescriptions storage system", ex));
        }
        
        String qrCodeMessage = null;
        Exam exam = null;
        Prescription prescription = null;
        try {
            exam = examDao.getByCode(Integer.parseInt(id));
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get exam", ex));
        }
        
        if(exam!=null){
            try {
                prescription = prescriptionDao.getPrescriptionByExamCode(exam.getCode());
            } catch (DAOException ex) {
                throw new RuntimeException(new ServletException("Impossible to get exam", ex));
            }
            qrCodeMessage = "Prescription code: " + prescription.getCode() + "  Doctor id: " + exam.getIDDoctor() + "  Patient id: " + exam.getIDPatient() + "  Exame date: " + exam.getExaminationDate().toString();
            
            try (PDDocument doc = new PDDocument()) {
                PDPage page = new PDPage();
                PDFont font = PDType1Font.TIMES_ROMAN;
                PDFont fontBold = PDType1Font.TIMES_BOLD;
                int fontSize = 12;
                doc.addPage(page);
                PDImageXObject qrCode = JPEGFactory.createFromImage(doc,
                    QRCode.generate(JSON.toJSONString(qrCodeMessage)));
                
                try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                    contents.drawImage(qrCode, 30, 676, 110, 110);
                    PDStreamUtils.write(
                        contents,
                        "Exam Review",
                        PDType1Font.HELVETICA_BOLD,
                        26,
                        30,
                        676-60,
                        Color.BLUE);
                    PDStreamUtils.write(
                        contents,
                        "The following page shows the exam of the patient " + exam.getIDPatient() + " " + ".",
                        PDType1Font.HELVETICA_BOLD,
                        14,
                        30,
                        676-60-26,
                        Color.BLUE);
                    
                    scriviPdf(doc, contents, 30, 560, Color.BLACK, "Exam code: ", String.valueOf(exam.getCode()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 540, Color.BLACK, "SSD patient: ", String.valueOf(exam.getIDPatient()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 520, Color.BLACK, "SSD doctor: ", String.valueOf(exam.getIDDoctor()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 500, Color.BLACK, "Exam date: ", String.valueOf(exam.getExaminationDate()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 480, Color.BLACK, "The exam has been already done: ", String.valueOf(exam.getIsDone()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 460, Color.BLACK, "The exam is a recall: ", String.valueOf(exam.getIsRecall()), font, fontBold, fontSize);
                    scriviPdf(doc, contents, 30, 440, Color.BLACK, "Result of the exam: ", String.valueOf(exam.getResult()), font, fontBold, fontSize);
                    
                }
                //"C:\\Users\\franc\\Desktop\\Software-Sanitario-unitn\\SoftwareSanitario\\src\\main\\webapp\\pdfs"
                doc.save(new File(pdfFolder, "exam-" + exam.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf"));
                
                
                response.setContentType("application/pdf");
                
                response.setHeader("Content-disposition", "attachment; filename=exam.pdf");
                //System.out.println("ticket-" + ticket.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf");
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