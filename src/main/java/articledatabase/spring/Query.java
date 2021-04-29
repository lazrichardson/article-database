/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Query object representation for Spring Boot, is returned as JSON
*/

package articledatabase.spring;

import articledatabase.databases.Article;
import java.util.ArrayList;

public class Query {

  private final String type;
  private final String startYear;
  private final String endYear;
  private final String searchTerm;
  private final ArrayList<Article> results;

  /**
   * Query object mapping for the JSON representation of a query result in the API.
   *
   * @param type       database type used to fulfill the query
   * @param startYear  the starting date of the query
   * @param endYear    the ending data of the query
   * @param searchTerm searchTerm the keyword(s) used for searching the database
   * @param results    the result of the database search with the given query params
   */
  public Query(String type, String startYear,
      String endYear, String searchTerm, ArrayList<Article> results) {
    this.type = type;
    this.startYear = startYear;
    this.endYear = endYear;
    this.searchTerm = searchTerm;
    this.results = results;
  }

  /**
   * Getter for the search results.
   *
   * @return article titles of the search results
   */
  public ArrayList<Article> getResults() {
    return results;
  }

  /**
   * ToString representation of the object.
   *
   * @return string of all the items in the object
   */
  public String toString() {
    return type + " " + startYear + " " + endYear + " " + searchTerm + " " + "\n" + results;
  }
}
