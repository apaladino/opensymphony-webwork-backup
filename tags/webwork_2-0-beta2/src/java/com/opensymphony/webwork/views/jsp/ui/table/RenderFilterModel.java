/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui.table;

import javax.swing.table.TableModel;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class RenderFilterModel extends AbstractFilterModel {
    //~ Instance fields ////////////////////////////////////////////////////////

    private boolean rendered;

    //~ Constructors ///////////////////////////////////////////////////////////

    public RenderFilterModel(TableModel tm) {
        super(tm);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public boolean isRendered() {
        return rendered;
    }
}