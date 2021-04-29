/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Pipeline stage which creates article objects when given an array of NodeLists
*/

package articledatabase.pipeline;

import articledatabase.databases.Article;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ParseArticles implements Step<ArrayList<NodeList>, ArrayList<Article>> {

  /**
   * Create list of Articles from given list of NodeLists.
   *
   * @param nodeLists list of XML NodeLists
   * @return Parsed article tags in the NodeLists
   */
  public ArrayList<Article> parseArticles(ArrayList<NodeList> nodeLists) {
    ArrayList<Article> articles = new ArrayList<>();
    for (NodeList nodeList : nodeLists) {
      for (int i = 0; i < nodeList.getLength(); i++) {
        // get the node
        Node node = nodeList.item(i);
        // cast to element to get the tags
        Element elem = (Element) node;
        // find and print the title
        NodeList title = elem.getElementsByTagName("ArticleTitle");
        String articleTitle = title.item(0).getTextContent();
        // get the article year
        String articleYear = ""; // sometimes it's blank
        NodeList year = elem.getElementsByTagName("ArticleDate");
        // first item in the date
        Element dateParts = (Element) year.item(0);
        // if no date-parts, go one level deeper
        if (dateParts != null) {
          year = dateParts.getChildNodes();
          if (year.getLength() > 0) {
            // pull out the year from XML
            StringBuilder builder = new StringBuilder();
            String data = year.item(1).getTextContent();
            builder.append(data);
            // remove spaces from the date
            String cleanYear = builder.toString().replace("  ", "");
            cleanYear = cleanYear.replaceAll("[\\n\\t ]", "");
            articleYear = cleanYear;
          }
          // add to the list of articles
          articles.add(new Article(articleTitle, articleYear));
        }
      }
    }
    return articles;
  }

  /**
   * Implementation of the Pipeline Pattern - runs the parseArticles() method.
   *
   * @param input receives a list of NodeLists from the previous pipeline step
   * @return returns a articles for next step in the pipeline
   * @throws StepException                step may not be able to complete based on inputs
   * @throws ParserConfigurationException when parser is mis-configured
   * @throws IOException                  files may not exist
   * @throws SAXException                 SAXE parser may not be unable to parse the file
   */
  public ArrayList<Article> process(ArrayList<NodeList> input)
      throws StepException, ParserConfigurationException, SAXException, IOException {
    return parseArticles(input);
  }
}
