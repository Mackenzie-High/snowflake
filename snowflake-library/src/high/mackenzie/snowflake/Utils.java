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
 * This class provides static utility methods. 
 * 
 * @author Mackenzie High
 */
public final class Utils 
{
    /**
     * This method ensures that an object reference is non-null.
     * 
     * @param object is the object reference itself. 
     * @throws NullPointerException if <code>object</code> is null.
     */
    public static void checkNonNull(final Object object)
    {
        if(object == null) { throw new NullPointerException(); }
    }
    
    /**
     * This method ensures that an array is non-null and that the array contains no null elements.
     * 
     * @param array the array itself.  
     * @throws NullPointerException if <code>array</code> is null.
     * @throws NullPointerException if <code>array</code> contains a null element.
     */
    public static void checkNonNullArray(final Object[] array)
    {
        checkNonNull(array);
        
        for(Object element : array) { checkNonNull(element); }
    }
}
