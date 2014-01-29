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
package high.mackenzie.snowflake.designer.gui;

import high.mackenzie.snowflake.Grammar;
import high.mackenzie.snowflake.LinesAndColumns;
import high.mackenzie.snowflake.NewlineStyles;
import high.mackenzie.snowflake.Parser;
import high.mackenzie.snowflake.ParserOutput;
import high.mackenzie.snowflake.designer.gui.tabs.ConcreteSyntaxTreePanel;
import high.mackenzie.snowflake.designer.gui.tabs.EditorPanel;
import high.mackenzie.snowflake.designer.io.StringFile;
import high.mackenzie.snowflake.parsergen.ParserGenerator;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 * This class centralizes the logic behind the GUI.
 *
 * @author Michael Mackenzie High
 */
public final class Core
{
    private static final FileFilter filter = new FileFilter()
    {
        @Override
        public boolean accept(final File path)
        {
            return path.toString().endsWith(".snow") || path.isDirectory();
        }

        @Override
        public String getDescription()
        {
            return "Snowflake Project";
        }
    };

    // This style will work on both Windows and Linux.
    private static final NewlineStyles NEWLINE = NewlineStyles.LF;

    private static final Preferences preferences = Preferences.userRoot().node(Core.class.getName());

    private static final String FONT_SIZE_PREFERENCE = "font-size";

    private static JFrame main = null;

    private static final JFileChooser file_chooser = new JFileChooser();

    private static File current_file = null;

    private static final EditorPanel grammar = new EditorPanel();

    private static final EditorPanel input = new EditorPanel();

    private static final ConcreteSyntaxTreePanel parse_tree = new ConcreteSyntaxTreePanel();

    private static final EditorPanel generated_parser = new EditorPanel();

    private static final EditorPanel generated_visitor = new EditorPanel();

    private static final EditorPanel output = new EditorPanel();

    private static final EditorPanel license = new EditorPanel();

    private static final JTabbedPane tabs = new JTabbedPane();

    private static JLabel line_number = new JLabel();

    private static JLabel column_number = new JLabel();

    private static JLabel project_label = new JLabel();

    private static Thread parse_thread;

    private static String saved_grammar = "";

    private static String saved_input = "";

    private static final FindAndReplaceDialog find_and_replace_dialog = new FindAndReplaceDialog(null, false);

    public static JTabbedPane getTabs()
    {
        return tabs;
    }

    public static JLabel getLineNumber()
    {
        return line_number;
    }

    public static JLabel getColumnNumber()
    {
        return column_number;
    }

    public static JLabel getProjectLabel()
    {
        return project_label;
    }

