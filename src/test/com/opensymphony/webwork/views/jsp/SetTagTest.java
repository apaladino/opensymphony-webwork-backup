/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;

import com.mockobjects.servlet.MockBodyContent;


/**
 * @author plightbo
 * @author tmjee
 * @version $Date$ $Id$
 */
public class SetTagTest extends AbstractUITagTest {

    Chewbacca chewie;
    SetTag tag;

    public void testSetTagWithBodyContent() throws Exception {
    	MockBodyContent bodyContent = new MockBodyContent() {
    		public String getString() {
    			return "testing testing 123";
    		}
    	};
    	tag.setName("foo");
    	tag.doStartTag();
    	tag.setBodyContent(bodyContent);
    	tag.doInitBody();
    	tag.doAfterBody();
    	tag.doEndTag();
    	
    	assertEquals(stack.getContext().get("foo"), "testing testing 123");
    }
    
    
    public void testSetTagWithEmptyBody() throws Exception {
    	MockBodyContent bodyContent = new MockBodyContent() {
    		public String getString() {
    			return "";
    		}
    	};
    	tag.setValue("%{'Hello World'}");
    	tag.setName("foo");
    	tag.doStartTag();
    	tag.setBodyContent(bodyContent);
    	tag.doInitBody();
    	tag.doAfterBody();
    	tag.doEndTag();
    	
    	assertEquals(stack.getContext().get("foo"), "Hello World");
    }
    
    public void testSetTagWithNullBody() throws Exception {
    	MockBodyContent bodyContent = new MockBodyContent() {
    		public String getString() {
    			return null;
    		}
    	};
    	tag.setValue("%{'Hello World'}");
    	tag.setName("foo");
    	tag.doStartTag();
    	tag.setBodyContent(bodyContent);
    	tag.doInitBody();
    	tag.doAfterBody();
    	tag.doEndTag();
    	
    	assertEquals(stack.getContext().get("foo"), "Hello World");
    }
    
    
    public void testSetTagWithBodyAndScope() throws Exception {
    	MockBodyContent bodyContent = new MockBodyContent() {
    		public String getString() {
    			return "testing testing 123";
    		}
    	};
    	tag.setScope("request");
    	tag.setName("foo");
    	tag.doStartTag();
    	tag.setBodyContent(bodyContent);
    	tag.doInitBody();
    	tag.doAfterBody();
    	tag.doEndTag();
    	
    	assertEquals(request.getAttribute("foo"), "testing testing 123");
    	assertEquals(stack.getContext().get("foo"), "testing testing 123");
    }
    
    
    public void testApplicationScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("application");
        tag.doStartTag();
        tag.doEndTag();

        assertEquals("chewie", servletContext.getAttribute("foo"));
    }

    public void testPageScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("page");
        tag.doStartTag();
        tag.doEndTag();

        assertEquals("chewie", pageContext.getAttribute("foo"));
    }

    public void testRequestScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("request");
        tag.doStartTag();
        tag.doEndTag();
        assertEquals("chewie", request.getAttribute("foo"));
    }

    public void testSessionScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("session");
        tag.doStartTag();
        tag.doEndTag();

        assertEquals("chewie", session.get("foo"));
    }

    public void testWebWorkScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.doStartTag();
        tag.doEndTag();
        assertEquals("chewie", context.get("foo"));
    }

    public void testWebWorkScope2() throws JspException {
        tag.setName("chewie");
        tag.doStartTag();
        tag.doEndTag();
        assertEquals(chewie, context.get("chewie"));
    }

    protected void setUp() throws Exception {
        super.setUp();

        tag = new SetTag();
        chewie = new Chewbacca("chewie", true);
        stack.push(chewie);
        tag.setPageContext(pageContext);
    }


    public class Chewbacca {
        String name;
        boolean furry;

        public Chewbacca(String name, boolean furry) {
            this.name = name;
            this.furry = furry;
        }

        public void setFurry(boolean furry) {
            this.furry = furry;
        }

        public boolean isFurry() {
            return furry;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
