import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;

public class XmlParser {
    public static void main(String[] args) {
        try {
            // 1) Build the parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2) Show working directory & load the original XML
            String cwd = System.getProperty("user.dir");
            System.out.println("Working dir: " + cwd);
            File xmlFile = new File(cwd, "books.xml");
            System.out.println("Loading XML from: " + xmlFile.getAbsolutePath());
            Document document = builder.parse(xmlFile);

            // 3) Normalize and read existing books
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("book");
            System.out.println("Original data:");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element book = (Element) nodeList.item(i);
                System.out.println("  • " +
                    book.getElementsByTagName("title").item(0).getTextContent() +
                    " (" +
                    book.getElementsByTagName("year").item(0).getTextContent() +
                    ")");
            }

            // 4) Modify the year of the first <book>
            if (nodeList.getLength() > 0) {
                Element firstBook = (Element) nodeList.item(0);
                firstBook.getElementsByTagName("year").item(0)
                         .setTextContent("2023");
                System.out.println("\nUpdated first book year to 2023.");
            }

            // 5) Save the modified document as updated_books.xml
            File outFile = new File(cwd, "updated_books.xml");
            Transformer transformer = TransformerFactory
                                      .newInstance()
                                      .newTransformer();
            transformer.transform(new DOMSource(document),
                                  new StreamResult(outFile));
            System.out.println("Saved modified XML to: " +
                                outFile.getAbsolutePath());

            // 6) Print out the updated book list
            System.out.println("\nFinal data from updated_books.xml:");
            Document updatedDoc = builder.parse(outFile);
            updatedDoc.getDocumentElement().normalize();
            NodeList updatedList = updatedDoc.getElementsByTagName("book");
            for (int i = 0; i < updatedList.getLength(); i++) {
                Element book = (Element) updatedList.item(i);
                System.out.println("  • " +
                    book.getElementsByTagName("title").item(0).getTextContent() +
                    " (" +
                    book.getElementsByTagName("year").item(0).getTextContent() +
                    ")");
            }

        } catch (ParserConfigurationException
               | IOException
               | DOMException
               | SAXException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            // Handles TransformerException
            ex.printStackTrace();
        }
    }
}
