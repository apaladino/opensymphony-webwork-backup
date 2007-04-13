/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.easymock.MockControl;
import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class CookieInterceptorTest extends WebWorkTestCase {
	

	public void testIntercepDefault() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie[] {
				new Cookie("cookie1", "cookie1value"),
				new Cookie("cookie2", "cookie2value"),
				new Cookie("cookie3", "cookie3value")
		});
		ServletActionContext.setRequest(request);
		
		MockActionWithCookieAware action = new MockActionWithCookieAware();
		
		ActionContext.getContext().getValueStack().push(action);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) actionInvocationControl.getMock();
		actionInvocationControl.expectAndDefaultReturn(
				invocation.getAction(), action);
		actionInvocationControl.expectAndDefaultReturn(
				invocation.invoke(), Action.SUCCESS);
		
		actionInvocationControl.replay();
		
		// by default the interceptor doesn't accept any cookies
		CookieInterceptor interceptor = new CookieInterceptor();
		interceptor.intercept(invocation);
		
		assertTrue(action.getCookiesMap().isEmpty());
		assertNull(action.getCookie1(), null);
		assertNull(action.getCookie2(), null);
		assertNull(action.getCookie3(), null);
		assertNull(ActionContext.getContext().getValueStack().findValue("cookie1"));
		assertNull(ActionContext.getContext().getValueStack().findValue("cookie2"));
		assertNull(ActionContext.getContext().getValueStack().findValue("cookie3"));
	}
	
	public void testInterceptAll1() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie[] {
				new Cookie("cookie1", "cookie1value"),
				new Cookie("cookie2", "cookie2value"),
				new Cookie("cookie3", "cookie3value")
		});
		ServletActionContext.setRequest(request);
		
		MockActionWithCookieAware action = new MockActionWithCookieAware();
		
		ActionContext.getContext().getValueStack().push(action);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) actionInvocationControl.getMock();
		actionInvocationControl.expectAndDefaultReturn(
				invocation.getAction(), action);
		actionInvocationControl.expectAndDefaultReturn(
				invocation.invoke(), Action.SUCCESS);
		
		actionInvocationControl.replay();
		
		CookieInterceptor interceptor = new CookieInterceptor();
		interceptor.setCookiesName("*");
		interceptor.setCookiesValue("*");
		interceptor.intercept(invocation);
		
		assertFalse(action.getCookiesMap().isEmpty());
		assertEquals(action.getCookiesMap().size(), 3);
		assertEquals(action.getCookiesMap().get("cookie1"), "cookie1value");
		assertEquals(action.getCookiesMap().get("cookie2"), "cookie2value");
		assertEquals(action.getCookiesMap().get("cookie3"), "cookie3value");
		assertEquals(action.getCookie1(), "cookie1value");
		assertEquals(action.getCookie2(), "cookie2value");
		assertEquals(action.getCookie3(), "cookie3value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie1"), "cookie1value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie2"), "cookie2value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie3"), "cookie3value");
	}
	
	
	public void testInterceptAll2() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie[] {
				new Cookie("cookie1", "cookie1value"),
				new Cookie("cookie2", "cookie2value"),
				new Cookie("cookie3", "cookie3value")
		});
		ServletActionContext.setRequest(request);
		
		MockActionWithCookieAware action = new MockActionWithCookieAware();
		
		ActionContext.getContext().getValueStack().push(action);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) actionInvocationControl.getMock();
		actionInvocationControl.expectAndDefaultReturn(
				invocation.getAction(), action);
		actionInvocationControl.expectAndDefaultReturn(
				invocation.invoke(), Action.SUCCESS);
		
		actionInvocationControl.replay();
		
		CookieInterceptor interceptor = new CookieInterceptor();
		interceptor.setCookiesName("cookie1, cookie2, cookie3");
		interceptor.setCookiesValue("cookie1value, cookie2value, cookie3value");
		interceptor.intercept(invocation);
		
		assertFalse(action.getCookiesMap().isEmpty());
		assertEquals(action.getCookiesMap().size(), 3);
		assertEquals(action.getCookiesMap().get("cookie1"), "cookie1value");
		assertEquals(action.getCookiesMap().get("cookie2"), "cookie2value");
		assertEquals(action.getCookiesMap().get("cookie3"), "cookie3value");
		assertEquals(action.getCookie1(), "cookie1value");
		assertEquals(action.getCookie2(), "cookie2value");
		assertEquals(action.getCookie3(), "cookie3value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie1"), "cookie1value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie2"), "cookie2value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie3"), "cookie3value");
	}
	
	public void testInterceptSelectedCookiesNameOnly1() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie[] {
				new Cookie("cookie1", "cookie1value"),
				new Cookie("cookie2", "cookie2value"),
				new Cookie("cookie3", "cookie3value")
		});
		ServletActionContext.setRequest(request);
		
		MockActionWithCookieAware action = new MockActionWithCookieAware();
		
		ActionContext.getContext().getValueStack().push(action);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) actionInvocationControl.getMock();
		actionInvocationControl.expectAndDefaultReturn(
				invocation.getAction(), action);
		actionInvocationControl.expectAndDefaultReturn(
				invocation.invoke(), Action.SUCCESS);
		
		actionInvocationControl.replay();
		
		CookieInterceptor interceptor = new CookieInterceptor();
		interceptor.setCookiesName("cookie1, cookie3");
		interceptor.setCookiesValue("cookie1value, cookie2value, cookie3value");
		interceptor.intercept(invocation);
		
		assertFalse(action.getCookiesMap().isEmpty());
		assertEquals(action.getCookiesMap().size(), 2);
		assertEquals(action.getCookiesMap().get("cookie1"), "cookie1value");
		assertEquals(action.getCookiesMap().get("cookie2"), null);
		assertEquals(action.getCookiesMap().get("cookie3"), "cookie3value");
		assertEquals(action.getCookie1(), "cookie1value");
		assertEquals(action.getCookie2(), null);
		assertEquals(action.getCookie3(), "cookie3value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie1"), "cookie1value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie2"), null);
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie3"), "cookie3value");
	}
	
	public void testInterceptSelectedCookiesNameOnly2() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie[] {
				new Cookie("cookie1", "cookie1value"),
				new Cookie("cookie2", "cookie2value"),
				new Cookie("cookie3", "cookie3value")
		});
		ServletActionContext.setRequest(request);
		
		MockActionWithCookieAware action = new MockActionWithCookieAware();
		
		ActionContext.getContext().getValueStack().push(action);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) actionInvocationControl.getMock();
		actionInvocationControl.expectAndDefaultReturn(
				invocation.getAction(), action);
		actionInvocationControl.expectAndDefaultReturn(
				invocation.invoke(), Action.SUCCESS);
		
		actionInvocationControl.replay();
		
		CookieInterceptor interceptor = new CookieInterceptor();
		interceptor.setCookiesName("cookie1, cookie3");
		interceptor.setCookiesValue("*");
		interceptor.intercept(invocation);
		
		assertFalse(action.getCookiesMap().isEmpty());
		assertEquals(action.getCookiesMap().size(), 2);
		assertEquals(action.getCookiesMap().get("cookie1"), "cookie1value");
		assertEquals(action.getCookiesMap().get("cookie2"), null);
		assertEquals(action.getCookiesMap().get("cookie3"), "cookie3value");
		assertEquals(action.getCookie1(), "cookie1value");
		assertEquals(action.getCookie2(), null);
		assertEquals(action.getCookie3(), "cookie3value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie1"), "cookie1value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie2"), null);
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie3"), "cookie3value");
	}
	
	public void testInterceptSelectedCookiesNameOnly3() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie[] {
				new Cookie("cookie1", "cookie1value"),
				new Cookie("cookie2", "cookie2value"),
				new Cookie("cookie3", "cookie3value")
		});
		ServletActionContext.setRequest(request);
		
		MockActionWithCookieAware action = new MockActionWithCookieAware();
		
		ActionContext.getContext().getValueStack().push(action);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) actionInvocationControl.getMock();
		actionInvocationControl.expectAndDefaultReturn(
				invocation.getAction(), action);
		actionInvocationControl.expectAndDefaultReturn(
				invocation.invoke(), Action.SUCCESS);
		
		actionInvocationControl.replay();
		
		CookieInterceptor interceptor = new CookieInterceptor();
		interceptor.setCookiesName("cookie1, cookie3");
		interceptor.setCookiesValue("");
		interceptor.intercept(invocation);
		
		assertFalse(action.getCookiesMap().isEmpty());
		assertEquals(action.getCookiesMap().size(), 2);
		assertEquals(action.getCookiesMap().get("cookie1"), "cookie1value");
		assertEquals(action.getCookiesMap().get("cookie2"), null);
		assertEquals(action.getCookiesMap().get("cookie3"), "cookie3value");
		assertEquals(action.getCookie1(), "cookie1value");
		assertEquals(action.getCookie2(), null);
		assertEquals(action.getCookie3(), "cookie3value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie1"), "cookie1value");
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie2"), null);
		assertEquals(ActionContext.getContext().getValueStack().findValue("cookie3"), "cookie3value");
	}
	
	
	public void testInterceptSelectedCookiesNameAndValue() throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest();
			request.setCookies(new Cookie[] {
					new Cookie("cookie1", "cookie1value"),
					new Cookie("cookie2", "cookie2value"),
					new Cookie("cookie3", "cookie3value")
			});
			ServletActionContext.setRequest(request);
			
			MockActionWithCookieAware action = new MockActionWithCookieAware();
			
			ActionContext.getContext().getValueStack().push(action);
			
			MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
			ActionInvocation invocation = (ActionInvocation) actionInvocationControl.getMock();
			actionInvocationControl.expectAndDefaultReturn(
					invocation.getAction(), action);
			actionInvocationControl.expectAndDefaultReturn(
					invocation.invoke(), Action.SUCCESS);
			
			actionInvocationControl.replay();
			
			CookieInterceptor interceptor = new CookieInterceptor();
			interceptor.setCookiesName("cookie1, cookie3");
			interceptor.setCookiesValue("cookie1value");
			interceptor.intercept(invocation);
			
			assertFalse(action.getCookiesMap().isEmpty());
			assertEquals(action.getCookiesMap().size(), 1);
			assertEquals(action.getCookiesMap().get("cookie1"), "cookie1value");
			assertEquals(action.getCookiesMap().get("cookie2"), null);
			assertEquals(action.getCookiesMap().get("cookie3"), null);
			assertEquals(action.getCookie1(), "cookie1value");
			assertEquals(action.getCookie2(), null);
			assertEquals(action.getCookie3(), null);
			assertEquals(ActionContext.getContext().getValueStack().findValue("cookie1"), "cookie1value");
			assertEquals(ActionContext.getContext().getValueStack().findValue("cookie2"), null);
			assertEquals(ActionContext.getContext().getValueStack().findValue("cookie3"), null);
	}
	
	
	/**
	 * @author tmjee
	 * @version $Date$ $Id$
	 */
	public static class MockActionWithCookieAware extends ActionSupport implements CookiesAware {
		
		private static final long serialVersionUID = -6202290616812813386L;
		
		private Map cookies = Collections.EMPTY_MAP;
		private String cookie1;
		private String cookie2;
		private String cookie3;
		
		public void setCookiesMap(Map cookies) {
			this.cookies = cookies;
		}
		public Map getCookiesMap() { 
			return this.cookies;
		}
		
		public String getCookie1() { return cookie1; }
		public void setCookie1(String cookie1) { this.cookie1 = cookie1; }
		
		public String getCookie2() { return cookie2; }
		public void setCookie2(String cookie2) { this.cookie2 = cookie2; }
		
		public String getCookie3() { return cookie3; }
		public void setCookie3(String cookie3) { this.cookie3 = cookie3; }
	}
	
}
