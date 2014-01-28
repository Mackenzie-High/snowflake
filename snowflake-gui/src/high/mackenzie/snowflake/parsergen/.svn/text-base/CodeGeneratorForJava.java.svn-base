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

import high.mackenzie.snowflake.Grammar;
import high.mackenzie.snowflake.GrammarBuilder;
import high.mackenzie.snowflake.IGrammarBuilder;
import high.mackenzie.snowflake.IParser;
import high.mackenzie.snowflake.ITreeNodeVisitor;
import high.mackenzie.snowflake.NewlineStyles;
import high.mackenzie.snowflake.ParserOutput;
import high.mackenzie.snowflake.TreeNode;
import high.mackenzie.snowflake.Utils;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * An instance of this class generates Java source code for a parser-file and a visitor-file.
 * 
 * @author Mackenzie High
 */
final class CodeGeneratorForJava implements ICodeGenerator
{
    /**
     * This object builds a dynamic version of the grammar, while source-code is also generated.
     */
    private final GrammarBuilder grammar_builder = new GrammarBuilder();
    
    /**
     * This is the name of the root grammar-rule. 
     */
    private String root_name = null;
    
    /**
     * This is the name of the package that will contain the generated parser and visitor.
     */
    private String package_name = null;
    
    /**
     * This is the name of the generated parser. 
     */
    private String parser_name = null;
    
    /**
     * This is the name of the generated visitor. 
     */
    private String visitor_name = null;
    
    /**
     * This is the number of trace-records the generated parser will store concurrently. 
     */
    private int trace_count = 1024;
    
    /**
     * This set stores the source-code of the statements that initialize character-classes.
     */
    private final Set<String> classes = new LinkedHashSet<String>();
    
    /**
     * This set stores the source-code of the statements that initialize grammar-rules. 
     */
    private final Set<String> grammar = new TreeSet<String>();
    
    /**
     * This set stores the conditions needed to dispatch to visitation methods. 
     */
    private final List<String> dispatch_table = new LinkedList<String>();
    
    /**
     * This set stores the source-code of the visitation methods in the generated visitor.
     */
    private final Set<String> visitor_methods = new TreeSet<String>();
    
    private void addCharacterClass(final String code) { classes.add("    " + "    " + code); }
    
    private void addGrammarRule(final String code) { grammar.add("    " + "    " + code); }
    
    private void addVisitor(final String name)
    { 
        this.addVisitorDispatchTableEntry(name);
        this.addVisitorMethod(name);
    }

    private void addVisitorMethod(final String name)
    {
        if(name.contains("@")) { return; }
        
        final StringBuilder visitor = new StringBuilder();
        
        visitor.append("    /**\n");
        visitor.append("     * This method visits a parse-tree node created by rule \"").append(name).append("\".\n");
        visitor.append("     */\n");
        visitor.append("    protected void visit_").append(name).append("(ITreeNode node)\n");
        visitor.append("    {\n");
        visitor.append("        // You should *not* place your code right here. \n");
        visitor.append("        // Instead, you should override this method via a subclass.\n");
        visitor.append("        visitUnknown(node); // Default Behavior\n");
        visitor.append("    }\n\n");
        
        visitor_methods.add(visitor.toString());
    }
    
    private void addVisitorDispatchTableEntry(final String name)
    {
        if(name.contains("@")) { return; }
        
        final String entry = "if(\"" + name + "\".equals(name)) { visit_" + name + "(node); }";
        
        if(dispatch_table.isEmpty())
        {
            dispatch_table.add(entry);
        }
        else
        {
            dispatch_table.add("else " + entry);
        }
    }
    
