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

import com.mackenziehigh.snowflake.NewlineStyles;
import com.mackenziehigh.snowflake.ParserOutput;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This program prints the information contained in a phone-book file.
 *
 * @author Mackenzie High
 */
final class Main
{
    /**
     * Entry Point.
     *
     * @param args is a one-element array containing the path to the phone-book.
     */
    public static void main(String[] args)
    {
        // Ensure that a phone-book file was specified.
        if (args.length < 1)
        {
            System.out.println("No input file was specified.");
            return;
        }

        // This will contain the text from the phone-book.
        final String input;

        try
        {
            input = Main.readFile(new File(args[0]));
        }
        catch (IOException ex)
        {
            System.out.println("The input file could not be read.");
            return;
        }

        // This parser will parse the phone-book.
        final Parser parser = new Parser();

        // Perform the actual parsing.
        final ParserOutput output = parser.parse(input);

        // If a syntax-error was encountered, then report it and exit early.
        if (output.success() == false)
        {
            // Determine the type of newline that is present in the input.
            // Different systems use different types of newlines.
            // For example Linux uses "\n", whereas Windows uses "\r\n".
            // If the parser expected Windows newlines on a Linux system,
            // then line-numbers in error messages would be wildly off.
            // This line fixes that type of problem.
            final NewlineStyles newline = NewlineStyles.fromGuess(input, NewlineStyles.LF);

            // Issue a detailed "Parsing Failed!" error message to the user via standard-output.
            output.print(System.out, newline, true, true, false);

            // Fail fast, because parsing failed.
            return;
        }

        // This visitor will transverse the parse-tree and print the names and phone-numbers.
        final Visitor visitor = new Visitor();

        // Perform the transversal.
        visitor.visit(output.parseTree());
    }

    /**
     * This method reads a text file.
     *
     * @param file is the path to the file that will be read.
     * @return the content of the text file.
     * @throws IOException if something goes wrong.
     */
    private static String readFile(final File file)
            throws IOException
    {
        final FileInputStream fis = new FileInputStream(file);

        final BufferedInputStream bis = new BufferedInputStream(fis);

        final StringBuilder builder = new StringBuilder();

        try
        {
            int c;

            while ((c = bis.read()) != -1)
            {
                builder.append((char) c);
            }

            return builder.toString();
        }
        finally
        {
            bis.close();
        }
    }
}
