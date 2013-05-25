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
import high.mackenzie.snowflake.IGrammarBuilder;

/**
 * An instance of this interface generates source-code for a parser-file and a visitor-file.
 *
 * <p>This object generates code as the grammar-builder methods are invoked.</p>
 * 
 * @author Mackenzie High
 */
interface ICodeGenerator extends IGrammarBuilder
{   
    /**
     * This method specifies the name of the package that contains the parser and visitors. 
     * 
     * @param name is the aforedescribed name. 
     * @return this.
     * @thows IllegalStateException if this method was previously invoked. 
     */
    public ICodeGenerator setPackageName(String name); 
    
    /**
     * This method specifies the name of the parser that will be generated. 
     * 
     * @param name is the aforedescribed name. 
     * @return this.
     * @thows IllegalStateException if this method was previously invoked. 
     */
    public ICodeGenerator setParserName(String name);
    
    /**
     * This method specifies the name of the visitor that will be generated. 
     * 
     * @param name is the aforedescribed name. 
     * @return this.
     * @thows IllegalStateException if this method was previously invoked. 
     */
    public ICodeGenerator setVisitorName(String name);
    
    /**
     * This method returns the source-code to place into the parser-file. 
     * 
     * @return the aforedescribed source-code. 
     */
    public String getParserFile();
    
    /**
     * This method returns the source-code to place into the visitor-file. 
     * 
     * @return the aforedescribed source-code. 
     */
    public String getVisitorFile();
    
    /**
     * This method builds a dynamic-grammar object for immediate usage.
     * 
     * <p>
     * This method will throw an exception, if the grammar cannot be built.
     * For example, an exception will be thrown, if the grammar used rules that were not declared.
     * </p>
     * 
     * @return a dynamically built grammar.
     * @throws IllegalStateException if something goes wrong. 
     */
    public Grammar build();
}
