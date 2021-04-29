/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Interface for searching article databases
*/

package articledatabase.search;

import articledatabase.databases.Article;
import java.io.IOException;
import java.util.ArrayList;

public interface ArticleSearch {

  /**
   * Search for articles in the document database with given parameters.
   *
   * @param searchTerm the keyword(s) used for searching the database
   * @param fromDate   the starting date of the query
   * @param toDate     the ending data of the query
   * @throws IOException may be unable to access Lucene file locations
   */
  void searchArticles(String searchTerm, String fromDate, String toDate)
      throws IOException;

  /**
   * Getter for search results.
   *
   * @return list of article titles returned from search
   */
  ArrayList<Article> getSearchResults();

}


