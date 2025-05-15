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
 * An instance of this class is a character-class that negates another character-class. 
 * 
 * @author Michael Mackenzie High
 */
final class CharacterClassNegation implements ICharacterClass
{
    /**
     * This is the character-class that must not match.
     */
    final ICharacterClass negates;

    /**
     * Sole Constructor. 
     * 
     * @param negates are the negated character-class itself.
     */
    public CharacterClassNegation(final ICharacterClass negates)
    {
        Utils.checkNonNull(negates);
        
        this.negates = negates;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(final char c) { return !negates.match(c); }
}
