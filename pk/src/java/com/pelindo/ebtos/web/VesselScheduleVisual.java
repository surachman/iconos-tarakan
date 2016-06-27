/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.pelindo.ebtos.ejb.remote.VesselScheduleVisualRemote;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
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
@WebServlet(name="VesselScheduleVisual", urlPatterns={"/vesselScheduleVisual.png"})
public class VesselScheduleVisual extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @EJB private VesselScheduleVisualRemote vesselMonitoringVisualRemote;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String dockCode = request.getParameter("dockCode");
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String fromEpoch = request.getParameter("from");
        String toEpoch = request.getParameter("to");

        if (dockCode == null ||  width==null || fromEpoch == null || toEpoch == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar400.
            return;
        }

        Date from = new Date(new Long(fromEpoch));
        Date to = new Date(new Long(toEpoch));

        vesselMonitoringVisualRemote.init(dockCode, Integer.parseInt(width), height == null ? null : Integer.parseInt(height), from, to);
        
        InputStream image = null;
        try {
            image = vesselMonitoringVisualRemote.generate().getBinaryStream();
        } catch (SQLException ex) {
            Logger.getLogger(VesselScheduleVisual.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("image");
        response.setHeader("Content-Disposition", "inline; filename=\"vesselScheduleVisual.png\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(image, DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
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
    }// </editor-fold>

}
