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

import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An instance of this class is the output of a tracer that records match-attempts.
 * 
 * @author mackenzie
 */
public final class Trace
{
    final List<TraceElement> elements = new LinkedList<TraceElement>();
    
    final int total;
    
    /**
     * Sole Constructor.
     * 
     * @param elements are records describing match-attempts.
     * @param total is the total number of records that were created. 
     */
    public Trace(final List<TraceElement> elements, final int total)
    {
        Utils.checkNonNull(elements);
        
        this.elements.addAll(elements);
        this.total = total;
    }
    
    /**
     * This method return records that describe the starts/failures/successes of match-attempts. 
     * 
     * @return a readonly list of immutable objects. 
     */
    public List<TraceElement> elements()
    {
        return Collections.unmodifiableList(elements);
    }
    
    /**
     * This method returns the number of records that were created. 
     * 
     * <p>This count may be greater-than <code>elements().size()</code>.</p>
     * 
     * @return the aforedescribed count. 
     */
    public int callCount()
    {
        return total;
    }
    
    /**
     * This method prints the records herein contained to a writer.
     * 
     * <p><b>Note:</b> The output format may change in the future.</p>
     * 
     * @param out is the writer itself. 
     */
    public void print(final PrintWriter out)
    {
        out.print("Tracer Records ("); 
        out.print(elements.size()); 
        out.print(" of ");
        out.print(total);
        out.println(") (Most Recent Call Last):");
        
        int i = 0;
        
        for(TraceElement element : elements) 
        { 
            out.print("    ");
            out.print('[');
            out.print(i++);
            out.print(']');
            out.print(' ');
            out.print(element.rule);
            out.print(" => ");
            out.print(element.reason.name());
            out.print(" @ ");
            out.print(element.position);
            out.println();
        }
    }
}
