/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class CheckboxTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
    * The name of the default template for the CheckboxTag
    */
    final public static String TEMPLATE = "checkbox.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String fieldValueAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setFieldValue(String aValue) {
        this.fieldValueAttr = aValue;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected Class getValueClassType() {
        return Boolean.class; // for checkboxes, everything needs to end up as a Boolean
    }

    protected void evaluateExtraParams(OgnlValueStack stack) {
        if (fieldValueAttr != null) {
            addParameter("fieldValue", findValue(fieldValueAttr, String.class));
        }
    }
}
