/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mackenziehigh.snowflake.designer.gui;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

/**
 * Under Development.
 *
 * An instance of this class is a listener that performs syntax-highlighting of Snowflake code.
 *
 * @author Mackenzie High
 */
public final class SyntaxHighlighter
        extends DefaultStyledDocument
{
    /**
     * This is the textbox that contains the text to color.
     */
    private final JTextPane textbox;

    private final int delay;

    private final Pattern pattern;

    private final AtomicBoolean highlighting_needed = new AtomicBoolean();

    private final Thread thread;

    private final Runnable highlighter = new Runnable()
    {
        public void run()
        {
            while (true)
            {
                if (highlighting_needed.get())
                {
                    highlight();

                    highlighting_needed.set(false);
                }
                try
                {
                    Thread.sleep(delay);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(SyntaxHighlighter.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        }

        public void highlight()
        {
            Style style1 = textbox.addStyle("Default", null);
            StyleConstants.setForeground(style1, Color.BLACK);
            setCharacterAttributes(0, textbox.getText().length(), textbox.getStyle("Default"), true);

            final Matcher matcher = pattern.matcher(textbox.getText());

            while (matcher.find())
            {
                final int start = matcher.start();

                final int end = matcher.end();

                final int length = end - start;

                Style style = textbox.addStyle("Red", null);
                StyleConstants.setForeground(style, Color.RED);

                setCharacterAttributes(start, length, textbox.getStyle("Red"), true);
            }
        }
    };

    /**
     * Sole Constructor.
     *
     * @param textbox is the textbox that contains the text to color.
     * @param delay is the number of milliseconds to wait, between highlighting passes.
     */
    public SyntaxHighlighter(final JTextPane textbox,
                             final int delay)
    {
        this.textbox = textbox;
        this.delay = delay;

        this.pattern = Pattern.compile("[#]");

        this.thread = new Thread(this.highlighter, "Syntax Highlighter");
    }

    /**
     * Invoke this method to activate the syntax-highlighting.
     */
    public void start()
    {
//        thread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(int offs,
                       int len)
            throws BadLocationException
    {
        super.remove(offs, len);

        this.highlighting_needed.set(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertString(int offset,
                             String str,
                             AttributeSet a)
            throws BadLocationException
    {
        super.insertString(offset, str, a);

        this.highlighting_needed.set(true);
    }
}
