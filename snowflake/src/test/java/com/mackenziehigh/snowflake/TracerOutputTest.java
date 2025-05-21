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

import java.io.StringWriter;
import java.io.PrintWriter;
import org.junit.Test;
import static org.junit.Assert.*;

public final class TracerOutputTest 
{
    private final String NEWLINE = System.getProperty("line.separator");
    
    /**
     * Test: 20130326220709608518
     * 
     * <p>
     * Method: <code>print(PrintWriter)</code>
     * </p>
     *
     * <p>
     * Case: zero elements
     * </p>
     */
     @Test
     public void test20130326220709608518()
     {
         System.out.println("Test: 20130326220709608518");
         
         final StringWriter writer = new StringWriter();
         
         final PrintWriter out = new PrintWriter(writer);
         
         final Tracer object = new Tracer(3);

         object.output().print(out);
        
         final String expected = "Tracer Records (0 of 0) (Most Recent Call Last):\n"
                 .replace("\n", NewlineStyles.fromSystem().newline());
        
         final String actual = writer.toString();
        
         assertEquals(expected, actual);
     }
    
    /**
     * Test: 20130326214823757510
     * 
     * <p>
     * Method: <code>print(PrintWriter)</code>
     * </p>
     *
     * <p>
     * Case: multiple elements
     * </p>
     */
     @Test
     public void test20130326214823757510()
     {
         System.out.println("Test: 20130326214823757510");
         
         final StringWriter writer = new StringWriter();
         
         final PrintWriter out = new PrintWriter(writer);
         
         final Tracer object = new Tracer(3);
        
         object.add(new RuleString("rule1"), 100, TraceElementReason.BEGIN);
         object.add(new RuleString("rule2"), 200, TraceElementReason.BEGIN);
         object.add(new RuleString("rule3"), 300, TraceElementReason.BEGIN);
        
         object.output().print(out);
        
         final String expected = ("Tracer Records (3 of 3) (Most Recent Call Last):\n"
                               + "    [0] rule1 => BEGIN @ 100\n"
                               + "    [1] rule2 => BEGIN @ 200\n"
                               + "    [2] rule3 => BEGIN @ 300\n")
                               .replace("\n", NewlineStyles.fromSystem().newline());
         
         final String actual = writer.toString();
        
         assertEquals(expected, actual);
     }
     
    /**
     * Test: 20130326220709608616
     * 
     * <p>
     * Method: <code>print(PrintWriter)</code>
     * </p>
     *
     * <p>
     * Case: The total number of insertions differs from the actual number of elements.
     * </p>
     */
     @Test
     public void test20130326220709608616()
     {
         System.out.println("Test: 20130326220709608616");
         
         final StringWriter writer = new StringWriter();
         
         final PrintWriter out = new PrintWriter(writer);
         
         final Tracer object = new Tracer(2);
        
         object.add(new RuleString("rule1"), 100, TraceElementReason.BEGIN);
         object.add(new RuleString("rule2"), 200, TraceElementReason.BEGIN);
         object.add(new RuleString("rule3"), 300, TraceElementReason.BEGIN);
         object.add(new RuleString("rule4"), 400, TraceElementReason.BEGIN);

         object.output().print(out);
        
         final String expected = ("Tracer Records (2 of 4) (Most Recent Call Last):\n"
                               + "    [0] rule3 => BEGIN @ 300\n"
                               + "    [1] rule4 => BEGIN @ 400\n")
                               .replace("\n", NewlineStyles.fromSystem().newline());
         
         final String actual = writer.toString();
        
         assertEquals(expected, actual);
     }
}
