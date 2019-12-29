/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.XLSmanagement;

import com.mycompany.softwaresanitario.commons.persistence.dao.DrugDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.PrescriptionDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.Drug;
import com.mycompany.softwaresanitario.commons.persistence.entities.Prescription;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author franc
 */
public class PrescriptionsRecipeXLS {
    
    private static String[] columns = {"Prescription Code", "Analysis", "Recipe code", "Doctor fiscal code", "Patient fiscal code", "Date", "Farmaco 1", "Farmaco 2", "Farmaco 3", "Farmaco 4", "Farmaco 5"};
    
    public static void generatePrescriptionsXLS(String date, String province, DAOFactory daoFactory, HttpServletRequest request, HttpServletResponse response, String xlsFolder) throws IOException{
        
        PrescriptionDAO prescriptionDao;
        DrugDAO drugDao;
        List<Prescription> prescriptions = null;
        
        try {
            prescriptionDao = daoFactory.getDAO(PrescriptionDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for prescriptions storage system", ex));
        }
        
        try {
            drugDao = daoFactory.getDAO(DrugDAO.class);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for drugs storage system", ex));
        }
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report of recipes");
        
        try {
            prescriptions = prescriptionDao.getPrescriptionsByDateAndProvince(date, province);
        } catch (DAOException ex) {
            Logger.getLogger(PrescriptionsRecipeXLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        
        Row headerRow = sheet.createRow(0);
        
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        
        int rowNum = 1;
        List<Drug> drugs = null;
        for(Prescription prescription: prescriptions) {
            Row row = sheet.createRow(rowNum++);
            
            try {
                drugs = drugDao.getAllDrugsByRecipe(prescription.getIdrecipe());
            } catch (DAOException ex) {
                Logger.getLogger(PrescriptionsRecipeXLS.class.getName()).log(Level.SEVERE, null, ex);
            }

            row.createCell(0)
                    .setCellValue(prescription.getCode());

            row.createCell(1)
                    .setCellValue(prescription.getAnalisys());
            
            row.createCell(2)
                    .setCellValue(prescription.getIdrecipe());
            
            row.createCell(3)
                    .setCellValue(prescription.getIddoctor());
            
            row.createCell(4)
                    .setCellValue(prescription.getIdpatient());

            row.createCell(5)
                    .setCellValue(prescription.getDate().toString());
            
            int numcell = 6;
            for(Drug drug: drugs){
                row.createCell(numcell)
                    .setCellValue(drug.getName());
                numcell ++;
            }
        }
        
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        System.out.println(xlsFolder);
        FileOutputStream fileOut = new FileOutputStream(xlsFolder + "\\recipeReport-" + Calendar.getInstance().getTimeInMillis() + ".xlsx");
        workbook.write(fileOut);
        
        response.setContentType("application/vnd.ms-excel");
                
        response.setHeader("Content-disposition", "attachment; filename=reportRecipes.xlsx");
        
        OutputStream out = response.getOutputStream();
        
        workbook.write(out);

        // Closing the workbook
        fileOut.close();
        //fileOutUser.close();
        
        workbook.close();
    }
}
