/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 14.10.2003
 * Time: 17:24:05
 */
public class DocumentAdapter extends DefaultAdapterNode implements Document {
    //~ Instance fields ////////////////////////////////////////////////////////

    private BeanAdapter rootElement;

    //~ Constructors ///////////////////////////////////////////////////////////

    public DocumentAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
        rootElement = new BeanAdapter(getRootAdapter(), this, getPropertyName(), getValue());
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public NodeList getChildNodes() {
        return new NodeList() {
                public Node item(int i) {
                    return rootElement;
                }

                public int getLength() {
                    return 1;
                }
            };
    }

    public DocumentType getDoctype() {
        return null;
    }

    public Element getDocumentElement() {
        return rootElement;
    }

    public Element getElementById(String string) {
        return null;
    }

    public NodeList getElementsByTagName(String string) {
        return null;
    }

    public NodeList getElementsByTagNameNS(String string, String string1) {
        return null;
    }

    public Node getFirstChild() {
        return rootElement;
    }

    public DOMImplementation getImplementation() {
        return null;
    }

    public Node getLastChild() {
        return rootElement;
    }

    public Node getNextSibling(AdapterNode value) {
        return null;
    }

    public String getNodeName() {
        return "#document";
    }

    public short getNodeType() {
        return Node.DOCUMENT_NODE;
    }

    public Attr createAttribute(String string) throws DOMException {
        return null;
    }

    public Attr createAttributeNS(String string, String string1) throws DOMException {
        return null;
    }

    public CDATASection createCDATASection(String string) throws DOMException {
        return null;
    }

    public Comment createComment(String string) {
        return null;
    }

    public DocumentFragment createDocumentFragment() {
        return null;
    }

    public Element createElement(String string) throws DOMException {
        return null;
    }

    public Element createElementNS(String string, String string1) throws DOMException {
        return null;
    }

    public EntityReference createEntityReference(String string) throws DOMException {
        return null;
    }

    public ProcessingInstruction createProcessingInstruction(String string, String string1) throws DOMException {
        return null;
    }

    public Text createTextNode(String string) {
        return null;
    }

    public boolean hasChildNodes() {
        return true;
    }

    public Node importNode(Node node, boolean b) throws DOMException {
        return null;
    }
}
