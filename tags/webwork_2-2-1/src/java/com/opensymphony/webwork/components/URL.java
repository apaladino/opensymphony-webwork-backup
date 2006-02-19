package com.opensymphony.webwork.components;

import com.opensymphony.webwork.portlet.context.PortletActionContext;
import com.opensymphony.webwork.portlet.util.PortletUrlHelper;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * <p>This tag is used to create a URL.</p>
 *
 * <p>You can use the "param" tag inside the body to provide
 * additional request parameters.</p>
 * 
 * <b>NOTE:</b>
 * <p>When includeParams is 'all' or 'get', the parameter defined in param tag will take
 * precedence and will not be overriden if they exists in the parameter submitted. For 
 * example, in Example 3 below, if there is a id parameter in the url where the page this
 * tag is included like http://<host>:<port>/<context>/editUser.action?id=3333&name=John
 * the generated url will be http://<host>:<port>/context>/editUser.action?id=22&name=John
 * cause the parameter defined in the param tag will take precedence.</p>
 * 
 * <!-- END SNIPPET: javadoc -->
 *
 *
 * <!-- START SNIPPET: params -->
 * 
 * <ul>
 *      <li>action (String) - (value or action choose either one, if both exist value takes precedence) action's name (alias) <li>
 *      <li>value (String) - (value or action choose either one, if both exist value takes precedence) the url itself</li>
 *      <li>scheme (String) - http scheme (http, https) default to the scheme this request is in</li>
 *      <li>namespace - action's namespace</li>
 *      <li>method (String) - action's method, default to execute() </li>
 *      <li>encode (Boolean) - url encode the generated url. Default is true</li>
 *      <li>includeParams (String) - The includeParams attribute may have the value 'none', 'get' or 'all'. Default is 'get'.
 *                                   none - include no parameters in the URL
 *                                   get  - include only GET parameters in the URL (default)
 *                                   all  - include both GET and POST parameters in the URL
 *      </li>
 *      <li>includeContext (Boolean) - determine wheather to include the web app context path. Default is true.</li>
 * </ul>
 * 
 * <!-- END SNIPPET: params -->
 *
 * <p/> <b>Examples</b>
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;-- Example 1 --&gt;
 * &lt;ww:url value="editGadget.action"&gt;
 *     &lt;ww:param name="id" value="%{selected}" /&gt;
 * &lt;/ww:url&gt;
 *
 * &lt;-- Example 2 --&gt;
 * &lt;ww:url action="editGadget"&gt;
 *     &lt;ww:param name="id" value="%{selected}" /&gt;
 * &lt;/ww:url&gt;
 * 
 * &lt;-- Example 3--&gt;
 * &lt;ww:url includeParams="get"  &gt;
 *     &lt:param name="id" value="%{'22'}" /&gt;
 * &lt;/ww:url&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @author Patrick Lightbody
 * @author Ian Roughley
 * @author Rene Gielen
 * @author Rainer Hermanns
 * @version $Revision$
 * @since 2.2
 *
 * @see Param
 *
 * @ww.tag name="url" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.URLTag"
 * description="This tag is used to create a URL"
 */
public class URL extends Component {
    private static final Log LOG = LogFactory.getLog(URL.class);

    /**
     * The includeParams attribute may have the value 'none', 'get' or 'all'.
     * It is used when the url tag is used without a value attribute.
     * Its value is looked up on the ValueStack
     * If no includeParams is specified then 'get' is used.
     * none - include no parameters in the URL
     * get  - include only GET parameters in the URL (default)
     * all  - include both GET and POST parameters in the URL
     */
    public static final String NONE = "none";
    public static final String GET = "get";
    public static final String ALL = "all";

    private HttpServletRequest req;
    private HttpServletResponse res;

    protected String includeParams;
    protected String scheme;
    protected String value;
    protected String action;
    protected String namespace;
    protected String method;
    protected boolean encode = true;
    protected boolean includeContext = true;
    protected String portletMode;
    protected String windowState;
    protected String portletUrlType;

