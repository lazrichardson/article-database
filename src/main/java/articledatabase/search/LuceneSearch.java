/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Concrete implementation of strategy for searching LuceneDB article databases
*/

package articledatabase.search;

import articledatabase.databases.Article;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class LuceneSearch implements ArticleSearch {

  FSDirectory index;
  ArrayList<Article> searchResults;

  /**
   * Constructor for LuceneSearch.
   *
   * @param index the index of the Lucene Database instance that is being searched
   */
  public LuceneSearch(FSDirectory index) {
    this.index = index;
    this.searchResults = new ArrayList<>();
  }

  /**
   * Search the Lucene Database using the given query terms.
   *
   * @param searchTerm the keyword(s) used for searching the database
   * @param fromDate   the starting date of the query
   * @param toDate     the ending data of the query
   * @throws IOException may be unable to access Lucene file locations
   */
  public void searchArticles(String searchTerm, String fromDate, String toDate)
      throws IOException {
    searchResults.clear();

    // Search
    int hitsPerPage = 1000000000;
    IndexReader reader = DirectoryReader.open(index);
    IndexSearcher searcher = new IndexSearcher(reader);
    Builder query = buildQuery(searchTerm, fromDate, toDate);
    TopDocs docs = searcher.search(query.build(), hitsPerPage);

    // Add results
    ScoreDoc[] hits = docs.scoreDocs;
    for (ScoreDoc hit : hits) {
      int docId = hit.doc;
      Document d = searcher.doc(docId);
      Article article = new Article(d.get("title"), d.get("year"));
      searchResults.add(article);
    }
    reader.close();

  }

  /**
   * Get the search results from the query.
   *
   * @return search results
   */
  public ArrayList<Article> getSearchResults() {
    return searchResults;
  }

  /**
   * Utility method to build a query for the Lucene Database given parameters from the
   * SearchArticles method.
   *
   * @param searchTerm the keyword(s) used for searching the database
   * @param fromDate   the starting date of the query
   * @param toDate     the ending data of the query
   * @return Query object to enable Lucene Database search
   */
  private Builder buildQuery(String searchTerm, String fromDate, String toDate) {
    // initialize the boolean query
    Builder query = new Builder();

    // include the search term
    // must include the search term in the title
    query.add(new TermQuery(new Term("title", searchTerm)), Occur.MUST);

    // add the date range
    int start = Integer.parseInt(fromDate);
    int end = Integer.parseInt(toDate);
    // must have a year value between the fromDate and toDate
    do {
      query.add(new TermQuery(new Term("year", Integer.toString(start))),
          Occur.SHOULD);
      start++;
    } while (start <= end);
    // must include at least one of the dates in the range
    query.setMinimumNumberShouldMatch(1);

    return query;
  }
}
