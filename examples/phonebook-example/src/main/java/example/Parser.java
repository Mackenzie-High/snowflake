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
 * <p>Generated On: Mon Jan 27 16:42:45 EST 2014</p>
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
        g.range("@class0", (char) 45, (char) 45);
        g.range("@class1", (char) 48, (char) 57);
        g.range("@class2", (char) 61, (char) 61);
        g.range("@class3", (char) 65, (char) 90);
        g.range("@class4", (char) 97, (char) 122);
        g.combine("@class5", "@class3", "@class4");
        g.range("@class6", (char) 10, (char) 10);
        g.range("@class7", (char) 13, (char) 13);
        g.range("@class8", (char) 32, (char) 32);
        g.combine("@class9", "@class6", "@class7", "@class8");

        // Grammar Rules
        g.choose("name", "full_name", "partial_name");
        g.chr("@0", "@class5");
        g.chr("@1", "@class9");
        g.chr("DASH", "@class0");
        g.chr("DIGIT", "@class1");
        g.chr("EQ", "@class2");
        g.repeat("LETTERS", "@0", 1, 2147483647);
        g.repeat("WS", "@1", 0, 2147483647);
        g.repeat("entries", "entry", 0, 2147483647);
        g.sequence("area_code", "DIGIT", "DIGIT", "DIGIT");
        g.sequence("book", "WS", "entries", "WS", "END");
        g.sequence("entry", "name", "WS", "phone_number", "WS");
        g.sequence("first_name", "LETTERS");
        g.sequence("full_name", "first_name", "WS", "middle_name", "WS", "last_name");
        g.sequence("last_name", "LETTERS");
        g.sequence("line", "DIGIT", "DIGIT", "DIGIT", "DIGIT");
        g.sequence("middle_name", "LETTERS");
        g.sequence("partial_name", "first_name", "WS", "last_name");
        g.sequence("phone_number", "area_code", "DASH", "prefix", "DASH", "line");
        g.sequence("prefix", "DIGIT", "DIGIT", "DIGIT");

        // Specify which rule is the root of the grammar.
        g.setRoot("book");

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