    /**
     * Static Constructor.
     */
    static
    {
        file_chooser.addChoosableFileFilter(filter);
        file_chooser.setFileFilter(filter);

        tabs.add("Grammar", grammar);
        tabs.add("Input", input);
        tabs.add("Parse Tree", parse_tree);
        tabs.add("Output", output);
        tabs.add("Generated Parser", generated_parser);
        tabs.add("Generated Visitor", generated_visitor);
        tabs.add("License", license);

        final Font font = new Font("Monospaced", Font.PLAIN, 14);

        Core.input.getTextPane().setFont(font);

        Core.grammar.getTextPane().setFont(font);

        Core.output.getTextPane().setEditable(false);

        license.getTextPane().setEditable(false);
        license.getTextPane().setText("Copyright 2013 Michael Mackenzie High\n\nLicensed under the Apache License, Version 2.0 (the \"License\");\nyou may not use this file except in compliance with the License.\nYou may obtain a copy of the License at\n\n     http://www.apache.org/licenses/LICENSE-2.0\n\nUnless required by applicable law or agreed to in writing, software\ndistributed under the License is distributed on an \"AS IS\" BASIS,\nWITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\nSee the License for the specific language governing permissions and\nlimitations under the License.".replace("\n", NEWLINE.newline()));

        // Add listeners that update the line and column status labels.
        Core.grammar.getTextPane().addCaretListener(Core.newRefreshListener(Core.grammar.getTextPane()));
        Core.input.getTextPane().addCaretListener(Core.newRefreshListener(Core.input.getTextPane()));
        Core.parse_tree.getTextPane().addCaretListener(Core.newRefreshListener(Core.parse_tree.getTextPane()));
        Core.output.getTextPane().addCaretListener(Core.newRefreshListener(Core.output.getTextPane()));
        Core.generated_parser.getTextPane().addCaretListener(Core.newRefreshListener(Core.generated_parser.getTextPane()));
        Core.generated_visitor.getTextPane().addCaretListener(Core.newRefreshListener(Core.generated_visitor.getTextPane()));
        Core.license.getTextPane().addCaretListener(Core.newRefreshListener(Core.license.getTextPane()));

        Core.parse_tree.getTextPane().setFont(font);
        Core.parse_tree.getTextPane().setEditable(false);

        // Make sure that the line and column numbers are displayed, when the program loads.
        Core.grammar.getTextPane().setText(" ");
        Core.grammar.getTextPane().setCaretPosition(0);
        Core.grammar.getTextPane().setText("");

//        final SyntaxHighlighter highlighter = new SyntaxHighlighter(Core.grammar.getTextPane(), 200);
//        Core.grammar.getTextPane().setDocument(highlighter);
//        highlighter.start();
    }

