/*
 * Copyright 2013 Michael Mackenzie High
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package high.mackenzie.snowflake;

/**
 * An instance of this class is a parser that is based on a Parsing Expression Grammar.
 * 
 * @author Michael Mackenzie High
 */
public final class Parser implements IParser
{
    /**
     * This is the grammar that this parser is based on.
     */
    private final Grammar grammar;
    
    /**
     * This is the tracer that was most recently used by this parser. 
     */
    private Tracer most_recent_tracer;
    
    /**
     * Sole Constructor.
     * 
     * @param grammar is the grammar that the new parser will obey. 
     */
    Parser(final Grammar grammar)
    {
        Utils.checkNonNull(grammar);
        
        this.grammar = grammar;
    }
    
    /**
     * This method returns a record describing stages of the most-recent parsing-attempt. 
     * 
     * <p>The returned list may be useful for debugging grammar and/or finding syntax-errors.</p>
     * 
     * <p>
     * This method is provided here, because invalid grammars may cause stack-overflows.
     * When using the Snowflake GUI, finding information regarding the stack-overflow is helpful.
     * However, the trace-records cannot be obtained, because no parser output was created. 
     * </p>
     * 
     * @return the aforedescribed record. 
     */
    public Trace getTrace()
    {
        if(most_recent_tracer == null)
        {
            throw new IllegalStateException();
        }
        else
        {
            return most_recent_tracer.output();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public ParserOutput parse(final char[] input)
    {
        Utils.checkNonNull(input);
        
        final State state = new State(input, grammar.maximum_record_count);

        this.most_recent_tracer = state.tracer;
        
        final TreeNode parse_tree = grammar.root.match(state);
        
        final ParserOutput output = new ParserOutput(input,
                                                     parse_tree, 
                                                     state.tracer, 
                                                     state.farthest_position);
        
        return output;
    }

    /**
     * {@inheritDoc}
     */
    public ParserOutput parse(final String input) 
    { 
        Utils.checkNonNull(input);
        
        return parse(input.toCharArray()); 
    }
}
