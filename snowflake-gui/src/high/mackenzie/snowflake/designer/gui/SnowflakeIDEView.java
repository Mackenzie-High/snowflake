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

package high.mackenzie.snowflake.designer.gui;

import java.awt.Dimension;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;

/**
 * The application's main frame.
 */
public class SnowflakeIDEView extends FrameView 
{
    public SnowflakeIDEView(SingleFrameApplication app)
    {
        super(app);

        initComponents();

        this.getFrame().setTitle("Snowflake's Grammar Designer");
        
        this.getFrame().getContentPane().setPreferredSize(new Dimension(800, 800));
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openProjectMenuItem = new javax.swing.JMenuItem();
        saveProjectAsMenuItem = new javax.swing.JMenuItem();
        saveProjectMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        actionMenu = new javax.swing.JMenu();
        generateParserMenuItem = new javax.swing.JMenuItem();
        parseMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        LineNumber = Core.getLineNumber();
        ColumnNumber = Core.getColumnNumber();

        mainPanel.setName("mainPanel"); // NOI18N

        Tabs.setName("Tabs"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
            .addComponent(Tabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(high.mackenzie.snowflake.designer.gui.SnowflakeIDEApp.class).getContext().getResourceMap(SnowflakeIDEView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(high.mackenzie.snowflake.designer.gui.SnowflakeIDEApp.class).getContext().getActionMap(SnowflakeIDEView.class, this);
        openProjectMenuItem.setAction(actionMap.get("openProjectClick")); // NOI18N
        openProjectMenuItem.setText(resourceMap.getString("openProjectMenuItem.text")); // NOI18N
        openProjectMenuItem.setName("openProjectMenuItem"); // NOI18N
        fileMenu.add(openProjectMenuItem);
        if(Core.isJNLP())
        {
            openProjectMenuItem.setText("(Unavailable) Open Project");
        }

        saveProjectAsMenuItem.setAction(actionMap.get("saveProjectAsClick")); // NOI18N
        saveProjectAsMenuItem.setText(resourceMap.getString("saveProjectAsMenuItem.text")); // NOI18N
        saveProjectAsMenuItem.setName("saveProjectAsMenuItem"); // NOI18N
        fileMenu.add(saveProjectAsMenuItem);
        if(Core.isJNLP())
        {
            saveProjectAsMenuItem.setText("(Unavailable) Save Project As");
        }

        saveProjectMenuItem.setAction(actionMap.get("saveProjectClick")); // NOI18N
        saveProjectMenuItem.setText(resourceMap.getString("saveProjectMenuItem.text")); // NOI18N
        saveProjectMenuItem.setName("saveProjectMenuItem"); // NOI18N
        fileMenu.add(saveProjectMenuItem);
        if(Core.isJNLP())
        {
            saveProjectMenuItem.setText("(Unavailable) Save Project");
        }

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        actionMenu.setText(resourceMap.getString("actionMenu.text")); // NOI18N
        actionMenu.setName("actionMenu"); // NOI18N

        generateParserMenuItem.setAction(actionMap.get("generateParserClick")); // NOI18N
        generateParserMenuItem.setText(resourceMap.getString("generateParserMenuItem.text")); // NOI18N
        generateParserMenuItem.setName("generateParserMenuItem"); // NOI18N
        actionMenu.add(generateParserMenuItem);

        parseMenuItem.setAction(actionMap.get("parseClick")); // NOI18N
        parseMenuItem.setText(resourceMap.getString("parseMenuItem.text")); // NOI18N
        parseMenuItem.setName("parseMenuItem"); // NOI18N
        actionMenu.add(parseMenuItem);

        menuBar.add(actionMenu);

        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(741, 35));

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        LineNumber.setText("Line: 00001"); // NOI18N
        LineNumber.setName("LineNumber"); // NOI18N

        ColumnNumber.setText("Column: 00001"); // NOI18N
        ColumnNumber.setName("ColumnNumber"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(statusMessageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 717, Short.MAX_VALUE)
                        .addComponent(statusAnimationLabel))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(LineNumber)
                        .addGap(18, 18, 18)
                        .addComponent(ColumnNumber)))
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LineNumber)
                    .addComponent(ColumnNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel))
                .addGap(12, 12, 12))
        );

        setComponent(Tabs);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
    
    @Action
    public void parseClick() 
    { 
        Core.parse(); 
    }

    @Action
    public void generateParserClick() 
    { 
        Core.generateParser(); 
    }

    @Action
    public void openProjectClick() 
    {
        Core.openProject();
    }

    @Action
    public void saveProjectAsClick()
    { 
        Core.saveProjectAs();
    }

    @Action
    public void saveProjectClick()
    {
        Core.saveProject();
    }
    
    @Action
    public void exitClick()
    {
        this.getApplication().exit();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ColumnNumber;
    private javax.swing.JLabel LineNumber;
    private final javax.swing.JTabbedPane Tabs = Core.getTabs();
    private javax.swing.JMenu actionMenu;
    private javax.swing.JMenuItem generateParserMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openProjectMenuItem;
    private javax.swing.JMenuItem parseMenuItem;
    private javax.swing.JMenuItem saveProjectAsMenuItem;
    private javax.swing.JMenuItem saveProjectMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

}