    /**
     * An instance of this class is run in order to implement Action->Parse.
     */
    private static final class ParseRunner
            implements Runnable
    {
        private static class BadGrammarException
                extends Exception
        {
        }

        private static class ParsingFailedException
                extends Exception
        {
        }

        /**
         * This writer stores the standard-output to output via the GUI's "Output" tab.
         */
        private final StringWriter stdout = new StringWriter();

        /**
         * This writer allows the previous writer to be treated like a print-stream.
         */
        private final PrintWriter writer = new PrintWriter(stdout);

        /**
         * Get the grammar from the "Grammar" tab.
         */
        final String strGrammar = grammar.getTextPane().getText();

        /**
         * Get the input from the "Input" tab.
         */
        final String strInput = input.getTextPane().getText();

        /**
         * This will be the dynamically created parser.
         */
        private Parser parser = null;

        /**
         * This will be the output of the dynamically created parser.
         */
        private ParserOutput parser_output;

        /**
         * When this method is invoked, the user-specified input is parsed
         * using the user-specified grammar.
         */
        public void run()
        {
            // Inform the user that parsing may take a moment.
            // Under very limited normal circumstances, the program may appear to freeze.
            // However, the parser will run out of heap space.
            // As a result, the parser will eventually complete its parsing attempt.
            output.getTextPane().setForeground(Color.BLACK);
            output.getTextPane().setText(("Please wait, parsing may take a moment.\n"
                                          + "\n"
                                          + "If the program appears to freeze, your grammar most likely contains a bug.\n"
                                          + "After a few minutes max, the program will run out of heap space.\n"
                                          + "As a result, parsing will be forced to stop and the program will unfreeze.\n"
                                          + "\n"
                                          + "Example of a buggy grammar:\n"
                                          + "  root = moo *;\n"
                                          + "  moo = \"\";\n").replace("\n", NEWLINE.newline()));
            output.getTextPane().setCaretPosition(0);
            tabs.setSelectedComponent(output);

            try
            {
                try
                {
                    // Create the parser that will parse the user-specified input.
                    this.createParser();

                    // Parse the user-specified input and display the result.
                    this.parseInput();

                    // Display the result of the successful parsing attempt.
                    this.reportParsingSuccess();
                }
                catch (BadGrammarException ex)
                {
                    this.reportBadGrammar();
                }
                catch (ParsingFailedException ex)
                {
                    this.reportParsingFailed();
                }
                catch (StackOverflowError ex)
                {
                    this.reportStackOverflow(ex);
                }
                catch (Throwable ex)
                {
                    this.reportUnexpectedException(ex);
                }

                // Make sure the streams are flushed.
                writer.flush();
                stdout.flush();

                // Display the standard-output via the GUI's "Output" tab.
                output.getTextPane().setText(stdout.toString());
                output.getTextPane().setCaretPosition(0);

                // Stop blocking the parse action.
                Core.parse_thread = null;
            }
            catch (RuntimeException t)
            {
                // Stop blocking the parse action.
                Core.parse_thread = null;

                throw t;
            }
        }

        /**
         * This method dynamically creates a parser object from the user-specified grammar.
         *
         * @throws BadGrammarException if the user-specified grammar is malformed.
         */
        private void createParser()
                throws BadGrammarException
        {
            // Create a parser generator in order to create the user-specified parser.
            final ParserGenerator pg = ParserGenerator.forJava(writer);

            // Use the parser generator to dynamically create a grammar object.
            final Grammar dynamic_grammar = pg.parseGrammar(strGrammar);

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
         * This method uses the dynamically created parser to parse the user-specified input.
         */
        private void parseInput()
                throws ParsingFailedException
        {
            // Parse the user-specified input.
            parser_output = parser.parse(strInput);

            // If the parsing attempt failed, then it must be reported to the user.
            if (parser_output.success() == false)
            {
                throw new ParsingFailedException();
            }
        }

        /**
         * This method reports a malformed grammar to the user.
         */
        private void reportBadGrammar()
        {
            // Note: The parser-generator has already written the error info to the print-stream.

            output.getTextPane().setForeground(Color.RED);

            tabs.setSelectedComponent(output);
        }

        /**
         * This method reports the results of a successful parsing attempt to the user.
         */
        private void reportParsingSuccess()
        {
            // Make the parse-tree be displayable via a GUI.
            final DisplayableTreeNode disroot = new DisplayableTreeNode(null,
                                                                        parser_output.parseTree());

            // Display the parse-tree via the GUI.
            final TreeModel model = new DefaultTreeModel(disroot);
            parse_tree.getParseTree().setModel(model);
            parse_tree.getTextPane().setText("");

            // Since the parsing attempt was successful, the output color must be black.
            output.getTextPane().setForeground(Color.BLACK);

            // Report that the parsing attempt was successful.
            // Also, display the tracer-records generated by the parser.
            // These records can help ths user find bugs in their grammar.
            writer.println("Excellent, parsing was successful!");
            writer.println();
            parser_output.trace().print(writer);

            // Keep the "Generated Parser" and "Generated Visitor" tabs up to date.
            generateParser();

            // Focus on the "Parse Tree" tab, because that is what the user most likely wants.
            tabs.setSelectedComponent(parse_tree);
        }

        private void reportParsingFailed()
        {
            // Print information regarding the failed parsing-attempt.
            // For example, print the estimated location of the syntax error.
            parser_output.print(writer, NEWLINE, true, true, true);

            // Since an error is being reported, the output color must be red.
            output.getTextPane().setForeground(Color.RED);

            // Since an error is being reported, the GUI's "Output" tab must take the focus.
            tabs.setSelectedComponent(output);
        }

        private void reportStackOverflow(final StackOverflowError exception)
        {
            // Write the output that the user will see.
            writer.println("Oh no! - A stack-overflow occurred!");
            writer.println();
            writer.println("Most likely, your grammar contains left-recursion.");
            writer.println();
            parser.getTrace().print(writer);
            writer.println();
            exception.printStackTrace(writer);

            // Since an error is being reported, the output color must be red.
            output.getTextPane().setForeground(Color.RED);

            // Since an error is being reported, the GUI's "Output" tab must take the focus.
            tabs.setSelectedComponent(output);
        }

        private void reportUnexpectedException(final Throwable unexpected)
        {
            // Write the output that the user will see.
            writer.println("Unfortunately, parsing failed due to an unexpected exception.");
            writer.println();
            unexpected.printStackTrace(writer);

            // Since an error is being reported, the output color must be red.
            output.getTextPane().setForeground(Color.RED);

            // Since an error is being reported, the GUI's "Output" tab must take the focus.
            tabs.setSelectedComponent(output);
        }
    }

    /**
     * This method opens a project file and loads its contents into the GUI.
     *
     * <p>
     * This method asks the user for the location of the project file using a dialog.
     * </p>
     */
    public static void openProject()
    {
        // Display the dialog to the user.
        file_chooser.showOpenDialog(null);

        // Retrieve the file that the user selected.
        final File file = file_chooser.getSelectedFile();

        try
        {
            // If the user did not select a file, then there is nothing else that can be done.
            // Likewise, nothing else can be done, if the selected file does not exist.
            if (file == null || !file.exists())
            {
                throw new RuntimeException("No openable file was selected.");
            }

            // The project file is actually a .zip file.
            // This list will contain the files inside the project .zip file.
            List<StringFile> zip;

            // This variable is used to determine whether the user really opened a project file.
            boolean valid = false;

            // Read the project file.
            zip = StringFile.readZipFile(file);

            // Only two of the files inside the .zip file will be loaded.
            // Firstly, the "Grammar.txt" file must be loaded into the "Grammar" tab.
            // Secondly, the "Input.txt" file must be loaded into the "Input" tab.
            for (StringFile stringFile : zip)
            {
                if (stringFile.getFilepath().equals(new File("Grammar.txt")))
                {
                    valid = true;
                    grammar.getTextPane().setText(stringFile.getSourceCode());
                    saved_grammar = grammar.getTextPane().getText();
                }

                if (stringFile.getFilepath().equals(new File("Input.txt")))
                {
                    valid = true;
                    input.getTextPane().setText(stringFile.getSourceCode());
                    saved_input = input.getTextPane().getText();
                }
            }

            // If the user did not select a project file, report the error.
            if (!valid)
            {
                throw new RuntimeException("The selected file is not a Snowflake project file.");
            }

            // Save the path to the file in case the user wishes to save the file again later.
            current_file = file;
        }
        catch (Exception ex)
        {
            // Display the error via the output tab.
            output.getTextPane().setForeground(Color.RED);
            output.getTextPane().setText(ex.toString());
            output.getTextPane().setCaretPosition(0);
            tabs.setSelectedComponent(output);
            return;
        }

        // Update the text displayed via the project-label.
        refreshProjectLabel();
    }

    /**
     * This method saves the contents of the GUI as a project file.
     *
     * <p>
     * This method always asks the user for the location of the project file using a dialog.
     * </p>
     */
    public static void saveProjectAs()
    {
        // Display the dialog to the user.
        file_chooser.showSaveDialog(null);

        // Retrieve the file selected by the user.
        final File file = file_chooser.getSelectedFile();

        // If the user did not select a file, then no action can be performed.
        if (file == null)
        {
            return;
        }

        // Perform the actual save operation.
        saveProjectToFile(file);
    }

    /**
     * This method saves the contents of the GUI as a project file.
     *
     * <p>
     * This method may ask the user for the location of the project file using a dialog.
     * This method will not use a dialog, if the user previous opened or saved the project.
     * </p>
     */
    public static void saveProject()
    {
        if (current_file == null)
        {
            saveProjectAs();
        }
        else
        {
            saveProjectToFile(current_file);
        }
    }

    /**
     * This method tries to saves the content of the GUI to a project file.
     *
     * @param file is where to save the project file to.
     */
    private static void saveProjectToFile(final File file)
    {
        // Retrieve the content of the "Grammar" tab.
        final StringFile grammar_file = new StringFile();
        grammar_file.setFilepath(new File("Grammar.txt"));
        grammar_file.setSourceCode(grammar.getTextPane().getText());

        // Retrieve the content of the "Input" tab.
        final StringFile input_file = new StringFile();
        input_file.setFilepath(new File("Input.txt"));
        input_file.setSourceCode(input.getTextPane().getText());

        // Retrieve the content of the "Generated Parser" tab.
        final StringFile parser_file = new StringFile();
        parser_file.setFilepath(new File("Parser.java"));
        parser_file.setSourceCode(generated_parser.getTextPane().getText());

        // Retrieve the content of the "Generated Visitor" tab.
        final StringFile visitor_file = new StringFile();
        visitor_file.setFilepath(new File("Visitor.java"));
        visitor_file.setSourceCode(generated_visitor.getTextPane().getText());

        // Create a list of the files that will be placed into the .zip file.
        final List<StringFile> files = Arrays.asList(grammar_file,
                                                     input_file,
                                                     parser_file,
                                                     visitor_file);

        try
        {
            // Write the project file, which is really a .zip file.
            StringFile.writeZipFile(file, files);

            // Save the path to the file in case the user wishes to save the file again later.
            current_file = file;

            saved_grammar = grammar_file.getSourceCode();
            saved_input = input_file.getSourceCode();
        }
        catch (Exception ex)
        {
            // Display the error via the "Output" tab.
            output.getTextPane().setForeground(Color.RED);
            output.getTextPane().setText(ex.toString());
            output.getTextPane().setCaretPosition(0);
            tabs.setSelectedComponent(output);
        }

        // Update the text displayed via the project-label.
        refreshProjectLabel();
    }

    /**
     * This method implements the "Parse" action.
     *
     * <p>This method is synchronized to prevent multiple parsers from running simultaneously.</p>
     */
    public static synchronized void parse()
    {
        // Do not allow multiple parsing threads to be running concurrently.
        if (parse_thread != null)
        {
            return;
        }

        Core.parse_thread = new Thread(new ParseRunner());
        Core.parse_thread.start();
    }

    /**
     * This method implements the "Generate Parser" action.
     */
    public static void generateParser()
    {
        try
        {
            final StringWriter writer = new StringWriter();

            final PrintWriter stdout = new PrintWriter(writer);

            final ParserGenerator pg = ParserGenerator.forJava(stdout);

            pg.parseGrammar(grammar.getTextPane().getText());

            generated_parser.getTextPane().setText(pg.getParserFile());
            generated_parser.getTextPane().setCaretPosition(0);

            generated_visitor.getTextPane().setText(pg.getVisitorFile());
            generated_visitor.getTextPane().setCaretPosition(0);

            pg.exportFiles();

            output.getTextPane().setForeground(Color.BLACK);
            output.getTextPane().setText("Your parser and visitor were successfully generated.");

            tabs.setSelectedComponent(generated_parser);
        }
        catch (RuntimeException ex)
        {
            // Display the error via the "Output" tab.
            output.getTextPane().setForeground(Color.RED);
            output.getTextPane().setText(ex.toString());
            output.getTextPane().setCaretPosition(0);
            tabs.setSelectedComponent(output);
        }
    }

    /**
     * This method implements the "Find and Replace" action.
     */
    public static void findAndReplace()
    {
        Core.find_and_replace_dialog.setVisible(true);
    }

    /**
     * This method implements the "Exit" action.
     */
    public static void exit()
    {
        final boolean grammar_changed = !grammar.getTextPane().getText().equals(saved_grammar);
        final boolean input_changed = !input.getTextPane().getText().equals(saved_input);

        if (grammar_changed || input_changed)
        {
            final int answer = JOptionPane.showOptionDialog(main,
                                                            "Do you want to exit without saving?",
                                                            "Question",
                                                            JOptionPane.YES_NO_OPTION,
                                                            JOptionPane.QUESTION_MESSAGE,
                                                            null,
                                                            null,
                                                            false);

            if (answer != JOptionPane.YES_OPTION)
            {
                return;
            }
        }

        System.out.println("exit");
        System.exit(0);
    }

    public static void showAboutDialog()
    {
        final HelpDialog dialog = new HelpDialog(main, true);

        dialog.setVisible(true);
    }

    public static void showFontDialog()
    {
        float size = Core.grammar.getTextPane().getFont().getSize();

        final String answer = JOptionPane.showInputDialog(main,
                                                          "Font Size: ",
                                                          size);

        if (answer == null)
        {
            return;
        }

        size = Float.parseFloat(answer);

        preferences.putFloat(FONT_SIZE_PREFERENCE, size);

        Core.updateFont();
    }

    public static void updateFont()
    {
        final float size = preferences.getFloat(FONT_SIZE_PREFERENCE, 12.0F);

        final Font old_font = grammar.getTextPane().getFont();

        final Font new_font = old_font.deriveFont(size);

        grammar.getTextPane().setFont(new_font);
        input.getTextPane().setFont(new_font);
        output.getTextPane().setFont(new_font);
        parse_tree.getTextPane().setFont(new_font);
        generated_parser.getTextPane().setFont(new_font);
        generated_visitor.getTextPane().setFont(new_font);
        license.getTextPane().setFont(new_font);
    }

    private static void refreshProjectLabel()
    {
        String file = Core.current_file == null ? "<none>" : Core.current_file.getName();

        project_label.setText("Project: " + file);
    }

    /**
     * This method refreshes the line-number and column-number label, given a text-box.
     *
     * @param text is the text-box whose line and column numbers shall be displayed.
     */
    private static void refreshPosition(final JTextPane text)
    {
        final int position = text.getCaretPosition();

        String txt = (text.getText() + "$");

        // If we are on Windows, convert Windows newlines to Linux newlines.
        txt = NewlineStyles.fromSystem() == NewlineStyles.CR_LF
                ? txt.replace("\r", "")
                : txt;

        // If we are on Mac?, convert Mac? newlines to Linux newlines.
        txt = NewlineStyles.fromSystem() == NewlineStyles.CR
                ? txt.replace("\r", "\n")
                : txt;

        final char[] chars = txt.toCharArray();

        final LinesAndColumns finder = new LinesAndColumns(chars, NEWLINE);

        final String line = "Line: " + zfill(5, finder.lineNumbers()[position]);

        final String column = "Column: " + zfill(5, finder.columnNumbers()[position]);

        line_number.setText(line);

        column_number.setText(column);
    }

    /**
     * This method prefixes zeros onto the string representation of an integer,
     * until the string reaches a given length.
     *
     * @param length is the length that the string must reach.
     * @param data is the integer that will be converted to a string.
     * @return the aforedescribed string with the zeros prepended.
     */
    private static String zfill(int length,
                                int data)
    {
        String number = "" + data;

        while (number.length() < length)
        {
            number = "0" + number;
        }

        return number;
    }

    /**
     * This method creates a new listener that invokes the refresh method, whenever,
     * a given text-box changes.
     *
     * @param textbox is the text-box that may change.
     * @return the aforedescribed new listener.
     */
    private static CaretListener newRefreshListener(final JTextPane textbox)
    {
        return new CaretListener()
        {
            public void caretUpdate(CaretEvent e)
            {
                Core.refreshPosition(textbox);
            }
        };
    }

    /**
     * This method implements the find-action of the "Find and Replace" dialog.
     *
     * @param text is the text to find.
     */
    public static void find(final String text)
    {
        // TODO
        System.out.println("find");
    }

    /**
     * This method implements the replace-action of the "Find and Replace" dialog.
     *
     * @param find is the text to find.
     * @param replacement is the replacement for the found text.
     */
    public static void replace(final String find,
                               final String replacement)
    {
        // TODO
        System.out.println("replace");
    }

    /**
     * This method closes the "Find and Replace" dialog.
     */
    public static void cancelFindAndReplace()
    {
        find_and_replace_dialog.setVisible(false);
    }

    /**
     * This method is used to pass the main frame to the core class.
     *
     * @param main is the main frame of the application.
     */
    public static void setMain(final JFrame main)
    {
        assert Core.main == null;

        Core.main = main;
    }
}
