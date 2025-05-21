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

/**
 * An instance of this interface specifies a set of characters.
 * 
 * @author Michael Mackenzie High
 */
interface ICharacterClass
{
    
    /**
     * This methods determines whether a specific character is in the set of characters.
     * 
     * @param character is the specific character itself.
     * @return true, if and only if, <code>character</code> is in the set of characters.
     */
    public boolean match(char character);
    
}
