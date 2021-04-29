/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Implements Pipeline Pattern
*/

package articledatabase.pipeline;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class ArticlePipeline<I, O> {

  private final Step<I, O> currentStep;

  /**
   * Constructor for the article pipeline.
   *
   * @param currentStep the step in the pipeline that is currently active
   */
  public ArticlePipeline(Step<I, O> currentStep) {
    this.currentStep = currentStep;
  }

  /**
   * Cycle through the article pipeline.
   *
   * @param newStep new step that will be added to the end of the pipeline
   * @param <K>     output if the new step
   * @return creates new article pipeline with added step
   */
  public <K> ArticlePipeline<I, K> addStep(Step<O, K> newStep) {
    return new ArticlePipeline<>(input -> newStep.process(currentStep.process(input)));
  }

  /**
   * Run the current step and return the output.
   *
   * @param input value input to the first step of the pipeline
   * @return output of the pipeline
   * @throws ParserConfigurationException when parser is mis-configured
   * @throws IOException                  files may not exist
   * @throws SAXException                 SAX parser may not be unable to parse the file
   */
  public O run(I input) throws IOException, SAXException, ParserConfigurationException {
    return currentStep.process(input);
  }
}