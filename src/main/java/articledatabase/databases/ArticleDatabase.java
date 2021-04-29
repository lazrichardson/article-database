/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Interface for databases storing article objects
*/

package articledatabase.databases;

import java.io.IOException;
import java.util.ArrayList;

public interface ArticleDatabase {

  /**
   * Add articles to the document database.
   *
   * @param articles articles which will be added to the database
   * @throws IOException database may not exist
   */
  void addArticles(ArrayList<Article> articles) throws IOException;

  /**
   * Search for articles in the article database.
   *
   * @param searchTerm keyword(s) that will be used for the search
   * @param startYear  starting year of the search
   * @param endYear    ending year of the search
   * @return article titles of the search results
   */
  ArrayList<Article> search(String searchTerm, String startYear, String endYear) throws IOException;

}
