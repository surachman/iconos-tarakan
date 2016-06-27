/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.qtasnim.persistence.EntityManagerHelper;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.remote.RecapJurnalFacadeRemote;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Dyware-Dev01
 */
@WebServlet(name = "jurnalKasMasuk", urlPatterns = {"/jurnalKasMasuk.pdf"})
public class JurnalKasMasuk extends HttpServlet {
    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    @EJB
    RecapJurnalFacadeRemote recapJurnalFacadeRemote;
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String sumber = (String) request.getParameter("sumber");

        if (sumber == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        InputStream report = null;
        InputStream subreport = null;
        Connection connection = null;

        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();

            map.put(JRParameter.REPORT_CONNECTION, connection);
            BigDecimal totalTagihan = recapJurnalFacadeRemote.findTotalTagihanBySumber(sumber);
            if (totalTagihan != null) {
                map.put("sumber", sumber);
                map.put("terbilang", new IndonesianNumberConverter().convertToWord(totalTagihan.toBigInteger().toString()));
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JKM.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/JKM_Invoices_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"ebtos_" +sumber+ ".pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                throw new Exception("Total tagihan is Null");
            }

            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            jp = JasperFillManager.fillReport(report, map);
            byte[] file = JasperExportManager.exportReportToPdf(jp);

            servletOutputStream.write(file);
            servletOutputStream.flush();
        } catch (JRException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            close(report);
            close(subreport);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(JurnalKasMasuk.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(JurnalKasMasuk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
