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

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public final class TracerTest 
{
    /**
     * Test: 20130308085851807696
     *
     * <p>
     * Case: size = 0
     * </p>
     */
    @Test
    public void test20130308085851807696()
    {
        System.out.println("Test: 20130308085851807696");
        
        final Tracer object = new Tracer(0);
        
        object.add(new RuleString("x"), 100, TraceElementReason.BEGIN);
        object.add(new RuleString("y"), 200, TraceElementReason.FAIL);
        
        final Trace output = object.output();
        
        assertEquals(2, output.callCount());
        
        assertEquals(0, output.elements().size());
    }
    
    /**
     * Test: 20130308085851807807
     *
     * <p>
     * Case: single element
     * </p>
     */
    @Test
    public void test20130308085851807807()
    {
        System.out.println("Test: 20130308085851807807");
        
        final Tracer object = new Tracer(1);
        
        assertEquals(0, object.output().elements().size());
        
        final RuleString rule1 = new RuleString("rule1");
        final RuleString rule2 = new RuleString("rule2");
        final RuleString rule3 = new RuleString("rule3");
        
        object.add(rule1, 100, TraceElementReason.BEGIN);
        final Trace output1 = object.output();
        final List<TraceElement> list1 = output1.elements();
        assertEquals(1, list1.size());
        assertEquals("rule1", list1.get(0).name());
        assertEquals(100, list1.get(0).position());
        assertEquals(TraceElementReason.BEGIN, list1.get(0).reason());
        
        object.add(rule2, 200, TraceElementReason.SUCCESS);
        final Trace output2 = object.output();
        final List<TraceElement> list2 = output2.elements();
        assertEquals(1, list2.size());
        assertEquals("rule2", list2.get(0).name());
        assertEquals(200, list2.get(0).position());
        assertEquals(TraceElementReason.SUCCESS, list2.get(0).reason());
        
        object.add(rule3, 300, TraceElementReason.FAIL);
        final Trace output3 = object.output();
        final List<TraceElement> list3 = output3.elements();
        assertEquals(1, list3.size());
        assertEquals("rule3", list3.get(0).name());
        assertEquals(300, list3.get(0).position());
        assertEquals(TraceElementReason.FAIL, list3.get(0).reason());
        
        assertFalse(list1 == list2);
        assertFalse(list1 == list3);
        assertFalse(list2 == list3);
    }
    
    /**
     * Test: 20130308085851807859
     *
     * <p>
     * Case: multiple elements
     * </p>
     */
    @Test
    public void test20130308085851807859()
    {
        System.out.println("Test: 20130308085851807859");
        
        final Tracer object = new Tracer(2);
        
        assertEquals(0, object.output().elements().size());
        
        final RuleString rule1 = new RuleString("rule1");
        final RuleString rule2 = new RuleString("rule2");
        final RuleString rule3 = new RuleString("rule3");
        
        object.add(rule1, 100, TraceElementReason.BEGIN);
        final Trace output1 = object.output();
        final List<TraceElement> list1 = output1.elements();
        assertEquals(1, list1.size());
        //
        assertEquals("rule1", list1.get(0).name());
        assertEquals(100, list1.get(0).position());
        assertEquals(TraceElementReason.BEGIN, list1.get(0).reason());
        
        
        object.add(rule2, 200, TraceElementReason.SUCCESS);
        final Trace output2 = object.output();
        final List<TraceElement> list2 = output2.elements();
        assertEquals(2, list2.size());
        //
        assertEquals("rule1", list2.get(0).name());
        assertEquals(100, list2.get(0).position());
        assertEquals(TraceElementReason.BEGIN, list2.get(0).reason());
        //
        assertEquals("rule2", list2.get(1).name());
        assertEquals(200, list2.get(1).position());
        assertEquals(TraceElementReason.SUCCESS, list2.get(1).reason());
        
        
        object.add(rule3, 300, TraceElementReason.FAIL);
        final Trace output3 = object.output();
        final List<TraceElement> list3 = output3.elements();
        assertEquals(2, list3.size());
        //
        assertEquals("rule2", list3.get(0).name());
        assertEquals(200, list3.get(0).position());
        assertEquals(TraceElementReason.SUCCESS, list3.get(0).reason());
        //
        assertEquals("rule3", list3.get(1).name());
        assertEquals(300, list3.get(1).position());
        assertEquals(TraceElementReason.FAIL, list3.get(1).reason());
    }
     
}
