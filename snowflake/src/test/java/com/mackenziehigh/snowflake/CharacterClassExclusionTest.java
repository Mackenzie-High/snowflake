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

public final class CharacterClassExclusionTest 
{
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: normal
     */
    @Test
    public void testIsMatch_normal()
    {
        // abcdefghi
        // xxxxefghijklmnopqrstuvwxyz
        
        final CharacterClassRange x = new CharacterClassRange('a', 'i');
        
        final CharacterClassRange y = new CharacterClassRange('e', 'z');
        
        final CharacterClassExclusion m = new CharacterClassExclusion(x, y);    
        
        assertTrue(m.match('a'));
        
        assertTrue(m.match('d'));
        
        assertFalse(m.match('5'));
        
        assertFalse(m.match('e'));
    }
    
}
