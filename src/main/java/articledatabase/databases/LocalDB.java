/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Local database implementation using arraylist
*/

package articledatabase.databases;

import articledatabase.search.LocalDbSearch;
import java.util.ArrayList;

public class LocalDB implements ArticleDatabase {

  ArrayList<Article> articleDatabase; // articles loaded by parser

  /**
   * Constructor for LocalDB.
   */
  public LocalDB() {
    this.articleDatabase = new ArrayList<>();
  }

  /**
   * Add articles to the LocalDB.
   *
   * @param articles articles which will be added to the database
   */
  public void addArticles(ArrayList<Article> articles) {
    articleDatabase = articles;
  }

  /**
   * Search the local DB - utilizes Strategy pattern.
   *
   * @param searchTerm keyword(s) that will be used for the search
   * @param startYear  starting year of the search
   * @param endYear    ending year of the search
   * @return results of the search
   */
  public ArrayList<Article> search(String searchTerm, String startYear, String endYear) {

    LocalDbSearch localDbSearch = new LocalDbSearch(articleDatabase);
    localDbSearch.searchArticles(searchTerm, startYear, endYear);

    return localDbSearch.getSearchResults();
  }

}
