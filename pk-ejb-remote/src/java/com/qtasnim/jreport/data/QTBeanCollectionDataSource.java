package com.qtasnim.jreport.data;

import net.sf.jasperreports.engine.data.*;
import java.util.*;

public class QTBeanCollectionDataSource extends JRBeanCollectionDataSource
{
    public QTBeanCollectionDataSource(final Collection collection) {
        super(collection);
    }
    
    public QTBeanCollectionDataSource reset() {
        this.moveFirst();
        return this;
    }
}
