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
 * An instance of this class is a character-class that specifies an inclusive range of characters.
 * 
 * @author Michael Mackenzie High
 */
final class CharacterClassRange implements ICharacterClass
{
    /**
     * This is the character, in the range, whose numeric form is least. 
     */
    final char minimum;

    /**
     * This is the character, in the range, whose numeric form is greatest. 
     */
    final char maximum;
    
    /**
     * Sole Constructor. 
     * 
     * @param minimum is the lowest character in the range.
     * @param maximum is the greatest character in the range. 
     * @throws IllegalArgumentException if <code>minimum &gt; maximum</code>.
     */
    public CharacterClassRange(final char minimum,
                               final char maximum)
    {
        if(minimum > maximum) { throw new IllegalArgumentException("minimum > maximum"); }
        
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(final char c) { return minimum <= c && c <= maximum; }
    
}
