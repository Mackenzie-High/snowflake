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

public final class UtilsTest 
{
    /**
     * Test: 20130326011914540641
     *
     * <p>
     * Method: <code>checkNonNull(Object)</code>
     * </p>
     * 
     * <p>
     * Case: null argument
     * </p>
     */
    @Test (expected = NullPointerException.class)
    public void test20130326011914540641()
    {
        System.out.println("Test: 20130326011914540641");
        
        Utils.checkNonNull(null);
    }
    
    /**
     * Test: 20130326011914540751
     *
     * <p>
     * Method: <code>checkNonNullArray(Object[])</code>
     * </p>
     * 
     * <p>
     * Case: null argument
     * </p>
     */
    @Test (expected = NullPointerException.class)
    public void test20130326011914540751()
    {
        System.out.println("Test: 20130326011914540751");
        
        Utils.checkNonNullArray(null);
    }
    
    /**
     * Test: 20130326011914540802
     *
     * <p>
     * Method: <code>checkNonNullArray(Object[])</code>
     * </p>
     * 
     * <p>
     * Case: array containing null elements
     * </p>
     */
    @Test (expected = NullPointerException.class)
    public void test20130326011914540802()
    {
        System.out.println("Test: 20130326011914540802");
        
        Utils.checkNonNullArray(new Object[] { null, null, null });
    }

    /**
     * Test: 20130326021846385929
     *
     * <p>
     * Method: <code>checkNonNullArray(Object[])</code>
     * </p>
     * 
     * <p>
     * Case: empty array
     * </p>
     */
    @Test
    public void test20130326021846385929()
    {
        System.out.println("Test: 20130326021846385929");
        
        Utils.checkNonNullArray(new Object[] { });
    }
    
    /**
     * Test: 20130326021846386020
     *
     * <p>
     * Method: <code>checkNonNullArray(Object[])</code>
     * </p>
     * 
     * <p>
     * Case: non-empty array that does not contain any null elements
     * </p>
     */
    @Test
    public void test20130326021846386020()
    {
        System.out.println("Test: 20130326021846386020");
        
        Utils.checkNonNullArray(new Object[] { 12, 34, 56 });
    }

    /**
     * Test: 20130326024121235956
     *
     * <p>
     * Case: The code-coverage utility should ignore default-constructors, but it is not doing so.
     *       This method prevents the bug from messing up the code-coverage statistics. 
     * </p>
     */
     @Test
     public void test20130326024121235956()
     {
         System.out.println("Test: 20130326024121235956");
         
         new Utils();
     }    
}
