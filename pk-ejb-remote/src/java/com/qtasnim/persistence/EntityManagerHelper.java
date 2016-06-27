package com.qtasnim.persistence;

import java.util.logging.*;
import javax.sql.*;
import java.sql.*;
import javax.naming.*;

public class EntityManagerHelper
{
    private static final String DEFAULT_DATA_SOURCE_CONTEXT = "ebtos_ds";
    
    public static Connection getConnection(final String dataSourceContext) {
        Connection conn = null;
        try {
            final Context initialContext = new InitialContext();
            if (initialContext == null) {
                Logger.getLogger(EntityManagerHelper.class.getName()).log(Level.FINE, "JNDI problem. Cannot get InitialContext");
            }
            final DataSource ds = (DataSource)initialContext.lookup(dataSourceContext);
            if (ds != null) {
                conn = ds.getConnection();
                Logger.getLogger(EntityManagerHelper.class.getName()).log(Level.FINE, "Connected to db using DataSource");
            }
            else {
                Logger.getLogger(EntityManagerHelper.class.getName()).log(Level.FINE, "Failed to lookup datasource");
            }
        }
        catch (NamingException ex) {
            Logger.getLogger(EntityManagerHelper.class.getName()).log(Level.SEVERE, "Cannot get connection: ", (Throwable)ex);
        }
        catch (SQLException ex2) {
            Logger.getLogger(EntityManagerHelper.class.getName()).log(Level.SEVERE, "Cannot get connection: ", (Throwable)ex2);
        }
        return conn;
    }
    
    public static Connection getDefaultConnection() {
        return getConnection("ebtos_ds");
    }
}
