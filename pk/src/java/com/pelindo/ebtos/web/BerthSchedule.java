/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.remote.BerthScheduleRemote;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
@WebServlet(name="BerthSchedule", urlPatterns={"/berthSchedule.png"})
public class BerthSchedule extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @EJB private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB private BerthScheduleRemote berthScheduleRemote;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String dockCode = request.getParameter("dockCode");
        String width = request.getParameter("width");
        String height = request.getParameter("height");

        if (dockCode == null || height ==null|| width==null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar400.
            return;
        }

        berthScheduleRemote.init(dockCode, Integer.parseInt(width), Integer.parseInt(height));
        
        InputStream image = null;
        try {
            image = berthScheduleRemote.generate().getBinaryStream();
        } catch (SQLException ex) {
            Logger.getLogger(BerthSchedule.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("image");
        response.setHeader("Content-Disposition", "inline; filename=\"BerthSchedule.png\"");

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

    public List<String> getConflictBerthings(String dockCode, Date eta, Date etd, int frMeter, int toMeter, String noPpkb){
        return planningVesselFacadeRemote.findConflictBirthPlan(dockCode, eta, etd, frMeter, toMeter, noPpkb);
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