    public URL(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack);
        this.req = req;
        this.res = res;
    }

    public boolean start(Writer writer) {
        boolean result = super.start(writer);
        
        if (value != null) {
            value = findString(value);
        }

        // no explicit url set so attach params from current url, do
        // this at start so body params can override any of these they wish.
        try {
            String includeParams = null;

            if (this.includeParams != null) {
                includeParams = findString(this.includeParams);
            }

            if ((includeParams == null && value == null) || GET.equalsIgnoreCase(includeParams)) {
                // Parse the query string to make sure that the parameters come from the query, and not some posted data
                String query = req.getQueryString();

                if (query != null) {
                    // Remove possible #foobar suffix
                    int idx = query.lastIndexOf('#');

                    if (idx != -1) {
                        query = query.substring(0, idx - 1);
                    }

                    mergeRequestParameters(parameters, HttpUtils.parseQueryString(query));  
                }
            } else if (ALL.equalsIgnoreCase(includeParams)) {
                mergeRequestParameters(parameters, req.getParameterMap());
            } else if (value == null && !NONE.equalsIgnoreCase(includeParams)) {
                LOG.warn("Unknown value for includeParams parameter to URL tag: " + includeParams);
            }
        } catch (Exception e) {
            LOG.warn("Unable to put request parameters (" + req.getQueryString() + ") into parameter map.", e);
        }


        return result;
    }

    public boolean end(Writer writer, String body) {
        String scheme = req.getScheme();

        if (this.scheme != null) {
            scheme = this.scheme;
        }

        String result;
        if (value == null && action != null) {
            if(PortletActionContext.isPortletRequest()) {
                result = PortletUrlHelper.buildUrl(action, namespace, parameters, portletUrlType, portletMode, windowState);
            }
            else {
                result = determineActionURL(action, namespace, method, req, res, parameters, scheme, includeContext, encode);
            }
        } else {
            if(PortletActionContext.isPortletRequest()) {
                result = PortletUrlHelper.buildResourceUrl(value, parameters);
            }
            else {
                result = UrlHelper.buildUrl(value, req, res, parameters, scheme, includeContext, encode);
            }
        }

        String id = getId();

        if (id != null) {
            getStack().getContext().put(id, result);

            // add to the request and page scopes as well
            req.setAttribute(id, result);
        } else {
            try {
                writer.write(result);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("IOError: " + e.getMessage(), e);
            }
        }
        return super.end(writer, body);
    }

    /**
     * @ww.tagattribute required="false" default="get"
     * description="The includeParams attribute may have the value 'none', 'get' or 'all'."
     */
    public void setIncludeParams(String includeParams) {
        this.includeParams = includeParams;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set scheme attribute"
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The target value to use, if not using action"
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The action generate url for, if not using value"
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The namespace to use"
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The method of action to use"
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="whether to encode parameters"
     */
    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="whether actual context should be included in url"
     */
    public void setIncludeContext(boolean includeContext) {
        this.includeContext = includeContext;
    }
    
    /**
     * @ww.tagattribute required="false"
     * description="The resulting portlet mode"
     * @param portletMode
     */
    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The resulting portlet window state
     * @param windowState
     */
    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Specifies if this should be a portlet render or action url"
     * @param portletUrlType
     */
    public void setPortletUrlType(String portletUrlType) {
        this.portletUrlType = portletUrlType;
    }

    /**
     * Merge request parameters into current parameters. If a parameter is
     * already present, than the request parameter will not override its value.
     * 
     * @param parameters component parameters
     * @param contextParameters request parameters
     */
    protected void mergeRequestParameters(Map parameters, Map contextParameters){
        for (Iterator iterator = contextParameters.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            
            if (!parameters.containsKey(key)) {
                parameters.put(key, entry.getValue());
            }
        }
    }
}