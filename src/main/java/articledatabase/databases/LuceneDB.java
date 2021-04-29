/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Lucene Database / Search Index implementation
*/

package articledatabase.databases;

import articledatabase.search.LuceneSearch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class LuceneDB implements ArticleDatabase {

  StandardAnalyzer analyzer; // analyzer for Lucene
  FSDirectory index; // document index for Lucene
  String indexLocation; // path of the index
  IndexWriterConfig config; // configuration for Lucene Index
  IndexWriter writer; // Writer for Lucene

  /**
   * Configures the Lucene Database including the index.
   *
   * @throws IOException may be unable to open path to the index
   */
  public LuceneDB() throws IOException {

    File f1 = new File("./src/main/luceneIndex");
    String indexDirectory = f1.getAbsolutePath();

    // Specify the analyzer for tokenizing text.
    analyzer = new StandardAnalyzer();

    // Create the index
    indexLocation = indexDirectory;
    indexCleanup();
    index = FSDirectory.open(Paths.get(indexLocation));
    config = new IndexWriterConfig(analyzer);
    writer = new IndexWriter(index, config);

  }

  /**
   * Add articles to the Lucene Database.
   *
   * @param articles articles which will be added to the database
   * @throws IOException may be unable to access the directory for the Lucene index
   */
  public void addArticles(ArrayList<Article> articles) throws IOException {

    for (Article article : articles) {
      addDoc(article);
    }
    System.out.println("Added " + articles.size() + " to lucene");
    writer.close();
  }

  /**
   * Search the Lucene Database.
   *
   * @param searchTerm keyword(s) that will be used for the search
   * @param startYear  starting year of the search
   * @param endYear    ending year of the search
   * @return results of the search
   * @throws IOException may be unable to access Lucene Index
   */
  public ArrayList<Article> search(String searchTerm, String startYear, String endYear)
      throws IOException {
    LuceneSearch luceneSearch = new LuceneSearch(index);
    luceneSearch.searchArticles(searchTerm, startYear, endYear);
    return luceneSearch.getSearchResults();
  }

  /**
   * Adapter to adjust formatting of Article for ingestion to Lucene document.
   *
   * @param article Article object to be added to the Lucene Database
   * @throws IOException may be unable to access file location of Lucene Database
   */
  private void addDoc(Article article) throws IOException {

    Document doc = new Document();
    doc.add(new TextField("title", article.getArticleTitle(), Field.Store.YES));
    doc.add(new TextField("year", article.getArticleYear(), Field.Store.YES));
    writer.addDocument(doc);
  }

  /**
   * Remove files from the Lucene index directory to avoid data quality issues.
   */
  public void indexCleanup() {
    ArrayList<Boolean> filesDeleted = new ArrayList<>();
    File[] indexCleanup = new File(indexLocation).listFiles();
    // clean up the index for the next run
    assert indexCleanup != null;
    for (File file : indexCleanup) {
      filesDeleted.add(file.delete());
    }
    for (Boolean file : filesDeleted) {
      if (file) {
        System.out.println("index file deleted");
      }
    }

  }

  /**
   * Used for unit testing - gets the index of Lucene Database.
   *
   * @return index of the Lucene Database
   */
  public FSDirectory getIndex() {
    return index;
  }


}


