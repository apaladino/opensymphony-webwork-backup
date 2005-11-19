/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Token;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @jsp.tag name="token" bodycontent="JSP"
 * @see Token
 */
public class TokenTag extends AbstractUITag {
    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Token(stack, req, res);
    }
}
