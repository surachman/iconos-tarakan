package com.qtasnim.jsf.ui;


import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.text.MessageIterator;
import com.pelindo.ebtos.bean.application.Navigation;
import java.io.IOException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author senoanggoro
 */
@FacesComponent(value = "dynamicBreadcrumb")
public class DynamicBreadcrumb extends UIComponentBase {
    private String message;

    @Override
    public String getFamily() {
        return "custom";
    }


    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        Navigation nav = Navigation.getCurrentInstance();
        message = nav.getCurrentMessage();
        if (message != null) {
            ResponseWriter responseWriter = context.getResponseWriter();
            responseWriter.startElement("div", null);
            responseWriter.writeAttribute("class","ui-breadcrumb",null);
            responseWriter.startElement("div", null);
            responseWriter.writeAttribute("style","overflow:hidden; position:relative;",null);
            responseWriter.startElement("div", null);
            responseWriter.startElement("ul", null);
            responseWriter.startElement("li", null);
            responseWriter.writeAttribute("class","first",null);
            responseWriter.startElement("a", null);
            responseWriter.writeAttribute("class","ui-icon ui-icon-home",null);
            ExternalContext econtext = FacesContext.getCurrentInstance().getExternalContext();
            responseWriter.writeAttribute("href",econtext.getRequestScheme() + "://" + econtext.getRequestServerName() + ":" + econtext.getRequestServerPort() + econtext.getRequestContextPath() + Navigation.FACES_CONTEXT_NAME + Navigation.DEFAULT_HOME_URL,null);
            responseWriter.endElement("a");
            responseWriter.startElement("span", null);
            responseWriter.writeAttribute("class","ui-breadcrumb-chevron ui-icon ui-icon-triangle-1-e",null);
            responseWriter.endElement("span");
            responseWriter.endElement("li");
            MessageIterator iterator = new MessageIterator(message, "menu");
            if (iterator.hasNext()) {
                iterator.next();
                while (iterator.hasNext()){
                    responseWriter.startElement("li", null);
                    responseWriter.startElement("a", null);
                    responseWriter.write(FacesHelper.getLocaleMessage(iterator.getCurrentMessage(), context));
                    responseWriter.endElement("a");
                    responseWriter.startElement("span", null);
                    responseWriter.writeAttribute("class","ui-breadcrumb-chevron ui-icon ui-icon-triangle-1-e",null);
                    responseWriter.endElement("span");
                    responseWriter.endElement("li");
                    iterator.next();
                }
                responseWriter.startElement("li", null);
                responseWriter.writeAttribute("class", "last", null);
                responseWriter.writeAttribute("style", "background-image: none; background-attachment: initial; background-origin: initial; background-clip: initial; background-color: initial; background-position: initial initial; background-repeat: initial initial; ", null);
                responseWriter.startElement("a", null);
                responseWriter.write(FacesHelper.getLocaleMessage(iterator.getCurrentMessage(), context));
                responseWriter.endElement("a");
                responseWriter.startElement("span", null);
                responseWriter.endElement("span");
                responseWriter.endElement("li");
            }
            responseWriter.endElement("ul");
            responseWriter.endElement("div");
            responseWriter.endElement("div");
            responseWriter.endElement("div");
        }
    }
 }