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

package high.mackenzie.snowflake.parsergen;

import high.mackenzie.snowflake.ParserOutput;
import high.mackenzie.snowflake.parsergen.ICodeGenerator;
import high.mackenzie.snowflake.parsergen.GrammarVisitor;
import high.mackenzie.snowflake.parsergen.GrammarParser;
import high.mackenzie.snowflake.parsergen.CodeGeneratorForJava;
import org.junit.Test;
import static org.junit.Assert.*;
import static high.mackenzie.snowflake.parsergen.TestUtils.*;

public final class GrammarParserTest 
{
    private ICodeGenerator parse(final String grammar, 
                                 final boolean default_root)
    {
        final ICodeGenerator code_generator = new CodeGeneratorForJava();
        
        final GrammarParser parser = new GrammarParser();
        
        final ParserOutput output = parser.parse(grammar.replace("''", "\""));
        
        assertTrue(output.success());
        
        final GrammarVisitor visitor = new GrammarVisitor(code_generator);
        
        assertTrue(visitor.operands.isEmpty());
        
        visitor.visit(output.parseTree());
        
        if(default_root) { code_generator.setRoot("root"); }
        
        return code_generator;
    }
    
    private ICodeGenerator parse(final String grammar) { return parse(grammar, true); }
    
    /**
     * Test: 20130517195010027607
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named string rule
     * </p>
     */
     @Test
     public void test20130517195010027607()
     {
         System.out.println("Test: 20130517195010027607");
         
         String parser;
         
         // Case #1: zero-width string
         
         parser = parse(" root = \"\" ; ").getParserFile();
         
         check(parser, "g.str(''root'', '''');");
         
         // Case #2: single-character string
         
         parser = parse(" root = \"X\" ; ").getParserFile();
         
         check(parser, "g.str(''root'', ''X'');");
         
         // Case #3: multi-character string
         
         parser = parse(" root = \"XYZ\" ; ").getParserFile();
         
         check(parser, "g.str(''root'', ''XYZ'');");
     }
     
    /**
     * Test: 20130517195010027729
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named character rule
     * </p>
     */
     @Test
     public void test20130517195010027729()
     {
         System.out.println("Test: 20130517195010027729");
         
         final String parser = parse(" root = 'A' - 'Z' ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 90);");
         
         check(parser, "g.chr(''root'', ''@class0'');");    
     }
     
    /**
     * Test: 20130517195010027776
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named sequence rule
     * </p>
     */
     @Test
     public void test20130517195010027776()
     {
         System.out.println("Test: 20130517195010027776");
         
         String parser;
         
         // Case #1: alias 
         
         parser = parse(" root = xoo ; "
                      + " xoo = ''1234567'' ; ").getParserFile();
         
         check(parser, "g.sequence(''root'', ''xoo'');");
         check(parser, "g.str(''xoo'', ''1234567'');");
         
         // Case #2: binary sequence
    
         parser = parse(" root = xoo , yoo ; "
                      + " xoo = ''123'' ; "
                      + " yoo = ''456'' ; ").getParserFile();
         
         check(parser, "g.sequence(''root'', ''xoo'', ''yoo'');");
         check(parser, "g.str(''xoo'', ''123'');");
         check(parser, "g.str(''yoo'', ''456'');");
         
         // Case #3: multi-element seqence
         
         parser = parse(" root = xoo , yoo , zoo ; "
                      + " xoo = ''123'' ; "
                      + " yoo = ''456'' ; "
                      + " zoo = ''789'' ; ").getParserFile();
         
         check(parser, "g.sequence(''root'', ''xoo'', ''yoo'', ''zoo'');");
         check(parser, "g.str(''xoo'', ''123'');");
         check(parser, "g.str(''yoo'', ''456'');");
         check(parser, "g.str(''zoo'', ''789'');");
         
         // Case #4: anonymous sequence elements
         
         parser = parse(" root = ( ''12'' , ''34'' ) , (''56'', ''78'') ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''12'');");
         check(parser, "g.str(''@1'', ''34'');");
         check(parser, "g.sequence(''@2'', ''@0'', ''@1'');");
         
         check(parser, "g.str(''@3'', ''56'');");
         check(parser, "g.str(''@4'', ''78'');");
         check(parser, "g.sequence(''@5'', ''@3'', ''@4'');");
         
         check(parser, "g.sequence(''root'', ''@2'', ''@5'');");
     }
     
