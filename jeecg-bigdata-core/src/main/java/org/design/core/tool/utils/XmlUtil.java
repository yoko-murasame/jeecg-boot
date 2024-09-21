package org.design.core.tool.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/XmlUtil.class */
public class XmlUtil {
    private final XPath path = getXPathFactory().newXPath();
    private final Document doc;
    private static volatile boolean preventedXXE = false;

    private XmlUtil(InputSource inputSource) throws ParserConfigurationException, SAXException, IOException {
        this.doc = getDocumentBuilderFactory().newDocumentBuilder().parse(inputSource);
    }

    private static XmlUtil create(InputSource inputSource) {
        try {
            return new XmlUtil(inputSource);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static XmlUtil of(InputStream is) {
        return create(new InputSource(is));
    }

    public static XmlUtil of(String xmlStr) {
        StringReader sr = new StringReader(xmlStr.trim());
        XmlUtil xmlUtil = create(new InputSource(sr));
        IoUtil.closeQuietly(sr);
        return xmlUtil;
    }

    private Object evalXPath(String expression, @Nullable Object item, QName returnType) {
        try {
            return this.path.evaluate(expression, null == item ? this.doc : item, returnType);
        } catch (XPathExpressionException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public String getString(String expression) {
        return (String) evalXPath(expression, null, XPathConstants.STRING);
    }

    public Boolean getBoolean(String expression) {
        return (Boolean) evalXPath(expression, null, XPathConstants.BOOLEAN);
    }

    public Number getNumber(String expression) {
        return (Number) evalXPath(expression, null, XPathConstants.NUMBER);
    }

    public Node getNode(String expression) {
        return (Node) evalXPath(expression, null, XPathConstants.NODE);
    }

    public NodeList getNodeList(String expression) {
        return (NodeList) evalXPath(expression, null, XPathConstants.NODESET);
    }

    public String getString(Object node, String expression) {
        return (String) evalXPath(expression, node, XPathConstants.STRING);
    }

    public Boolean getBoolean(Object node, String expression) {
        return (Boolean) evalXPath(expression, node, XPathConstants.BOOLEAN);
    }

    public Number getNumber(Object node, String expression) {
        return (Number) evalXPath(expression, node, XPathConstants.NUMBER);
    }

    public Node getNode(Object node, String expression) {
        return (Node) evalXPath(expression, node, XPathConstants.NODE);
    }

    public NodeList getNodeList(Object node, String expression) {
        return (NodeList) evalXPath(expression, node, XPathConstants.NODESET);
    }

    public Map<String, String> toMap() {
        Element root = this.doc.getDocumentElement();
        Map<String, String> params = new HashMap<>(16);
        NodeList list = root.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                params.put(node.getNodeName(), node.getTextContent());
            }
        }
        return params;
    }

    private static DocumentBuilderFactory getDocumentBuilderFactory() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = XmlHelperHolder.documentBuilderFactory;
        if (!preventedXXE) {
            preventXXE(dbf);
        }
        return dbf;
    }

    private static void preventXXE(DocumentBuilderFactory dbf) throws ParserConfigurationException {
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        dbf.setXIncludeAware(false);
        dbf.setExpandEntityReferences(false);
        preventedXXE = true;
    }

    private static XPathFactory getXPathFactory() {
        return XmlHelperHolder.xPathFactory;
    }

    /* access modifiers changed from: private */
    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/XmlUtil$XmlHelperHolder.class */
    public static class XmlHelperHolder {
        private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        private static XPathFactory xPathFactory = XPathFactory.newInstance();

        private XmlHelperHolder() {
        }
    }
}
