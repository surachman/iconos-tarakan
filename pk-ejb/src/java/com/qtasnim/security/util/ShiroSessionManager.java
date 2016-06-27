package com.qtasnim.security.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroSessionManager
{
  private static Session session;
  
  public static void newInstance()
  {
    Factory<SecurityManager> factory = new IniSecurityManagerFactory();
    SecurityManager securityManager = (SecurityManager)factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);
  }
  
  public static Session getCurrentSession(boolean createIfNothing)
  {
    Subject currentUser = SecurityUtils.getSubject();
    session = currentUser.getSession(createIfNothing);
    return session;
  }
  
  public static Session getCurrentSession()
  {
    Subject currentUser = SecurityUtils.getSubject();
    session = currentUser.getSession();
    return session;
  }
}
