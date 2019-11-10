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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for exam storage system", ex));
        }
        
        try {
            prescriptionDao = daoFactory.getDAO(PrescriptionDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for prescription storage system", ex));
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

                    float margin = 30;
                    float yStartNewPage = page.getMediaBox().getHeight() - (4 * margin);
                    float tableWidth = page.getMediaBox().getWidth() - (4 * margin);
                
                    boolean drawContent = true;
                    float yStart = yStartNewPage;
                    float bottomMargin = 70;
                    float yPosition = 676-60-26-14;
                
                    BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);
                    Row<PDPage> header = table.createRow(20);
                    header.createCell(20, "Code");
                    header.createCell(20,"IDPatient");
                    header.createCell(20, "IDDoctor");
                    header.createCell(20, "ExaminationDate");
                    header.createCell(10,"IsDone");
                    header.createCell(10,"IsRecall");
                    header.createCell(30, "Result");
                    table.addHeaderRow(header);
                
                    
                    Row<PDPage> row = table.createRow(12);
                    row.createCell(String.valueOf(exam.getCode()));
                    row.createCell(String.valueOf(exam.getIDPatient()));
                    row.createCell(String.valueOf(exam.getIDDoctor()));
                    row.createCell(String.valueOf(exam.getExaminationDate()));
                    row.createCell(String.valueOf(exam.getIsDone()));
                    row.createCell(String.valueOf(exam.getIsRecall()));
                    row.createCell(String.valueOf(exam.getResult()));
                
                    table.draw();
                    
                }
                //"C:\\Users\\franc\\Desktop\\Software-Sanitario-unitn\\SoftwareSanitario\\src\\main\\webapp\\pdfs"
                doc.save(new File(pdfFolder, "exam-" + exam.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf"));
                
                
                response.setContentType("application/pdf");
                
                response.setHeader("Content-disposition", "attachment; filename='exam.pdf'");
                //System.out.println("ticket-" + ticket.getCode() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf");
                doc.save(response.getOutputStream());   
                
                //doc.close();
                //return doc;
            }
        }
        //return null;
    }
}