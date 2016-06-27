/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;


import com.pelindo.ebtos.ejb.remote.DataStorageManagerRemote;
import com.pelindo.ebtos.model.db.ChangeVesselService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "dataCrossTest", urlPatterns = {"/dataCrossTest.html"})
public class DataCrossTest extends HttpServlet {
    @EJB
    private DataStorageManagerRemote dataStorageManagerRemote;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    public DataCrossTest() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("key");
        PrintWriter out = response.getWriter();

        if (key != null) {
            HttpSession session = request.getSession(true);
            List<ChangeVesselService> object = (List<ChangeVesselService>) dataStorageManagerRemote.getData(session.getId(), key);

            if (object != null) {
                out.println("size :" + object.size());
            } else {
                out.println("key is valid but fail to get the Objects");
            }

            System.out.println(session.getId());
        } else {
            out.println("key is not valid");
        }
    }

}
