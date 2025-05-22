# Snowflake Cheat Sheet

| What is It?                   | Syntax |
| ----------------------------- | ------ |
| Named And-Predicate Rule      | `<name> = & <operand> ;`                             |
| Named Character Rule          | `<name> = <character-class> ;`                       |
| Named Choice Rule             | `<name> = <option-1> / <option-2> / <option-N> ;`    |
| Named Not-Predicate Rule      | `<name> = ! <operand> ;`                             |
| Named Option Rule             | `<name> = <operand> ? ;`                             |
| Named Plus Rule               | `<name> = <operand> + ;`                             |
| Named Repetition Rule         | `<name> = <operand> { <minimum> , <maximum> } ;`     |
| Named Sequence Rule           | `<name> = <element-1> , <element-2> , <element-N> ;` |
| Named Star Rule               | `<name> = <operand> * ;`                             |
| Named String Rule             | `<name> = "<string of text>" ;`                      |
| Anonymous And-Predicate Rule  | `( & <operand> )`                                    |
| Anonymous Character Rule      | `( <character-class> )`                              |
| Anonymous Choice Rule         | `( <option-1> / <option-2> / ... / <option-N> )`     |
| Anonymous Not-Predicate Rule  | `( ! <operand> )`                                    |
| Anonymous Option Rule         | `( <operand> ? )`                                    |
| Anonymous Plus Rule           | `( <operand> + )`                                    |
| Anonymous Repetition Rule     | `( <operand> { <minimum> , <maximum> } )`            |
| Anonymous Sequence Rule       | `( <element-1> , <element-2> , ... , <element-N> )`  |
| Anonymous Star Rule           | `( <operand> * )`                                    |
| Anonymous String Rule         | `"<string of text>"`                                 |
| End of Input Predicate        | `END`                                                |
| Character Class - Combination | `[ <class-1> <class-2> ... <class-N> ]`              |
| Character Class - Exclusion   | `[ <includes> ^ <excludes> ]`                        |
| Character Class - Negation    | `[ ^ <class-1> <class-2> ... <class-N> ]`            |
| Character Class - Range       | `<minimum> - <maximum>`                              |
| Character Class - Single      | `'<character>'` **OR** `<digits>C`                   |
| Directive - Root (required)   | `%root <name> ;`                                     |
| Directive - Hide              | `%hide ;`                                            |
| Directive - Package           | `%package "<fully qualified name of package>" ;`     |
| Directive - Parser            | `%parser "<name of parser class>"` ;                 |
| Directive - Trace             | `%trace <count> ;`                                   |
| Directive - Visitor           | `%visitor "<name of visitor class>" ;`               |
| Directive - Export Parser     | `%export-parser "<filepath>" ;`                      |
| Directive - Export Visitor    | `%export-visitor "<filepath>" ;`                     |

## Example Character Classes

| What does it match?                               | Character Class                     |
| ------------------------------------------------- | ----------------------------------- |
| Any uppercase letter                              | `'A' - 'Z'`                         |
| Any lowercase letter                              | `'a' - 'z'`                         |
| Any letter or digit                               | `[ 'A'-'Z' 'a'-'z' '0'-'9' ]`       |
| Line feed (i.e., “\n”)                            | `10C`                               |
| Carriage return (i.e., '\r')                      | `13C`                               |
| Space character                                   | `32C`                               |
| Tab character                                     | `9C`                                |
| Whitespace                                        | `[ 9C 10C 13C 32C ]`                |
| Any uppercase letter except 'X' & 'Y'            | `[ 'A'-'Z' ^ 'X'-'Y' ]`              |
| Any character                                     | `MIN-MAX`                           |
| Any character except 'M'                          | `[^ 'M']`                           |
| Single character 'A'                              | `'A'`                               |
| Single character 'A'                              | `65C`                               |

## Common Rules

