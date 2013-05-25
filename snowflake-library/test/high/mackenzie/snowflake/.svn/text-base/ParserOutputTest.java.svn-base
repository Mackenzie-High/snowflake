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

import java.io.Writer;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public final class ParserOutputTest 
{
    
    /**
     * Test: 20130326222115921829
     * 
     * <p>
     * Case: Test everything, during a failed match attempt. 
     * </p>
     */
     @Test
     public void test20130326222115921829()
     {
         System.out.println("Test: 20130326222115921829");
         
         final GrammarBuilder g = new GrammarBuilder();
         g.setRoot("root");
         
         g.sequence("root", "x", "y", "z");
         g.str("x", "ABC");
         g.str("y", "DEF");
         g.str("z", "GHI");
         
         assertEquals(1024, g.build().maximum_record_count);
         
         g.setTraceCount(27);
         
         final Grammar grammar = g.build();
         
         assertEquals(27, grammar.maximum_record_count);
         
         final ParserOutput output = grammar.newParser().parse("ABCDEFJHI");
         
         assertFalse(output.success());
         
         assertEquals(null, output.parseTree());
         
         assertEquals(6, output.lengthOfConsumption());
        
         final Trace tracer = output.trace();
         
         assertEquals(1 + 2 + 2 + 2 + 1, tracer.callCount());
         
         final List<TraceElement> elements = tracer.elements();
         
         int i = 0;
         
         assertEquals("root", elements.get(i).name());
         assertEquals(0, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("x", elements.get(i).name());
         assertEquals(0, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("x", elements.get(i).name());
         assertEquals(3, elements.get(i).position());
         assertEquals(TraceElementReason.SUCCESS, elements.get(i).reason());
         ++i;
         
         assertEquals("y", elements.get(i).name());
         assertEquals(3, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("y", elements.get(i).name());
         assertEquals(6, elements.get(i).position());
         assertEquals(TraceElementReason.SUCCESS, elements.get(i).reason());
         ++i;
         
         assertEquals("z", elements.get(i).name());
         assertEquals(6, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("z", elements.get(i).name());
         assertEquals(7, elements.get(i).position());
         assertEquals(TraceElementReason.FAIL, elements.get(i).reason());
         ++i;
         
         assertEquals("root", elements.get(i).name());
         assertEquals(6, elements.get(i).position());
         assertEquals(TraceElementReason.FAIL, elements.get(i).reason());
         ++i;
     }
     
    /**
     * Test: 20130326223130010422
     *
     * <p>
     * Case: Test everything, during a successful match attempt. 
     * </p>
     */
     @Test
     public void test20130326223130010422()
     {
         System.out.println("Test: 20130326223130010422");
         
         final GrammarBuilder g = new GrammarBuilder();
         g.setRoot("root");
         
         g.sequence("root", "x", "y", "z");
         g.str("x", "ABC");
         g.str("y", "DEF");
         g.str("z", "GHI");
         
         assertEquals(1024, g.build().maximum_record_count);
         
         g.setTraceCount(27);
         
         final Grammar grammar = g.build();
         
         assertEquals(27, grammar.maximum_record_count);
         
         final ParserOutput output = grammar.newParser().parse("ABCDEFGHI");
         
         assertTrue(output.success());
         
         assertFalse(null == output.parseTree());
         
         assertEquals(8, output.lengthOfConsumption());
         
         final Trace tracer = output.trace();
         
         assertEquals(1 + 2 + 2 + 2 + 1, tracer.callCount());
         
         final List<TraceElement> elements = tracer.elements();
         
         int i = 0;
         
         assertEquals("root", elements.get(i).name());
         assertEquals(0, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("x", elements.get(i).name());
         assertEquals(0, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("x", elements.get(i).name());
         assertEquals(3, elements.get(i).position());
         assertEquals(TraceElementReason.SUCCESS, elements.get(i).reason());
         ++i;
         
         assertEquals("y", elements.get(i).name());
         assertEquals(3, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("y", elements.get(i).name());
         assertEquals(6, elements.get(i).position());
         assertEquals(TraceElementReason.SUCCESS, elements.get(i).reason());
         ++i;
         
         assertEquals("z", elements.get(i).name());
         assertEquals(6, elements.get(i).position());
         assertEquals(TraceElementReason.BEGIN, elements.get(i).reason());
         ++i;
         
         assertEquals("z", elements.get(i).name());
         assertEquals(9, elements.get(i).position());
         assertEquals(TraceElementReason.SUCCESS, elements.get(i).reason());
         ++i;
         
         assertEquals("root", elements.get(i).name());
         assertEquals(9, elements.get(i).position());
         assertEquals(TraceElementReason.SUCCESS, elements.get(i).reason());
         ++i;
     }    
     
    /**
     * Test: 20130514113254896018
     * 
     * <p>
     * Method: <code>print(PrintWriter, boolean, boolean, boolean)</code>
     * </p>
     *
     * <p>
     * Case: parsing succeeded
     * </p>
     */
     @Test
     public void test20130514113254896018()
     {
         System.out.println("Test: 20130514113254896018");
         
         final char[] input = "ABC".toCharArray();
         
         final Tracer tracer = new Tracer(3);
         tracer.add(new RuleString("rule1"), 3, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule2"), 5, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule3"), 7, TraceElementReason.BEGIN);
         
         final ITreeNode parse_tree = new TreeNode();
         
         final int farthest_match = 589;
         
         final ParserOutput po = new ParserOutput(input, parse_tree, tracer, farthest_match);
         
         final Writer writer = new StringWriter();
         final PrintWriter stdout = new PrintWriter(writer);
         
         po.print(stdout, true, true, true);
         
         stdout.flush();
         
         final String actual = writer.toString();
         
         final String expected = "Parsing Succeeded!\n"
                 .replace("\n", NewlineStyles.fromSystem().newline());
         
         assertEquals(expected, actual);
     }
     
    /**
     * Test: 20130514114008854311
     * 
     * <p>
     * Method: <code>print(PrintWriter, boolean, boolean, boolean)</code>
     * </p>
     *
     * <p>
     * Case: parsing failed (estimate = false, exact = false, trace = false)
     * </p>
     */
     @Test
     public void test20130514114008854311()
     {
         System.out.println("Test: 20130514114008854311");
         
         final char[] input = "ABC".toCharArray();
         
         final Tracer tracer = new Tracer(3);
         tracer.add(new RuleString("rule1"), 3, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule2"), 5, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule3"), 7, TraceElementReason.BEGIN);
         
         final int farthest_match = 589;
         
         final ParserOutput po = new ParserOutput(input, null, tracer, farthest_match);
         
         final Writer writer = new StringWriter();
         final PrintWriter stdout = new PrintWriter(writer);
         
         po.print(stdout, false, false, false);
         
         stdout.flush();
         
         final String actual = writer.toString();
         
         final String expected = "Parsing Failed!\n"
                 .replace("\n", NewlineStyles.fromSystem().newline());
         
         assertEquals(expected, actual);
     }      
     
    /**
     * Test: 20130514113254896138
     * 
     * <p>
     * Method: <code>print(PrintWriter, boolean, boolean, boolean)</code>
     * </p>
     *
     * <p>
     * Case: parsing failed (estimate = true, exact = false, trace = false)
     * </p>
     */
     @Test
     public void test20130514113254896138()
     {
         System.out.println("Test: 20130514113254896138");
         
         final char[] input = "ABC\nDEF\nGHI\nJKL".toCharArray();
         
         final Tracer tracer = new Tracer(3);
         tracer.add(new RuleString("rule1"), 3, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule2"), 5, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule3"), 7, TraceElementReason.BEGIN);
         
         final int farthest_match = 10;
         
         final ParserOutput po = new ParserOutput(input, null, tracer, farthest_match);
         
         final Writer writer = new StringWriter();
         final PrintWriter stdout = new PrintWriter(writer);
         
         po.print(stdout, true, false, false);
         
         stdout.flush();
         
         final String actual = writer.toString();
         
         final String expected = ("Parsing Failed!\n"
                               + "\n"
                               + "Syntax Error Position (Approximate):\n"
                               + "  Line: #3\n"
                               + "  Column: #3\n")
                               .replace("\n", NewlineStyles.fromSystem().newline());
         
         assertEquals(expected, actual);
     }
     
    /**
     * Test: 20130514113254896189
     * 
     * <p>
     * Method: <code>print(PrintWriter, boolean, boolean, boolean)</code>
     * </p>
     *
     * <p>
     * Case: parsing failed (estimate = false, exact = true, trace = false)
     * </p>
     */
     @Test
     public void test20130514113254896189()
     {
         System.out.println("Test: 20130514113254896189");
         
         final char[] input = "ABC\nDEF\nGHI\nJKL".toCharArray();
         
         final Tracer tracer = new Tracer(3);
         tracer.add(new RuleString("rule1"), 3, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule2"), 5, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule3"), 7, TraceElementReason.BEGIN);
         
         final int farthest_match = 10;
         
         final ParserOutput po = new ParserOutput(input, null, tracer, farthest_match);
         
         final Writer writer = new StringWriter();
         final PrintWriter stdout = new PrintWriter(writer);
         
         po.print(stdout, false, true, false);
         
         stdout.flush();
         
         final String actual = writer.toString();
         
         final String expected = ("Parsing Failed!\n"
                               + "\n"
                               + "Length of Consumption: 10\n")
                               .replace("\n", NewlineStyles.fromSystem().newline());
         
         assertEquals(expected, actual);
     }
     
    /**
     * Test: 20130514113254896230
     * 
     * <p>
     * Method: <code>print(PrintWriter, boolean, boolean, boolean)</code>
     * </p>
     *
     * <p>
     * Case: parsing failed (estimate = false, exact = false, trace = true)
     * </p>
     */
     @Test
     public void test20130514113254896230()
     {
         System.out.println("Test: 20130514113254896230");
         
         final char[] input = "ABC\nDEF\nGHI\nJKL".toCharArray();
         
         final Tracer tracer = new Tracer(3);
         tracer.add(new RuleString("rule1"), 3, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule2"), 5, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule3"), 7, TraceElementReason.BEGIN);
         
         final int farthest_match = 10;
         
         final ParserOutput po = new ParserOutput(input, null, tracer, farthest_match);
         
         final Writer writer = new StringWriter();
         final PrintWriter stdout = new PrintWriter(writer);
         
         po.print(stdout, false, false, true);
         
         stdout.flush();
         
         final String actual = writer.toString();
         
         final String expected = ("Parsing Failed!\n"
                               + "\n"
                               + "Tracer Records (3 of 3) (Most Recent Call Last):\n"
                               + "    [0] rule1 => BEGIN @ 3\n"
                               + "    [1] rule2 => BEGIN @ 5\n"
                               + "    [2] rule3 => BEGIN @ 7\n")
                               .replace("\n", NewlineStyles.fromSystem().newline());
         
         assertEquals(expected, actual);
     }
     
    /**
     * Test: 20130514113254896268
     * 
     * <p>
     * Method: <code>print(PrintWriter, boolean, boolean, boolean)</code>
     * </p>
     *
     * <p>
     * Case: parsing failed (estimate = true, exact = true, trace = true)
     * </p>
     */
     @Test
     public void test20130514113254896268()
     {
         System.out.println("Test: 20130514113254896268");
         
         final char[] input = "".toCharArray();
         
         final Tracer tracer = new Tracer(3);
         tracer.add(new RuleString("rule1"), 3, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule2"), 5, TraceElementReason.BEGIN);
         tracer.add(new RuleString("rule3"), 7, TraceElementReason.BEGIN);
         
         final int farthest_match = 0;
         
         final ParserOutput po = new ParserOutput(input, null, tracer, farthest_match);
         
         final Writer writer = new StringWriter();
         final PrintWriter stdout = new PrintWriter(writer);
         
         po.print(stdout, true, true, true);
         
         stdout.flush();
         
         final String actual = writer.toString();
         
         final String expected = ("Parsing Failed!\n"
                               + "\n"
                               + "Syntax Error Position (Approximate):\n"
                               + "  Line: #1\n"
                               + "  Column: #1\n"                 
                               + "\n"
                               + "Length of Consumption: 0\n"                 
                               + "\n"
                               + "Tracer Records (3 of 3) (Most Recent Call Last):\n"
                               + "    [0] rule1 => BEGIN @ 3\n"
                               + "    [1] rule2 => BEGIN @ 5\n"
                               + "    [2] rule3 => BEGIN @ 7\n")
                               .replace("\n", NewlineStyles.fromSystem().newline());
         
         assertEquals(expected, actual);
     }
}
