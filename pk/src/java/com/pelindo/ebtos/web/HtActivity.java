/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterDeviceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author senoanggoro
 */
@WebServlet(name="HtActivity", urlPatterns={"/HtActivity.json"}) 
public class HtActivity extends HttpServlet {
    private static final String DEFAULT_PARAM_NOT_VALID_MESSAGE = "";
    private static final String DEFAULT_NOT_AUTHORIZED_MESSAGE = "";
    private static final String DEFAULT_DEVICE_IS_IDLE_MESSAGE = "";

    @EJB private MasterDeviceFacadeRemote masterDeviceFacade;
    @EJB private ServiceVesselFacadeRemote serviceVesselFacade;

    private JSONObject main = new JSONObject();
    private String status;
    private String type;
    private JSONArray data;
    private String message;

    private FacesContext context;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/x-javascript;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String key = request.getParameter("key");

        context = FacesHelper.getFacesContextFromServlet(request, response);
        status = new String();
        type = new String();
        data = new JSONArray();
        message = new String();

        if (key == null){
            message = FacesHelper.getLocaleMessage("message.access.ht.param_not_valid", context);
            if (message.equals(""))
                message = DEFAULT_PARAM_NOT_VALID_MESSAGE;
            buildError("key", message);
        } else {
            Object[] id = masterDeviceFacade.idKeyValid(key);
            if (id == null){
                message = FacesHelper.getLocaleMessage("message.access.ht.not_authorized", context);
                if (message.equals(""))
                    message = DEFAULT_NOT_AUTHORIZED_MESSAGE;
                buildError("key", message);
            } else {
                if (id[1] == null) {
                    message = FacesHelper.getLocaleMessage("message.access.ht.device_is_idle", context);
                    if (message.equals(""))
                        message = DEFAULT_DEVICE_IS_IDLE_MESSAGE;
                    buildError("key", message);
                } else {
                    List<Object[]> conts = serviceVesselFacade.findTruckDestinationByAppKey(key);
                    buildContList(conts);
                    message = id[1].toString();
                }
            }
        }
        
        try {
            main.put("message", message);
            main.put("data", data);
            main.put("type", type);
            main.put("status", status);
            StringWriter stringWriter = new StringWriter();
            try {
                main.writeJSONString(stringWriter);
            } catch (IOException ex) {
                Logger.getLogger(HtActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.print(request.getParameter("callback") + "({\"value\":");
            out.print(stringWriter.toString());
            out.print("})");
        } finally { 
            out.close();
        }
    }

    private void buildContList(List<Object[]> list){
        JSONObject temp;
        status = "ok";
        for (Object[] obj: list){
            temp = new JSONObject();
            temp.put("cont", obj[0]);
            temp.put("destination1", obj[1]);
            temp.put("destination2", obj[2]);
            data.add(temp);
        }
        type = "";
        message = "";
    }

    private void buildError(String _type, String _message){
        status = "error";
        type = _type;
        message = _message;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
