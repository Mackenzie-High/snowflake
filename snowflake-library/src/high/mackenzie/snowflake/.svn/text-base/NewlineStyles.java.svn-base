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

import java.util.Map;
import java.util.TreeMap;

/**
 * This enumeration defines constants for various platform-dependent newlines sequences.
 *
 * @author Michael Mackenzie High
 */
public enum NewlineStyles
{
    /**
     * This constant represents a newline that is a carriage-return (i.e. \r).
     */
    CR("\r"),
    /**
     * This constant represents a newline that is a line-feed (i.e. \n).
     */
    LF("\n"),
    /**
     * This constant represents a newline that is a carriage-return followed by a line-feed.
     */
    CR_LF("\r\n"),
    /**
     * This constant indicates that the newline-style is not currently supported.
     */
    UNSUPPORTED("");

    /**
     * This is the newline represented by this constant object.
     */
    private final String newline;

    /**
     * Sole Constructor.
     *
     * @param newline is the represented newline.
     */
    private NewlineStyles(final String newline)
    {
        this.newline = newline;
    }

    /**
     * This method retrieves a string containing the newline represented by this style.
     *
     * <p>
     * If this style is the
     * <code>UNSUPPORTED</code> style, then an empty string is returned.
     * </p>
     *
     * @return the represented newline.
     */
    public String newline()
    {
        return newline;
    }

    /**
     * This method determines the best-matching newline-style constant for a given newline.
     *
     * <p>The
     * <code>UNSUPPORTED</code> style is returned, if the newline is unrecognized.</p>
     *
     * @param newline is the newline that is represented by the returned constant.
     * @return a constant that represent the given newline.
     */
    public static NewlineStyles fromNewline(final String newline)
    {
        final Map<String, NewlineStyles> styles = new TreeMap<String, NewlineStyles>();

        for (NewlineStyles style : NewlineStyles.values())
        {
            styles.put(style.newline(), style);
        }

        final NewlineStyles result = styles.containsKey(newline)
                ? styles.get(newline) : UNSUPPORTED;

        return result;
    }

    /**
     * This method retrieves the best-matching newline-style for the current computer system.
     *
     * <p>The platform newline is found using
     * <code>System.getProperty("line.separator").</code></p>
     *
     * <p>The
     * <code>UNSUPPORTED</code> style is returned, if the newline is unrecognized.</p>
     *
     * @return the aforedescribed newline-style.
     */
    public static NewlineStyles fromSystem()
    {
        final String line_separator = System.getProperty("line.separator");

        final NewlineStyles result = fromNewline(line_separator);

        return result;
    }
}
