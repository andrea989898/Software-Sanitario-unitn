/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.image;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author franc
 */
public class ImageUtil {
    
    private static final Logger LOGGER = Logger.getLogger(ImageUtil.class.getName());
    private static ServletContext SERVLET_CONTEXT = null;
    private static BufferedImage LOGO = null;

    
    /**
     * Configure the class
     *
     * @param servletContext The {@link ServletContext} get information from
     * @throws NullPointerException If servletContext is null
     */
    public static void configure(final ServletContext servletContext) throws NullPointerException {
        if (servletContext == null)
            throw new NullPointerException("ServletContext cannot be null");

        SERVLET_CONTEXT = servletContext;
    }
    
     /**
     * Check if ImageUtil has been configured
     *
     * @throws NullPointerException If ImageUtil has not been configured
     */
    private static void isConfigured() throws NullPointerException {
        if (SERVLET_CONTEXT == null)
            throw new NullPointerException("ImageUtil has not been configured");
    }

    
    /**
     * Return the real Image Path on the Server
     *
     * @return Image Path on the Server
     * @throws NullPointerException If ImageUtil has not been configured
     */
    public static String getImagePath() throws NullPointerException {
        isConfigured();
        String realPath = SERVLET_CONTEXT.getRealPath("/");
        String imageFolder = realPath.substring(0, realPath.length() - 38);
        System.out.println(imageFolder);
        return imageFolder + "src\\main\\webapp\\images";
    }

    /**
     * Return the {@link BufferedImage image} representing the Logo of the Application
     *
     * @return The Logo of the Application
     * @throws NullPointerException If ImageUtil has not been configured
     */
    public static BufferedImage getLOGO() throws NullPointerException, IOException {
        isConfigured();
        if (LOGO == null) {
            LOGO = ImageIO.read(new File(FilenameUtils.separatorsToUnix(getImagePath() + "/favicon/stethoscope.png")));
        }
        return LOGO;
    }
    
}
