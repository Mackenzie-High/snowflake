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

import java.io.PrintWriter;

/**
 * An instance of this class is the output of a parser. 
 * 
 * @author Mackenzie High
 */
public final class ParserOutput 
{
    /**
     * This is the parse-tree, if parsing was successful; otherwise, this field is null. 
     */
    final ITreeNode parse_tree;
    
    /**
     * This is the object that recorded the parsing-attempt. 
     */
    final Tracer tracer;
    
    /**
     * This is the farthest location that parsing reached. 
     */
    final int farthest_position;
    
    /**
     * This is the input that the parser attempted to parse. 
     */
    final char[] input;
    
    /**
     * Sole Constructor.
     * 
     * @param input is the input that the parser tried to parse. 
     * @param parse_tree is the parse-tree that was created by the parser, 
     *        or null, if parsing was unsuccessful.
     * @param tracer is the object that recorded the parsing-attempt. 
     * @param farthest_match is the farthest location that parsing reached. 
     */
    ParserOutput(final char[] input,
                 final ITreeNode parse_tree, 
                 final Tracer tracer,
                 final int farthest_position)
    {
        Utils.checkNonNull(input);
        Utils.checkNonNull(tracer);
        
        this.input = input;
        this.parse_tree = parse_tree;
        this.tracer = tracer;
        this.farthest_position = farthest_position;
    }
    
    /**
     * This method determines whether parsing succeeded. 
     * 
     * @return true, if and only if, parsing was successful.
     */
    public boolean success() { return parse_tree != null; }
    
    /**
     * This method returns the farthest location that parsing reached.  
     * 
     * <p>The returned position is often useful, when searching for syntax-errors.</p>
     * 
     * @return the aforedescribed location, which is the index relating to the parser's input. 
     */
    public int lengthOfConsumption() { return farthest_position; }
    
    /**
     * This method returns a record describing stages of the parsing-attempt. 
     * 
     * <p>The returned list may be useful for debugging grammar and/or finding syntax-errors.</p>
     * 
     * @return the aforedescribed record. 
     */
    public Trace trace() { return tracer.output(); }
    
    /**
     * This method retrieves and returns the parse-tree created by this parser.
     * 
     * <p>The returned value is actually the root node in the parse-tree.</p>
     * 
     * @return the parse-tree created by the parser, or null, if no such parse-tree exists.
     */    
    public ITreeNode parseTree() { return parse_tree; }
    
    /**
     * This method prints a message describing the status of the output.
     * 
     * <p> If <code>sucess() == true</code>, then the string "Parsing Succeeded!" is printed. </p>
     * 
     * <p> If <code>sucess() == false</code>, then the string "Parsing Failed!" is printed. </p>
     * 
     * @param writer is the writer to write to. 
     * @param estimate is true, if and only if the line and column numbers are to be written also.
     * @param exact is true, if and only if the length of consumption is to be written also. 
     * @param trace is true, if and only if the trace is to be written also. 
     */
    public void print(final PrintWriter writer,
                      final boolean estimate,
                      final boolean exact,
                      final boolean trace)
    {
        
        if(success())
        {
            writer.println("Parsing Succeeded!");
        }
        else
        {
            writer.println("Parsing Failed!");
            
            final int position = lengthOfConsumption();
            
            if(estimate)
            {
                final LinesAndColumns finder = new LinesAndColumns(input, 
                                                                   NewlineStyles.fromSystem());
                
                final int line = position > 0 ? finder.lineNumbers()[position] : 1;
                
                final int column = position > 0 ? finder.columnNumbers()[position] : 1;
                
                writer.println();
                writer.print("Syntax Error Position (Approximate):"); writer.println();
                writer.print("  Line: #"); writer.println(line);
                writer.print("  Column: #"); writer.println(column);
            }
            
            if(exact)
            {
                writer.println();
                writer.print("Length of Consumption: "); writer.println(lengthOfConsumption());
            }
            
            if(trace)
            {
                writer.println();
                tracer.output().print(writer);
            }
        }
    }
}
