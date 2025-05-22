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
 * <p>Generated On: Mon Jan 27 19:08:36 EST 2014</p>
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
        g.range("@class0", (char) 43, (char) 43);
        g.range("@class1", (char) 47, (char) 47);
        g.range("@class2", (char) 42, (char) 42);
        g.range("@class3", (char) 45, (char) 45);
        g.range("@class4", (char) 45, (char) 45);
        g.range("@class5", (char) 48, (char) 57);
        g.combine("@class6", "@class5");
        g.range("@class7", (char) 46, (char) 46);
        g.range("@class8", (char) 48, (char) 57);
        g.combine("@class9", "@class8");
        g.range("@class10", (char) 40, (char) 40);
        g.range("@class11", (char) 41, (char) 41);
        g.range("@class12", (char) 10, (char) 10);
        g.range("@class13", (char) 13, (char) 13);
        g.range("@class14", (char) 32, (char) 32);
        g.combine("@class15", "@class12", "@class13", "@class14");

        // Grammar Rules
        g.choose("binary_operation", "divide", "multiply", "add", "subtract");
        g.choose("expression", "binary_operation", "operand");
        g.choose("operand", "nested_expression", "binary_operation", "unary_operation", "number");
        g.chr("@0", "@class6");
        g.chr("@2", "@class7");
        g.chr("@3", "@class9");
        g.chr("@7", "@class15");
        g.chr("ADD", "@class0");
        g.chr("DIV", "@class1");
        g.chr("MINUS", "@class4");
        g.chr("MUL", "@class2");
        g.chr("PAREN_L", "@class10");
        g.chr("PAREN_R", "@class11");
        g.chr("SUB", "@class3");
        g.repeat("@1", "@0", 1, 2147483647);
        g.repeat("@4", "@3", 1, 2147483647);
        g.repeat("@6", "@5", 0, 1);
        g.repeat("WS", "@7", 0, 2147483647);
        g.sequence("@5", "@2", "@4");
        g.sequence("NUMBER", "@1", "@6");
        g.sequence("add", "ADD", "WS", "operand", "WS", "operand", "WS");
        g.sequence("divide", "DIV", "WS", "operand", "WS", "operand", "WS");
        g.sequence("input", "WS", "expression", "WS", "END");
        g.sequence("multiply", "MUL", "WS", "operand", "WS", "operand", "WS");
        g.sequence("negate_operation", "MINUS", "WS", "operand", "WS");
        g.sequence("nested_expression", "PAREN_L", "WS", "expression", "WS", "PAREN_R", "WS");
        g.sequence("number", "NUMBER");
        g.sequence("subtract", "SUB", "WS", "operand", "WS", "operand", "WS");
        g.sequence("unary_operation", "negate_operation");

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
