/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Pipeline stage which finds XML files in a given directory
*/

package articledatabase.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class DirectoryFileFinder implements Step<String, ArrayList<File>> {

  /**
   * Find all files in a given directory with a .xml extension.
   *
   * @param inputFiles folder path to find files within
   * @return lise of File objects that have a .xml extension
   */
  private ArrayList<File> findXmlFiles(String inputFiles) {
    ArrayList<File> xmlFiles = new ArrayList<>();

    // get all files in the directory
    File[] files = new File(inputFiles).listFiles();
    // we know that the directory is not empty
    assert files != null;
    // loop through all of the files and if it's xml, add it to the list
    for (File file : files) {
      String rootFileExtension = file.getPath()
          .substring(file.getPath().length() - 4);
      // if it is XML, then add
      if (rootFileExtension.equals(".xml")) {
        xmlFiles.add(file);
        System.out.println("Added Doc");
      }
    }
    return xmlFiles;
  }

  /**
   * Implementation of the Pipeline Pattern - runs the findXMLFiles() method.
   *
   * @param input receives a file path
   * @return returns a list of file locations for next step in the pipeline
   * @throws StepException                step may not be able to complete based on inputs
   * @throws ParserConfigurationException when parser is mis-configured
   * @throws IOException                  files may not exist
   * @throws SAXException                 SAXE parser may not be unable to parse the file
   */
  public ArrayList<File> process(String input)
      throws ParserConfigurationException, SAXException, IOException {
    return findXmlFiles(input);
  }
}
