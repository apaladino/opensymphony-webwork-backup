/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Set;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @jsp.tag name="set" bodycontent="empty"
 * @see Set
 */
public class SetTag extends ComponentTagSupport {
    protected String name;
    protected String scope;
    protected String value;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Set(stack);
    }

    protected void populateParams() {
        super.populateParams();

        Set set = (Set) component;
        set.setName(name);
        set.setScope(scope);
        set.setValue(value);
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setValue(String value) {
        this.value = value;
    }
}
