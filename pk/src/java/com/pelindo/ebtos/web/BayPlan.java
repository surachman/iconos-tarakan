/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.pelindo.ebtos.ejb.remote.ShipProfileRemote;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author senoanggoro
 */
@WebServlet(name="BayPlan", urlPatterns={"/bayPlan.png"})
public class BayPlan extends HttpServlet {
    @EJB private ShipProfileRemote shipProfile;

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String noPpkb = request.getParameter("ppkb");
        String vesselCode = request.getParameter("vessel");
        String bay = request.getParameter("bay");
        String width = request.getParameter("w");
        String height = request.getParameter("h");
        String isLoad = request.getParameter("isLoad");

        if ((noPpkb == null && vesselCode == null) || bay == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }


        InputStream image = null;
        try {
            if (noPpkb == null)
                shipProfile.initWithVessel(vesselCode, Integer.parseInt(bay), width != null ? Integer.parseInt(width) : null, height != null ? Integer.parseInt(height) : null);
            else{
                shipProfile.initWithPpkb(noPpkb, Integer.parseInt(bay), width != null ? Integer.parseInt(width) : null, height != null ? Integer.parseInt(height) : null, isLoad == null ? false : isLoad.equalsIgnoreCase("t"));
            }
            image = shipProfile.generate().getBinaryStream();
        } catch (SQLException ex) {
            Logger.getLogger(BayPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("image");
        response.setHeader("Content-Disposition", "inline; filename=\"bayPlan.png\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open strim.
            input = new BufferedInputStream(image, DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // tulis file
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // close strim
            close(output);
            close(input);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
