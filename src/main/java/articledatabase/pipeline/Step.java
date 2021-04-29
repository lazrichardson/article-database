/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Implementation of step in Pipeline Pattern
*/

package articledatabase.pipeline;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public interface Step<I, O> {

  /**
   * Process the algorithm implemented by the step in the pipeline.
   *
   * @param input value from previous step in the pipeline
   * @return value created as a result of this current step's algorithm
   * @throws StepException                step may not be able to complete based on inputs
   * @throws ParserConfigurationException when parser is mis-configured
   * @throws IOException                  files may not exist
   * @throws SAXException                 SAXE parser may not be unable to parse the file
   */
  O process(I input) throws StepException, ParserConfigurationException, SAXException, IOException;
}


