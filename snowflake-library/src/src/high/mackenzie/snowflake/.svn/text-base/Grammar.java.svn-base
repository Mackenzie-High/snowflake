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
 * An instance of this class is a dynamically created Parsing Expression Grammar. 
 * 
 * @author Mackenzie High
 */
public final class Grammar
{
    /**
     * This is the root rule of the grammar. 
     */
    final Rule root;
    
    /**
     * This is the maximum number of records will be stored regarding match attempts.
     */
    final int maximum_record_count;
 
    /**
     * Sole Constructor. 
     * 
     * @param root is the root rule of the grammar. 
     * @param maximum_record_count is the number of records to store regarding match attempts.
     */
    Grammar(final Rule root, final int trace_count)
    {
        Utils.checkNonNull(root);
        
        this.root = root;
        
        this.maximum_record_count = trace_count;
    }
    
    /**
     * This method creates a new parser that is based on this grammar.
     * 
     * @return the aforedescribed parser.  
     */
    public Parser newParser()
    { 
        final Parser parser = new Parser(this);
        
        return parser;    
    }
}
