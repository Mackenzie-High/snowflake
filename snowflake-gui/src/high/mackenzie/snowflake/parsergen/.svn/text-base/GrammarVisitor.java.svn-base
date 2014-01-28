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

import high.mackenzie.snowflake.ITreeNode;
import high.mackenzie.snowflake.TreeNode;
import high.mackenzie.snowflake.Utils;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * An instance of this class invokes grammar-builder methods, given a grammar's syntax-tree.
 * 
 * @author Mackenzie High
 */
final class GrammarVisitor
{

    /**
     * This stack is used to store intermediate results, 
     * which are the either the names of anonymous rules or character-classes. 
     */
    final Stack<String> operands = new Stack<String>();
    
    /**
     * This is the code-generator that will be notified of each significant visitation.
     */
    final ICodeGenerator pigeon;
    
    /**
     * This counter is used to create names for anonymous grammar-rules.
     */
    int anon_counter = 0;
    
    /**
     * This counter is used to create names for character-classes.
     */
    int class_counter = 0;
    
    /**
     * Sole Constructor.
     * 
     * @param code is the code-generator that will be invoked by this visitor. 
     */
    public GrammarVisitor(final ICodeGenerator code)
    {
        Utils.checkNonNull(code);
        
        this.pigeon = code;
    }
    
    /**
     * This method dispatches the visitation of a node to specific visitation methods.     
     * 
     * @param node is the node to visit. 
     */
    final void visit(ITreeNode node)
    {   

        if("directive_root".equals(node.rule()))
        {
            this.visit_directiveRoot(node);
        }
        else if("directive_trace".equals(node.rule()))
        {
            this.visit_directiveTrace(node);
        }
        else if("directive_package".equals(node.rule()))
        {
            this.visit_directivePackage(node);
        }
        else if("directive_parser".equals(node.rule()))
        {
            this.visit_directiveParser(node);
        }
        else if("directive_visitor".equals(node.rule()))
        {
            this.visit_directiveVisitor(node);
        }
        else if("name".equals(node.rule()))
        {
            operands.push(node.text());
        }
        else if("single_cc".equals(node.rule()))
        {
            this.visit_classSingle(node);
        }
        else if("range_cc".equals(node.rule()))
        {
            this.visit_classRange(node);
        }
        else if("combination_cc".equals(node.rule()))
        {
            this.visit_classCombination(node);
        }
        else if("negation_cc".equals(node.rule()))
        {
            this.visit_classNegation(node);
        }
        else if("exclusion_cc".equals(node.rule()))
        {
            this.visit_classExclusion(node);
        }
        else if("named_string_rule".equals(node.rule()))
        {
            this.visit_namedString(node);
        }
        else if("named_character_rule".equals(node.rule()))
        { 
            this.visit_namedCharacter(node);
        }
        else if("named_sequence_rule".equals(node.rule()))
        {
            this.visit_namedSequence(node);
        }
        else if("named_sequencedlr_rule".equals(node.rule()))
        {
            this.visit_namedSequenceDLR(node);
        }
        else if("named_choice_rule".equals(node.rule()))
        {
            this.visit_namedChoice(node);
        }
        else if("named_option_rule".equals(node.rule()))
        {
            this.visit_namedOption(node);
        }
        else if("named_star_rule".equals(node.rule()))
        {
            this.visit_namedStar(node);
        }
        else if("named_plus_rule".equals(node.rule()))
        {
            this.visit_namedPlus(node);
        }
        else if("named_repetition_rule".equals(node.rule()))
        {
            this.visit_namedRepetition(node);
        }
        else if("named_and_rule".equals(node.rule()))
        {
            this.visit_namedAnd(node);
        }
        else if("named_not_rule".equals(node.rule()))
        {
            this.visit_namedNot(node);
        } 
        else if("anon_string_rule".equals(node.rule()))
        {
            this.visit_anonString(node);
        }
        else if("anon_character_rule".equals(node.rule()))
        {
            this.visit_anonCharacter(node);
        }
        else if("anon_sequence_rule".equals(node.rule()))
        {
            this.visit_anonSequence(node);
        }
        else if("anon_sequencedlr_rule".equals(node.rule()))
        {
            this.visit_anonSequenceDLR(node);
        }
        else if("anon_choice_rule".equals(node.rule()))
        {
            this.visit_anonChoice(node);
        }
        else if("anon_option_rule".equals(node.rule()))
        {
            this.visit_anonOption(node);
        }
        else if("anon_star_rule".equals(node.rule()))
        {
            this.visit_anonStar(node);
        }
        else if("anon_plus_rule".equals(node.rule()))
        {
            this.visit_anonPlus(node);
        }
        else if("anon_repetition_rule".equals(node.rule()))
        {
            this.visit_anonRepetition(node);
        }
        else if("anon_and_rule".equals(node.rule()))
        {
            this.visit_anonAnd(node);
        }
        else if("anon_not_rule".equals(node.rule()))
        {
            this.visit_anonNot(node);
        }
        else
        {
            for(ITreeNode child : node.children()) { visit(child); }
        }
    }

