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

import static org.junit.Assert.*;
import org.junit.Test;

public final class NewlineStylesTest
{
    /**
     * Test: 20130522030218649677
     *
     * <p>
     * Method:
     * <code>newline()</code>
     * </p>
     *
     * <p>
     * Case: Test the method on all the constants.
     * </p>
     */
    @Test
    public void test20130522030218649677()
    {
        System.out.println("Test: 20130522030218649677");

        assertEquals("\r", NewlineStyles.CR.newline());
        assertEquals("\n", NewlineStyles.LF.newline());
        assertEquals("\r\n", NewlineStyles.CR_LF.newline());
        assertEquals("", NewlineStyles.UNSUPPORTED.newline());
    }

    /**
     * Test: 20130522030218649801
     *
     * <p>
     * Method:
     * <code>fromNewline(String)</code>
     * </p>
     *
     * <p>
     * Case: All Cases
     * </p>
     */
    @Test
    public void test20130522030218649801()
    {
        System.out.println("Test: 20130522030218649801");

        assertEquals(NewlineStyles.CR, NewlineStyles.fromNewline("\r"));
        assertEquals(NewlineStyles.LF, NewlineStyles.fromNewline("\n"));
        assertEquals(NewlineStyles.CR_LF, NewlineStyles.fromNewline("\r\n"));
        assertEquals(NewlineStyles.UNSUPPORTED, NewlineStyles.fromNewline(""));
        assertEquals(NewlineStyles.UNSUPPORTED, NewlineStyles.fromNewline("X"));
    }

    /**
     * Test: 20130522030218649852
     *
     * <p>
     * Method:
     * <code>fromSystem()</code>
     * </p>
     *
     * <p>
     * Case: Test the method for the current platform.
     * </p>
     */
    @Test
    public void test20130522030218649852()
    {
        System.out.println("Test: 20130522030218649852");

        final String expected = System.getProperty("line.separator");

        final NewlineStyles actual = NewlineStyles.fromSystem();

        assertTrue(expected.length() > 0);

        assertEquals(expected, actual.newline());
    }

    /**
     * Test: 20140129094921730432
     *
     * <p>
     * Method:
     * <code>fromGuess(String, NewlineStyles)</code>
     * </p>
     *
     * <p>
     * Case: All Cases
     * </p>
     */
    @Test
    public void test20140129094921730432()
    {
        System.out.println("Test: 20140129094921730432");

        String input;
        NewlineStyles expect;
        NewlineStyles correct;

        input = "123\r\n456";
        expect = NewlineStyles.UNSUPPORTED;
        correct = NewlineStyles.CR_LF;
        assertEquals(correct, NewlineStyles.fromGuess(input, expect));

        input = "123\r456";
        expect = NewlineStyles.UNSUPPORTED;
        correct = NewlineStyles.CR;
        assertEquals(correct, NewlineStyles.fromGuess(input, expect));

        input = "123\n456";
        expect = NewlineStyles.UNSUPPORTED;
        correct = NewlineStyles.LF;
        assertEquals(correct, NewlineStyles.fromGuess(input, expect));

        input = "123456";
        expect = NewlineStyles.UNSUPPORTED;
        correct = NewlineStyles.UNSUPPORTED;
        assertEquals(correct, NewlineStyles.fromGuess(input, expect));

        input = "123456";
        expect = NewlineStyles.CR_LF;
        correct = NewlineStyles.CR_LF;
        assertEquals(correct, NewlineStyles.fromGuess(input, expect));

        input = "123456";
        expect = NewlineStyles.CR;
        correct = NewlineStyles.CR;
        assertEquals(correct, NewlineStyles.fromGuess(input, expect));

        input = "123456";
        expect = NewlineStyles.LF;
        correct = NewlineStyles.LF;
        assertEquals(correct, NewlineStyles.fromGuess(input, expect));
    }
}
