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

import org.junit.Test;
import static org.junit.Assert.*;

public final class ParserTest 
{  
    
    /**
     * Test: 20130522031703780167
     * 
     * <p>
     * Method: <code>getTrace()</code>
     * </p>
     *
     * <p>
     * Case: Call the method before a parsing attempt was made.
     * </p>
     */
     @Test(expected = IllegalStateException.class)
     public void test20130522031703780167()
     {
         System.out.println("Test: 20130522031703780167");
         
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("root", "abc");
        
        g.setRoot("root");
        
        final Parser p = g.build().newParser();
        
        p.getTrace();
     }
     
    /**
     * Test: 20130522031703780272
     * 
     * <p>
     * Method: <code>getTrace()</code>
     * </p>
     *
     * <p>
     * Case: Call the method after a match attempt that threw a stack-overflow error.
     * </p>
     */
     @Test
     public void test20130522031703780272()
     {
         System.out.println("Test: 20130522031703780272");
         
        final GrammarBuilder g = new GrammarBuilder();
        
        g.sequence("root", "root", "moo");
        g.str("moo", "x");
        
        g.setRoot("root");
        
        final Parser p = g.build().newParser();
        
        try 
        {
            p.parse("xxx");
        }
        catch(StackOverflowError ex)
        {
            // Do nothing, because the exception is expected and desired. 
        }
        
        // Assert that the trace is present and that information was recorded.
        // The tracer itself is tested elsewhere. 
        assertTrue(p.getTrace().callCount() > 100);
     }
     
    /**
     * Test: 20130522031703780320
     * 
     * <p>
     * Method: <code>parse(String)</code>
     * </p>
     *
     * <p>
     * Case: null argument
     * </p>
     */
     @Test(expected = NullPointerException.class)
     public void test20130522031703780320()
     {
         System.out.println("Test: 20130522031703780320");
         
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("root", "abc");
        
        g.setRoot("root");
        
        final Parser p = g.build().newParser();
        
        p.parse((char[]) null);
     }    
}