    private String getVisitorDispatchTable()
    {
        final StringBuilder result = new StringBuilder();
        
        result.append("\n");
        
        for(String entry : dispatch_table) 
        { 
            result.append("        ").append(entry).append('\n');
        }
        
        result.append("        ").append("else { visitUnknown(node); }\n");
        
        return result.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Grammar build()
    {
        return grammar_builder.build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getParserFile()
    {
        grammar_builder.build();
        
        final StringBuilder parser = new StringBuilder();
        
        final String namespace = package_name == null ? "PACKAGE_NAME" : package_name;
        
        final String name_of_parser = parser_name == null ? "PARSER_NAME" : parser_name;
        
        final String name_of_root = root_name == null ? "root" : root_name;
        
        // Package Declaration
        parser.append("package ").append(namespace).append(";\n");
        parser.append('\n');
        
        // Imports
        parser.append("import ").append(Grammar.class.getName()).append(";\n");
        parser.append("import ").append(GrammarBuilder.class.getName()).append(";\n");
        parser.append("import ").append(IParser.class.getName()).append(";\n");
        parser.append("import ").append(ParserOutput.class.getName()).append(";\n");
        parser.append('\n');
        parser.append('\n');
        
        // Start of Class
        parser.append("public final class ").append(name_of_parser).append(" implements ")
              .append(IParser.class.getSimpleName()).append("\n");
        parser.append("{\n");
        parser.append('\n');
        
        // Generate a field to store the grammar that is used by the parser.
        parser.append("    private static final Grammar grammar = grammar();\n");
        
        parser.append('\n');
        parser.append('\n');
        
        // Generate the method that creates the grammar object used by the parser.
        parser.append("    /**\n");
        parser.append("     * This method constructs the grammar object.\n");
        parser.append("     */\n");
        parser.append("    private static Grammar grammar()\n");
        parser.append("    {\n");
        parser.append("        final GrammarBuilder g = new GrammarBuilder();\n");
        parser.append('\n');
        parser.append("        // Character Classes\n");
        for(String c : classes) { parser.append(c).append("\n"); }
        parser.append('\n');
        parser.append("        // Grammar Rules\n");
        for(String r : grammar) { parser.append(r).append("\n"); }
        parser.append('\n');
        parser.append("        // Specify which rule is the root of the grammar.\n");
        parser.append("        g.setRoot(\"").append(name_of_root).append("\");\n");
        parser.append('\n');
        parser.append("        // Specify the number of tracing records to store concurrently.\n");
        parser.append("        g.setTraceCount(").append(trace_count).append(");\n");
        parser.append('\n');
        parser.append("        // Perform the actual construction of the grammar object.\n");
        parser.append("        return g.build();\n");
        parser.append("    }\n");
        parser.append('\n');
        parser.append('\n');
        
        // Implement the parse(char[]) method. 
        parser.append("    /**\n");
        parser.append("     * {@inheritDoc}\n");
        parser.append("     */\n");
        parser.append("    @Override\n");
        parser.append("    public ParserOutput parse(final char[] input)\n");
        parser.append("    {\n");
        parser.append("        return grammar.newParser().parse(input);\n");
        parser.append("    }\n");        
        parser.append('\n');
        parser.append('\n');
        
        // Implement the parse(String) method.
        parser.append("    /**\n");
        parser.append("     * {@inheritDoc}\n");
        parser.append("     */\n");
        parser.append("    @Override\n");
        parser.append("    public ParserOutput parse(final String input)\n");
        parser.append("    {\n");
        parser.append("        return parse(input.toCharArray());\n");
        parser.append("    }\n"); 
        
        // End of Class
        parser.append("}\n");
        
        return parser.toString().replace("\n", NewlineStyles.fromSystem().newline());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getVisitorFile()
    {
        final StringBuilder visitor = new StringBuilder();

        final String namespace = package_name == null ? "PACKAGE_NAME" : package_name;
        
        final String name_of_visitor = visitor_name == null ? "VISITOR_NAME" : visitor_name;

        // Package 
        visitor.append("package ").append(namespace).append(";\n");
        visitor.append('\n');
        
        // Imports
        visitor.append("import ").append(TreeNode.class.getName()).append(";\n");
        visitor.append("import ").append(ITreeNodeVisitor.class.getName()).append(";\n");
        visitor.append('\n');
        visitor.append('\n');        
        
        // Start of Class
        visitor.append("public abstract class ")
               .append(name_of_visitor).append(" implements ")
               .append(ITreeNodeVisitor.class.getSimpleName()).append("\n");
        visitor.append("{\n");
        visitor.append("\n");
        
        // Implement the visit(ITreeNode) method.
        visitor.append("    /**\n");
        visitor.append("     * {@inheritDoc}\n");
        visitor.append("     */\n");
        visitor.append("    @Override\n");
        visitor.append("    public void visit(ITreeNode node)\n");
        visitor.append("    {\n");
        visitor.append("        final String name = node.rule();\n");
        visitor.append(getVisitorDispatchTable());
        visitor.append("    }\n");
        visitor.append("\n");

        // Implement the visitUnknown(ITreeNode) method.
        visitor.append("    /**\n");
        visitor.append("     * {@inheritDoc}\n");
        visitor.append("     */\n");
        visitor.append("    @Override\n");
        visitor.append("    public void visitUnknown(ITreeNode node)\n");
        visitor.append("    {\n");
        visitor.append("        // You should *not* place your code right here.\n");
        visitor.append("        // Instead, you should override this method via a subclass.\n");
        visitor.append("    }\n");
        visitor.append("\n");
        
        // Add the overridable visitation methods. 
        for(String method : visitor_methods) { visitor.append(method); }
        
        // End of Class
        visitor.append("}\n");
        
        return visitor.toString().replace("\n", NewlineStyles.fromSystem().newline());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder and(String name, String target) 
    {
        grammar_builder.and(name, target);
        
        addVisitor(name);
        
        addGrammarRule("g.and(\"" + name + "\", \"" + target + "\");");
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder not(String name, String target)
    {
        grammar_builder.not(name, target);
        
        addVisitor(name);
        
        addGrammarRule("g.not(\"" + name + "\", \"" + target + "\");");
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder sequence(String name, String... elements)
    {
        grammar_builder.sequence(name, elements);
        
        addVisitor(name);
        
        final StringBuilder xoo = new StringBuilder();
        
        xoo.append("g.sequence(\"").append(name).append("\"");
        
        for(String element : elements)
        {
            xoo.append(", \"").append(element).append("\"");
        }
        
        xoo.append(");");
        
        addGrammarRule(xoo.toString());
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder sequenceDLR(String name, String base, String... shared) 
    {
        grammar_builder.sequenceDLR(name, base, shared);
        
        addVisitor(name);
        
        final StringBuilder xoo = new StringBuilder();
        
        xoo.append("g.sequenceDLR(\"").append(name).append("\"");
        
        xoo.append(", \"").append(base).append("\"");
        
        for(String element : shared)
        {
            xoo.append(", \"").append(element).append("\"");
        }
        
        xoo.append(");");
        
        addGrammarRule(xoo.toString());
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder choose(String name, String... options) 
    {
        grammar_builder.choose(name, options);
        
        addVisitor(name);
        
        final StringBuilder xoo = new StringBuilder();
        
        xoo.append("g.choose(\"").append(name).append("\"");
        
        for(String option : options)
        {
            xoo.append(", \"").append(option).append("\"");
        }
        
        xoo.append(");");
        
        addGrammarRule(xoo.toString());
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder repeat(String name, String target, int minimum, int maximum) 
    {
        grammar_builder.repeat(name, target, minimum, maximum);
        
        addVisitor(name);
        
        final StringBuilder xoo = new StringBuilder();
        
        xoo.append("g.repeat(")
           .append("\"").append(name).append("\"")
           .append(", ")
           .append("\"").append(target).append("\"")
           .append(", ")
           .append(minimum)
           .append(", ")
           .append(maximum)
           .append(");");
        
        addGrammarRule(xoo.toString());
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder star(String name, String target) 
    {
        return repeat(name, target, 0, Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder plus(String name, String target)
    {
        return repeat(name, target, 1, Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder option(String name, String target) 
    {
        return repeat(name, target, 0, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder str(String name, String string) 
    {
        grammar_builder.str(name, string);
        
        addVisitor(name);
        
        addGrammarRule("g.str(\"" + name + "\", \"" + string + "\");");
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder chr(String name, String clazz)
    {
        grammar_builder.chr(name, clazz);
        
        addVisitor(name);
        
        addGrammarRule("g.chr(\"" + name + "\", \"" + clazz + "\");");
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder range(String name, char character) 
    {           
        return range(name, character, character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder range(String name, char minimum, char maximum) 
    {
        grammar_builder.range(name, minimum, maximum);
        
        addCharacterClass("g.range(\"" + name + "\", " 
                                       + "(char) " + (int) minimum + ", " 
                                       + "(char) " + (int) maximum + ");");   
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder combine(String name, String... elements) 
    {
        grammar_builder.combine(name, elements);
        
        final StringBuilder builder = new StringBuilder();
        
        builder.append("g.combine(");
        
        builder.append('"').append(name).append('"');
        
        for(String element : elements) 
        { 
            builder.append(", ").append('"').append(element).append('"');
        }
       
        builder.append(");");
        
        final String result = builder.toString().replace("(, ", "");
        
        addCharacterClass(result);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder exclude(String name, String include, String exclude) 
    {
        grammar_builder.exclude(name, include, exclude);
        
        final StringBuilder builder = new StringBuilder();
        
        builder.append("g.exclude(");
        
        builder.append('"').append(name).append('"');
        
        builder.append(", ");
        
        builder.append('"').append(include).append('"');
        
        builder.append(", ");
        
        builder.append('"').append(exclude).append('"');
        
        builder.append(");");

        addCharacterClass(builder.toString());
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder negate(String name, String negates) 
    {
        grammar_builder.negate(name, negates);
        
        final StringBuilder builder = new StringBuilder();
        
        builder.append("g.negate(");
        
        builder.append('"').append(name).append('"');
        
        builder.append(", ");
        
        builder.append('"').append(negates).append('"');
        
        builder.append(");");

        addCharacterClass(builder.toString());
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICodeGenerator setPackageName(String name) 
    {
        Utils.checkNonNull(name);
        
        if(package_name != null)
        {
            throw new IllegalStateException("The package name was already specified.");
        }
        else
        {
            this.package_name = name;
        }
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICodeGenerator setParserName(String name) 
    {
        Utils.checkNonNull(name);
        
        if(parser_name != null)
        {
            throw new IllegalStateException("The parser name was already specified.");
        }
        else
        {
            this.parser_name = name;
        }
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICodeGenerator setVisitorName(String name) 
    {
        Utils.checkNonNull(name);
        
        if(visitor_name != null)
        {
            throw new IllegalStateException("The visitor name was already specified.");
        }
        else
        {
            this.visitor_name = name;
        }
        
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder setRoot(String name)
    {
        Utils.checkNonNull(name);
        
        if(root_name != null)
        {
            throw new IllegalStateException("The root rule was already specified.");
        }
        else
        {
            this.grammar_builder.setRoot(name);
            
            this.root_name = name;
        }
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder setTraceCount(int count) 
    {
        if(count <= 0) 
        {
            throw new IllegalArgumentException("A negative trace-count was specified.");
        }
        else
        {
            this.grammar_builder.setTraceCount(count);
            
            this.trace_count = count;
        }
        
        return this;
    }
    
}
