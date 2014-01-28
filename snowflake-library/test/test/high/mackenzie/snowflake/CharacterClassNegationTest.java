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

import org.junit.Test;
import static org.junit.Assert.*;

public final class CharacterClassNegationTest
{
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: true
     */
    @Test
    public void testIsMatch_true() 
    {
        final CharacterClassRange r0 = new CharacterClassRange('b', 'e');
        
        final CharacterClassNegation n = new CharacterClassNegation(r0);
        
        assertTrue(n.match('a'));
        
        assertTrue(n.match('f'));
    }
    
    /**
     * Method: <code>match(char)</code>
     * 
     * Case: false
     */
    @Test
    public void testIsMatch_false() 
    {
        final CharacterClassRange r0 = new CharacterClassRange('b', 'e');
        
        final CharacterClassNegation n = new CharacterClassNegation(r0);
        
        assertFalse(n.match('b'));
        
        assertFalse(n.match('e'));
    }    
}
