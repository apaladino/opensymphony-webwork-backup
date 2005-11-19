package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Panel;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @jsp.tag name="panel" bodycontent="JSP"
 * @see Panel
 */
public class PanelTag extends DivTag {
    protected String tabName;
    protected String subscribeTopicName;
    protected String remote;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Panel(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Panel panel = ((Panel) component);
        panel.setTabName(tabName);
        panel.setSubscribeTopicName(subscribeTopicName);
        panel.setRemote(remote);
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setSubscribeTopicName(String subscribeTopicName) {
        this.subscribeTopicName = subscribeTopicName;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setRemote(String remote) {
        this.remote = remote;
    }
}
