package com.qtasnim.jsf;

import java.util.Map;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FacesHelper
{
  public static <T> T translateElExpression(String el, FacesContext context, Class<T> objectClass)
  {
    Application app = context.getApplication();
    ValueExpression expression = app.getExpressionFactory().createValueExpression(context.getELContext(), el, objectClass);
    return objectClass.cast(expression.getValue(context.getELContext()));
  }
  
  public static String getLocaleMessage(String key, FacesContext context)
  {
    return (String)translateElExpression(String.format("#{msg['%s']}", new Object[] { key }), context, String.class);
  }
  
  public static String getParam(String param, FacesContext context)
  {
    Map<String, String> params = context.getExternalContext().getRequestParameterMap();
    return (String)params.get(param);
  }
  
  public static void putParam(String key, String value, FacesContext context)
  {
    context.getExternalContext().getRequestMap().put(key, value);
  }
  
  public static FacesContext getFacesContextFromServlet(HttpServletRequest request, HttpServletResponse response)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext == null)
    {
      FacesContextFactory contextFactory = (FacesContextFactory)FactoryFinder.getFactory("javax.faces.context.FacesContextFactory");
      LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
      Lifecycle lifecycle = lifecycleFactory.getLifecycle("DEFAULT");
      facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);
      InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);
      UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
      facesContext.setViewRoot(view);
    }
    return facesContext;
  }
  
  private static abstract class InnerFacesContext
    extends FacesContext
  {
    protected static void setFacesContextAsCurrentInstance(FacesContext facesContext)
    {
      FacesContext.setCurrentInstance(facesContext);
    }
  }
  
  public static void addFacesMessage(FacesContext context, FacesMessage.Severity severity, String title)
  {
    FacesMessage facesMessage = new FacesMessage(getLocaleMessage(title, context), "");
    facesMessage.setSeverity(severity);
    context.addMessage(null, facesMessage);
  }
  
  public static void addFacesMessage(FacesContext context, FacesMessage.Severity severity, String title, String message)
  {
    FacesMessage facesMessage = new FacesMessage(title, getLocaleMessage(message, context));
    facesMessage.setSeverity(severity);
    context.addMessage(null, facesMessage);
  }
  
  public static void addFacesTextMessage(FacesContext context, FacesMessage.Severity severity, String title, String text, String clientId)
  {
    FacesMessage facesMessage = new FacesMessage(title, text);
    facesMessage.setSeverity(severity);
    context.addMessage(clientId, facesMessage);
  }
  
  public static void addErrorFacesMessage(FacesContext context, String title, String message)
  {
    addFacesMessage(context, FacesMessage.SEVERITY_ERROR, title, message);
  }
  
  public static void addErrorFacesMessage(FacesContext context, String title)
  {
    addFacesMessage(context, FacesMessage.SEVERITY_ERROR, title);
  }
}
