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

public final class CharacterClassCombinationTest 
{
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: true
     */
    @Test
    public void testIsMatch_true()
    {
        final ICharacterClass a = new CharacterClassRange('a', 'a');
        
        final ICharacterClass b = new CharacterClassRange('b', 'b');
        
        final ICharacterClass x = new CharacterClassCombination(a, b);
        
        assertTrue(x.match('a'));
        
        assertTrue(x.match('b'));
    }        
    
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: false
     */
    @Test
    public void testIsMatch_false()
    {
        final ICharacterClass a = new CharacterClassRange('a', 'a');
        
        final ICharacterClass b = new CharacterClassRange('b', 'b');
        
        final ICharacterClass x = new CharacterClassCombination(a, b);
        
        assertFalse(x.match('c'));
    }      
}
