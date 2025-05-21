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
package com.mackenziehigh.snowflake.parsergen;

import com.mackenziehigh.snowflake.Grammar;
import com.mackenziehigh.snowflake.NewlineStyles;
import com.mackenziehigh.snowflake.ParserOutput;
import com.mackenziehigh.snowflake.Utils;
import java.io.PrintWriter;

/**
 * An instance of this class builds a parser-file and visitor-file from a given grammar.
 *
 * @author Mackenzie High
 */
public final class ParserGenerator
{
    private static final NewlineStyles NEWLINE = NewlineStyles.LF;

    private final ICodeGenerator code_generator;

    private final PrintWriter stream;

    private boolean parsed = false;

    /**
     * This method creates a new instance that generates Java source-code.
     *
     * @param stream is a stream to verbose output to.
     * @return the aforedescribed instance.
     */
    public static ParserGenerator forJava(final PrintWriter stream)
    {
        final CodeGeneratorForJava cg = new CodeGeneratorForJava();

        return new ParserGenerator(cg, stream);
    }

    /**
     * Sole Constructor.
     *
     * @param code_generator is an object that generates code in a specific programming language.
     * @param stream is a stream to print error and verbose output to.
     */
    private ParserGenerator(final ICodeGenerator code_generator,
                            final PrintWriter stream)
    {
        Utils.checkNonNull(code_generator);
        Utils.checkNonNull(stream);

        this.code_generator = code_generator;
        this.stream = stream;
    }

    /**
     * This method parses a grammar and generates a derived parser-file and visitor-file.
     *
     * @param grammar is the grammar to parse.
     * @return a dynamic implementation of the parsed grammar,
     * or null, if the parsing failed.
     */
    public Grammar parseGrammar(final String grammar)
    {
        if (parsed)
        {
            throw new IllegalStateException();
        }

        this.parsed = true;

        final char[] input = grammar.toCharArray();

        final GrammarParser parser = new GrammarParser();

        final ParserOutput output = parser.parse(input);

        try
        {
            if (output.success())
            {
                final GrammarVisitor visitor = new GrammarVisitor(code_generator);

                visitor.visit(output.parseTree());

                return code_generator.build();
            }
            else
            {
                stream.println("Oops, there is a problem with your grammar.");
                stream.println();

                output.print(stream, NEWLINE, true, true, true);
                stream.println();

                return null;
            }
        }
        catch (RuntimeException ex)
        {
            stream.println("Unfortunately, parser generation failed due to an exception.");
            stream.println();
            ex.printStackTrace(stream);

            return null;
        }
    }

    /**
     * This method returns the source-code to place into the parser-file.
     *
     * @return the aforedescribed source-code.
     */
    public String getParserFile()
    {
        if (parsed == false)
        {
            throw new IllegalStateException();
        }

        return code_generator.getParserFile();
    }

    /**
     * This method returns the source-code to place into the visitor-file.
     *
     * @return the aforedescribed source-code.
     */
    public String getVisitorFile()
    {
        if (parsed == false)
        {
            throw new IllegalStateException();
        }

        return code_generator.getVisitorFile();
    }

    /**
     * This method performs the exportation of the generated parser and the generated visitor.
     */
    public void exportFiles()
    {
        if (parsed == false)
        {
            throw new IllegalStateException();
        }

        code_generator.exportFiles();
    }
}
