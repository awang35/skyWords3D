/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

//import java.sql.Array;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import net.proteanit.sql.DbUtils;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author James Le
 */
public class UI extends javax.swing.JFrame {

    Connection conn = null;
    ResultSet rs = null;
    Statement stat = null;
    public String CurrentPlayer = "Default";
    PreparedStatement pst = null;
    private int Vehicle = 0;
    private int Object = 1, Animal = 2, People = 3,
            Flowers = 4, UserDefined = 5, Default = 6;
    public List<String> Words = new ArrayList();

    /**
     * Creates new form UI
     */
    public UI() {
        initComponents();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:wordGame.db");
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        UpdateScoreTable();
        UpdateWordTable();
        
    }

    public void getWords() {
        try {
            pst = conn.prepareStatement("select WordName from WordBank;");
            rs = pst.executeQuery();
            Words.clear();
            int i = 0;
            while (rs.next()) {
                Words.add(rs.getString("WordName"));
                System.out.println(Words.get(i));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setWords(int type) throws SQLException {
        switch (type) {
            case (0):

                pst = conn.prepareStatement("select WordName from WordBank where Category = 'Vehicle';");
                rs = pst.executeQuery();
                Words.clear();
                while (rs.next()) {
                    Words.add(rs.getString("WordName"));
                }
            case (1):
                pst = conn.prepareStatement("select WordName from WordBank where Category = 'Object';");
                Words = (ArrayList<String>) DbUtils.resultSetToTableModel(rs);
                rs = pst.executeQuery();
                Words.clear();
                while (rs.next()) {
                    Words.add(rs.getString("WordName"));
                }
                break;
            case (2):
                pst = conn.prepareStatement("select WordName from WordBank where Category = 'Animal';");
                Words = (ArrayList<String>) DbUtils.resultSetToTableModel(rs);
                rs = pst.executeQuery();
                Words.clear();
                while (rs.next()) {
                    Words.add(rs.getString("WordName"));
                }
                break;
            case (3):
                pst = conn.prepareStatement("select WordName from WordBank where Category = 'People';");
                Words = (ArrayList<String>) DbUtils.resultSetToTableModel(rs);
                rs = pst.executeQuery();
                Words.clear();
                while (rs.next()) {
                    Words.add(rs.getString("WordName"));
                }
                break;
            case (4):
                pst = conn.prepareStatement("select WordName from WordBank where Category = 'Flowers';");
                Words = (ArrayList<String>) DbUtils.resultSetToTableModel(rs);
                rs = pst.executeQuery();
                Words.clear();
                while (rs.next()) {
                    Words.add(rs.getString("WordName"));
                }
                break;
            case (5):
                pst = conn.prepareStatement("select WordName from WordBank where Category = 'Uncategorized';");
                Words = (ArrayList<String>) DbUtils.resultSetToTableModel(rs);
                rs = pst.executeQuery();
                Words.clear();
                while (rs.next()) {
                    Words.add(rs.getString("WordName"));
                }
                break;
            default:
                System.out.println("Something went wrong with Category picking");
                break;

        }
    }

    public void UpdateScoreAndWordTable() {
        UpdateScoreTable();
        UpdateWordTable();
    }

    private void UpdateScoreTable() {
        String sql = "select Score, PlayerName, NumWordsCorrect, Time from ScoreBank join PlayerBank on ScoreBank.PlayerID = PlayerBank.PlayerID order by score DESC LIMIT 5;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            ScoreTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void UpdateWordTable() {
        String sql = "select Distinct WordName from WordBank as wb join PlayerBank as pb on wb.PlayerID = pb.PlayerID where pb.PlayerName = 'Default';";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            TableWords.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void UpdateFullWordTable() {
        String sql = "select Distinct WordName from WordBank;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            TableWords.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InsertSynWord(String syn, String word) {
        String tword = word;
        String tsyn = syn;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery("INSERT INTO SynBank(WordName,Syn,PlayerID)VALUES('" + tword + "','" + tsyn + "',5);");
            rs.close();
        } catch (SQLException ex) {
            //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Syn Erro");
        }
        System.out.println("work! SYN!!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        NewPlayerField = new javax.swing.JTextField();
        AddPlayerButton = new javax.swing.JButton();
        GetButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        CurrentPlayerField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        CurrentUserField = new javax.swing.JTextField();
        DeleteUsrButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ScoreTable = new javax.swing.JTable();
        PlayerSearchButton = new javax.swing.JButton();
        PlayerSearchField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ViewTop5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        SearchField = new javax.swing.JTextField();
        GoButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        AddWordField = new javax.swing.JTextField();
        InsertButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableWords = new javax.swing.JTable();
        UserDefinedWordsBut = new javax.swing.JButton();
        OriginalWordsBut = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        DeleteField = new javax.swing.JTextField();
        DeleteButton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        VehicleCategory = new javax.swing.JButton();
        ObjectCategory = new javax.swing.JButton();
        AnimalCategory = new javax.swing.JButton();
        PeopleCategory = new javax.swing.JButton();
        FlowersCategory = new javax.swing.JButton();
        UserDefinedCategory = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jSlider1 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jSlider2 = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        Play = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Bell MT", 3, 24)); // NOI18N
        jLabel1.setText("Main Menu");

        jLabel8.setText("New Player: ");

        AddPlayerButton.setText("Add");
        AddPlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPlayerButtonActionPerformed(evt);
            }
        });

        GetButton.setText("Get");
        GetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GetButtonActionPerformed(evt);
            }
        });

        jLabel9.setText("UserName: ");

        jLabel10.setText("Current User:");

        DeleteUsrButton.setText("Delete");
        DeleteUsrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteUsrButtonActionPerformed(evt);
            }
        });

        jLabel11.setText("*Note - Delete will permenatly delete the current user logged ");

        jLabel12.setText("including, scores, words or anything assoicated with the user.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(NewPlayerField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CurrentPlayerField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AddPlayerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(GetButton)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(CurrentUserField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DeleteUsrButton))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AddPlayerButton, DeleteUsrButton, GetButton});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CurrentPlayerField, CurrentUserField, NewPlayerField});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(NewPlayerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddPlayerButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GetButton)
                    .addComponent(jLabel9)
                    .addComponent(CurrentPlayerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(CurrentUserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteUsrButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Player", jPanel2);

        ScoreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ScoreTable.setEnabled(false);
        ScoreTable.setRowSelectionAllowed(false);
        ScoreTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(ScoreTable);
        ScoreTable.getColumnModel().getColumn(0).setResizable(false);
        ScoreTable.getColumnModel().getColumn(1).setResizable(false);
        ScoreTable.getColumnModel().getColumn(2).setResizable(false);
        ScoreTable.getColumnModel().getColumn(3).setResizable(false);

        PlayerSearchButton.setText("Search");
        PlayerSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayerSearchButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("Player:");

        ViewTop5.setText("View");
        ViewTop5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewTop5ActionPerformed(evt);
            }
        });

        jLabel14.setText("View top 5 score");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(29, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PlayerSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel13)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ViewTop5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(PlayerSearchButton)
                .addGap(31, 31, 31))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(PlayerSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PlayerSearchButton))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ViewTop5)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel14))))
        );

        jTabbedPane1.addTab("Score", jPanel3);

        jLabel4.setText("Search: ");

        SearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFieldActionPerformed(evt);
            }
        });

        GoButton.setText("Go");
        GoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Add Word");

        InsertButton.setText("Insert");
        InsertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertButtonActionPerformed(evt);
            }
        });

        TableWords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableWords.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TableWords.setEnabled(false);
        TableWords.setRowSelectionAllowed(false);
        TableWords.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(TableWords);
        TableWords.getColumnModel().getColumn(0).setResizable(false);
        TableWords.getColumnModel().getColumn(1).setResizable(false);
        TableWords.getColumnModel().getColumn(2).setResizable(false);
        TableWords.getColumnModel().getColumn(3).setResizable(false);

        UserDefinedWordsBut.setText("All Words");
        UserDefinedWordsBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserDefinedWordsButActionPerformed(evt);
            }
        });

        OriginalWordsBut.setText("Original Words");
        OriginalWordsBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OriginalWordsButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(UserDefinedWordsBut)
                .addGap(39, 39, 39)
                .addComponent(OriginalWordsBut)
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {OriginalWordsBut, UserDefinedWordsBut});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserDefinedWordsBut)
                    .addComponent(OriginalWordsBut))
                .addContainerGap())
        );

        jLabel6.setText("Delete Word");

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteField)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddWordField, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GoButton)
                    .addComponent(InsertButton)
                    .addComponent(DeleteButton))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AddWordField, DeleteField, SearchField});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(GoButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddWordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(InsertButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeleteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(DeleteButton))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Words", jPanel4);

        VehicleCategory.setText("Vehicle");
        VehicleCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VehicleCategoryActionPerformed(evt);
            }
        });

        ObjectCategory.setText("Object");
        ObjectCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ObjectCategoryActionPerformed(evt);
            }
        });

        AnimalCategory.setText("Animal");
        AnimalCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnimalCategoryActionPerformed(evt);
            }
        });

        PeopleCategory.setText("People");
        PeopleCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PeopleCategoryActionPerformed(evt);
            }
        });

        FlowersCategory.setText("Flowers");
        FlowersCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlowersCategoryActionPerformed(evt);
            }
        });

        UserDefinedCategory.setText("UserDefined");
        UserDefinedCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserDefinedCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(VehicleCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ObjectCategory)
                    .addComponent(AnimalCategory)
                    .addComponent(PeopleCategory)
                    .addComponent(FlowersCategory)
                    .addComponent(UserDefinedCategory))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AnimalCategory, FlowersCategory, ObjectCategory, PeopleCategory, UserDefinedCategory, VehicleCategory});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VehicleCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ObjectCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AnimalCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PeopleCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FlowersCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserDefinedCategory)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Category", jPanel7);

        jRadioButton1.setText("Easy");

        jRadioButton2.setText("Normal");

        jRadioButton3.setText("Hard");

        jLabel2.setText("Master Sound");

        jLabel3.setText("Music");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addContainerGap(194, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jRadioButton1, jRadioButton2, jRadioButton3});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSlider1, jSlider2});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jSlider1, jSlider2});

        jTabbedPane1.addTab("Options", jPanel5);

        Play.setText("Play");
        Play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(Play, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Play)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayActionPerformed
        // TODO add your handling code here:
        if(CurrentPlayer.equals("Default")){
            System.out.println("Please Have User");
        }
        else{
            getWords();
            System.out.println("PlayGame");
        }
    }//GEN-LAST:event_PlayActionPerformed

    private void GoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoButtonActionPerformed
        // TODO add your handling code here:
        String str = SearchField.getText();
        if (str.startsWith(" ")) {
            System.out.println("Don't work!");
        } else {
            try {
                pst = conn.prepareStatement("Select wb.WordName, sb.Syn, wb.WordLength from SynBank as sb Join WordBank as wb where sb.WordName = wb.WordName and wb.WordNAme = '" + str + "';");
                rs = pst.executeQuery();
                SearchField.setText(" ");
                TableWords.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException ex) {
                //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                if (!str.equals(rs)) {
                    CurrentUserField.setText("Invalid User Name");
                }
            }
        }
    }//GEN-LAST:event_GoButtonActionPerformed

    private void SearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchFieldActionPerformed

    private void AddPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPlayerButtonActionPerformed
        // TODO add your handling code here:
        String str = NewPlayerField.getText();
        boolean allowScore = false;
        if (str.startsWith(" ")) {
            System.out.println("Don't work!");
        } else {
            System.out.println("Go Here");
            try {
                allowScore = true;
                stat = conn.createStatement();
                rs = stat.executeQuery("INSERT INTO PlayerBank( PlayerName )VALUES('" + str + "');");
                rs.close();


            } catch (Exception e) {
                // Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);        
            }


            NewPlayerField.setText("");
            CurrentUserField.setText(str);
            CurrentPlayer = str;
        }
        if (allowScore == true) {
            updateScore(0, str, 0, 0);
        }

    }//GEN-LAST:event_AddPlayerButtonActionPerformed
    public void updateScore(int Score, String CurrentUser, int wordscorrect, int time) {
        String str = CurrentUser;
        System.out.println("UpdateScore");
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery("INSERT INTO ScoreBank ( Score, PlayerID, NumWordsCorrect, Time ) VALUES ( " + Score
                    + ",( SELECT PlayerID FROM PlayerBank WHERE PlayerName = '" + str + "' ),"
                    + wordscorrect + "," + time + ");");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UpdateScore Error line 757");
        }
    }

    private void GetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetButtonActionPerformed
        // TODO add your handling code here:
        String str = CurrentPlayerField.getText();
        if (str.startsWith(" ")) {
            System.out.println("Don't work!");
        } else {
            try {
                pst = conn.prepareStatement("Select PlayerName from PlayerBank where PlayerName = '" + str + "';");
                rs = pst.executeQuery();

                System.out.print(rs.getString(WIDTH));
                CurrentPlayerField.setText(" ");
                CurrentUserField.setText(str);
                CurrentPlayer = str;
            } catch (SQLException ex) {
                //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                if (!str.equals(rs)) {
                    CurrentUserField.setText("Invalid User Name");
                }
            }
        }

    }//GEN-LAST:event_GetButtonActionPerformed

    private void DeleteUsrButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteUsrButtonActionPerformed
        // TODO add your handling code here:
        String str = CurrentUserField.getText();
        String sql = "DELETE FROM PlayerBank where PlayerName = '" + str + "';";
        if (str.startsWith(" ")) {
            System.out.println("Don't work!");
        } else {
            try {
                stat = conn.createStatement();
                //  rs = stat.executeQuery("DELETE FROM SynBank where PlayerID = (select PlayerID from PlayerBank where PlayerName = '" + str + "')");
                //   rs.close();
                //   rs = stat.executeQuery("DELETE FROM WordBank where PlayerID = (select PlayerID from PlayerBank where PlayerName = '" + str + "')");
                //    rs.close();
                //     rs = stat.executeQuery("DELETE FROM ScoreBank where PlayerID = (select PlayerID from PlayerBank where PlayerName = '" + str + "')");
                //     rs.close();
                rs = stat.executeQuery("DELETE FROM PlayerBank where PlayerName = '" + str + "';");
                rs.close();
                System.out.println(str);
                CurrentPlayer = "Default";
            } catch (SQLException ex) {
                // Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DeleteUsrButtonActionPerformed

    private void VehicleCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VehicleCategoryActionPerformed
        try {
            setWords(0);
        } catch (SQLException ex) {
            //  Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_VehicleCategoryActionPerformed

    private void PlayerSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayerSearchButtonActionPerformed
        // TODO add your handling code here:
        String str = PlayerSearchField.getText();
        if (str.startsWith(" ")) {
            System.out.println("Don't work!");
        } else {
            try {
                pst = conn.prepareStatement("select Score, PlayerName, NumWordsCorrect, Time from ScoreBank join PlayerBank on ScoreBank.PlayerID = PlayerBank.PlayerID where PlayerBank.PlayerName = '" + str + "';");
                rs = pst.executeQuery();
                ScoreTable.setModel(DbUtils.resultSetToTableModel(rs));
                PlayerSearchField.setText(" ");

            } catch (SQLException ex) {
                //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_PlayerSearchButtonActionPerformed

    private void ViewTop5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewTop5ActionPerformed
        // TODO add your handling code here:
        UpdateScoreTable();
    }//GEN-LAST:event_ViewTop5ActionPerformed

    private void InsertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertButtonActionPerformed
        // TODO add your handling code here:
        String str = AddWordField.getText();
        boolean allowSynWrite = false;
        if (CurrentPlayer.equals("Default")) {
            AddWordField.setText("Please set your user name");
        } else {

            int length = str.length();
            if (str.startsWith(" ")) {
                System.out.println("Don't work!");
            } else {
                try {
                    System.out.println("Length " + length + " Word " + str + " Current Player: " + CurrentPlayer);
                    allowSynWrite = true;
                    stat = conn.createStatement();
                    rs = stat.executeQuery("INSERT INTO WordBank(WordName,WordLength,PlayerID)VALUES ('" + str + "'," + length + ", (select PlayerID from PlayerBank where PlayerName = '" + CurrentPlayer + "'));");
                    rs.close();
                    AddWordField.setText(" ");

                } catch (SQLException ex) {
                    System.out.println("Insert word Error");
                    //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (allowSynWrite == true) {
            InsertSynWord(str, "bro");
        }
        UpdateFullWordTable();
    }//GEN-LAST:event_InsertButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        // TODO add your handling code here:
        String str = DeleteField.getText();
        System.out.println("Delete Button");
        if (str.startsWith(" ")) {
            System.out.println("Don't work!");
        } else {
            try {
                stat = conn.createStatement();
                rs = stat.executeQuery("Delete from WordBank where WordName = '" + str + "' AND PlayerID <> 0;");
                rs.close();
                // TableWords.setModel(DbUtils.resultSetToTableModel(rs));


            } catch (SQLException ex) {
                //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Delete Error");
            }
        }
        DeleteField.setText(" ");
        UpdateFullWordTable();
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void ObjectCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ObjectCategoryActionPerformed
        try {
            // TODO add your handling code here:
            setWords(1);
        } catch (SQLException ex) {
            //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ObjectCategoryActionPerformed

    private void AnimalCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnimalCategoryActionPerformed
        try {
            // TODO add your handling code here:
            setWords(2);
        } catch (SQLException ex) {
            //Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AnimalCategoryActionPerformed

    private void PeopleCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PeopleCategoryActionPerformed
        try {
            // TODO add your handling code here:
            setWords(3);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PeopleCategoryActionPerformed

    private void FlowersCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlowersCategoryActionPerformed
        try {
            // TODO add your handling code here:
            setWords(4);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_FlowersCategoryActionPerformed

    private void UserDefinedCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserDefinedCategoryActionPerformed
        try {
            // TODO add your handling code here:
            setWords(5);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UserDefinedCategoryActionPerformed

    private void OriginalWordsButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OriginalWordsButActionPerformed
        // TODO add your handling code here:
        UpdateWordTable();
    }//GEN-LAST:event_OriginalWordsButActionPerformed

    private void UserDefinedWordsButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserDefinedWordsButActionPerformed
        // TODO add your handling code here:
        UpdateFullWordTable();
    }//GEN-LAST:event_UserDefinedWordsButActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPlayerButton;
    private javax.swing.JTextField AddWordField;
    private javax.swing.JButton AnimalCategory;
    private javax.swing.JTextField CurrentPlayerField;
    private javax.swing.JTextField CurrentUserField;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JTextField DeleteField;
    private javax.swing.JButton DeleteUsrButton;
    private javax.swing.JButton FlowersCategory;
    private javax.swing.JButton GetButton;
    private javax.swing.JButton GoButton;
    private javax.swing.JButton InsertButton;
    private javax.swing.JTextField NewPlayerField;
    private javax.swing.JButton ObjectCategory;
    private javax.swing.JButton OriginalWordsBut;
    private javax.swing.JButton PeopleCategory;
    private javax.swing.JButton Play;
    private javax.swing.JButton PlayerSearchButton;
    private javax.swing.JTextField PlayerSearchField;
    private javax.swing.JTable ScoreTable;
    private javax.swing.JTextField SearchField;
    private javax.swing.JTable TableWords;
    private javax.swing.JButton UserDefinedCategory;
    private javax.swing.JButton UserDefinedWordsBut;
    private javax.swing.JButton VehicleCategory;
    private javax.swing.JButton ViewTop5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
