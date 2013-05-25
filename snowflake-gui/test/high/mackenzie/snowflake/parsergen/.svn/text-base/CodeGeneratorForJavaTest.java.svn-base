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

import high.mackenzie.snowflake.parsergen.CodeGeneratorForJava;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public final class CodeGeneratorForJavaTest
{
    /**
     * Test: 20130404222819997505
     * 
     * <p>
     * Case: Test Parser Code Generation
     * </p>
     */
     @Test
     public void test20130404222819997505()
     {
         System.out.println("Test: 20130404222819997505");
         
         final CodeGeneratorForJava g = new CodeGeneratorForJava();
         
         // Directives
         g.setPackageName("my.namespace");
         g.setParserName("my_parser");
         g.setVisitorName("my_visitor");
         g.setRoot("Saturn");
         g.setTraceCount(2995);
         
         // Character Classes
         g.range("Titan", (char) 123);
         g.range("Ganymede", (char) 456, (char) 789);
         g.combine("Mimas", "Titan", "Ganymede");
         g.exclude("Hyperion", "Titan", "Ganymede");
         g.negate("Rhea", "Hyperion");
         
         // Grammar Rules
         g.str("Saturn", "Saturn sent spaceships somewhere.");
         g.chr("Pluto", "Mimas");
         g.and("Mars", "Tritan");
         g.not("Neptune", "Mars");
         g.sequence("Jupiter", "Mars", "Neptune", "Pluto");
         g.sequenceDLR("Venus", "Jupiter", "Mars", "Pluto");
         g.choose("Earth", "Venus", "Tritan", "Mars");
         g.option("Tritan", "Mars");
         g.star("Nereid", "Venus");
         g.plus("Proteus", "Neptune");
         g.repeat("Thalassa", "Pluto", 17, 29);
         
         
         final String p = g.getParserFile();
       
         assertTrue(p.contains("package my.namespace;"));
         
         assertTrue(p.contains("public final class my_parser"));
         
         assertTrue(p.contains("g.setRoot(\"Saturn\");"));
         
         assertTrue(p.contains("g.setTraceCount(2995);"));
         
         assertTrue(p.contains("g.range(\"Titan\", (char) 123, (char) 123);"));
         
         assertTrue(p.contains("g.range(\"Ganymede\", (char) 456, (char) 789);"));
         
         assertTrue(p.contains("g.combine(\"Mimas\", \"Titan\", \"Ganymede\");"));
         
         assertTrue(p.contains("g.exclude(\"Hyperion\", \"Titan\", \"Ganymede\");"));
         
         assertTrue(p.contains("g.negate(\"Rhea\", \"Hyperion\");"));
         
         assertTrue(p.contains("g.str(\"Saturn\", \"Saturn sent spaceships somewhere.\");"));
         
         assertTrue(p.contains("g.chr(\"Pluto\", \"Mimas\");"));
         
         assertTrue(p.contains("g.and(\"Mars\", \"Tritan\");"));
         
         assertTrue(p.contains("g.not(\"Neptune\", \"Mars\");"));
         
         assertTrue(p.contains("g.sequence(\"Jupiter\", \"Mars\", \"Neptune\", \"Pluto\");"));
         
         assertTrue(p.contains("g.sequenceDLR(\"Venus\", \"Jupiter\", \"Mars\", \"Pluto\");"));
         
         assertTrue(p.contains("g.choose(\"Earth\", \"Venus\", \"Tritan\", \"Mars\");"));
         
         assertTrue(p.contains("g.repeat(\"Tritan\", \"Mars\", 0, 1);"));
         
         assertTrue(p.contains("g.repeat(\"Nereid\", \"Venus\", 0, 2147483647);"));
         
         assertTrue(p.contains("g.repeat(\"Proteus\", \"Neptune\", 1, 2147483647);"));
         
         assertTrue(p.contains("g.repeat(\"Thalassa\", \"Pluto\", 17, 29);"));
         
         
         final String v = g.getVisitorFile();
         
         assertTrue(v.contains("package my.namespace;"));
         
         assertTrue(v.contains("public abstract class my_visitor"));
         
         // Note: Character classes are not visitable; therefore, they do not have visit methods.
         
         assertTrue(v.contains("void visit_Saturn(ITreeNode node)"));
         assertTrue(v.contains("void visit_Pluto(ITreeNode node)"));
         assertTrue(v.contains("void visit_Mars(ITreeNode node)"));
         assertTrue(v.contains("void visit_Neptune(ITreeNode node)"));
         assertTrue(v.contains("void visit_Jupiter(ITreeNode node)"));
         assertTrue(v.contains("void visit_Venus(ITreeNode node)"));
         assertTrue(v.contains("void visit_Earth(ITreeNode node)"));
         assertTrue(v.contains("void visit_Tritan(ITreeNode node)"));
         assertTrue(v.contains("void visit_Nereid(ITreeNode node)"));
         assertTrue(v.contains("void visit_Proteus(ITreeNode node)"));
         assertTrue(v.contains("void visit_Thalassa(ITreeNode node)"));
     }
     
    /**
     * Test: 20130405052712945676
     * 
     * <p>
     * Method: <code>setTraceCount(int)</code>
     * </p>
     *
     * <p>
     * Case: negative argument
     * </p>
     */
     @Test (expected = IllegalArgumentException.class)
     public void test20130405052712945676()
     {
         System.out.println("Test: 20130405052712945676");
         
         final CodeGeneratorForJava g = new CodeGeneratorForJava();
         
         g.setTraceCount(-1);
     }
     
    /**
     * Test: 20130405052712945794
     * 
     * <p>
     * Method: <code>setParserName(String)</code>
     * </p>
     *
     * <p>
     * Case: multiple calls
     * </p>
     */
     @Test (expected = IllegalStateException.class)
     public void test20130405052712945794()
     {
         System.out.println("Test: 20130405052712945794");
         
         final CodeGeneratorForJava g = new CodeGeneratorForJava();
         
         g.setParserName("A");
         g.setParserName("B");
     }
     
    /**
     * Test: 20130405052712945841
     * 
     * <p>
     * Method: <code>setVisitorName(String)</code>
     * </p>
     *
     * <p>
     * Case: multiple calls
     * </p>
     */
     @Test (expected = IllegalStateException.class)
     public void test20130405052712945841()
     {
         System.out.println("Test: 20130405052712945841");

         final CodeGeneratorForJava g = new CodeGeneratorForJava();
         
         g.setVisitorName("A");
         g.setVisitorName("B");
     }
     
    /**
     * Test: 20130405052712945882
     * 
     * <p>
     * Method: <code>setPackageName(String)</code>
     * </p>
     *
     * <p>
     * Case: multiple calls
     * </p>
     */
     @Test (expected = IllegalStateException.class)
     public void test20130405052712945882()
     {
         System.out.println("Test: 20130405052712945882");
         
         final CodeGeneratorForJava g = new CodeGeneratorForJava();
         
         g.setPackageName("A");
         g.setPackageName("B");
     }
     
    /**
     * Test: 20130405052712945919
     * 
     * <p>
     * Method: <code>build()</code>
     * </p>
     *
     * <p>
     * Case: normal
     * </p>
     */
     @Test
     public void test20130405052712945919()
     {
         System.out.println("Test: 20130405052712945919");
         
         final CodeGeneratorForJava g = new CodeGeneratorForJava();
         
         g.str("name", "Me Myself and I");
         
         g.setRoot("name");
         
         assertTrue(null != g.build());
     }
     
}
