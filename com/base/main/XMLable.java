package scripts.CombatAIO.com.base.main;

import org.w3c.dom.Element;

/**
 *
 * @author Spencer
 */
public interface XMLable {
    
    Element toXML(XMLWriter writer, Element parent, Object... data);
    
    void fromXML(XMLReader reader, String path, Object... data);
    
    String getXMLName();
}