    /**
     * This method performs the visitation of a %root directive.
     * 
     * @param node is the node to visit. 
     */
    final void visit_directiveRoot(final ITreeNode node)
    {
        // Retrieve the user-specified name of the root grammar rule.
        final String root = find(node, "name").text().trim();
        
        pigeon.setRoot(root);
    }
    
    /**
     * This method performs the visitation of a %trace directive.
     * 
     * @param node is the node to visit. 
     */
    final void visit_directiveTrace(final ITreeNode node)
    {
        // Retrieve the user-specified number of trace records to store simultaneously. 
        final int count = Integer.parseInt(find(node, "digits").text().trim());
        
        pigeon.setTraceCount(count);
    }
    
    /**
     * This method performs the visitation of a %package directive. 
     * 
     * @param node is the node to visit. 
     */
    final void visit_directivePackage(final ITreeNode node)
    {
        // Retrieve the user-specified name of the package that the parser will be a part of. 
        final String namespace = TreeNode.find(node, "string_body").text();
        
        pigeon.setPackageName(namespace);
    }
    
    /**
     * This method performs the visitation of a %parser directive. 
     * 
     * @param node is the node to visit. 
     */
    final void visit_directiveParser(final ITreeNode node)
    {
        // Retrieve the user-specified name of the parser.
        final String parser_name = TreeNode.find(node, "string_body").text();
        
        pigeon.setParserName(parser_name);
    }
    
    /**
     * This method performs the visitation of a %visitor directive. 
     * 
     * @param node is the node to visit. 
     */
    final void visit_directiveVisitor(final ITreeNode node)
    {
        // Retrieve the user-specified name of the visitor. 
        final String visitor_name = TreeNode.find(node, "string_body").text();
        
        pigeon.setVisitorName(visitor_name);
    }
    
