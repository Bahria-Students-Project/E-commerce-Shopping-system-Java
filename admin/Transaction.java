package admin;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class Transaction extends JFrame implements ActionListener {

    JTextField searchtextfield;
    JButton back;
    JTable table;
    String[] columnNames1 = {"PurchaseID", "UserID", "ProductID", "Quantity", "Price", "Total"};

    Transaction() {
        setLayout(null);
        setTitle("Transaction");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bw.png"));
        Image i2 = i1.getImage().getScaledInstance(900, 600, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 600);
        add(image);

        JLabel title = new JLabel("Transaction Details");
        title.setFont(new Font("Montserrat", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(360, 10, 200, 40);
        image.add(title);

        JLabel search = new JLabel("Search Product:");
        search.setFont(new Font("Montserrat", Font.BOLD, 14));
        search.setBounds(545, 55, 150, 30);
        image.add(search);
        searchtextfield = new JTextField();
        searchtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        searchtextfield.setBounds(660, 60, 200, 25);
        image.add(searchtextfield);

        Color color = Color.decode("#495057");

        back = new JButton("Back");
        back.setBackground(color);
        back.setForeground(Color.WHITE);
        back.setBounds(760, 510, 100, 30);
        back.setFont(new Font("Montserrat", Font.BOLD, 16));
        back.addActionListener(this);
        image.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Data for the first table
        Object[][] data = fetchData();

        // Column names for the first table
        DefaultTableModel model = new DefaultTableModel(data, columnNames1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 810, 400);
        table.setFont(new Font("Montserrat", Font.PLAIN, 12));
        image.add(scrollPane);

        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(900, 600);
        setLocation(200, 50);
        setVisible(true);

        // Add a document listener to your searchtextfield
        searchtextfield.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                search();
            }
            public void removeUpdate(DocumentEvent e) {
                search();
            }
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            public void search() {
                String key = searchtextfield.getText();
                if (key.length() > 0) {
                    // Search for the key in your data
                    Object[][] searchData = fetchSearchData(key);
                    // Update your table model
                    DefaultTableModel model = new DefaultTableModel(searchData, columnNames1) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    table.setModel(model);
                } else {
                    // If the search field is empty, show all data
                    Object[][] data = fetchData();
                    DefaultTableModel model = new DefaultTableModel(data, columnNames1) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    table.setModel(model);
                }
            }
        });
    }

    private Object[][] fetchData() {
        ArrayList<Object[]> data = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");
            String sql = "SELECT id, uid, pid, qty, price, total FROM purchase";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("id");
                row[1] = rs.getInt("uid");
                row[2] = rs.getInt("pid");
                row[3] = rs.getInt("qty");
                row[4] = rs.getFloat("price");
                row[5] = rs.getFloat("total");
                data.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data.toArray(new Object[0][]);
    }

    private Object[][] fetchSearchData(String key) {
        ArrayList<Object[]> data = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");
            String sql = "SELECT id, uid, pid, qty, price, total FROM purchase WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(key));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("id");
                row[1] = rs.getInt("uid");
                row[2] = rs.getInt("pid");
                row[3] = rs.getInt("qty");
                row[4] = rs.getFloat("price");
                row[5] = rs.getFloat("total");
                data.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data.toArray(new Object[0][]);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            this.setVisible(false);
            new AdminDashboard().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Transaction().setVisible(true);
    }
}