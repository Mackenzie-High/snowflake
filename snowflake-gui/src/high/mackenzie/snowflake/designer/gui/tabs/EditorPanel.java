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

package high.mackenzie.snowflake.designer.gui.tabs;

import high.mackenzie.snowflake.designer.gui.Core;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author mackenzie
 */
public class EditorPanel extends javax.swing.JPanel {

    /** Creates new form EditorPanel */
    public EditorPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editorTextboxPopupMenu = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        editorTextbox = new javax.swing.JTextArea();

        editorTextboxPopupMenu.setName("editorTextboxPopupMenu"); // NOI18N

        //
        // Create the "Cut" menu item.
        final JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut");
        editorTextboxPopupMenu.add(cut);
        //
        // Create the "Copy" menu item.
        final JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Copy");
        editorTextboxPopupMenu.add(copy);
        //
        // Create the "Paste" menu item.
        final JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        editorTextboxPopupMenu.add(paste);
        //
        // Create the "Select All" menu item.
        final JMenuItem selectAll = new JMenuItem(new SelectAllAction());
        selectAll.setText("Select All");
        editorTextboxPopupMenu.add(selectAll);

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        editorTextbox.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                editorTextboxCaretUpdate(evt);
            }
        });
        jScrollPane1.setViewportView(editorTextbox);
        editorTextbox.setMargin(new java.awt.Insets(10, 10, 10, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editorTextboxCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_editorTextboxCaretUpdate
        Core.refresh();
    }//GEN-LAST:event_editorTextboxCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea editorTextbox;
    private javax.swing.JPopupMenu editorTextboxPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private final class SelectAllAction extends AbstractAction
    {
        public void actionPerformed(ActionEvent e) 
        {
            editorTextbox.selectAll();
        }
    }
    
    public JTextArea getTextArea() { return editorTextbox; }
}
