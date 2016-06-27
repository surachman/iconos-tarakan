package com.qtasnim.persistence;

import java.util.*;

public interface EntityAuditable
{
    Date getCreatedDate();
    
    void setCreatedDate(Date p0);
    
    Date getModifiedDate();
    
    void setModifiedDate(Date p0);
    
    String getCreatedBy();
    
    void setCreatedBy(String p0);
    
    String getModifiedBy();
    
    void setModifiedBy(String p0);
}
