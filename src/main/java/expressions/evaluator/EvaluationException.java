/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.evaluator;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class EvaluationException extends RuntimeException {

    public EvaluationException() {
    }

    public EvaluationException(String message) {
        super(message);
    }

    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvaluationException(Throwable cause) {
        super(cause);
    }

    public EvaluationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