    /**
     * This method performs the visitation of a named string-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedString(final ITreeNode node)
    {
        // Retrieve the name of the new string rule.
        final String name = find(node, "assignee").text();
        
        // Retrieve the value of the new string rule. 
        final String value = find(node, "string_body").text();
        
        // Create the new string rule. 
        pigeon.str(name, value);
    }
    
    /**
     * This method performs the visitation of a named character-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedCharacter(final ITreeNode node)
    {
        // Visit the character-class.
        visitChildren(node);
        
        // Retrieve the name of the new character rule. 
        final String name = find(node, "assignee").text();

        // Retrieve the name of the character-class that the new rule will match.
        final String clazz = operands.pop().toString();
        
        // Create the new character rule. 
        pigeon.chr(name, clazz);
    }
    
    /**
     * This method performs the visitation of a named sequence-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedSequence(final ITreeNode node)
    {
        // Visit the elements in the sequence.  
        visitChildren(node);
        
        // Retrieve the name of the new sequence rule. 
        final String name = find(node, "assignee").text();
        
        // Retrieve the names of the elements in the new sequence rule. 
        final List<String> elements = pop(0);
        
        // Create the new sequence rule. 
        pigeon.sequence(name, elements.toArray(new String[0]));
    }
    
    /**
     * This method performs the visitation of a named sequence-dlr-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedSequenceDLR(final ITreeNode node)
    {
        // Visit the base-case and the shared elements of the sequence. 
        visitChildren(node);
        
        // Retrieve the name of the new sequence-DLR rule.
        final String name = find(node, "assignee").text();
        
        // These are the shared elements of the sequence. 
        final List<String> shared = pop(1);
        
        // Retrieve the base-case element of the sequence. 
        final String base = operands.pop().toString();

        // Create the new sequence-DLR rule. 
        pigeon.sequenceDLR(name, base, shared.toArray(new String[0]));
    }
    
    /**
     * This method performs the visitation of a named choice-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedChoice(final ITreeNode node)
    {
        // Visit the options. 
        visitChildren(node);
        
        // Retrieve the name of the new choice rule. 
        final String name = find(node, "assignee").text();
        
        // Retrieve the names of the options in the new choice rule. 
        final List<String> options = pop(0);
        
        // Create the new choice rule. 
        pigeon.choose(name, options.toArray(new String[0]));
    }
    
    /**
     * This method performs the visitation of a named option-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedOption(final ITreeNode node)
    {
        // Visit the item. 
        visitChildren(node);
        
        // Retrieve the name of the new option rule. 
        final String name = find(node, "assignee").text();
        
        // Retrieve the name of the item to match.
        final String item = operands.pop();
        
        // Create the new option rule. 
        pigeon.option(name, item);
    }
    
    /**
     * This method performs the visitation of a named star-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedStar(final ITreeNode node)
    {
        // Visit the item.
        visitChildren(node);
        
        // Retrieve the name of the new star rule. 
        final String name = find(node, "assignee").text();
        
        // Retrieve the name of the item to match. 
        final String item = operands.pop();
        
        // Create the new star rule. 
        pigeon.star(name, item);
    }
    
    /**
     * This method performs the visitation of a named plus-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedPlus(final ITreeNode node)
    {
        // Visit the item. 
        visitChildren(node);
        
        // Retrieve the name of the new plus rule. 
        final String name = find(node, "assignee").text();
        
        // Retrieve the name of the item to match. 
        final String item = operands.pop();
        
        // Create the new plus rule. 
        pigeon.plus(name, item);
    }
    
    /**
     * This method performs the visitation of a named repetition-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedRepetition(final ITreeNode node)
    {
        // Visit the item. 
        visitChildren(node);
        
        // Retrieve the name of the new repetition rule. 
        final String name = find(node, "assignee").text();
        
        // Retrieve the name of the item to match. 
        final String item = operands.pop();
        
        // Retrieve the minimum number of times to repeat the item. 
        final String minimum = find(find(node, "repetition_minimum"), "digits").text().trim();
        
        // Retrieve the maximum number of times to repeat the item. 
        final String maximum = find(find(node, "repetition_maximum"), "digits").text().trim();

        final int min = Integer.parseInt(minimum);
        
        final int max = Integer.parseInt(maximum);
        
        // Create the new repetition rule. 
        pigeon.repeat(name, item, min, max);
    }
    
    /**
     * This method performs the visitation of a named and-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedAnd(final ITreeNode node)
    {
        // Visit the item. 
        visitChildren(node);
        
        // Retrieve the name of the new and-rule. 
        final String name = find(node, "assignee").text();
        
        // Retrieve the name of the item to match. 
        final String item = operands.pop();
        
        // Create the new and-rule. 
        pigeon.and(name, item);
    }
    
    /**
     * This method performs the visitation of a named not-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_namedNot(final ITreeNode node)
    {
        // Visit the item. 
        visitChildren(node);
        
        // Retrieve the name of the new not-rule.
        final String name = find(node, "assignee").text();
        
        // Retrieve the name of the item to match. 
        final String item = operands.pop();
        
        // Create the new not-rule. 
        pigeon.not(name, item);
    }
    
    /**
     * This method performs the visitation of an anonymous string-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonString(final ITreeNode node)
    {
        // Create the name of the new string rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the value of the new string rule. 
        final String value = find(node, "string_body").text();
        
        // Create the new string rule. 
        pigeon.str(name, value);
        
        // Push the name of the new rule onto the operand stack. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous character-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonCharacter(final ITreeNode node)
    {
        // Visit the character-class.
        visitChildren(node);
        
        // Create the name of the new character rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the character-class that the new rule will match.
        final String clazz = operands.pop().toString();
        
        // Create the new character rule. 
        pigeon.chr(name, clazz);
        
        // Push the name of the new rule onto the operand stack. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous sequence-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonSequence(final ITreeNode node)
    {
        // The current operands are not a part of the sequence. 
        final int stack_size = operands.size();
        
        // Visit the elements in the sequence. 
        visitChildren(node);
        
        // Create the name of the new sequence rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the names of the elements in the sequence. 
        final List<String> elements = pop(stack_size);
        
        // Create the new sequence rule. 
        pigeon.sequence(name, elements.toArray(new String[0]));
        
        // The name of the new rule is an operand of another rule. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous sequence-dlr-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonSequenceDLR(final ITreeNode node)
    {
        // The current operands are not a part of the sequence. 
        final int stack_size = operands.size();
        
        // Visit the base-case and shared elements of the sequence. 
        visitChildren(node);
        
        // Create the name of the new sequence-DLR rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the shared elements of the sequence. 
        final List<String> shared = pop(stack_size + 1);
        
        // Retrieve the base-case of the sequence. 
        final String base = operands.pop();
        
        // Create the new sequence-DLR rule. 
        pigeon.sequenceDLR(name, base, shared.toArray(new String[0]));
        
        // The name of the new rule is an operand of another rule. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous choice-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonChoice(final ITreeNode node)
    {
        // The current operands are not options in the choice. 
        final int stack_size = operands.size();
        
        // Visit the options in the choice. 
        visitChildren(node);
        
        // Create the name of the new choice-rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the options in the choice.
        final List<String> options = pop(stack_size);
        
        // Create the new choice-rule. 
        pigeon.choose(name, options.toArray(new String[0]));
        
        // The name of the new rule is an operand of another rule. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous option-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonOption(final ITreeNode node)
    {
        // Visit the item.
        visitChildren(node);
        
        // Create the name of the new option rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the name of the item.
        final String item = operands.pop().toString();

        // Create the new option rule. 
        pigeon.option(name, item);
        
        // Push the name of the new option rule onto the operand stack.
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous star-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonStar(final ITreeNode node)
    {
        // Visit the item.
        visitChildren(node);
        
        // Create the name of the new star rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the name of the item.
        final String item = operands.pop().toString();
        
        // Create the new star rule. 
        pigeon.star(name, item);
        
        // Push the name of the new rule onto the operand stack. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous plus-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonPlus(final ITreeNode node)
    {
        // Visit the item.
        visitChildren(node);
        
        // Create the name of the new plus rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the name of the item.
        final String item = operands.pop().toString();

        // Create the new plus rule. 
        pigeon.plus(name, item);
        
        // Push the name of the new rule onto the operand stack. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous repetition-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonRepetition(final ITreeNode node)
    {
        // Visit the item. 
        visitChildren(node);
        
        // Create the name of the new repetition rule.
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the name of the item to match. 
        final String item = operands.pop();
        
        // Retrieve the minimum number of times to repeat the item. 
        final String minimum = find(find(node, "repetition_minimum"), "digits").text().trim();
        
        // Retrieve the maximum number of times to repeat the item. 
        final String maximum = find(find(node, "repetition_maximum"), "digits").text().trim();

        final int min = Integer.parseInt(minimum);
        
        final int max = Integer.parseInt(maximum);
        
        // Create the new repetition rule. 
        pigeon.repeat(name, item, min, max);
        
        // Push the name of the new rule onto the operand stack. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous and-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonAnd(final ITreeNode node)
    {
        // Visit the item. 
        visitChildren(node);
        
        // Create the name of the new and-rule.
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the name of the item.
        final String item = operands.pop().toString();

        // Create the new and-rule.
        pigeon.and(name, item);
        
        // Push the name of the new rule onto the operand stack. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of an anonymous not-rule.
     * 
     * @param node is the node to visit. 
     */
    final void visit_anonNot(final ITreeNode node)
    {
        // Visit the item.
        visitChildren(node);
        
        // Create the name of the new not-rule. 
        final String name = '@' + Integer.toString(anon_counter++);
        
        // Retrieve the name of the item. 
        final String item = operands.pop().toString();
   
        // Create the new not-rule. 
        pigeon.not(name, item);
        
        // Push the name of the new rule onto the operand stack. 
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of a single-character character-class.
     * 
     * @param node is the node to visit. 
     */
    final void visit_classSingle(final ITreeNode node)
    {
        // Compute the value of the represented character.
        final char character = extractChar(node.text());
        
        // Create the name of the character-class. 
        final String name = "@class" + Integer.toString(class_counter++);

        // Create the new character-class.
        pigeon.range(name, character);

        // The name of the new character-class is an operand, so push it onto the operand-stack.
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of a multiple-character character-class.
     * 
     * @param node is the node to visit. 
     */
    final void visit_classRange(final ITreeNode node)
    {
        // Compute the value of the minimum character in the range.
        final char minimum = extractChar(node.childAt(0).text());
        
        // Compute the value of the maximum character in the range.
        final char maximum = extractChar(node.childAt(4).text());
        
        // Create the name of the new character-class.
        final String name = "@class" + Integer.toString(class_counter++);

        // Create the new character-class itself. 
        pigeon.range(name, minimum, maximum);
        
        // The name of the new character-class is an operand, so push it onto the operand-stack.
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of a combination character-class.
     * 
     * @param node is the node to visit. 
     */
    final void visit_classCombination(final ITreeNode node)
    {
        // The current operands are not a part of the combination. 
        int stack_size = operands.size();
        
        // Visit the elements of the combination.
        visitChildren(node);

        // Create the name of the new character-class.
        final String name = "@class" + Integer.toString(class_counter++);
        
        // Retrieve the elements of the combination. 
        final List<String> elements = pop(stack_size);

        // Create the new character-class. 
        pigeon.combine(name, elements.toArray(new String[0]));
        
        // The name of the new character-class is an operand, so push it onto the operand-stack.
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of a negation character-class.
     * 
     * @param node is the node to visit. 
     */
    final void visit_classNegation(final ITreeNode node)
    {
        // The current operands are not a part of the negation. 
        int stack_size = operands.size();
        
        // Visit the elements of the negation. 
        visitChildren(node);

        // Retrieve the elements of the negation. 
        final List<String> elements = pop(stack_size);
        
        // It is necessary to combine the elements into a single character-class.
        // Create the name of the aforedescribed character-class. 
        final String negated_name = "@class" + Integer.toString(class_counter++);
        
        // Create the name of the negation character-class. 
        final String name = "@class" + Integer.toString(class_counter++);
   
        // Create the combination character-class. 
        pigeon.combine(negated_name, elements.toArray(new String[0]));
        
        // Create the negation character-class, which negates the combination character-class. 
        pigeon.negate(name, negated_name);
        
        // The name of the new character-class is an operand, so push it onto the operand-stack.
        operands.push(name);
    }
    
    /**
     * This method performs the visitation of a exclusion character-class.
     * 
     * @param node is the node to visit. 
     */
    final void visit_classExclusion(final ITreeNode node)
    {
        // Visit the includes and excludes parts of the exclusion. 
        visitChildren(node);
        
        // Retrieve the character-class that is excluded. 
        final String excludes = operands.pop().toString();
        
        // Retrieve the character-class from which characters are excluded. 
        final String includes =  operands.pop().toString();
        
        // Create the name of the new character-class. 
        final String name = "@class" + Integer.toString(class_counter++);

        // Create the new character-class. 
        pigeon.exclude(name, includes, excludes);
        
        // The name of the new character-class is an operand, so push it onto the operand-stack.
        operands.push(name);
    }
    
    final List<String> pop(final int stack_size)
    {
        final LinkedList<String> result = new LinkedList<String>();
        
        while(stack_size < operands.size()) { result.addFirst(operands.pop()); }
        
        return result;
    }
    
    /**
     * This method performs a depth-first search in order to find a node in an AST.
     * 
     * <p>This method returns the first match that it finds.</p>
     * 
     * @param parent is the root of the subtree.
     * @param name is the name of the rule that created the node to find.
     * @return the found node, or null, if no such node was found.
     */
    final ITreeNode find(final ITreeNode parent, final String name)
    {
        for(ITreeNode child : parent.iterableDFS())
        {
            if(child.rule().equals(name)) { return child; }
        }
        
        return null;
    }
    
    /**
     * This method invokes the generalized visitation method on all the children of a node.
     * 
     * @param parent is the node whose children will be visited. 
     */
    final void visitChildren(final ITreeNode parent)
    {
        for(ITreeNode child : parent.children()) { visit(child); }
    }
  
    /**
     * This method converts a character-literal to a char value. 
     *
     * <p>
     * If the character is of the form 'X', then the value is the numeric form of X.
     * If the character is of the form 123C, then the value is 123. 
     * If the character is of the form 
     * </p>
     * 
     * @param literal is the literal itself. 
     * @return the aforedescribed value.
     */
    final char extractChar(String literal)
    {
        literal = literal.trim();
        
        if(literal.equals("MIN")) { return Character.MIN_VALUE; }
        
        if(literal.equals("MAX")) { return Character.MAX_VALUE; }
        
        return literal.matches("[0-9]+C") 
             ? (char) Integer.parseInt(literal.replace("C", "")) 
             : literal.charAt(1);
    }
}
