package controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XMLParser<T> {
    public String convertToXML(T resp) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(resp.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        marshaller.marshal(resp, sw);
        return sw.toString();
    }
}
