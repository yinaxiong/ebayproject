/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ebayproject;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 *
 * @author nbevacqu
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        
        if(this.searializedSearchPatternsFile.exists()) {
            try {
                FileInputStream f = new FileInputStream(this.searializedSearchPatternsFile);
                ObjectInputStream s = new ObjectInputStream(f);
                ArrayList<HashMap<String, String>> fileObj = (ArrayList<HashMap<String, String>>) s.readObject();
                s.close();
                this.searchPatterns = fileObj;
                System.out.println(String.format("found %d search patterns in file", searchPatterns.size()));
            } catch (Exception ex) {
                System.err.println("Could not read/find pre-existing saved search patterns file");
                this.searchPatterns = new ArrayList<HashMap<String, String>>();
            }
        } else {
            this.searchPatterns = new ArrayList<HashMap<String, String>>();
        }
        
        final JButton deleteButton = this.deleteSearchPatternButton;
        final JButton searchButton = this.performSearchButton;
        final JButton editButton = this.editSearchPatternButton;
        
        checkBoxSelectedRunnable = new Runnable() {
            public void run() {
                int numSelected = 0;
                
                synchronized(checkboxes) {
                    for(JCheckBox cb : checkboxes) {
                        if(cb.isSelected())
                            numSelected++;
                    }
                }
                
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                searchButton.setEnabled(false);
                
                if(numSelected == 0) {
                    return;
                }
                
                deleteButton.setEnabled(true);
                searchButton.setEnabled(true);
                
                if(numSelected == 1) {
                    editButton.setEnabled(true);
                }
            }
        };
        
        synchronized(searchPatterns) {
            for(HashMap<String, String> hm : searchPatterns) {
                this.jPanel1.add(new HomeSelection(hm, this.checkboxes, checkBoxSelectedRunnable, mapCheckboxToSearchPattern));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editSearchPatternButton = new javax.swing.JButton();
        deleteSearchPatternButton = new javax.swing.JButton();
        performSearchButton = new javax.swing.JButton();
        addSearchPatternButton = new javax.swing.JButton();
        openSavedSearchResultButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        selectAllButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        editSearchPatternButton.setText("Edit Search Pattern");
        editSearchPatternButton.setEnabled(false);
        editSearchPatternButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSearchPatternButtonActionPerformed(evt);
            }
        });

        deleteSearchPatternButton.setText("Delete Search Patten");
        deleteSearchPatternButton.setEnabled(false);
        deleteSearchPatternButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSearchPatternButtonActionPerformed(evt);
            }
        });

        performSearchButton.setText("Perform Search");
        performSearchButton.setEnabled(false);
        performSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                performSearchButtonActionPerformed(evt);
            }
        });

        addSearchPatternButton.setText("Add Search Patten");
        addSearchPatternButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSearchPatternButtonActionPerformed(evt);
            }
        });

        openSavedSearchResultButton.setText("Open Saved Search Result");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Ebay Saved Searches");

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        selectAllButton.setText("Select All");
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openSavedSearchResultButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addSearchPatternButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(performSearchButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteSearchPatternButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(editSearchPatternButton, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(101, 101, 101))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(selectAllButton))
                        .addContainerGap(322, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(selectAllButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editSearchPatternButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteSearchPatternButton)
                        .addGap(18, 18, 18)
                        .addComponent(performSearchButton)
                        .addGap(18, 18, 18)
                        .addComponent(addSearchPatternButton)
                        .addGap(18, 18, 18)
                        .addComponent(openSavedSearchResultButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(362, 362, 362))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editSearchPatternButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSearchPatternButtonActionPerformed
        HashMap<String, String> map = null;
        for(JCheckBox cb : checkboxes) {
            if(cb.isSelected()) {
                map = this.mapCheckboxToSearchPattern.get(cb).getData();
            }
        }
        if(map != null) {
            Runnable serializeDataRunnable = new Runnable() {
                public void run() {
                    Helpers.serializeListOfHashMaps(searializedSearchPatternsFile, searchPatterns);
                    for(HomeSelection hs : mapCheckboxToSearchPattern.values())
                        hs.updateLabels();
                }
            };
            EditFrame ef = new EditFrame(this, map, serializeDataRunnable);
            ef.setVisible(true);
        }
    }//GEN-LAST:event_editSearchPatternButtonActionPerformed

    private void deleteSearchPatternButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSearchPatternButtonActionPerformed
        System.out.println("delete button pressed");
        ArrayList<HashMap<String, String>> toRemove = new ArrayList<HashMap<String, String>>();
        ArrayList<Component> componentsToRemove = new ArrayList<Component>();
        
        synchronized(checkboxes) {
            for(JCheckBox cb : checkboxes) {
                if(cb.isSelected()) {
                    toRemove.add(this.mapCheckboxToSearchPattern.get(cb).getData());
                    componentsToRemove.add(this.mapCheckboxToSearchPattern.get(cb));
                }
            }
        }
        
        this.searchPatterns.removeAll(toRemove);
        for(Component c : componentsToRemove) {
            this.jPanel1.remove(c);
            if(c instanceof HomeSelection) {
                ((HomeSelection)c).delete();
            }
        }
        this.jPanel1.repaint();
        this.jPanel1.updateUI();
        Helpers.serializeListOfHashMaps(searializedSearchPatternsFile, searchPatterns);
    }//GEN-LAST:event_deleteSearchPatternButtonActionPerformed

    private void performSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_performSearchButtonActionPerformed
        try {
            ResultsFrame rf = new ResultsFrame(this, this.searchPatterns);
            rf.setVisible(true);
        } catch (Exception ex) {
            System.err.println("could not show results frame");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_performSearchButtonActionPerformed

    private void addSearchPatternButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSearchPatternButtonActionPerformed
        System.out.println("adding new search pattern");
        HashMap<String, String> hm = new HashMap<String, String>();
        this.searchPatterns.add(hm);
        hm.put("name", "new search pattern");
        this.jPanel1.add(new HomeSelection(hm, this.checkboxes, checkBoxSelectedRunnable, mapCheckboxToSearchPattern));
        Helpers.serializeListOfHashMaps(this.searializedSearchPatternsFile, this.searchPatterns);
        this.jPanel1.repaint();
        this.jPanel1.updateUI();
        //javax.swing.SwingUtilities.invokeLater(checkBoxSelectedRunnable);
    }//GEN-LAST:event_addSearchPatternButtonActionPerformed

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        // TODO add your handling code here:
        for(JCheckBox cb : checkboxes) {
            cb.setSelected(true);
            //this.checkBoxSelectedRunnable.run();
            javax.swing.SwingUtilities.invokeLater(checkBoxSelectedRunnable);
        }
    }//GEN-LAST:event_selectAllButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Home().setVisible(true);
            }
        });
    }
    
    private Runnable checkBoxSelectedRunnable;
    private ArrayList<HashMap<String, String>> searchPatterns;
    private HashMap<JCheckBox, HomeSelection> mapCheckboxToSearchPattern = new HashMap<JCheckBox, HomeSelection>();
    private File searializedSearchPatternsFile = new File("searchPatterns.data");
    private ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSearchPatternButton;
    private javax.swing.JButton deleteSearchPatternButton;
    private javax.swing.JButton editSearchPatternButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton openSavedSearchResultButton;
    private javax.swing.JButton performSearchButton;
    private javax.swing.JButton selectAllButton;
    // End of variables declaration//GEN-END:variables
}
