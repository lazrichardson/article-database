/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Pipeline stage that makes array of NodeLists given a list of files in a directory
*/

package articledatabase.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseNodeList implements Step<ArrayList<File>, ArrayList<NodeList>> {

  /**
   * Create a NodeList when given an array of file locations.
   *
   * @param files XML files that will be converted to NodeLists
   * @return NodeLists generated from input files
   * @throws ParserConfigurationException when parser is mis-configured
   * @throws IOException                  files may not exist
   * @throws SAXException                 SAXE parser may not be unable to parse the file
   */
  private ArrayList<NodeList> createNodeList(ArrayList<File> files)
      throws ParserConfigurationException, IOException, SAXException {

    String tagName = "PubmedArticle";
    ArrayList<NodeList> nodeLists = new ArrayList<>();

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

    for (File file : files) {
      Document document = documentBuilder.parse(file);
      NodeList nodeList = document.getElementsByTagName(tagName);
      nodeLists.add(nodeList);
      System.out.println(nodeList.getLength());
    }
    return nodeLists;
  }

  /**
   * Implementation of the Pipeline Pattern - runs the createNodeList() method.
   *
   * @param input receives a list of files from the previous pipeline step
   * @return returns a NodeList for next step in the pipeline
   * @throws StepException                step may not be able to complete based on inputs
   * @throws ParserConfigurationException when parser is mis-configured
   * @throws IOException                  files may not exist
   * @throws SAXException                 SAXE parser may not be unable to parse the file
   */
  public ArrayList<NodeList> process(ArrayList<File> input)
      throws StepException, ParserConfigurationException, SAXException, IOException {
    return createNodeList(input);
  }

}
