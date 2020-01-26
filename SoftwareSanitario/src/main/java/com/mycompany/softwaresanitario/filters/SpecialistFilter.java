/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.filters;

import com.mycompany.softwaresanitario.commons.persistence.dao.CityDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.DrugDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExamDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.ExaminationDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.GeneralDoctorDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.PatientDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.UserDAO;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOException;
import com.mycompany.softwaresanitario.commons.persistence.dao.exceptions.DAOFactoryException;
import com.mycompany.softwaresanitario.commons.persistence.dao.factories.DAOFactory;
import com.mycompany.softwaresanitario.commons.persistence.entities.City;
import com.mycompany.softwaresanitario.commons.persistence.entities.Drug;
import com.mycompany.softwaresanitario.commons.persistence.entities.Exam;
import com.mycompany.softwaresanitario.commons.persistence.entities.Examination;
import com.mycompany.softwaresanitario.commons.persistence.entities.GeneralDoctor;
import com.mycompany.softwaresanitario.commons.persistence.entities.Patient;
import com.mycompany.softwaresanitario.commons.persistence.entities.User;
import com.mycompany.softwaresanitario.manipulate.ManipulateExam;
import com.mycompany.softwaresanitario.manipulate.ManipulateExaminations;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author franc
 */
public class SpecialistFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public SpecialistFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SpecialistFilter:DoBeforeProcessing");
        }

        DAOFactory daoFactory = (DAOFactory) request.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system"));
        }
        UserDAO userDao = null;
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
            request.setAttribute("userDao", userDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system", ex));
        }
        
        PatientDAO patientDao = null;
        try {
            patientDao = daoFactory.getDAO(PatientDAO.class);
            request.setAttribute("patientDao", patientDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system", ex));
        }
        
        CityDAO cityDao = null;
        try {
            cityDao = daoFactory.getDAO(CityDAO.class);
            request.setAttribute("cityDao", cityDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for cities storage system", ex));
        }
        
        GeneralDoctorDAO generalDoctorDao = null;
        try {
            generalDoctorDao = daoFactory.getDAO(GeneralDoctorDAO.class);
            request.setAttribute("GeneralDoctorDao", generalDoctorDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
        }
        
        ExaminationDAO examinationDao = null;
        try {
            examinationDao = daoFactory.getDAO(ExaminationDAO.class);
            request.setAttribute("ExaminationDao", examinationDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for examination storage system", ex));
        }
        
        ExamDAO examDao = null;
        try {
            examDao = daoFactory.getDAO(ExamDAO.class);
            request.setAttribute("ExamDao", examDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for exam storage system", ex));
        }
        
        DrugDAO drugDao = null;
        try {
            drugDao = daoFactory.getDAO(DrugDAO.class);
            request.setAttribute("drugDao", drugDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the dao factory for generalDoctor storage system", ex));
        }
        
        String contextPath = request.getServletContext().getContextPath();
        if (contextPath.endsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        request.setAttribute("contextPath", contextPath);
        
        User user = null;
        String isSpecialist = null;
        
        
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            user = (User) session.getAttribute("user");
            isSpecialist = (String) session.getAttribute("specialistSession");
        }
        
        if (user == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "/index.html"));
            return;
        }
        
        if(isSpecialist == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "/restricted/homePage.html"));
            return;
        }
        
        //System.out.println(request.getAttribute("patient"));
        
        
        try {
            List<User> allPatients = patientDao.getAllExecptSSP();
            request.setAttribute("patients", allPatients);
            List<Exam> screamExamsByDone = new ArrayList<Exam>();
            List<Exam> exams = examDao.getExamsOfSpecialist(user.getCf());
            List<Examination> screamExaminationsByDone = new ArrayList<Examination>();
            List<Examination> examinations = examinationDao.getExaminationsSpecialist(user.getCf());
            if(examinations.size()>0)  {
                screamExaminationsByDone = ManipulateExaminations.ScreamExaminationsByDone(examinations);
                request.setAttribute("examinations", examinations);
                if(screamExaminationsByDone.size()>0)    request.setAttribute("screamExaminationsByDone", screamExaminationsByDone);
            }
            if(exams.size()>0) {
                screamExamsByDone = ManipulateExam.ScreamExamsByDone(exams);
                request.setAttribute("exams", exams);
                if(screamExamsByDone.size()>0)    request.setAttribute("screamExamsByDone", screamExamsByDone);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get examinations or exams", ex));
        }
        
        //System.out.println(request.getAttribute("patient"));
        ArrayList<Drug> drugs = new ArrayList();
        try {
            drugs = drugDao.getAllDrugs();
            if(drugs.size() > 0)   request.setAttribute("drugs", drugs);
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or generalDoctor", ex));
        }
        
        try {
            User user2 = userDao.getByCode(user.getCf());
            Patient patient = patientDao.getByCode(user.getCf());
            if(patient != null){
                User generaldoctorpatient = userDao.getByCode(patient.getGeneralDoctorCf());
                //System.out.println(user.getBirth_city_id() + " " + user.getCity_id());
                City birth_city_Patient = cityDao.getByCode(user.getBirth_city_id());
                City city_Patient = cityDao.getByCode(user.getCity_id());
                request.setAttribute("patient", patient);
                request.setAttribute("generaldoctorpatient", generaldoctorpatient);
                request.setAttribute("birth_city_Patient", birth_city_Patient);
                request.setAttribute("city_Patient", city_Patient);
                String avatarPath = "../images/avatar/" + user2.getAvatarPath();
                request.setAttribute("avatarPath", avatarPath);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the patient", ex));
        }
        
        try {
            boolean isAGeneralDoctor = generalDoctorDao.isAGeneralDoctor(user.getCf());
            GeneralDoctor generalDoctor = generalDoctorDao.getByCode(user.getCf());
            List<User> generalDoctors = generalDoctorDao.getAllGeneralDoctors(user.getCf(), generalDoctor.getCf(), user.getCity_id());
            if(generalDoctors != null)   request.setAttribute("AllGeneralDoctor", generalDoctors);
            if(isAGeneralDoctor)   request.setAttribute("generalDoctor", "he/she is a doctor");
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get user or generalDoctor", ex));
        }
        
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SpecialistFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("SpecialistFilter:doFilter()");
        }
        
        doBeforeProcessing(request, response);
        
        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }
        
        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("SpecialistFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SpecialistFilter()");
        }
        StringBuffer sb = new StringBuffer("SpecialistFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
