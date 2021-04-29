/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Data class for Article object used by databases
*/

package articledatabase.databases;

public class Article {

  String articleTitle; // title of the article
  String articleYear; // year the article was created

  /**
   * Data class for Pubmed Articles.
   *
   * @param articleTitle the title of the article
   * @param articleYear  the year that the article was created in
   */
  public Article(String articleTitle, String articleYear) {
    this.articleTitle = articleTitle;
    this.articleYear = articleYear;
  }

  /**
   * Getter for the title of the article.
   *
   * @return title of the article
   */
  public String getArticleTitle() {
    return articleTitle;
  }

  /**
   * Setter for the title of the article.
   *
   * @param articleTitle title of the article
   */
  public void setArticleTitle(String articleTitle) {
    this.articleTitle = articleTitle;
  }

  /**
   * Getter for the year an article was created.
   *
   * @return year the article was created
   */
  public String getArticleYear() {
    return articleYear;
  }
}
