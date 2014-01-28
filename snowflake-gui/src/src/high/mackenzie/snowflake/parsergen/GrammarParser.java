package high.mackenzie.snowflake.parsergen;

import high.mackenzie.snowflake.Grammar;
import high.mackenzie.snowflake.GrammarBuilder;
import high.mackenzie.snowflake.IParser;
import high.mackenzie.snowflake.ParserOutput;

/**
 * This class was auto-generated using the Snowflake parser-generator.
 *
 * <p>Generated On: Sun Jan 26 22:44:34 EST 2014</p>
 */
public final class GrammarParser
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
        g.range("@class0", (char) 38, (char) 38);
        g.range("@class1", (char) 0, (char) 1000);
        g.range("@class2", (char) 58, (char) 58);
        g.range("@class3", (char) 44, (char) 44);
        g.range("@class4", (char) 123, (char) 123);
        g.range("@class5", (char) 125, (char) 125);
        g.range("@class6", (char) 45, (char) 45);
        g.range("@class7", (char) 48, (char) 57);
        g.range("@class8", (char) 36, (char) 36);
        g.range("@class9", (char) 61, (char) 61);
        g.range("@class10", (char) 47, (char) 47);
        g.range("@class11", (char) 67, (char) 67);
        g.range("@class12", (char) 97, (char) 122);
        g.range("@class13", (char) 65, (char) 90);
        g.range("@class14", (char) 97, (char) 122);
        g.range("@class15", (char) 36, (char) 36);
        g.range("@class16", (char) 95, (char) 95);
        g.combine("@class17", "@class13", "@class14", "@class15", "@class16");
        g.range("@class18", (char) 65, (char) 90);
        g.range("@class19", (char) 97, (char) 122);
        g.range("@class20", (char) 36, (char) 36);
        g.range("@class21", (char) 95, (char) 95);
        g.range("@class22", (char) 48, (char) 57);
        g.combine("@class23", "@class18", "@class19", "@class20", "@class21", "@class22");
        g.range("@class24", (char) 10, (char) 10);
        g.range("@class25", (char) 10, (char) 10);
        g.combine("@class26", "@class25");
        g.negate("@class27", "@class26");
        g.range("@class28", (char) 34, (char) 34);
        g.combine("@class29", "@class28");
        g.negate("@class30", "@class29");
        g.range("@class31", (char) 33, (char) 33);
        g.range("@class32", (char) 40, (char) 40);
        g.range("@class33", (char) 41, (char) 41);
        g.range("@class34", (char) 43, (char) 43);
        g.range("@class35", (char) 35, (char) 35);
        g.range("@class36", (char) 63, (char) 63);
        g.range("@class37", (char) 34, (char) 34);
        g.range("@class38", (char) 59, (char) 59);
        g.range("@class39", (char) 39, (char) 39);
        g.range("@class40", (char) 32, (char) 32);
        g.range("@class41", (char) 91, (char) 91);
        g.range("@class42", (char) 93, (char) 93);
        g.range("@class43", (char) 42, (char) 42);
        g.range("@class44", (char) 9, (char) 9);
        g.range("@class45", (char) 95, (char) 95);
        g.range("@class46", (char) 65, (char) 90);
        g.range("@class47", (char) 94, (char) 94);
        g.range("@class48", (char) 32, (char) 32);
        g.range("@class49", (char) 10, (char) 10);
        g.range("@class50", (char) 9, (char) 9);
        g.combine("@class51", "@class48", "@class49", "@class50");

        // Grammar Rules
        g.choose("WS_ELEMENT", "WS_CHAR", "comment");
        g.choose("anon_rule", "anon_string_rule", "anon_character_rule", "anon_sequence_rule", "anon_sequencedlr_rule", "anon_choice_rule", "anon_option_rule", "anon_star_rule", "anon_plus_rule", "anon_repetition_rule", "anon_and_rule", "anon_not_rule");
        g.choose("character", "character_literal", "character_numeric", "character_maximum", "character_minimum");
        g.choose("class", "range_cc", "single_cc", "negation_cc", "exclusion_cc", "combination_cc");
        g.choose("comment", "pound_comment", "slash_comment");
        g.choose("directive", "directive_root", "directive_hide", "directive_trace", "directive_package", "directive_parser", "directive_visitor", "directive_export_parser", "directive_export_visitor");
        g.choose("entry", "directive", "named_rule");
        g.choose("item", "anon_rule", "name");
        g.choose("named_rule", "named_string_rule", "named_character_rule", "named_sequence_rule", "named_sequencedlr_rule", "named_choice_rule", "named_option_rule", "named_star_rule", "named_plus_rule", "named_repetition_rule", "named_and_rule", "named_not_rule");
        g.choose("named_sequence_rule", "alias_rule", "multi_element_rule");
        g.choose("shared", "sequence", "item");
        g.chr("AND", "@class0");
        g.chr("ANY_CHAR", "@class1");
        g.chr("COLON", "@class2");
        g.chr("COMMA", "@class3");
        g.chr("CURLY_BRACKET_L", "@class4");
        g.chr("CURLY_BRACKET_R", "@class5");
        g.chr("DASH", "@class6");
        g.chr("DIGIT", "@class7");
        g.chr("DOLLAR", "@class8");
        g.chr("EQUALS", "@class9");
        g.chr("FORWARD_SLASH", "@class10");
        g.chr("LETTER_C", "@class11");
        g.chr("LOWER", "@class12");
        g.chr("NAME_HEAD", "@class17");
        g.chr("NAME_TAIL_CHAR", "@class23");
        g.chr("NEWLINE", "@class24");
        g.chr("NON_NEWLINE", "@class27");
        g.chr("NON_QUOTE", "@class30");
        g.chr("NOT", "@class31");
        g.chr("PAREN_L", "@class32");
        g.chr("PAREN_R", "@class33");
        g.chr("PLUS", "@class34");
        g.chr("POUND", "@class35");
        g.chr("QUESTION", "@class36");
        g.chr("QUOTE", "@class37");
        g.chr("SEMICOLON", "@class38");
        g.chr("SINGLE_QUOTE", "@class39");
        g.chr("SPACE", "@class40");
        g.chr("SQUARE_BRACKET_L", "@class41");
        g.chr("SQUARE_BRACKET_R", "@class42");
        g.chr("STAR", "@class43");
        g.chr("TAB", "@class44");
        g.chr("UNDERSCORE", "@class45");
        g.chr("UPPER", "@class46");
        g.chr("WS_CHAR", "@class51");
        g.chr("XOR", "@class47");
        g.not("@1", "name");
        g.not("@3", "name");
        g.repeat("NAME_TAIL", "NAME_TAIL_CHAR", 0, 2147483647);
        g.repeat("WS", "WS_ELEMENT", 0, 2147483647);
        g.repeat("choice_tail", "choice_tail_element", 1, 2147483647);
        g.repeat("classes", "class", 1, 2147483647);
        g.repeat("comment_body", "NON_NEWLINE", 0, 2147483647);
        g.repeat("digits", "DIGIT", 1, 2147483647);
        g.repeat("entries", "entry", 0, 2147483647);
        g.repeat("sequence_tail", "sequence_tail_element", 1, 2147483647);
        g.repeat("string_body", "NON_QUOTE", 0, 2147483647);
        g.sequence("alias_rule", "assignee", "WS", "EQUALS", "WS", "name", "WS", "SEMICOLON", "WS");
        g.sequence("anon_and_rule", "PAREN_L", "WS", "AND", "WS", "item", "WS", "PAREN_R", "WS");
        g.sequence("anon_character_rule", "class", "WS");
        g.sequence("anon_choice_rule", "PAREN_L", "WS", "choices", "WS", "PAREN_R", "WS");
        g.sequence("anon_not_rule", "PAREN_L", "WS", "NOT", "WS", "item", "WS", "PAREN_R", "WS");
        g.sequence("anon_option_rule", "PAREN_L", "WS", "item", "WS", "QUESTION", "WS", "PAREN_R", "WS");
        g.sequence("anon_plus_rule", "PAREN_L", "WS", "item", "WS", "PLUS", "WS", "PAREN_R", "WS");
        g.sequence("anon_repetition_rule", "PAREN_L", "WS", "item", "WS", "CURLY_BRACKET_L", "WS", "repetition_minimum", "WS", "COMMA", "WS", "repetition_maximum", "WS", "CURLY_BRACKET_R", "WS", "PAREN_R", "WS");
        g.sequence("anon_sequence_rule", "PAREN_L", "WS", "sequence", "WS", "PAREN_R", "WS");
        g.sequence("anon_sequencedlr_rule", "PAREN_L", "WS", "base", "WS", "COLON", "WS", "shared", "WS", "PAREN_R", "WS");
        g.sequence("anon_star_rule", "PAREN_L", "WS", "item", "WS", "STAR", "WS", "PAREN_R", "WS");
        g.sequence("anon_string_rule", "string", "WS");
        g.sequence("assignee", "NAME_HEAD", "NAME_TAIL");
        g.sequence("base", "item");
        g.sequence("character_literal", "SINGLE_QUOTE", "ANY_CHAR", "SINGLE_QUOTE");
        g.sequence("character_maximum", "@0", "@1");
        g.sequence("character_minimum", "@2", "@3");
        g.sequence("character_numeric", "digits", "LETTER_C");
        g.sequence("choice_head", "item");
        g.sequence("choice_tail_element", "WS", "FORWARD_SLASH", "WS", "item");
        g.sequence("choices", "choice_head", "choice_tail");
        g.sequence("combination_cc", "SQUARE_BRACKET_L", "WS", "classes", "WS", "SQUARE_BRACKET_R", "WS");
        g.sequence("directive_export_parser", "EXPORT_PARSER", "WS", "string", "WS", "SEMICOLON", "WS");
        g.sequence("directive_export_visitor", "EXPORT_VISITOR", "WS", "string", "WS", "SEMICOLON", "WS");
        g.sequence("directive_hide", "HIDE", "WS", "SEMICOLON", "WS");
        g.sequence("directive_package", "PACKAGE", "WS", "string", "WS", "SEMICOLON", "WS");
        g.sequence("directive_parser", "PARSER", "WS", "string", "WS", "SEMICOLON", "WS");
        g.sequence("directive_root", "ROOT", "WS", "name", "WS", "SEMICOLON", "WS");
        g.sequence("directive_trace", "TRACE", "WS", "digits", "WS", "SEMICOLON", "WS");
        g.sequence("directive_visitor", "VISITOR", "WS", "string", "WS", "SEMICOLON", "WS");
        g.sequence("exclusion_cc", "SQUARE_BRACKET_L", "WS", "class", "WS", "XOR", "WS", "class", "WS", "SQUARE_BRACKET_R", "WS");
        g.sequence("multi_element_rule", "assignee", "WS", "EQUALS", "WS", "sequence", "WS", "SEMICOLON", "WS");
        g.sequence("name", "NAME_HEAD", "NAME_TAIL");
        g.sequence("named_and_rule", "assignee", "WS", "EQUALS", "WS", "AND", "WS", "item", "WS", "SEMICOLON", "WS");
        g.sequence("named_character_rule", "assignee", "WS", "EQUALS", "WS", "class", "WS", "SEMICOLON", "WS");
        g.sequence("named_choice_rule", "assignee", "WS", "EQUALS", "WS", "choices", "WS", "SEMICOLON", "WS");
        g.sequence("named_not_rule", "assignee", "WS", "EQUALS", "WS", "NOT", "WS", "item", "WS", "SEMICOLON", "WS");
        g.sequence("named_option_rule", "assignee", "WS", "EQUALS", "WS", "item", "WS", "QUESTION", "WS", "SEMICOLON", "WS");
        g.sequence("named_plus_rule", "assignee", "WS", "EQUALS", "WS", "item", "WS", "PLUS", "WS", "SEMICOLON", "WS");
        g.sequence("named_repetition_rule", "assignee", "WS", "EQUALS", "WS", "item", "WS", "CURLY_BRACKET_L", "WS", "repetition_minimum", "WS", "COMMA", "WS", "repetition_maximum", "WS", "CURLY_BRACKET_R", "WS", "SEMICOLON", "WS");
        g.sequence("named_sequencedlr_rule", "assignee", "WS", "EQUALS", "WS", "base", "WS", "COLON", "WS", "shared", "WS", "SEMICOLON", "WS");
        g.sequence("named_star_rule", "assignee", "WS", "EQUALS", "WS", "item", "WS", "STAR", "WS", "SEMICOLON", "WS");
        g.sequence("named_string_rule", "assignee", "WS", "EQUALS", "WS", "string", "WS", "SEMICOLON", "WS");
        g.sequence("negation_cc", "SQUARE_BRACKET_L", "WS", "XOR", "WS", "classes", "WS", "SQUARE_BRACKET_R", "WS");
        g.sequence("pound_comment", "POUND", "comment_body", "NEWLINE");
        g.sequence("range_cc", "character", "WS", "DASH", "WS", "character", "WS");
        g.sequence("repetition_maximum", "digits");
        g.sequence("repetition_minimum", "digits");
        g.sequence("root", "WS", "entries", "WS", "END");
        g.sequence("sequence", "sequence_head", "sequence_tail");
        g.sequence("sequence_head", "item");
        g.sequence("sequence_tail_element", "WS", "COMMA", "WS", "item");
        g.sequence("single_cc", "character", "WS");
        g.sequence("slash_comment", "DOUBLE_SLASH", "comment_body", "NEWLINE");
        g.sequence("string", "QUOTE", "string_body", "QUOTE");
        g.str("@0", "MAX");
        g.str("@2", "MIN");
        g.str("DOUBLE_SLASH", "//");
        g.str("EXPORT_PARSER", "%export-parser");
        g.str("EXPORT_VISITOR", "%export-visitor");
        g.str("HIDE", "%hide");
        g.str("PACKAGE", "%package");
        g.str("PARSER", "%parser");
        g.str("ROOT", "%root");
        g.str("TRACE", "%trace");
        g.str("VISITOR", "%visitor");

        // Specify which rule is the root of the grammar.
        g.setRoot("root");

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
