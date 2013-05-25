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
import high.mackenzie.snowflake.parsergen.ParserGenerator;
import high.mackenzie.snowflake.ParserOutput;
import high.mackenzie.snowflake.designer.gui.tabs.ConcreteSyntaxTreePanel;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import high.mackenzie.snowflake.designer.gui.tabs.EditorPanel;
import high.mackenzie.snowflake.designer.io.StringFile;
import java.awt.Color;
import java.awt.Font;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;

/**
 * This class centralizes the logic behind the GUI. 
 * 
 * @author Michael Mackenzie High
 */
public final class Core 
{   
    
    private static boolean initialized = false;
    
    private static final JFileChooser dialog;

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
    
    private static Thread parse_thread;
    
    public static JTabbedPane getTabs() { return tabs; }
    
    public static JLabel getLineNumber() { return line_number; }
    
    public static JLabel getColumnNumber() { return column_number; }
    
    
    
    static
    {
        initialized = false;
        
        dialog = Core.isJNLP() ? null : new JFileChooser();
        
        tabs.add("Grammar", grammar);
        tabs.add("Input", input);
        tabs.add("Parse Tree", parse_tree);
        tabs.add("Output", output);
        tabs.add("Generated Parser", generated_parser);
        tabs.add("Generated Visitor", generated_visitor);
        tabs.add("License", license);
       
        tabs.addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent e) { Core.refresh(); }
        });
        
        final Font font = new Font("Monospaced", Font.PLAIN, 14);
        
        Core.input.getTextArea().setFont(font);
        
        Core.grammar.getTextArea().setFont(font);
        
        Core.output.getTextArea().setEditable(false);
        
        license.getTextArea().setEditable(false);
        license.getTextArea().setText("Copyright 2013 Michael Mackenzie High\n\nLicensed under the Apache License, Version 2.0 (the \"License\");\nyou may not use this file except in compliance with the License.\nYou may obtain a copy of the License at\n\n     http://www.apache.org/licenses/LICENSE-2.0\n\nUnless required by applicable law or agreed to in writing, software\ndistributed under the License is distributed on an \"AS IS\" BASIS,\nWITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\nSee the License for the specific language governing permissions and\nlimitations under the License.".replace("\n", NewlineStyles.fromSystem().newline()));
        
        Core.parse_tree.getTextArea().setFont(font);
        Core.parse_tree.getTextArea().setEditable(false);
        
        initialized = true;
    }
    
    /**
     * An instance of this class is run in order to implement Action->Parse. 
     */
    private static final class ParseRunner implements Runnable
    {
        private static class BadGrammarException extends Exception { }

        private static class ParsingFailedException extends Exception { }
        
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
        final String strGrammar = grammar.getTextArea().getText();

        /**
         * Get the input from the "Input" tab.
         */
        final String strInput = input.getTextArea().getText();

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
            output.getTextArea().setForeground(Color.BLACK);
            output.getTextArea().setText((
                    "Please wait, parsing may take a moment.\n"
                  + "\n"
                  + "If the program appears to freeze, your grammar most likely contains a bug.\n"
                  + "After a few minutes max, the program will run out of heap space.\n"
                  + "As a result, parsing will be forced to stop and the program will unfreeze.\n"
                  + "\n"
                  + "Example of a buggy grammar:\n"
                  + "  root = moo *;\n"
                  + "  moo = \"\";\n").replace("\n", NewlineStyles.fromSystem().newline()));
            output.getTextArea().setCaretPosition(0);
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
                catch(BadGrammarException ex)
                { 
                    this.reportBadGrammar();
                }
                catch(ParsingFailedException ex)
                {
                    this.reportParsingFailed();
                }
                catch(StackOverflowError ex)
                {
                    this.reportStackOverflow(ex);
                }
                catch(Throwable ex)
                {
                    this.reportUnexpectedException(ex);
                }
                  
                // Make sure the streams are flushed.
                writer.flush();
                stdout.flush();

                // Display the standard-output via the GUI's "Output" tab.
                output.getTextArea().setText(stdout.toString());
                output.getTextArea().setCaretPosition(0);

                // Stop blocking the parse action.
                Core.parse_thread = null;              
            }
            catch(RuntimeException t)
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
        private void createParser() throws BadGrammarException
        {
            // Create a parser generator in order to create the user-specified parser. 
            final ParserGenerator pg = ParserGenerator.forJava(writer);

            // Use the parser generator to dynamically create a grammar object. 
            final Grammar dynamic_grammar = pg.parseGrammar(strGrammar); 

            // If the grammar object could not be created, 
            // then the user-specified grammar must invalid. 
            if(dynamic_grammar == null) { throw new BadGrammarException(); }

            // Dynamically create a parser based on the grammar from the "Grammar" tab. 
            parser = dynamic_grammar.newParser();
        }
        
        /**
         * This method uses the dynamically created parser to parse the user-specified input. 
         */
        private void parseInput() throws ParsingFailedException
        {
            // Parse the user-specified input.
            parser_output = parser.parse(strInput);
            
            // If the parsing attempt failed, then it must be reported to the user. 
            if(parser_output.success() == false) { throw new ParsingFailedException(); }
        }
        
        /**
         * This method reports a malformed grammar to the user. 
         */
        private void reportBadGrammar()
        {
            // Note: The parser-generator has already written the error info to the print-stream.
            
            output.getTextArea().setForeground(Color.RED);
            
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
            parse_tree.getTextArea().setText("");
            
            // Since the parsing attempt was successful, the output color must be black.
            output.getTextArea().setForeground(Color.BLACK);

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
            parser_output.print(writer, true, true, true);
            
            // Since an error is being reported, the output color must be red. 
            output.getTextArea().setForeground(Color.RED);
            
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
            output.getTextArea().setForeground(Color.RED);
            
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
            output.getTextArea().setForeground(Color.RED);
            
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
        // If the program was started via web start, then this feature is unavailable. 
        if(Core.isJNLP()) { return; }
        
        // Display the dialog to the user.
        dialog.showOpenDialog(null);

        // Retrieve the file that the user selected. 
        final File file = dialog.getSelectedFile();
        
        // If the user did not select a file, then there is nothing else that can be done.
        // Likewise, nothing else can be done, if the selected file does not exist. 
        if(file == null || !file.exists()) { return; }
        
        // The project file is actually a .zip file. 
        // This list will contain the files inside the project .zip file. 
        List<StringFile> zip = null;
        
        try
        {
            // Read the project file. 
            zip = StringFile.readZipFile(file);
            
            // Only two of the files inside the .zip file will be loaded.
            // Firstly, the "Grammar.txt" file must be loaded into the "Grammar" tab.
            // Secondly, the "Input.txt" file must be loaded into the "Input" tab. 
            for(StringFile stringFile : zip)
            {
                if(stringFile.getFilepath().equals(new File("Grammar.txt")))
                {
                    grammar.getTextArea().setText(stringFile.getSourceCode());
                }

                if(stringFile.getFilepath().equals(new File("Input.txt")))
                {
                    input.getTextArea().setText(stringFile.getSourceCode());
                }            
            }  
            
            // Save the path to the file in case the user wishes to save the file again later. 
            current_file = file;
        }
        catch(Exception ex)
        {
            // Display the error via the output tab. 
            output.getTextArea().setForeground(Color.RED);
            output.getTextArea().setText(ex.toString());
            output.getTextArea().setCaretPosition(0);
            tabs.setSelectedComponent(output); 
            return;
        }
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
        // If the program was started via web start, then this feature is unavailable. 
        if(Core.isJNLP()) { return; }
        
        // Display the dialog to the user.
        dialog.showSaveDialog(null);
        
        // Retrieve the file selected by the user.
        final File file = dialog.getSelectedFile();
        
        // If the user did not select a file, then no action can be performed. 
        if(file == null) { return; }        
        
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
        // If the program was started via web start, then this feature is unavailable. 
        if(Core.isJNLP()) { return; }
        
        if(current_file == null)
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
        final StringFile fileGrammar = new StringFile();
        fileGrammar.setFilepath(new File("Grammar.txt"));
        fileGrammar.setSourceCode(grammar.getTextArea().getText());
        
        // Retrieve the content of the "Input" tab.
        final StringFile fileInput = new StringFile();
        fileInput.setFilepath(new File("Input.txt"));
        fileInput.setSourceCode(input.getTextArea().getText());
        
        // Retrieve the content of the "Generated Parser" tab.
        final StringFile fileParser = new StringFile();
        fileParser.setFilepath(new File("Parser.java"));
        fileParser.setSourceCode(generated_parser.getTextArea().getText());
        
        // Retrieve the content of the "Generated Visitor" tab.
        final StringFile fileVisitor = new StringFile();
        fileVisitor.setFilepath(new File("Visitor.java"));
        fileVisitor.setSourceCode(generated_visitor.getTextArea().getText());
        
        // Create a list of the files that will be placed into the .zip file.
        final List<StringFile> files = Arrays.asList(fileGrammar, 
                                                     fileInput,
                                                     fileParser,
                                                     fileVisitor);
               
        try
        {
            // Write the project file, which is really a .zip file. 
            StringFile.writeZipFile(file, files);
            
            // Save the path to the file in case the user wishes to save the file again later. 
            current_file = file;
        }
        catch(Exception ex)
        {
            // Display the error via the "Output" tab. 
            output.getTextArea().setForeground(Color.RED);
            output.getTextArea().setText(ex.toString());
            output.getTextArea().setCaretPosition(0);
            tabs.setSelectedComponent(output); 
        }      
    }
    
    /**
     * This method implements the "Parse" action. 
     * 
     * <p>This method is synchronized to prevent multiple parsers from running simultaneously.</p>
     */
    public static synchronized void parse()
    {
        // Do not allow multiple parsing threads to be running concurrently. 
        if(parse_thread != null) { return; }
        
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

            pg.parseGrammar(grammar.getTextArea().getText());
            
            generated_parser.getTextArea().setText(pg.getParserFile());
            generated_parser.getTextArea().setCaretPosition(0);
            
            generated_visitor.getTextArea().setText(pg.getVisitorFile());
            generated_visitor.getTextArea().setCaretPosition(0);
            
            output.getTextArea().setForeground(Color.BLACK);
            output.getTextArea().setText("Your parser and visitor were successfully generated.");
            
            tabs.setSelectedComponent(generated_parser);
        }
        catch(RuntimeException ex)
        {
            // Display the error via the "Output" tab. 
            output.getTextArea().setForeground(Color.RED);
            output.getTextArea().setText(ex.toString());
            output.getTextArea().setCaretPosition(0);
            tabs.setSelectedComponent(output); 
        }
    }
    
    /**
     * This method refreshes information on the currently selected tab, whenever a change occurs.
     */
    public static void refresh()
    {
        if(initialized == false) { return; }
        
        if(tabs.getSelectedComponent().equals(grammar))
        {
            refreshPosition(grammar.getTextArea());
        }
        else if(tabs.getSelectedComponent().equals(input))
        {
            refreshPosition(input.getTextArea());
        }
        else if(tabs.getSelectedComponent().equals(output))
        {
            refreshPosition(output.getTextArea());
        }
        else if(tabs.getSelectedComponent().equals(generated_parser))
        {
            refreshPosition(generated_parser.getTextArea());
        }
        else if(tabs.getSelectedComponent().equals(generated_visitor))
        {
            refreshPosition(generated_visitor.getTextArea());
        }        
        else
        {
            line_number.setText("");
            column_number.setText("");
        }
    }
    
    /**
     * This method refreshes the line-number and column-number label, given a text-box. 
     * 
     * @param text is the text-box whose line and column numbers shall be displayed.
     */
    private static void refreshPosition(final JTextArea text)
    {
        final int position = text.getCaretPosition();
        
        final LinesAndColumns finder = new LinesAndColumns((text.getText() + "$").toCharArray(), 
                                                           NewlineStyles.fromSystem());
        
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
    private static String zfill(int length, int data)
    {
        String number = "" + data;
        
        while(number.length() < length) { number = "0" + number; }
    
        return number;
    }
    
    /**
     * This program uses a hack to determine whether the program was started via web start. 
     * 
     * @return true, if it is reasonable that the program was started via web start. 
     */
    public static boolean isJNLP()
    {
        try
        {
            Class.forName("javax.jnlp.ServiceManager");
            return true;
        }
        catch(ClassNotFoundException ex)
        {
            return false;
        }
    }
}
