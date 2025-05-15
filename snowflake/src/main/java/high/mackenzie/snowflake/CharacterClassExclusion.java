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
 * An instance of this class is a character-class that purposely excludes some characters.
 * 
 * @author Michael Mackenzie High
 */
final class CharacterClassExclusion implements ICharacterClass
{
    /**
     * This character-class matches the characters to include.
     */
    final ICharacterClass includes;

    /**
     * This character-class matches the characters to exclude.
     */
    final ICharacterClass excludes;

    /**
     * Sole Constructor.
     * 
     * @param includes is a character-class that matches the characters that will be included. 
     *   This character-class should overlap with the <code>excludes</code> character-class.
     * @param excludes is a character-class that matches the characters that will be excluded. 
     *   This character-class should be a subset of the <code>includes</code> character-class.
     */
    public CharacterClassExclusion(final ICharacterClass includes,
                                   final ICharacterClass excludes)
    {
        Utils.checkNonNull(includes);
        Utils.checkNonNull(excludes);
        
        this.includes = includes;
        this.excludes = excludes;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(final char c) { return !excludes.match(c) && includes.match(c); }

}
