/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


/**
 * A custom Result type for evaluating HTTP headers against the ValueStack.
 *
 * @author Jason Carreira
 */
public class HttpHeaderResult implements Result {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static final String DEFAULT_PARAM = "status";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected boolean parse = true;
    private Map headers;
    private int status = -1;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns a Map of all HTTP headers.
     *
     * @return a Map of all HTTP headers.
     */
    public Map getHeaders() {
        if (headers == null) {
            headers = new HashMap();
        }

        return headers;
    }

    /**
     * Sets whether or not the HTTP header values should be evaluated against the ValueStack (by default they are).
     *
     * @param parse <tt>true</tt> if HTTP header values should be evaluated agains the ValueStack, <tt>false</tt>
     *              otherwise.
     */
    public void setParse(boolean parse) {
        this.parse = parse;
    }

    /**
     * Sets the http servlet response status code that should be set on a response.
     *
     * @param status the Http status code
     * @see javax.servlet.http.HttpServletResponse#setStatus(int)
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Sets the optional HTTP response status code and also re-sets HTTP headers after they've
     * been optionally evaluated against the ValueStack.
     *
     * @param invocation an encapsulation of the action execution state.
     * @throws Exception if an error occurs when re-setting the headers.
     */
    public void execute(ActionInvocation invocation) throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();

        if (status != -1) {
            response.setStatus(status);
        }

        if (headers != null) {
            OgnlValueStack stack = ActionContext.getContext().getValueStack();

            for (Iterator iterator = headers.entrySet().iterator();
                    iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String value = (String) entry.getValue();
                String finalValue = parse ? TextParseUtil.translateVariables(value, stack) : value;
                response.addHeader((String) entry.getKey(), finalValue);
            }
        }
    }
}
