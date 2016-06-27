package com.qtasnim.persistence.criteria;

import javax.persistence.criteria.*;
import java.util.*;

public class CriteriaHelper
{
    public static Path translateRefference(final Root root, final String field) {
        final StringTokenizer st = new StringTokenizer(field, ".");
        Path domain = root.get(st.nextToken());
        while (st.hasMoreTokens()) {
            domain = domain.get(st.nextToken());
        }
        return domain;
    }
}