    /**
     * Test: 20130517195010027816
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named sequence-DLR rule
     * </p>
     */
     @Test
     public void test20130517195010027816()
     {
         System.out.println("Test: 20130517195010027816");
         
         String parser;
         
         // Case #1: one shared element
         
         parser = parse(" root = ''123'' : ''456'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.str(''@1'', ''456'');");
         check(parser, "g.sequenceDLR(''root'', ''@0'', ''@1'');");
         
         // Case #2: two shared elements
         
         parser = parse(" root = ''123'' : ''456'' , ''789'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.str(''@1'', ''456'');");
         check(parser, "g.str(''@2'', ''789'');");
         check(parser, "g.sequenceDLR(''root'', ''@0'', ''@1'', ''@2'');");
         
         // Case #3: multiple shared elements
         
         parser = parse(" root = ''12'' : ''34'' , ''56'' , ''78'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''12'');");
         check(parser, "g.str(''@1'', ''34'');");
         check(parser, "g.str(''@2'', ''56'');");
         check(parser, "g.str(''@3'', ''78'');");
         check(parser, "g.sequenceDLR(''root'', ''@0'', ''@1'', ''@2'', ''@3'');");
     }
     
    /**
     * Test: 20130517195010027851
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named choice rule
     * </p>
     */
     @Test
     public void test20130517195010027851()
     {
         System.out.println("Test: 20130517195010027851");
         
         String parser;

         // Case #1: binary choice
    
         parser = parse(" root = xoo / yoo ; "
                      + " xoo = ''123'' ; "
                      + " yoo = ''456'' ; ").getParserFile();
         
         check(parser, "g.choose(''root'', ''xoo'', ''yoo'');");
         check(parser, "g.str(''xoo'', ''123'');");
         check(parser, "g.str(''yoo'', ''456'');");
         
         // Case #2: multi-option choice
         
         parser = parse(" root = xoo / yoo / zoo ; "
                      + " xoo = ''123'' ; "
                      + " yoo = ''456'' ; "
                      + " zoo = ''789'' ; ").getParserFile();
         
         check(parser, "g.choose(''root'', ''xoo'', ''yoo'', ''zoo'');");
         check(parser, "g.str(''xoo'', ''123'');");
         check(parser, "g.str(''yoo'', ''456'');");
         check(parser, "g.str(''zoo'', ''789'');");
         
         // Case #3: anonymous sequence elements
         
         parser = parse(" root = ( ''12'' / ''34'' ) / (''56'' / ''78'') ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''12'');");
         check(parser, "g.str(''@1'', ''34'');");
         check(parser, "g.choose(''@2'', ''@0'', ''@1'');");
         
         check(parser, "g.str(''@3'', ''56'');");
         check(parser, "g.str(''@4'', ''78'');");
         check(parser, "g.choose(''@5'', ''@3'', ''@4'');");
         
         check(parser, "g.choose(''root'', ''@2'', ''@5'');");
     }
     
    /**
     * Test: 20130517195010027888
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named option rule
     * </p>
     */
     @Test
     public void test20130517195010027888()
     {
         System.out.println("Test: 20130517195010027888");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = moo ? ; "
                      + " moo = ''123'' ; ").getParserFile();
         
         check(parser, "g.repeat(''root'', ''moo'', 0, 1);");
         check(parser, "g.str(''moo'', ''123'');");
         
         // Case #2: The item is an anonymous rule.
         
