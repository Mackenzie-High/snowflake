# Snowflake Cheat Sheet

| What is It?                   | Syntax |
| ----------------------------- | ------ |
| Named And-Predicate Rule      | name = & item; |
| Named Character Rule          | name = character-class; |
| Named Choice Rule             | name = option1 / option2 / ... / option n; |
| Named Not-Predicate Rule      | name = ! operand; |
| Named Option Rule             | name = operand ?; |
| Named Plus Rule               | name = operand +; |
| Named Repetition Rule         | name = operand { minimum , maximum }; |
| Named Sequence Rule           | name = element1 , element2 , ... , element n; |
| Named Star Rule               | name = operand *; |
| Named String Rule             | name = “text”; |
| Anonymous And-Predicate Rule  | ( & item ) |
| Anonymous Character Rule      | ( character-class ) |
| Anonymous Choice Rule         | ( option1 / option2 / ... / option n ) |
| Anonymous Not-Predicate Rule  | ( ! operand ) |
| Anonymous Option Rule         | ( operand ? ) |
| Anonymous Plus Rule           | ( operand + ) |
| Anonymous Repetition Rule     | ( operand { minimum , maximum } ) |
| Anonymous Sequence Rule       | ( element1 , element2 , ... , element n ) |
| Anonymous Star Rule           | ( operand * ) |
| Anonymous String Rule         | “text” |
| End of Input Predicate        | END |
| Character Class - Combination | [ class1 class2 ... class n ] |
| Character Class - Exclusion   | [ includes ^ excludes ] |
| Character Class - Negation    | [ ^ class1 class2 ... class n ] |
| Character Class - Range       | minimum - maximum |
| Character Class - Single      | 'character1' OR digitsC |
| Directive - Root (required)   | %root name; |
| Directive - Hide              | %hide; |
| Directive - Package           | %package “Fully Qualified Name of Package”; |
| Directive - Parser            | %parser “Name of Parser Class”; |
| Directive - Trace             | %trace digits; |
| Directive - Visitor           | %visitor “Name of Visitor Class”; |
| Directive - Export Parser     | %export-parser “filepath”; |
| Directive - Export Visitor    | %export-visitor “filepath”; |





