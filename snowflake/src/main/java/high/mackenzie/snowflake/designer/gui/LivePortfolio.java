/**
 * Copyright 2025 Michael Mackenzie High
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
package high.mackenzie.snowflake.designer.gui;

import high.mackenzie.snowflake.Grammar;
import high.mackenzie.snowflake.ITreeNode;
import high.mackenzie.snowflake.NewlineStyles;
import high.mackenzie.snowflake.Parser;
import high.mackenzie.snowflake.ParserOutput;
import high.mackenzie.snowflake.parsergen.ParserGenerator;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import mjson.Json;

/**
 * This class centralizes the logic behind the GUI.
 *
 * @author Michael Mackenzie High
 */
public final class LivePortfolio
{

    private static class BadGrammarException
            extends Exception
    {
    }

    private static class ParsingFailedException
            extends Exception
    {
    }

    private static final Json output = Json.object();

    static
    {
        output.set("success", false);
        output.set("message", "");
        output.set("parse_tree", null);
        output.set("parser", "");
        output.set("visitor", "");
    }

    /**
     * This writer stores the standard-output to output via the GUI's
     * "Output" tab.
     */
    private static final StringWriter stdout = new StringWriter();

    /**
     * This writer allows the previous writer to be treated like a
     * print-stream.
     */
    private static final PrintWriter writer = new PrintWriter(stdout);

    /**
     * This will be the dynamically created parser.
     */
    private static Parser parser = null;

    /**
     * This will be the output of the dynamically created parser.
     */
    private static ParserOutput parser_output;

    /**
     * This method dynamically creates a parser object from the user-specified
     * grammar.
     *
     * @throws BadGrammarException if the user-specified grammar is malformed.
     */
    private static void createParser (final String grammar)
            throws BadGrammarException
    {
        // Create a parser generator in order to create the user-specified parser.
        final ParserGenerator pg = ParserGenerator.forJava(writer);

        // Use the parser generator to dynamically create a grammar object.
        final Grammar dynamic_grammar = pg.parseGrammar(grammar);

        // If the grammar object could not be created,
        // then the user-specified grammar must invalid.
        if (dynamic_grammar == null)
        {
            throw new BadGrammarException();
        }

        // Dynamically create a parser based on the grammar from the "Grammar" tab.
        parser = dynamic_grammar.newParser();
    }

    /**
     * This method uses the dynamically created parser to parse the
     * user-specified input.
     */
    private static void parseInput (final String input)
            throws ParsingFailedException
    {
        // Parse the user-specified input.
        parser_output = parser.parse(input);

        // If the parsing attempt failed, then it must be reported to the user.
        if (parser_output.success() == false)
        {
            throw new ParsingFailedException();
        }
    }

    /**
     * This method reports a malformed grammar to the user.
     */
    private static void reportBadGrammar ()
    {
        // Note: The parser-generator has already written the error info to the print-stream.
    }

    private static Json jsonifyLeafNode (final ITreeNode node)
    {
        final var result = Json.object();
        result.set("leaf", true);
        result.set("children", Json.array());
        result.set("start", node.start());
        result.set("length", node.length());
        result.set("text", node.text());
        result.set("rule", node.rule());
        return result;
    }

    private static Json jsonifyInnerNode (final ITreeNode node)
    {
        final var children = new ArrayList<Json>(node.childCount());

        for (ITreeNode child : node.children())
        {
            children.add(jsonify(child));
        }

        final var result = Json.object();
        result.set("leaf", true);
        result.set("children", Json.array(children));
        result.set("start", node.start());
        result.set("length", node.length());
        result.set("text", node.text());
        result.set("rule", node.rule());
        return result;
    }

    private static Json jsonify (final ITreeNode node)
    {
        if (node.childCount() == 0)
        {
            return jsonifyLeafNode(node);
        }
        else
        {
            return jsonifyInnerNode(node);
        }
    }

    /**
     * This method reports the results of a successful parsing attempt to
     * the user.
     */
    private static void reportParsingSuccess ()
    {
        final ITreeNode root = parser_output.parseTree();
        final Json json = jsonify(root);
        output.set("parse_tree", json);
        output.set("success", true);

        // Report that the parsing attempt was successful.
        // Also, display the tracer-records generated by the parser.
        // These records can help ths user find bugs in their grammar.
        writer.println("Excellent, parsing was successful!");
        writer.println();
        parser_output.trace().print(writer);
        writer.println();
        writer.println();
        writer.println(json.toString());
    }

    private static void reportParsingFailed (final String input)
    {
        // Determine the style of newline that is present in the input.
        final NewlineStyles newline = NewlineStyles.fromGuess(input, NewlineStyles.LF);

        // Print information regarding the failed parsing-attempt.
        // For example, print the estimated location of the syntax error.
        parser_output.print(writer, newline, true, true, true);
    }

    private static void reportStackOverflow (final StackOverflowError exception)
    {
        // Write the output that the user will see.
        writer.println("Oh no! - A stack-overflow occurred!");
        writer.println();
        writer.println("Most likely, your grammar contains left-recursion.");
        writer.println();
        parser.getTrace().print(writer);
        writer.println();
        exception.printStackTrace(writer);
    }

    private static void reportUnexpectedException (final Throwable unexpected)
    {
        // Write the output that the user will see.
        writer.println("Unfortunately, parsing failed due to an unexpected exception.");
        writer.println();
        unexpected.printStackTrace(writer);
    }

    private static void commandParse (final File grammarFile,
                                      final File inputFile,
                                      final File outputFile) throws IOException
    {
        final var grammar = Files.readString(grammarFile.toPath());
        final var input = Files.readString(inputFile.toPath());

        try
        {
            try
            {
                // Create the parser that will parse the user-specified input.
                createParser(grammar);

                // Parse the user-specified input and display the result.
                parseInput(input);

                // Display the result of the successful parsing attempt.
                reportParsingSuccess();
            }
            catch (BadGrammarException ex)
            {
                reportBadGrammar();
            }
            catch (ParsingFailedException ex)
            {
                reportParsingFailed(input);
            }
            catch (StackOverflowError ex)
            {
                reportStackOverflow(ex);
            }
            catch (Throwable ex)
            {
                reportUnexpectedException(ex);
            }
        }
        catch (RuntimeException t)
        {
            throw t;
        }
        finally
        {
            writeOutput(outputFile);
        }
    }

    private static void writeOutput (final File outputFile) throws IOException
    {
        writer.flush();
        stdout.flush();
        output.set("message", stdout.toString());
        Files.write(outputFile.toPath(), output.toString().getBytes());
    }

    public static void main (String[] args)
    {
//        args = new String[4];
//        args[0] = "parse";
//        args[1] = "/home/mackenzie/Code/snowflake/example/grammar.txt";
//        args[2] = "/home/mackenzie/Code/snowflake/example/input.txt";
//        args[3] = "/home/mackenzie/Code/snowflake/example/output.txt";

        try
        {
            if (0 == args.length)
            {
                System.err.println("Usage: parse <grammar> <input> <output>");
            }
            else if (args.length == 4 && "parse".equals(args[0]))
            {
                final var grammarFile = new File(args[1]);
                final var inputFile = new File(args[2]);
                final var outputFile = new File(args[3]);
                commandParse(grammarFile, inputFile, outputFile);
            }
            else
            {
                System.err.println("ERROR: Unable to parse the command-line.");
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace(System.err);
        }
    }
}
