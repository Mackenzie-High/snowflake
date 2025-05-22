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
import java.util.Scanner;

/**
 * This program is an infix calculator that reads input from standard input.
 *
 * @author Mackenzie High
 */
final class Main
{
    /**
     * Program Entry Point.
     *
     * @param args are ignored.
     */
    public static void main(String[] args)
    {
        System.out.println("Infix Calculator");
        System.out.println("Usage: Enter a infix expression to evaluate or 'exit' to exit.");

        // This visitor is the caluclator itself.
        final Visitor visitor = new Visitor();

        // This object allows text to be read from standard input.
        final Scanner stdin = new Scanner(System.in);

        // This parser will parse the expressions that are entered by the user.
        final Parser parser = new Parser();

        String input;

        // This is a Read-Eval-Print-Loop.
        while (true)
        {
            // Ask for a line of input from the user.
            System.out.print(">>> ");
            input = stdin.nextLine();

            // If the user requested that the program terminate, then terminate.
            if (input.toLowerCase().trim().equals("exit"))
            {
                System.out.println("Goodbye!");
                return;
            }

            // If the user did not enter any meaningful input, then do nothing.
            if (input.trim().isEmpty())
            {
                continue;
            }

            // Parse the user's input.
            final ParserOutput output = parser.parse(input);

            // If the input was not a valid expression, then report the syntax error.
            if (output.success() == false)
            {
                // Determine the type of newline that is present in the input.
                // Different systems use different types of newlines.
                // For example Linux uses "\n", whereas Windows uses "\r\n".
                // If the parser expected Windows newlines on a Linux system,
                // then line-numbers in error messages would be wildly off.
                // This line fixes that type of problem.
                //
                // Note: In this example, this is actually unnecessary,
                //       because the input is always a single line.
                //       However, it is good to know.
                final NewlineStyles newline = NewlineStyles.fromGuess(input, NewlineStyles.LF);

                // Issue a detailed "Parsing Failed!" error message to the user via standard-output.
                output.print(System.out, newline, true, true, false);

                // Fail fast, because parsing failed.
                return;
            }

            // Perform the calculation and print the result.
            visitor.visit(output.parseTree());
        }
    }
}