| What does it match?       | Rule                                                 |
| ------------------------- | ---------------------------------------------------- |
| Whitespace                | `WS = [ 9C 10C 13C 32C ] *;`                         |
| Signed integer            | `INT = ('-' ?) , ('0'-'9' +);`                       |
| Signed float, no exponent | `FLOAT = ('-' ?) , ('0'-'9' +) , '.' , ('0'-'9' +);` |
| String literal            | `STRING = '"' , ([^ '"'] *) , '"';`                  |

## About the Directives

| Directive                        | What does it do?  |
| -------------------------------- | ----------------- |
| `%root name;`                    | **Required.** Tells the parser which grammar rule is the root.                                                 |
| `%hide;`                         | **Optional.** Causes the generated parser and visitor to have package-private access instead of public access. |
| `%package “Fully Qualified Name of Package”;` | **Optional.** Specifies the package for the generated parser and visitor.                         |
| `%parser “Name of Parser Class”;`          | **Optional.** Specifies the name of the generated parser class.                                      |
| `%visitor “Name of Visitor Class”;`        | **Optional.** Specifies the name of the generated visitor class.                                     |
| `%trace digits;`                           | **Optional.** Specifies the depth of the debugging back-trace. Usually not useful to end users.      |
| `%export-parser “filepath”;`                | **Optional.** Causes the generated parser to be written to a file.                                  |
| `%export-visitor “filepath”;`               | **Optional.** Causes the generated visitor to be written to a file.                                 |

## Important Reminders

+ PEG-based parsers succeed fast. In other words, if a prefix of the input matches the grammar,
but the rest of the input does not match, then the parser will succeed without consuming all of
the input. There is a simple fix for this, use the **END** predicate rule. This rule only succeeds,
when the entire input has been successfully matched.
+ The GUI expects the input to contain line-feed (i.e. '\n') style newlines. The GUI is designed to
ensure this across platforms. Thus, if you specifically design your grammar to use Windows
newlines, then the grammar may seemingly fail to match the input. It is recommended that you
design your grammar to accept the newline styles of multiple platforms.
+ In order to use the generated parser and generated visitor, “snowflake.jar” must be on the
classpath of the program that uses the generated parser.
+ In a PEG-based parser, the lexer is integrated with the parser. As a result, it is necessary to use
whitespace rules in your grammar, unless your input will never contain any whitespace.

## Example Grammar

```plaintext
# The input must be a phrase and the input must be fully consumed.
# A phrase can be either a greeting or parting phrase.
# A greeting phrase is the word "Hello" followed by the name of a terrestrial planet.
# A parting phrase is the word "Goodbye" followed by the name of a terrestrial planet.

%root input;

input = phrase , END;

phrase = greeting_phrase / parting_phrase;

greeting_phrase = WS , "Hello" , WS , planet , WS;

parting_phrase = WS , "Goodbye" , WS , planet, WS;

planet = "Mercury" / "Venus" / "Earth" / "Mars";

# Whitespace: Zero-Or-More Tabs, Line-Feeds, Carriage-Returns, and/or Spaces
WS = [9C 10C 13C 32C] *;
```

## About the Rules

| Type of Rule        | When does it successfully match?                                                     |
| ------------------- | ------------------------------------------------------------------------------------- |
| **Character Rule**  | The given character class matches the next input character.                           |
| **String Rule**     | The given string of *N* characters matches the next *N* input characters.             |
| **Sequence Rule**   | Each of the rules in the sequence match in sequence.                                  |
| **Choice Rule**     | One of the options must match. Remember, the options are tried in the sequence that they appear in the rule. This is one of the major enhancements of PEG grammars. |
| **Option Rule**     | Makes another rule optional.                                                          |
| **Star Rule**       | The operand rule must be repeated zero or more times.                                 |
| **Plus Rule**       | The operand rule must be repeated one or more times.                                  |
| **Repetition Rule** | The operand rule must be repeated between *minimum* and *maximum* times.              |
| **And Predicate**   | The operand rule must match but will *not* be consumed.                               |
| **Not Predicate**   | The operand rule must *not* match, and will *not* be consumed.                        |
| **End of Input**    | The input must have already been fully consumed.                                      |



