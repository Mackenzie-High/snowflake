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

package com.mackenziehigh.snowflake.parsergen;

import static org.junit.Assert.*;

public final class TestUtils 
{
    public static void check(String code, String expected)
    {
        expected = expected.replace("''", "\"");
        
        boolean answer = code.contains(expected);
        
        if(!answer)
        {
            System.out.println(code);
        }
        
        assertTrue(answer);
    }
}
