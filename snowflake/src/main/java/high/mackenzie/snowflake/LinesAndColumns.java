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
 * An instance of this class computes line-numbers and column-numbers regarding a character-array.
 * 
 * @author Michael Mackenzie High
 */
public final class LinesAndColumns 
{
    
    /**
     * An element in this array is the line-number of the character at the 
     * equivalently indexed array element in the input. 
     */
    final int[] lineNumbers;
    
    /**
     * An element in this array is the column-number of the character at the
     * equivalently indexed array element in the input.
     */
    final int[] columnNumbers;
    
    /**
     * Sole Constructor.
     * 
     * @param input is the input itself. 
     * @param newline is the newline that separates lines in the input.
     * @throws UnsupportedOperationException if [newline] is not supported.
     */
    public LinesAndColumns(final char[] input,
                           final NewlineStyles newline)
    {
        Utils.checkNonNull(input);
        Utils.checkNonNull(newline);
        
        this.lineNumbers = new int[input.length];
        
        this.columnNumbers = new int[input.length];
        
        switch(newline)
        {
            case CR: compute(input, '\r'); break;  
            
            case LF: compute(input, '\n'); break;
            
            case CR_LF: compute(input, '\r', '\n'); break;
            
            default: throw new UnsupportedOperationException("Unsupported Newline Type");
        }                
    }
    
    /**
     * This method computes the line-number and column-number information,
     * if the newline is a single character. 
     * 
     * @param input is the input that contains the lines and columns.
     * @param newline is the newline character itself. 
     */
    final void compute(final char[] input, 
                       final char newline)
    {
        int lines = 1;
        
        int columns = 1;
        
        boolean condition = false;
        
        for(int i = 0; i < input.length; i++)
        {
            lineNumbers[i] = lines;
            
            columnNumbers[i] = columns;
            
            condition = newline == input[i];
            
            lines += condition ? 1 : 0;
            
            columns = condition ? 1 : ++columns;
        }       
    }
    
    /**
     * This method computes the line-number and column-number information,
     * if the newline is a two character sequence.
     * 
     * @param input is the input that contains the lines and columns.
     * @param newlineP1 is the first character in the newline sequence.
     * @param newlineP2 is the second character in the newline sequence. 
     */
    final void compute(final char[] input,
                       final char newlineP1,
                       final char newlineP2)
    {
        int lines = 1;
        
        int columns = 2;
        
        boolean condition = false;
        
        if(input.length > 0)
        {
            lineNumbers[0] = 1;
            
            columnNumbers[0] = 1;
        }
        
        for(int i = 1; i < input.length; i++)
        {
            lineNumbers[i] = lines;
            
            columnNumbers[i] = columns;
            
            condition = input[i - 1] == newlineP1 && input[i] == newlineP2;
            
            lines += condition ? 1 : 0;
            
            columns = condition ? 1 : ++columns;           
        }        
    }   
    
    /**
     * This method returns an array that contains exactly one element, 
     * a line-number, for each character in the input that was used to 
     * create this object.
     * 
     * @return an array of the line-numbers for the input that was used to 
     *   create this object. The returned array has a unique identity per 
     *   instance of this object, but not per invocation of this method.
     */
    public int[] lineNumbers()
    {
        return lineNumbers;
    }
    
    /**
     * This method returns an array that contains exactly one element, 
     * a column-number, for each character in the input that was used 
     * to create this object.
     * 
     * @return an array of the column-numbers for the input that was used 
     *   to create this object. The returned array has a unique identity 
     *   per instance of this object, but not per invocation of this method.
     */    
    public int[] columnNumbers()
    {
        return columnNumbers;
    }
            
}
