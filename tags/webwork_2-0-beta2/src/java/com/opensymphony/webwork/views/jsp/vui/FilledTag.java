/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Voice UI for filled tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class FilledTag extends AbstractVUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(FilledTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String actionAttr;
    protected String actionaudioAttr;
    protected String modeAttr;
    protected String namelistAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String aName) {
        actionAttr = aName;
    }

    public String getAction() {
        return actionAttr;
    }

    public void setActionaudio(String aName) {
        actionaudioAttr = aName;
    }

    public String getActionaudio() {
        return actionaudioAttr;
    }

    public String getFooterTemplate() {
        return "filled-footer.jsp";
    }

    // Public --------------------------------------------------------
    public String getHeaderTemplate() {
        return "filled-header.jsp";
    }

    public void setMode(String aName) {
        modeAttr = aName;
    }

    public String getMode() {
        return modeAttr;
    }

    public void setNamelist(String aName) {
        namelistAttr = aName;
    }

    public String getNamelist() {
        return namelistAttr;
    }

    protected void initializeAttributes() {
        String url = ((actionAttr != null) ? constructURL((String) findValue(actionAttr)) : null);

        if (url != null) {
            addParam("action", url);
            url = ((actionaudioAttr != null) ? constructURL((String) findValue(actionaudioAttr)) : null);

            if (url != null) {
                addParam("actionaudio", url);
            }
        }

        getSetParameter(namelistAttr, "namelist");
        getSetParameter(modeAttr, "mode");
    }
}