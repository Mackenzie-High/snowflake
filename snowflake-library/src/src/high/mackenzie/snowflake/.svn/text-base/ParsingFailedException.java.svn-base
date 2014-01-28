/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package high.mackenzie.snowflake;

/**
 * An instance of this class indicates that a parser's input does not obey the parser's grammar.
 * 
 * @author Mackenzie High
 */
public final class ParsingFailedException extends Exception
{
    final ParserOutput output;
    
    /**
     * Sole Constructor.
     * 
     * @param output is the output of the parser whose parsing attempt failed. 
     */
    public ParsingFailedException(final ParserOutput output)
    {
        super("Parsing Failed!");
        
        Utils.checkNonNull(output);
        
        this.output = output;
    }
    
    /**
     * This method retrieves the output of the parser whose parsing attempt failed.
     * 
     * @return the output of the aforedescribed parser.
     */
    public ParserOutput parserOutput() { return output; }
}
