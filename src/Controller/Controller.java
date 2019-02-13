package Controller;

import Entity.Individual;
import Entity.Lecturer;
import Entity.Student;
import Gui.ErrorBox;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Controller {

    private static final String emailExpression  = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\"" +
            ")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            "\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:" +
            "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private final static File studentData = new File("data\\studentdata.xml");

    private final static File lecturerData = new File("data\\lecturerdata.xml");
    private static String heading;
    private static Document doc;
    private static Mode mode;

    public static void Initiate(Mode m) throws IOException, ParserConfigurationException, SAXException {

        if (!studentData.getParentFile().exists())
            studentData.getParentFile().mkdir();
        if (m == Mode.student) {
            if (!studentData.exists() || studentData.length() == 0) {
                heading = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><studentdata></studentdata>";
                studentData.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(studentData));
                writer.write(heading);
                writer.close();
            }
        } else {
            if (!lecturerData.exists() || lecturerData.length() == 0) {
                lecturerData.createNewFile();
                heading = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><lecturerdata></lecturerdata>";
                BufferedWriter writer = new BufferedWriter(new FileWriter(lecturerData));
                writer.write(heading);
                writer.close();
            }
        }
        mode = m;
        doc = getDoc();
    }

    public static Mode getMode() {
        return mode;
    }

    public static <Individual> ArrayList<Individual> Read() {
        ArrayList<Individual> individuals = new ArrayList<>();

        Element root = doc.getDocumentElement();
        NodeList nodelist = root.getChildNodes();

        for (int i = 0; i < nodelist.getLength(); i++) {
            try {
                individuals.add((Individual) Read(nodelist.item(i).
                        getAttributes().getNamedItem("Id").getNodeValue()));
            }
            catch (NullPointerException e)
            {
                Gui.ErrorBox.display("Nothing to show");
            }
        }

        return individuals;
    }

    public static void Create(Individual individual) {

        Student student;
        Lecturer lecturer;

        if (individual.getId().equals("") || individual.getName().equals("") ||
                individual.getEmail().equals("") || individual.getDob().equals("") ||
                individual.getPhone().equals("") || individual.getAddress().equals(""))
            Gui.ErrorBox.display("Please fill all data");
        else if (mode == Mode.lecturer && (!individual.getId().matches("[0-9]{8}")))
            Gui.ErrorBox.display("Wrong ID format");
        else if (mode == Mode.student && (!individual.getId().matches("G[TC][0-9]{5}")))
            Gui.ErrorBox.display("Wrong ID format");
        else if (!individual.getName().matches("[A-Z][a-zA-Z][^#&<>\\\"~;$^%{}?]{0,20}$"))
            Gui.ErrorBox.display("Wrong Name format");
        else if (!individual.getEmail().matches(emailExpression))
            Gui.ErrorBox.display("Wrong Email format");
        else if(!isNumeric(individual.getPhone()))
            Gui.ErrorBox.display("Wrong phone number format");
        else {
            try {
                if (Search(individual.getId()) == null) {
                    Element root = doc.getDocumentElement();
                    Element id;
                    if (mode == Mode.student)
                        id = doc.createElement("Student");
                    else
                        id = doc.createElement("Lecturer");

                    id.setAttribute("Id", individual.getId());

                    Element name = doc.createElement("Name");
                    name.setTextContent(individual.getName());

                    Element dob = doc.createElement("Dob");
                    dob.setTextContent(individual.getDob());

                    Element address = doc.createElement("Address");
                    address.setTextContent(individual.getAddress());

                    Element email = doc.createElement("Email");
                    email.setTextContent(individual.getEmail());

                    Element phone = doc.createElement("Phone");
                    phone.setTextContent(individual.getPhone());

                    id.appendChild(name);
                    id.appendChild(dob);
                    id.appendChild(email);
                    id.appendChild(phone);
                    id.appendChild(address);
                    StreamResult streamResult;
                    if (mode == Mode.student) {
                        student = (Student) individual;
                        Element Class = doc.createElement("Class");
                        Class.setTextContent(student.getclass());
                        id.appendChild(Class);
                        streamResult = new StreamResult(new OutputStreamWriter
                                (new FileOutputStream(studentData),
                                StandardCharsets.ISO_8859_1));
                    } else {
                        lecturer = (Lecturer) individual;
                        Element department = doc.createElement("Department");
                        department.setTextContent(lecturer.getDepartment());
                        id.appendChild(department);
                        streamResult = new StreamResult(new OutputStreamWriter
                                (new FileOutputStream(lecturerData),
                                StandardCharsets.ISO_8859_1));
                    }

                    root.appendChild(id);
                    Source source = new DOMSource(doc);
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.transform(source, streamResult);
                }
                else
                {
                    ErrorBox.display("Duplicated data");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static Individual Read(String id) {
        Individual individual;
        if (mode == Mode.student)
            individual = new Student();
        else
            individual = new Lecturer();
        Element element = Search(id);
        if (element != null) {
            individual.addId(element.getAttribute("Id"));
            NodeList nList = element.getChildNodes();
            individual.addName(nList.item(0).getTextContent());
            individual.addDob(nList.item(1).getTextContent());
            individual.addEmail(nList.item(2).getTextContent());
            individual.addPhone(nList.item(3).getTextContent());
            individual.addAddress(nList.item(4).getTextContent());
            if (mode == Mode.student)
                ((Student) individual).addClass(nList.item(5).getTextContent());
            else
                ((Lecturer) individual).addDepartment(nList.item(5).getTextContent());
            return individual;

        } else
            return null;
    }

    public static void Update(String id, Individual individual)
            throws IOException, TransformerException {

        Student student;
        Lecturer lecturer;
        StreamResult streamResult;
        Source source = new DOMSource(doc);
        if (individual.getId().equals("") || individual.getName().equals("") ||
                individual.getEmail().equals("") || individual.getDob().equals("") ||
                individual.getPhone().equals("") || individual.getAddress().equals(""))
            Gui.ErrorBox.display("Please fill all data");
        else if (mode == Mode.lecturer && (!individual.getId().matches("[0-9]{8}")))
            Gui.ErrorBox.display("Wrong ID format");
        else if (mode == Mode.student && (!individual.getId().matches("G[TC][0-9]{5}")))
            Gui.ErrorBox.display("Wrong ID format");
        else if (!individual.getName().matches("[A-Z][a-zA-Z][^#&<>\\\"~;$^%{}?]{0,20}$"))
            Gui.ErrorBox.display("Wrong Name format");
        else if (!individual.getEmail().matches(emailExpression))
            Gui.ErrorBox.display("Wrong Email format");
        else if(!isNumeric(individual.getPhone()))
            Gui.ErrorBox.display("Wrong phone number format");
        else {
            NodeList nodelist = Search(id).getChildNodes();
            nodelist.item(0).setTextContent(individual.getName());
            nodelist.item(1).setTextContent(individual.getDob());
            nodelist.item(2).setTextContent(individual.getEmail());
            nodelist.item(3).setTextContent(individual.getPhone());
            nodelist.item(4).setTextContent(individual.getAddress());
            if (mode == Mode.student) {
                student = (Student) individual;
                nodelist.item(5).setTextContent(student.getclass());
                streamResult = new StreamResult(new OutputStreamWriter
                        (new FileOutputStream(studentData), StandardCharsets.ISO_8859_1));
            }
            else {
                lecturer = (Lecturer) individual;
                nodelist.item(5).setTextContent(lecturer.getDepartment());
                streamResult = new StreamResult(new OutputStreamWriter
                        (new FileOutputStream(lecturerData), StandardCharsets.ISO_8859_1));
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, streamResult);
        }
    }

    public static void Delete(String id) throws IOException, TransformerException {
        Element element = Search(id);
        if (element != null) {
            element.getParentNode().removeChild(element);
            StreamResult streamResult;
            Source source = new DOMSource(doc);
            if (mode == Mode.student) {
                streamResult = new StreamResult(new OutputStreamWriter
                        (new FileOutputStream(studentData),
                        StandardCharsets.ISO_8859_1));
            } else {
                streamResult = new StreamResult(new OutputStreamWriter
                        (new FileOutputStream(lecturerData),
                        StandardCharsets.ISO_8859_1));
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, streamResult);
        }
    }

    public static <Individual> ArrayList<Individual> SearchByName(String name) {
        Element root = doc.getDocumentElement();
        NodeList nodelist = root.getChildNodes();

        ArrayList<Individual> individuals = new ArrayList<>();

        String lowerName = name.toLowerCase();

        for (int i = 0; i < nodelist.getLength(); i++) {
            String lowerSearch = nodelist.item(i).getAttributes().getNamedItem("Id")
                    .getNodeValue().toLowerCase() +
                    nodelist.item(i).getFirstChild().getTextContent().toLowerCase();
            if (lowerSearch.contains(lowerName)) {
                individuals.add((Individual) Read(nodelist.item(i).getAttributes()
                        .getNamedItem("Id").getNodeValue()));
            }
        }
        return individuals;
    }

    @Nullable
    private static Element Search(String id) {
        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xpath = xFactory.newXPath();
        String expression;
        if (mode == Mode.student)
            expression = "/studentdata/Student";
        else
            expression = "/lecturerdata/Lecturer";
        expression = new StringBuilder().append(expression).append("[@Id=").append("\"").
                append(id).append("\"]").toString();
        Element element = null;
        try {
            element = (Element) xpath.evaluate(expression, doc, XPathConstants.NODE);
            return element;
        } catch (XPathExpressionException e) {
            return null;
        }
    }

    private static Document getDoc() throws ParserConfigurationException, IOException, SAXException {
        Document doc;
        if (mode == Mode.lecturer)
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(lecturerData);
        else
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(studentData);
        return doc;
    }

    private static boolean isNumeric(String str) {
        try {
            double i = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public enum Mode {student, lecturer}

}