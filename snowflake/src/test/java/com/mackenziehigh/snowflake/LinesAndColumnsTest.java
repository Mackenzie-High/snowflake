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

package com.mackenziehigh.snowflake;

import org.junit.Test;
import static org.junit.Assert.*;

public final class LinesAndColumnsTest 
{

    private String intArrayToString(final int[] array)
    {
        final StringBuilder str = new StringBuilder();
        
        for(int i : array)
        {
            str.append(i);
        }
        
        return str.toString();
    }
    
    /**
     * Constructor: <code>(char[], NewlineStyles)</code>
     * 
     * Case: The first argument is null.
     */
    @Test (expected = NullPointerException.class)
    public void testCtor_nullFirstArg()
    {
        new LinesAndColumns(null, NewlineStyles.CR);
    }
    
    /**
     * Constructor: <code>(char[], NewlineStyles)</code>
     * 
     * Case: The second argument is null.
     */
    @Test (expected = NullPointerException.class)
    public void testCtor_nullSecondArg()
    {
        new LinesAndColumns("abc".toCharArray(), null);
    }
    
    /**
     * Constructor: <code>(char[], NewlineStyles)</code>
     * 
     * Case: The newline is unsupported.
     */
    @Test (expected = UnsupportedOperationException.class)
    public void testCtor_unsupportedNewline()
    {
        new LinesAndColumns("abc".toCharArray(), NewlineStyles.UNSUPPORTED);
    }    
    
    /**
     * This test tests the entire class when the newline is a carriage-return.     
     */
    @Test
    public void testClass_newlineCR()
    {
        final String input = "abcd\refg\rhi";
        
        final String lines = "11111222233";
        
        final String columns = "12345123412";
        
        final LinesAndColumns object = new LinesAndColumns(input.toCharArray(), 
                                                           NewlineStyles.CR);
        
        final String linesResult = intArrayToString(object.lineNumbers());
        
        final String columnsResult = intArrayToString(object.columnNumbers());
        
        assertTrue(object.lineNumbers() == object.lineNumbers());
        
        assertTrue(object.columnNumbers() == object.columnNumbers());
        
        assertEquals(lines, linesResult);
        
        assertEquals(columns, columnsResult);
    }
    
    /**
     * This test tests the entire object when the newline is a line-feed.
     */
    @Test
    public void testClass_newlineLF()
    {
        final String input = "abcd\nefg\nhi";
        
        final String lines = "11111222233";
        
        final String columns = "12345123412";
        
        final LinesAndColumns object = new LinesAndColumns(input.toCharArray(), 
                                                           NewlineStyles.LF);
        
        final String linesResult = intArrayToString(object.lineNumbers());
        
        final String columnsResult = intArrayToString(object.columnNumbers());
        
        assertTrue(object.lineNumbers() == object.lineNumbers());
        
        assertTrue(object.columnNumbers() == object.columnNumbers());
        
        assertEquals(lines, linesResult);
        
        assertEquals(columns, columnsResult);
    }    
    
    /**
     * This test tests the entire object when the newline is a carriage-return 
     * followed by a line-feed.
     */
    @Test
    public void testClass_newlineCRLF()
    {
        final String input = "abcd\r\nefg\r\nhi";
        
        final String lines = "1111112222233";
        
        final String columns = "1234561234512";
        
        final LinesAndColumns object = new LinesAndColumns(input.toCharArray(), 
                                                          NewlineStyles.CR_LF);
        
        final String linesResult = intArrayToString(object.lineNumbers());
        
        final String columnsResult = intArrayToString(object.columnNumbers());
        
        assertTrue(object.lineNumbers() == object.lineNumbers());
        
        assertTrue(object.columnNumbers() == object.columnNumbers());
        
        assertEquals(lines, linesResult);
        
        assertEquals(columns, columnsResult);
    }        
}
