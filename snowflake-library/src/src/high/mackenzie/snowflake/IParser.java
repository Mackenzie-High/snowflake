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

package high.mackenzie.snowflake;

/**
 * An instance of this interface is a parser based on a Parsing Expression Grammar. 
 * 
 * @author Mackenzie High
 */
public interface IParser 
{
    /**
     * This method parses a character-array using the grammar. 
     * 
     * @param input is the character-array to parse. 
     * @return the result of the parsing attempt.
     */
    public ParserOutput parse(char[] input);
    
    /**
     * This method parses a string using the grammar. 
     * 
     * <p>Equivalence: <code>parse(input.toCharArray())/code> </p>
     * 
     * @param input is the string to parse. 
     * @return the result of the parsing attempt.
     */
    public ParserOutput parse(String input);
}