         parser = parse(" root = ''123'' ? ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.repeat(''root'', ''@0'', 0, 1);");
     }
     
    /**
     * Test: 20130517195010027923
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named star rule
     * </p>
     */
     @Test
     public void test20130517195010027923()
     {
         System.out.println("Test: 20130517195010027923");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = moo * ; "
                      + " moo = ''123'' ; ").getParserFile();
         
         check(parser, "g.repeat(''root'', ''moo'', 0, 2147483647);");
         check(parser, "g.str(''moo'', ''123'');");
         
         // Case #2: The item is an anonymous rule.
         
         parser = parse(" root = ''123'' * ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.repeat(''root'', ''@0'', 0, 2147483647);");
     }
     
    /**
     * Test: 20130517195010027956
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named plus rule
     * </p>
     */
     @Test
     public void test20130517195010027956()
     {
         System.out.println("Test: 20130517195010027956");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = moo + ; "
                      + " moo = ''123'' ; ").getParserFile();
         
         check(parser, "g.repeat(''root'', ''moo'', 1, 2147483647);");
         check(parser, "g.str(''moo'', ''123'');");
         
         // Case #2: The item is an anonymous rule.
         
         parser = parse(" root = ''123'' + ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.repeat(''root'', ''@0'', 1, 2147483647);");
     }
     
    /**
     * Test: 20130517195010027990
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named repetition rule
     * </p>
     */
     @Test
     public void test20130517195010027990()
     {
         System.out.println("Test: 20130517195010027990");
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = moo { 23 , 67 } ; "
                      + " moo = ''123'' ; ").getParserFile();
         
         check(parser, "g.repeat(''root'', ''moo'', 23, 67);");
         check(parser, "g.str(''moo'', ''123'');");
         
         // Case #2: The item is an anonymous rule.
         
         parser = parse(" root = ''123'' { 23 , 67 } ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.repeat(''root'', ''@0'', 23, 67);");
     }
     
    /**
     * Test: 20130517195010028023
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named and-rule
     * </p>
     */
     @Test
     public void test20130517195010028023()
     {
         System.out.println("Test: 20130517195010028023");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = & moo ; "
                      + " moo = ''123'' ; ").getParserFile();
         
         check(parser, "g.and(''root'', ''moo'');");
         check(parser, "g.str(''moo'', ''123'');");
         
         // Case #2: The item is an anonymous rule.
         
         parser = parse(" root = & ''123'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.and(''root'', ''@0'');");
     }
     
    /**
     * Test: 20130517195010028056
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: named not-rule
     * </p>
     */
     @Test
     public void test20130517195010028056()
     {
         System.out.println("Test: 20130517195010028056");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = ! moo ; "
                      + " moo = ''123'' ; ").getParserFile();
         
         check(parser, "g.not(''root'', ''moo'');");
         check(parser, "g.str(''moo'', ''123'');");
         
         // Case #2: The item is an anonymous rule.
         
         parser = parse(" root = ! ''123'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''123'');");
         check(parser, "g.not(''root'', ''@0'');");
     }
     
    /**
     * Test: 20130517195010028090
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous string rule
     * </p>
     */
     @Test
     public void test20130517195010028090()
     {
         System.out.println("Test: 20130517195010028090");
         
         String parser;
         
         // Case #1: zero-width string
         
         parser = parse(" root = \"\" , \"\" ; ").getParserFile();
         
         check(parser, "g.str(''@0'', '''');");
         check(parser, "g.str(''@1'', '''');");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         
         // Case #2: single-character string
         
         parser = parse(" root = \"X\" , \"Y\" ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''X'');");
         check(parser, "g.str(''@1'', ''Y'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         
         // Case #3: multi-character string
         
         parser = parse(" root = \"ABC\" , \"XYZ\" ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''ABC'');");
         check(parser, "g.str(''@1'', ''XYZ'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
     }
     
    /**
     * Test: 20130517195010028123
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous character rule
     * </p>
     */
     @Test
     public void test20130517195010028123()
     {
         System.out.println("Test: 20130517195010028123");
         
         final String parser = parse(" root = ''Venus'' , 'A' - 'Z' ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 90);");
         
         check(parser, "g.str(''@0'', ''Venus'');");
         check(parser, "g.chr(''@1'', ''@class0'');");    
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
     }
     
    /**
     * Test: 20130517195010028172
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous sequence rule
     * </p>
     */
     @Test
     public void test20130517195010028172()
     {
         System.out.println("Test: 20130517195010028172");
         
         String parser;
         
         // Case #1: binary sequence
         
         parser = parse(" root = ''2995'' , ( ''123'' , ''456'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''123'');");
         check(parser, "g.str(''@2'', ''456'');");
         check(parser, "g.sequence(''@3'', ''@1'', ''@2'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@3'');");
         
         // Case #2: multi-element sequence
         
         parser = parse(" root = ''2995'' , ( ''123'' , ''456'' , ''789'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''123'');");
         check(parser, "g.str(''@2'', ''456'');");
         check(parser, "g.str(''@3'', ''789'');");
         check(parser, "g.sequence(''@4'', ''@1'', ''@2'', ''@3'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@4'');");
     }
     
    /**
     * Test: 20130517195010028207
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous sequence-DLR rule
     * </p>
     */
     @Test
     public void test20130517195010028207()
     {
         System.out.println("Test: 20130517195010028207");
         
         String parser;
         
         // Case #1: one shared element
         
         parser = parse(" root = ''2995'' , ( ''123'' : ''456'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''123'');");
         check(parser, "g.str(''@2'', ''456'');");
         check(parser, "g.sequenceDLR(''@3'', ''@1'', ''@2'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@3'');");
         
         // Case #2: two shared elements
         
         parser = parse(" root = ''2995'' , ( ''123'' : ''456'' , ''789'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''123'');");
         check(parser, "g.str(''@2'', ''456'');");
         check(parser, "g.str(''@3'', ''789'');");
         check(parser, "g.sequenceDLR(''@4'', ''@1'', ''@2'', ''@3'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@4'');");
         
         // Case #3: multiple shared elements
         
         parser = parse(" root = ''X'' , ( ''12'' : ''34'' , ''56'' , ''78'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''X'');");
         check(parser, "g.str(''@1'', ''12'');");
         check(parser, "g.str(''@2'', ''34'');");
         check(parser, "g.str(''@3'', ''56'');");
         check(parser, "g.str(''@4'', ''78'');");
         check(parser, "g.sequenceDLR(''@5'', ''@1'', ''@2'', ''@3'', ''@4'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@5'');");
     }
     
    /**
     * Test: 20130517195010028241
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous choice rule
     * </p>
     */
     @Test
     public void test20130517195010028241()
     {
         System.out.println("Test: 20130517195010028241");
         
         String parser;
         
         // Case #1: binary choice
         
         parser = parse(" root = ''2995'' , ( ''123'' / ''456'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''123'');");
         check(parser, "g.str(''@2'', ''456'');");
         check(parser, "g.choose(''@3'', ''@1'', ''@2'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@3'');");
         
         // Case #2: multi-element sequence
         
         parser = parse(" root = ''2995'' , ( ''123'' / ''456'' / ''789'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''123'');");
         check(parser, "g.str(''@2'', ''456'');");
         check(parser, "g.str(''@3'', ''789'');");
         check(parser, "g.choose(''@4'', ''@1'', ''@2'', ''@3'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@4'');");
     }
     
    /**
     * Test: 20130517195010028274
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous option rule
     * </p>
     */
     @Test
     public void test20130517195010028274()
     {
         System.out.println("Test: 20130517195010028274");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = ''2995'' , ( moo ? ) ; "
                      + " moo = ''117'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.repeat(''@1'', ''moo'', 0, 1);");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         check(parser, "g.str(''moo'', ''117'');");
         
         // Case #2: The item is an anonymous rule. 
         
         parser = parse(" root = ''2995'' , ( ''117'' ? ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''117'');");
         check(parser, "g.repeat(''@2'', ''@1'', 0, 1);");
         check(parser, "g.sequence(''root'', ''@0'', ''@2'');");
     }
     
    /**
     * Test: 20130517195010028308
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous star rule
     * </p>
     */
     @Test
     public void test20130517195010028308()
     {
         System.out.println("Test: 20130517195010028308");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = ''2995'' , ( moo * ) ; "
                      + " moo = ''117'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.repeat(''@1'', ''moo'', 0, 2147483647);");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         check(parser, "g.str(''moo'', ''117'');");
         
         // Case #2: The item is an anonymous rule. 
         
         parser = parse(" root = ''2995'' , ( ''117'' * ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''117'');");
         check(parser, "g.repeat(''@2'', ''@1'', 0, 2147483647);");
         check(parser, "g.sequence(''root'', ''@0'', ''@2'');");
     }
     
    /**
     * Test: 20130517195010028341
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous plus rule
     * </p>
     */
     @Test
     public void test20130517195010028341()
     {
         System.out.println("Test: 20130517195010028341");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = ''2995'' , ( moo + ) ; "
                      + " moo = ''117'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.repeat(''@1'', ''moo'', 1, 2147483647);");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         check(parser, "g.str(''moo'', ''117'');");
         
         // Case #2: The item is an anonymous rule. 
         
         parser = parse(" root = ''2995'' , ( ''117'' + ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''117'');");
         check(parser, "g.repeat(''@2'', ''@1'', 1, 2147483647);");
         check(parser, "g.sequence(''root'', ''@0'', ''@2'');");
     }
     
    /**
     * Test: 20130517195010028379
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous repetition rule
     * </p>
     */
     @Test
     public void test20130517195010028379()
     {
         System.out.println("Test: 20130517195010028379");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = ''2995'' , ( moo { 23 , 45 } ) ; "
                      + " moo = ''117'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.repeat(''@1'', ''moo'', 23, 45);");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         check(parser, "g.str(''moo'', ''117'');");
         
         // Case #2: The item is an anonymous rule. 
         
         parser = parse(" root = ''2995'' , ( ''117'' { 23 , 45 } ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''117'');");
         check(parser, "g.repeat(''@2'', ''@1'', 23, 45);");
         check(parser, "g.sequence(''root'', ''@0'', ''@2'');");
     }
     
    /**
     * Test: 20130517202342199192
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous and-rule
     * </p>
     */
     @Test
     public void test20130517202342199192()
     {
         System.out.println("Test: 20130517202342199192");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = ''2995'' , ( & moo ) ; "
                      + " moo = ''117'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.and(''@1'', ''moo'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         check(parser, "g.str(''moo'', ''117'');");
         
         // Case #2: The item is an anonymous rule. 
         
         parser = parse(" root = ''2995'' , ( & ''117'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''117'');");
         check(parser, "g.and(''@2'', ''@1'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@2'');");
     }
     
    /**
     * Test: 20130517202342199313
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: anonymous not-rule
     * </p>
     */
     @Test
     public void test20130517202342199313()
     {
         System.out.println("Test: 20130517202342199313");
         
         String parser;
         
         // Case #1: The item is a name.
         
         parser = parse(" root = ''2995'' , ( ! moo ) ; "
                      + " moo = ''117'' ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.not(''@1'', ''moo'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@1'');");
         check(parser, "g.str(''moo'', ''117'');");
         
         // Case #2: The item is an anonymous rule. 
         
         parser = parse(" root = ''2995'' , ( ! ''117'' ) ; ").getParserFile();
         
         check(parser, "g.str(''@0'', ''2995'');");
         check(parser, "g.str(''@1'', ''117'');");
         check(parser, "g.not(''@2'', ''@1'');");
         check(parser, "g.sequence(''root'', ''@0'', ''@2'');");
     }
     
    /**
     * Test: 20130517202342199363
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: single-character range character-class
     * </p>
     */
     @Test
     public void test20130517202342199363()
     {
         System.out.println("Test: 20130517202342199363");
         
         String parser;
         
         // Case #1: literal character
         
         parser = parse(" root = 'K' ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 75, (char) 75);");
         check(parser, "g.chr(''root'', ''@class0'');");
         
         // Case #2: numeric character
         
         parser = parse(" root = 31C ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 31, (char) 31);");
         check(parser, "g.chr(''root'', ''@class0'');");
     }
     
    /**
     * Test: 20130517202342199406
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: multi-character range character-class
     * </p>
     */
     @Test
     public void test20130517202342199406()
     {
         System.out.println("Test: 20130517202342199406");
         
         String parser;
         
         // Case #1: literal characters
         
         parser = parse(" root = 'A' - 'Z' ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 90);");
         check(parser, "g.chr(''root'', ''@class0'');");
         
         // Case #2: numeric characters
         
         parser = parse(" root = 20C - 35C ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 20, (char) 35);");
         check(parser, "g.chr(''root'', ''@class0'');");
         
         // Case #3: minimum and maximum
         
         parser = parse(" root = MIN - MAX ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 0, (char) 65535);");
         check(parser, "g.chr(''root'', ''@class0'');");
     }
     
    /**
     * Test: 20130517202342199444
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: combination character-class
     * </p>
     */
     @Test
     public void test20130517202342199444()
     {
         System.out.println("Test: 20130517202342199444");
         
         String parser;
         
         // Case #1: single-element combination
         
         parser = parse(" root = [ 'A' ] ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 65);");
         check(parser, "g.combine(''@class1'', ''@class0'');");
         check(parser, "g.chr(''root'', ''@class1'');");
         
         // Case #2: multi-element combination
         
         parser = parse(" root = [ 'A' 'B' 'C' ] ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 65);");
         check(parser, "g.range(''@class1'', (char) 66, (char) 66);");
         check(parser, "g.range(''@class2'', (char) 67, (char) 67);");
         check(parser, "g.combine(''@class3'', ''@class0'', ''@class1'', ''@class2'');");
         check(parser, "g.chr(''root'', ''@class3'');");
         
         // Case #3: nested combination-class
         
         parser = parse(" root = [ ['A' 'B'] ['C' 'D'] ] ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 65);"); // A
         check(parser, "g.range(''@class1'', (char) 66, (char) 66);"); // B
         check(parser, "g.combine(''@class2'', ''@class0'', ''@class1'');");
         
         check(parser, "g.range(''@class3'', (char) 67, (char) 67);"); // C
         check(parser, "g.range(''@class4'', (char) 68, (char) 68);"); // D
         check(parser, "g.combine(''@class5'', ''@class3'', ''@class4'');");
         
         check(parser, "g.combine(''@class6'', ''@class2'', ''@class5'');");
         
         check(parser, "g.chr(''root'', ''@class6'');");         
     }
     
    /**
     * Test: 20130517202342199484
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: negation character-class
     * </p>
     */
     @Test
     public void test20130517202342199484()
     {
         System.out.println("Test: 20130517202342199484");
         
         String parser;
         
         // Case #1: single element
         
         parser = parse(" root = [ ^ 'A' ] ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 65);");
         check(parser, "g.combine(''@class1'', ''@class0'');");
         check(parser, "g.negate(''@class2'', ''@class1'');");
         check(parser, "g.chr(''root'', ''@class2'');");
         
         // Case #2: multiple elements
         
         parser = parse(" root = [ ^ 'A' 'B' 'C' ] ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 65);");
         check(parser, "g.range(''@class1'', (char) 66, (char) 66);");
         check(parser, "g.range(''@class2'', (char) 67, (char) 67);");
         check(parser, "g.combine(''@class3'', ''@class0'', ''@class1'', ''@class2'');");
         check(parser, "g.negate(''@class4'', ''@class3'');");
         check(parser, "g.chr(''root'', ''@class4'');");
         
         // Case #3: nested negation-class
         
         parser = parse(" root = [ ^ [^ 'A' 'B'] [^ 'C' 'D'] ] ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 65);"); // A
         check(parser, "g.range(''@class1'', (char) 66, (char) 66);"); // B
         check(parser, "g.combine(''@class2'', ''@class0'', ''@class1'');");
         check(parser, "g.negate(''@class3'', ''@class2'');");
         
         check(parser, "g.range(''@class4'', (char) 67, (char) 67);"); // C
         check(parser, "g.range(''@class5'', (char) 68, (char) 68);"); // D
         check(parser, "g.combine(''@class6'', ''@class4'', ''@class5'');");
         check(parser, "g.negate(''@class7'', ''@class6'');");
         
         check(parser, "g.combine(''@class8'', ''@class3'', ''@class7'');");
         
         check(parser, "g.negate(''@class9'', ''@class8'');");
         
         check(parser, "g.chr(''root'', ''@class9'');"); 
     }
     
    /**
     * Test: 20130517202342199521
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: exclusion character-class
     * </p>
     */
     @Test
     public void test20130517202342199521()
     {
         System.out.println("Test: 20130517202342199521");
         
         final String parser = parse(" root = [[ 'A' - 'Z' ^ 'B'-'C' ] ^ 'Y'] ; ").getParserFile();
         
         check(parser, "g.range(''@class0'', (char) 65, (char) 90);"); // 'A' - 'Z'
         check(parser, "g.range(''@class1'', (char) 66, (char) 67);"); // 'B' - 'C'
         check(parser, "g.range(''@class3'', (char) 89, (char) 89);"); // 'Y'
         check(parser, "g.exclude(''@class2'', ''@class0'', ''@class1'');");
         check(parser, "g.exclude(''@class4'', ''@class2'', ''@class3'');");
         
         check(parser, "g.chr(''root'', ''@class4'');");
     }
     
    /**
     * Test: 20130517202342199593
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: %parser directive
     * </p>
     */
     @Test
     public void test20130517202342199593()
     {
         System.out.println("Test: 20130517202342199593");
         
         final String parser = parse(" %parser ''Mars'' ; "
                                   + " root = ''123'' ; ").getParserFile();

         check(parser, "public final class Mars");
     }
     
    /**
     * Test: 20130517202342199629
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: %visitor directive
     * </p>
     */
     @Test
     public void test20130517202342199629()
     {
         System.out.println("Test: 20130517202342199629");
         
         final String visitor = parse(" %visitor ''Mars'' ; "
                                   + " root = ''123'' ; ").getVisitorFile();

         check(visitor, "public abstract class Mars");
     } 
     
    /**
     * Test: 20130517203326988433
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: %package directive
     * </p>
     */
     @Test
     public void test20130517203326988433()
     {
         System.out.println("Test: 20130517203326988433");
         
         // Case #1: parser-file
         
         final String parser = parse(" %package ''Mars'' ; "
                                   + " root = ''123'' ; ").getParserFile();

         check(parser, "package Mars;");
         
         // Case #2: visitor-file
         
         final String visitor = parse(" %package ''Mars'' ; "
                                    + " root = ''123'' ; ").getParserFile();

         check(visitor, "package Mars;");
     }
     
    /**
     * Test: 20130517203326988536
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: %root directive
     * </p>
     */
     @Test
     public void test20130517203326988536()
     {
         System.out.println("Test: 20130517203326988536");
         
         final String parser = parse(" %root moo ; moo = ''123'' ; ", false).getParserFile();

         check(parser, "g.str(''moo'', ''123'');");
         check(parser, "g.setRoot(''moo'');");
     }
     
    /**
     * Test: 20130520092958813589
     * 
     * <p>
     * Method: <code></code>
     * </p>
     *
     * <p>
     * Case: %trace directive
     * </p>
     */
     @Test
     public void test20130520092958813589()
     {
         System.out.println("Test: 20130520092958813589");
         
         final String parser = parse(" %trace 2995 ; root = ''x'' ; ").getParserFile();

         check(parser, "g.setTraceCount(2995);");
     }    
     
}
