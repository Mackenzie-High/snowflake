/*
 * Copyright 2014 Michael Mackenzie High
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
package example;

import com.mackenziehigh.snowflake.Grammar;
import com.mackenziehigh.snowflake.GrammarBuilder;
import com.mackenziehigh.snowflake.IParser;
import com.mackenziehigh.snowflake.ParserOutput;

/**
 * This class was auto-generated using the Snowflake parser-generator.
 *
 * <p>Generated On: Mon Jan 27 21:29:17 EST 2014</p>
 */
final class Parser
        implements IParser
{
    private static final Grammar grammar = grammar();

    /**
     * This method constructs the grammar object.
     */
    private static Grammar grammar()
    {
        final GrammarBuilder g = new GrammarBuilder();

        // Character Classes
        g.range("@class0", (char) 61, (char) 61);
        g.range("@class1", (char) 43, (char) 43);
        g.range("@class2", (char) 47, (char) 47);
        g.range("@class3", (char) 65, (char) 90);
        g.range("@class4", (char) 97, (char) 122);
        g.combine("@class5", "@class3", "@class4");
        g.range("@class6", (char) 65, (char) 90);
        g.range("@class7", (char) 97, (char) 122);
        g.combine("@class8", "@class6", "@class7");
        g.range("@class9", (char) 42, (char) 42);
        g.range("@class10", (char) 45, (char) 45);
        g.range("@class11", (char) 45, (char) 45);
        g.range("@class12", (char) 48, (char) 57);
        g.combine("@class13", "@class12");
        g.range("@class14", (char) 46, (char) 46);
        g.range("@class15", (char) 48, (char) 57);
        g.combine("@class16", "@class15");
        g.range("@class17", (char) 40, (char) 40);
        g.range("@class18", (char) 41, (char) 41);
        g.range("@class19", (char) 10, (char) 10);
        g.range("@class20", (char) 13, (char) 13);
        g.range("@class21", (char) 32, (char) 32);
        g.combine("@class22", "@class19", "@class20", "@class21");

        // Grammar Rules
        g.choose("expression", "assignment", "precedence1");
        g.choose("precedence1_operation", "add", "subtract");
        g.choose("precedence2_operation", "divide", "multiply");
        g.choose("value", "unary_operation", "nested_expression", "number", "variable_datum");
        g.chr("@0", "@class5");
        g.chr("@1", "@class8");
        g.chr("@10", "@class22");
        g.chr("@3", "@class13");
        g.chr("@5", "@class14");
        g.chr("@6", "@class16");
        g.chr("ADD", "@class1");
        g.chr("ASSIGN", "@class0");
        g.chr("DIV", "@class2");
        g.chr("MINUS", "@class11");
        g.chr("MUL", "@class9");
        g.chr("PAREN_L", "@class17");
        g.chr("PAREN_R", "@class18");
        g.chr("SUB", "@class10");
        g.repeat("@2", "@1", 0, 2147483647);
        g.repeat("@4", "@3", 1, 2147483647);
        g.repeat("@7", "@6", 1, 2147483647);
        g.repeat("@9", "@8", 0, 1);
        g.repeat("WS", "@10", 0, 2147483647);
        g.repeat("precedence1_operations", "precedence1_operation", 0, 2147483647);
        g.repeat("precedence2_operations", "precedence2_operation", 0, 2147483647);
        g.sequence("@8", "@5", "@7");
        g.sequence("ID", "@0", "@2");
        g.sequence("NUMBER", "@4", "@9");
        g.sequence("add", "ADD", "WS", "precedence1_operand", "WS");
        g.sequence("assignment", "variable", "WS", "ASSIGN", "WS", "expression");
        g.sequence("divide", "DIV", "WS", "precedence2_operand", "WS");
        g.sequence("input", "WS", "expression", "WS", "END");
        g.sequence("multiply", "MUL", "WS", "precedence2_operand", "WS");
        g.sequence("negate", "MINUS", "WS", "value", "WS");
        g.sequence("nested_expression", "PAREN_L", "WS", "expression", "WS", "PAREN_R", "WS");
        g.sequence("number", "NUMBER");
        g.sequence("precedence1", "precedence1_operand", "WS", "precedence1_operations");
        g.sequence("precedence1_operand", "precedence2");
        g.sequence("precedence2", "precedence2_operand", "WS", "precedence2_operations");
        g.sequence("precedence2_operand", "value");
        g.sequence("subtract", "SUB", "WS", "precedence1_operand", "WS");
        g.sequence("unary_operation", "negate");
        g.sequence("variable", "ID");
        g.sequence("variable_datum", "variable", "WS");

        // Specify which rule is the root of the grammar.
        g.setRoot("input");

        // Specify the number of tracing records to store concurrently.
        g.setTraceCount(1024);

        // Perform the actual construction of the grammar object.
        return g.build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParserOutput parse(final char[] input)
    {
        return grammar.newParser().parse(input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParserOutput parse(final String input)
    {
        return parse(input.toCharArray());
    }
}
