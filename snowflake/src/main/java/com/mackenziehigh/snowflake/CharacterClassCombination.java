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

import java.util.Arrays;

/**
 * An instance of this class specifies a set of characters made from other character-classes. 
 * 
 * @author Michael Mackenzie High
 */
final class CharacterClassCombination implements ICharacterClass
{
    /**
     * These are the character-classes that this character-class combines.
     */
    final ICharacterClass[] elements;

    /**
     * Sole Constructor. 
     * 
     * @param classes are the character-classes to combine.
     */
    public CharacterClassCombination(final ICharacterClass... classes)
    {
        Utils.checkNonNullArray(classes);
        
        // Defensive Copy
        this.elements = Arrays.copyOf(classes, classes.length);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(final char c) 
    {
        for(int i = 0; i < elements.length; i++)
        {
            if(elements[i].match(c)) { return true; }
        }

        return false;
    }
}
