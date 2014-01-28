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
 * An instance of this class stores the mutable state of a parser.
 *
 * @author Michael Mackenzie High
 */
final class State
{
    /**
     * This object records the starts/successes/failures of match-attempts. 
     */
    public final Tracer tracer;

    /**
     * This is the input being parsed by the parser.
     */
    public final char[] input;

    /**
     * This is the current position of the parser.
     *
     * <p>
     * The current position of the parser is the start position of the next match. 
     * When a rule matches, the position of the parser moves forward (i.e. advances). 
     * When the the parser backtracks, the position of the parser moves backward.
     * Note, predicate rules do not cause the position to advance, even if they successfully match. 
     * </p>
     */
    public int position = 0;

    /**
     * This is the farthest position that parsing has reached, thus far. 
     */
    public int farthest_position = 0;
    
    /**
     * Sole Constructor.
     *
     * @param input is to input to be parsed.
     */
    public State(final char[] input, final int trace_count)
    {
        Utils.checkNonNull(input);
        
        this.input = input;
        
        this.tracer = new Tracer(trace_count);
    }
    
    /**
     * This method is called whenever a rule begins a match attempt. 
     *
     * @param rule is the rule itself.
     * @return the current position of the parser. 
     */
    public int begin(final Rule rule)
    {
        // Record the start of a new match-attempt. 
        this.tracer.add(rule, position, TraceElementReason.BEGIN);
        
        return position;
    }

    /**
     * This method is called by a rule that has just successfully matched.
     *
     * @param rule is the rule itself.
     */
    public void succeed(final Rule rule)
    {
        // Record the success of a match-attempt. 
        this.tracer.add(rule, position, TraceElementReason.SUCCESS);
    }

    /**
     * This method is called by a rule that has just failed to match.
     * 
     * @param rule is the rule itself. 
     * @param restore_position is the position in the parser's input to backtrack to. 
     */
    public void fail(final Rule rule, final int restore_position)
    {   
        // Record the failure of a match-attempt. 
        this.tracer.add(rule, position, TraceElementReason.FAIL);
        
        // Backtrack
        this.position = restore_position;
    }

    /**
     * This method returns the number of characters in the input that still can be consumed.
     * 
     * @return the number of characters in the input waiting to be parsed.
     */
    public int remaining() { return input.length - position; }

    /**
     * This method returns the next character in the input and advances the position. 
     * 
     * @return the next character in the input.
     * @throws IndexOutOfBoundsException if there is no input waiting to be consumed. 
     */
    public char next()
    {
        // The parser is moving forward.
        // If this is the farthest position the parser has reached, record the location.
        // This record will help the user find syntax-errors. 
        this.farthest_position = position + 1 > farthest_position 
                               ? position : farthest_position;
        
        // Return the next character from the parser's input and advance one positition forward. 
        return input[position++];
    }
    
}