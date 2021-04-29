/*
 Name: Luther Richardson
 Date: 04/22/2021
 Course: CS-665 Final Project
 Description: Exception class for Pipeline Pattern
*/

package articledatabase.pipeline;

public abstract class StepException extends RuntimeException implements Step {

  /**
   * Exception thrown when a step is unable to be completed.
   *
   * @param t throwable exception
   */
  StepException(Throwable t) {
    super(t);
  }
}


