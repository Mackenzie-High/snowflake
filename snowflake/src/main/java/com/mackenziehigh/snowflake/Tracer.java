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

import java.util.LinkedList;

/**
 * An instance of this class facilitates the recording of a bounded number of match attempts. 
 * 
 * @author Mackenzie High
 */
final class Tracer
{
    /**
     * These are the rules in the bounded queue.
     */
    private final Rule[] rules;
    
    /**
     * These are the start-positions in the bounded queue.
     */
    private final int[] starts;
    
    /**
     * These are the reasons in the bounded queue.
     */
    private final TraceElementReason[] reasons;
    
    /**
     * This is the index of the most-recently added element in the bounded queue. 
     */
    private int most_recent = 0;
    
    /**
     * This is the actual number of elements that are currently in the bounded queue. 
     */
    private int count;
    
    /**
     * This is the total number of elements that have been added to the bounded-queue.
     */
    private int total = 0;
    
    /**
     * Sole Constructor 
     * 
     * @param size is the maximum number of trace-elements that are stored at any given time.
     * @throws IllegalArgumentException <code>if size &lt; 0</code>
     */
    Tracer(int size)
    {
        if(size < 0) { throw new IllegalArgumentException(); }
        
        this.reasons = new TraceElementReason[size];
        this.starts = new int[size];
        this.rules = new Rule[size];
    }
    
    /**
     * This function adds another record to the backtrace. 
     * 
     * @param rule is the rule that is attempting to match. 
     * @param start is the location where the parser began attempting to match. 
     * @param reason is the reason why the record is being created. 
     */
    final void add(final Rule rule, final int start, TraceElementReason result)
    {
        ++total;       
        
        if(rules.length == 0) { return; }
        
        // The bounded queue is implemented using arrays.
        // Therefore, keep track of of which element is at the front of the queue. 
        most_recent = most_recent + 1 == rules.length ? 0 : most_recent + 1;
        
        // The bounded queue cannot store more than the maximum number of elements. 
        count = count == rules.length ? count : count + 1;
        
        // Multiple array elements are used to store the value of a single element in the queue. 
        this.rules[most_recent] = rule;
        this.starts[most_recent] = start;
        this.reasons[most_recent] = result; 
    }

    /**
     * This method returns an immutable representation of the current state of this object. 
     * 
     * @return the aforedescribed object. 
     */
    public Trace output() 
    {
        final LinkedList<TraceElement> list = new LinkedList<TraceElement>();
        
        int index = most_recent;
        for(int i = 0; i < count; i++) 
        { 
            list.addFirst(new TraceElement(rules[index].name(), starts[index], reasons[index])); 
            
            index = index == 0 ? rules.length - 1 : index - 1;
        }
        
        return new Trace(list, total);
    }
    
}
