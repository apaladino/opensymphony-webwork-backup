package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 6:46:38 AM
 */
public class Submit extends UIBean {
    final public static String TEMPLATE = "submit";

    protected String action;
    protected String method;
    protected String align;
    protected String resultDivId;
    protected String onLoadJS;
    protected String notifyTopics;
    protected String listenTopics;
    protected String preInvokeJS;

    public Submit(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        if (align == null) {
            align = "right";
        }

        if (value == null) {
            value = "Submit";
        }

        super.evaluateParams();

        if (action != null || method != null) {
            String name;

            if (action != null) {
                name = "action:" + findString(action);

                if (method != null) {
                    name += findString(method);
                }
            } else {
                name = "method:" + findString(method);
            }
            
            addParameter("name", name);
        }

        addParameter("align", findString(align));

        if (null != resultDivId) {
            addParameter("resultDivId", findString(resultDivId));
        }

        if (null != onLoadJS) {
            addParameter("onLoadJS", findString(onLoadJS));
        }

        if (null != notifyTopics) {
            addParameter("notifyTopics", findString(notifyTopics));
        }

        if (null != listenTopics) {
            addParameter("listenTopics", findString(listenTopics));
        }

        if (preInvokeJS != null) {
            addParameter("preInvokeJS", findString(preInvokeJS));
        }
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setResultDivId(String resultDivId) {
        this.resultDivId = resultDivId;
    }

    public void setOnLoadJS(String onLoadJS) {
        this.onLoadJS = onLoadJS;
    }

    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }

    public void setPreInvokeJS(String preInvokeJS) {
        this.preInvokeJS = preInvokeJS;
    }
}
