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

import org.junit.Test;
import static org.junit.Assert.*;

public final class CharacterClassRangeTest
{
    /**
     * Method: Sole Constructor
     * 
     * Case: minimum greater-than maximum
     */
    @Test (expected = IllegalArgumentException.class)
    public void testMinGreaterThanMax()
    {
        new CharacterClassRange('z', 'a');
    }    
    
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: true, [minimum] is less-than [c] is less-than [maximum]
     */
    @Test
    public void testIsMatch_notMinOrMax()
    {
        final CharacterClassRange object = new CharacterClassRange('a', 'c');
        
        assertTrue(object.match('b'));
    }
    
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: true, [c] == [minimum]
     */
    @Test
    public void testIsMatch_min()
    {
        final CharacterClassRange object = new CharacterClassRange('a', 'c');
        
        assertTrue(object.match('a'));
    }
    
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: true, [c] == [maximum]
     */
    @Test
    public void testIsMatch_max()
    {
        final CharacterClassRange object = new CharacterClassRange('a', 'c');
        
        assertTrue(object.match('c'));
    }
    
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: false
     */
    @Test
    public void testIsMatch_false()
    {
        final CharacterClassRange object = new CharacterClassRange('a', 'c');
        
        assertFalse(object.match('d'));
    }    
    
}
