package start.Parser.ParserException;

/*
 * Exception raised when expected data is missing
 */

public class DialogueDataMissingException extends Exception {
    public DialogueDataMissingException() {
        super();
    }

    public DialogueDataMissingException(String msg) {
        super(msg);
    }
}
