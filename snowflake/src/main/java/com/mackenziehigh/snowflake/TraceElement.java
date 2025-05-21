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

package com.mackenziehigh.snowflake;

/**
 * An instance of this class describes either the entry or exit of a match attempt. 
 * 
 * @author Mackenzie High
 */
public final class TraceElement 
{
    /**
     * This is the name of the rule whose match attempt caused the creation of this record. 
     */
    final String rule;
    
    /**
     * This is the location in the parser's input, where this record was created.
     */
    final int position;
    
    /**
     * This enum constant indicates the reason why this record was created. 
     */
    final TraceElementReason reason;
    
    /**
     * Sole Constructor.
     * 
     * @param rule is the name of the rule that is causing the creation of this record.
     * @param position is the current position in the parser's input.
     * @param type is the reason why this record is being created. 
     */
    TraceElement(final String rule, 
                 final int start, 
                 final TraceElementReason reason)
    {
        Utils.checkNonNull(rule);
        Utils.checkNonNull(reason);
        
        this.reason = reason;
        this.rule = rule;
        this.position = start;
    }
    
    /**
     * This method returns the name of the rule that caused the creation of this record. 
     * 
     * @return the aforedescribed name. 
     */
    public String name() { return rule; }
    
    /**
     * This method returns the position in the parser's input where the record was created. 
     * 
     * @return aforedescribed position.
     */
    public int position() { return position; }
    
    /**
     * This method returns the reason why this record was created. 
     * 
     * @return the aforedescribed reason. 
     */
    public TraceElementReason reason() { return reason; }
    
}
