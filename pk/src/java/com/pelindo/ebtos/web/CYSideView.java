/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.pelindo.ebtos.ejb.remote.CYSideViewRemote;
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
@WebServlet(name="CYSideView", urlPatterns={"/cySideView.png"})
public class CYSideView extends HttpServlet {
    @EJB private CYSideViewRemote cySideView;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String block = request.getParameter("block");
        String slot = request.getParameter("slot");
        String eo = request.getParameter("eo");
        String width = request.getParameter("w");
        String height = request.getParameter("h");
        String ppkb = request.getParameter("ppkb");

        if (block == null || slot == null || width == null || height == null || eo == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }


        InputStream image = null;
        try {
            if (eo.equals("t"))
                cySideView.init(block, Integer.parseInt(slot), Integer.parseInt(width), Integer.parseInt(height), true, ppkb);
            else if (eo.equals("f"))
                cySideView.init(block, Integer.parseInt(slot), Integer.parseInt(width), Integer.parseInt(height), false, ppkb);
            image = cySideView.generate().getBinaryStream();
        } catch (SQLException ex) {
            Logger.getLogger(CYSideView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("image");
        response.setHeader("Content-Disposition", "inline; filename=\"cySideView.png\"");

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
