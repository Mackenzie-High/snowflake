Snowflake lets you interactively create [PEG](https://en.wikipedia.org/wiki/Parsing_expression_grammar)-based recursive-descent parsers via a GUI.

**Quick Links:**
+ [Grammar Cheat Sheet](examples/CheatSheet.md)
+ [JavaDoc](https://mackenzie-high.github.io/snowflake/javadoc/com/mackenziehigh/snowflake/package-summary.html)
+ [Code Coverage](https://mackenzie-high.github.io/snowflake/jacoco/com.mackenziehigh.snowflake/index.html)

**Maven Dependency:**

```xml
<dependency>
    <groupId>com.mackenziehigh</groupId>
    <artifactId>snowflake</artifactId>
    <version>2.0</version>
</dependency>
```

## Hello World

**Step: Download GUI from Maven Central:**

[Click Here](https://repo.maven.apache.org/maven2/com/mackenziehigh/snowflake/2.0/snowflake-2.0.jar) to download the ready-to-run Snowflake JAR file.

**Step: Run the GUI**

```bash
java -cp snowflake-2.0.jar com.mackenziehigh.snowflake.designer.gui.MainWindow
```

The following window will appear on screen.

![Java Swing Snowflake GUI][window1]

[window1]: https://www.mackenziehigh.com/snowflake/v2_0/intro/Window_1.png

**Step: Download the Demonstration Grammar:**

[Click Here](https://www.mackenziehigh.com/snowflake/v2_0/grammars/planet-demo.snow) to download the `planet-demo.snow` file.

**Step: Open the Demonstation Grammar:**

+ Click `File -> Open Project`
+ Select the `planet-demo.snow` file that you just downloaded in the previous step.

The GUI will be populated with the grammar and example from the `planet-demo.snow` file.

The example input is displayed on the `Input` tab of the GUI.

![Java Swing Snowflake GUI with Grammar][window2]

[window2]: https://www.mackenziehigh.com/snowflake/v2_0/intro/Window_2.png

**Step: Parse the Example Input:**

Click `Action -> Parse` on the GUI.

The grammar will be used to parse the example input and then the resulting parse tree will be displayed.

The nodes in the tree correspond to the matching grammar rule.

Click on nodes to see the portion of text corresponding to that node.

![Java Swing Snowflake GUI with Grammar][window4]

[window4]: https://www.mackenziehigh.com/snowflake/v2_0/intro/Window_4.png

## Introduction to Parsing using PEGs

[Here](https://www.mackenziehigh.com/snowflake/v2_0/Grammars.pdf) is a powerpoint presentation that will introduce you to parsing using Parsing Expression Grammars.

## Example Grammars

These grammar files can be opened using the grammar editor via `File -> Open Project`.

+ [Simple Phone Book](https://www.mackenziehigh.com/snowflake/v2_0/grammars/phonebook.snow)
+ [Prefix Notation Calculator](https://www.mackenziehigh.com/snowflake/v2_0/grammars/prefix.snow)
+ [Infix Notation Calculator](https://www.mackenziehigh.com/snowflake/v2_0/grammars/infix.snow)

## Full Example Projects

These projects include executable example programs that utilize Snowflake for parsing.

+ [Simple Phone Book](examples/phonebook-example/)
+ [Prefix Notation Calculator](examples/prefix-calculator-example)
+ [Infix Notation Calculator](examples/infix-calculator-example)

## External Resources

+ [Wikipedia: Parsing Expression Grammars](https://en.wikipedia.org/wiki/Parsing_expression_grammar)
+ [The Packrat Parsing and Parsing Expression Grammars Page](https://bford.info/packrat/)
+ [The Grammar of Snowflake Grammars](snowflake/src/main/java/com/mackenziehigh/snowflake/parsergen/GrammarOfGrammars.txt)

