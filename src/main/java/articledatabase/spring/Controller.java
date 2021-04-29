/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: REST Controller for Spring Project
*/

package articledatabase.spring;

import articledatabase.databases.Article;
import articledatabase.databases.LocalDB;
import articledatabase.databases.LuceneDB;
import articledatabase.pipeline.ArticlePipeline;
import articledatabase.pipeline.DirectoryFileFinder;
import articledatabase.pipeline.ParseArticles;
import articledatabase.pipeline.ParseNodeList;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

@RestController
public class Controller {

  private final LocalDB localDB;
  private final LuceneDB luceneDB;

  /**
   * REST API controller for Spring Boot - configures databases and adds articles.
   *
   * @throws ParserConfigurationException may be unable to parse files
   * @throws SAXException                 SAXE may be unable to handle XML file
   * @throws IOException                  May be unable to access Lucene or XML file locations
   */
  Controller()
      throws ParserConfigurationException, SAXException, IOException {

    String inputDirectory = "./src/main/Data";
    // configure the steps in the pipeline
    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder())
        .addStep(new ParseNodeList())
        .addStep(new ParseArticles());
    // Run the pipeline to load the XML articles
    ArrayList<Article> articles = xmlPipeline.run(inputDirectory);
    // Setup for LocalDB
    this.localDB = new LocalDB();
    localDB.addArticles(articles);
    // Setup for Lucene
    this.luceneDB = new LuceneDB();
    luceneDB.addArticles(articles);
  }

  /**
   * Configured how the API responds to queries through HTML get.
   *
   * @param type       database type used to fulfill the query
   * @param startYear  the starting date of the query
   * @param endYear    the ending data of the query
   * @param searchTerm searchTerm the keyword(s) used for searching the database
   * @return query object which is translated to JSON in the client's browser
   * @throws IOException may be unable to access Lucene file locations
   */
  // Example: http://localhost:8080/query?type=lucene&start=2018&end=2020&term=heart
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/query")
  public Query query(@RequestParam(value = "type", defaultValue = "local") String type,
      @RequestParam(value = "start", defaultValue = "1900") String startYear,
      @RequestParam(value = "end", defaultValue = "2100") String endYear,
      @RequestParam(value = "term", defaultValue = "cancer") String searchTerm
  ) throws IOException {
    searchTerm = searchTerm.replace("_", " ");
    ArrayList<Article> searchResults;

    if (type.equals("local")) {
      // Lucene Search
      searchResults = luceneDB.search(searchTerm, startYear, endYear);
    } else {
      // Local Search
      searchResults = localDB.search(searchTerm, startYear, endYear);
    }
    return new Query(type, startYear, endYear, searchTerm, searchResults);
  }
}