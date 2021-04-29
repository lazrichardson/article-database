/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Unit tests for email generator program
*/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import articledatabase.databases.Article;
import articledatabase.databases.LocalDB;
import articledatabase.databases.LuceneDB;
import articledatabase.pipeline.ArticlePipeline;
import articledatabase.pipeline.DirectoryFileFinder;
import articledatabase.pipeline.ParseArticles;
import articledatabase.pipeline.ParseNodeList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Tests {

  public Tests() {
  }

  @Test
  public void TestPipelineDirectory()
      throws IOException, ParserConfigurationException, SAXException {
    String inputDirectory = "./src/main/Data";

    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder());

    ArrayList<File> files = xmlPipeline.run(inputDirectory);
    assertEquals(2, files.size());
  }

  @Test
  public void TestPipelineNodeList()
      throws IOException, ParserConfigurationException, SAXException {
    String inputDirectory = "./src/main/Data";

    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder())
        .addStep(new ParseNodeList());

    ArrayList<NodeList> nodes = xmlPipeline.run(inputDirectory);
    assertTrue(nodes.size() > 0);
  }

  @Test
  public void TestPipelineArticles()
      throws IOException, ParserConfigurationException, SAXException {
    String inputDirectory = "./src/main/Data";

    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder())
        .addStep(new ParseNodeList())
        .addStep(new ParseArticles());

    ArrayList<Article> articles = xmlPipeline.run(inputDirectory);
    assertTrue(articles.size() > 0);
  }

  @Test
  public void TestLuceneConfiguration() throws IOException {

    LuceneDB luceneDB = new LuceneDB();
  }

  @Test
  public void TestLuceneArticleAddition()
      throws IOException, ParserConfigurationException, SAXException {
    String inputDirectory = "./src/main/Data";

    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder())
        .addStep(new ParseNodeList())
        .addStep(new ParseArticles());

    ArrayList<Article> articles = xmlPipeline.run(inputDirectory);

    LuceneDB luceneDB = new LuceneDB();
    luceneDB.addArticles(articles);

    // directory of the lucene index
    String inputFiles = "./src/main/luceneIndex";

    // get all files in the directory
    File[] files = new File(inputFiles).listFiles();
    assert files != null;
    assertTrue(files.length > 1);
  }


  @Test
  public void TestLuceneSearch()
      throws IOException, ParserConfigurationException, SAXException {
    String inputDirectory = "./src/main/Data";

    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder())
        .addStep(new ParseNodeList())
        .addStep(new ParseArticles());

    ArrayList<Article> articles = xmlPipeline.run(inputDirectory);

    LuceneDB luceneDB = new LuceneDB();
    luceneDB.addArticles(articles);
    ArrayList<Article> results = luceneDB.search("cancer", "2000", "2100");

    assertTrue(results.size() > 5);
  }

  @Test
  public void TestLocalDBConfiguration() {
    LocalDB localDB = new LocalDB();
  }

  @Test
  public void TestLocalDBArticleAddition()
      throws IOException, ParserConfigurationException, SAXException {
    String inputDirectory = "./src/main/Data";

    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder())
        .addStep(new ParseNodeList())
        .addStep(new ParseArticles());

    ArrayList<Article> articles = xmlPipeline.run(inputDirectory);

    LocalDB localDB = new LocalDB();
    localDB.addArticles(articles);
    assertTrue(articles.size() > 100);
  }

  @Test
  public void TestLocalDBSearch()
      throws IOException, ParserConfigurationException, SAXException {

    String inputDirectory = "./src/main/Data";

    var xmlPipeline = new ArticlePipeline<>(new DirectoryFileFinder())
        .addStep(new ParseNodeList())
        .addStep(new ParseArticles());

    ArrayList<Article> articles = xmlPipeline.run(inputDirectory);

    LocalDB localDB = new LocalDB();
    localDB.addArticles(articles);
    ArrayList<Article> results = localDB.search("cancer", "2000", "2100");

    assertTrue(results.size() > 5);
  }
}


