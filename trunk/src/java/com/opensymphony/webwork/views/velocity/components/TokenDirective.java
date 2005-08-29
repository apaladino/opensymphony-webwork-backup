package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Token;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: patrick
 * Date: Aug 5, 2005
 * Time: 8:55:26 AM
 */
public class TokenDirective extends AbstractDirective {
    public String getBeanName() {
        return "token";
    }

    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Token(stack, req, res);
    }
}
