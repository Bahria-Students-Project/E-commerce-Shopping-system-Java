package admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import connection.MyConnection;


public class Category extends JFrame implements ActionListener {

    JTextField searchtextfield, categorynametextfield, descriptiontextfield;
    JButton save,update,delete,clear,back;
    JTable table;

    public void loadCategories(JTable table) {
        try {
            // Establish a connection
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

            // Create a SQL query
            String sql = "SELECT * FROM category";

            // Create a statement
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(sql);

            // Get the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Clear the table
            model.setRowCount(0);

            // Add rows to the table
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("cid"), rs.getString("cname"), rs.getString("cdesc")});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    Category(){

        setLayout(null);
        setTitle("CATEGORY");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/lightgrey.png"));
        Image i2 = i1.getImage().getScaledInstance(1000, 650, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,1000,650);
        add(image);

        JLabel search = new JLabel("Search Product:");
        search.setFont(new Font("Montserrat",Font.BOLD,14));
        search.setBounds(585,30,150,30);
        image.add(search);
        searchtextfield = new JTextField();
        searchtextfield.setFont(new Font("Montserrat", Font.BOLD, 12));
        searchtextfield.setBounds(700,30,250,30);
        image.add(searchtextfield);



        JLabel categoryname = new JLabel("Category Name");
        categoryname.setFont(new Font("Montserrat",Font.BOLD,14));
        categoryname.setBounds(20,60,200,30);
        image.add(categoryname);
        categorynametextfield= new JTextField();
        categorynametextfield.setFont(new Font("Montserrat", Font.BOLD, 12));
        categorynametextfield.setBounds(20,85,250,30);
        image.add(categorynametextfield);

        JLabel description = new JLabel("Description");
        description.setFont(new Font("Montserrat",Font.BOLD,14));
        description.setBounds(20,120,200,30);
        image.add(description);
        descriptiontextfield = new JTextField();
        descriptiontextfield.setFont(new Font("Montserrat", Font.BOLD, 12));
        descriptiontextfield.setBounds(20,145,250,30);
        image.add(descriptiontextfield);

        Color color = Color.decode("#495057");

        save = new JButton("Save");
        save.setBounds(20,300,100,30);
        save.setFont(new Font("Montserrat", Font.BOLD, 16));
        save.setBackground(color);
        save.setForeground(Color.white);
        save.addActionListener(this);
        image.add(save);

        update = new JButton("Update");
        update.setBounds(170,300,100,30);
        update.setFont(new Font("Montserrat", Font.BOLD, 16));
        update.setBackground(color);
        update.setForeground(Color.white);
        update.addActionListener(this);
        image.add(update);

        delete = new JButton("Delete");
        delete.setBounds(20,350,100,30);
        delete.setFont(new Font("Montserrat", Font.BOLD, 16));
        delete.setBackground(color);
        delete.setForeground(Color.white);
        delete.addActionListener(this);
        image.add(delete);

        clear = new JButton("Clear");
        clear.setBounds(170,350,100,30);
        clear.setFont(new Font("Montserrat", Font.BOLD, 16));
        clear.setBackground(color);
        clear.setForeground(Color.white);
        clear.addActionListener(this);
        image.add(clear);

        back = new JButton("Back");
        back.setBounds(95,400,100,30);
        back.setFont(new Font("Montserrat", Font.BOLD, 16));
        back.setBackground(color);
        back.setForeground(Color.white);
        back.addActionListener(this);
        image.add(back);

        save.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                save.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        update.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                update.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        delete.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                delete.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        clear.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                clear.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });




        // Data for the first table
        Object[][] data1 = {
                // You can fill this with actual data
        };

        // Column names for the first table
        String[] columnNames1 = {"Category ID", "Category Name", "Description"};

// Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columnNames1, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are non-editable
                return false;
            }
        };

// Create the first table with the model
        table = new JTable(model);


// Create the first table with the model
        table = new JTable(model);

        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setBounds(300, 70, 650, 500);
        table.setFont(new Font("Montserrat", Font.PLAIN, 12));
        image.add(scrollPane1);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // Check if a row has been selected
                if (table.getSelectedRow() > -1) {
                    // Get the selected row
                    int selectedRow = table.getSelectedRow();

                    // Set the text fields with the data from the selected row
                    categorynametextfield.setText(table.getValueAt(selectedRow, 1).toString());
                    descriptiontextfield.setText(table.getValueAt(selectedRow, 2).toString());
                }
            }
        });

        // Add a KeyListener to the search text field
        searchtextfield.addKeyListener(new KeyAdapter(){
            public void keyReleased(KeyEvent e) {
                try {
                    // Establish a connection
                    Connection conn = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

                    // Get the search text
                    String searchText = searchtextfield.getText();

                    // Create a SQL query
                    String sql = "SELECT * FROM category WHERE cname LIKE ? OR CAST(cid AS VARCHAR(10)) LIKE ?";

                    // Create a statement
                    PreparedStatement stmt = conn.prepareStatement(sql);

                    // Set parameters
                    stmt.setString(1, "%" + searchText + "%");
                    stmt.setString(2, "%" + searchText + "%");

                    // Execute the query
                    ResultSet rs = stmt.executeQuery();

                    // Remove all existing rows
                    model.setRowCount(0);

                    // Loop through the result set and add rows to the table model
                    while (rs.next()) {
                        model.addRow(new Object[]{rs.getInt("cid"), rs.getString("cname"), rs.getString("cdesc")});
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadCategories(table);

        getContentPane().setBackground(Color.WHITE);


        setResizable(false);
        setSize(1000,650);
        setLocation(100,10);
        setVisible(true);
    }

    public void addCategory(String categoryName, String categoryDescription) {
    try {
        // Establish a connection
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

        // Create a SQL query
        String sql = "INSERT INTO category (cname, cdesc) VALUES (?, ?)";

        // Create a statement
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Set parameters
        stmt.setString(1, categoryName);
        stmt.setString(2, categoryDescription);

        // Execute the query
        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "A new category was inserted successfully!");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    public void updateCategory(String categoryName, String categoryDescription, int categoryId) {
        try {
            // Establish a connection
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

            // Create a SQL query
            String sql = "UPDATE category SET cname = ?, cdesc = ? WHERE cid = ?";

            // Create a statement
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setString(1, categoryName);
            stmt.setString(2, categoryDescription);
            stmt.setInt(3, categoryId);

            // Execute the query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "The category was updated successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            // Establish a connection
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

            // Create a SQL query
            String sql = "DELETE FROM category WHERE cid = ?";

            // Create a statement
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setInt(1, categoryId);

            // Execute the query
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "The category was deleted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isValidInput(String input) {
        // Check if the input is not empty and does not contain any numbers or special characters
        return !input.isEmpty() && input.matches("^[a-zA-Z ]*$");
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == save) {
            String categoryName = categorynametextfield.getText();
            String categoryDescription = descriptiontextfield.getText();
            if (isValidInput(categoryName) && isValidInput(categoryDescription)) {
                addCategory(categoryName, categoryDescription);
                loadCategories(table);  // Refresh the table
            } else {
                JOptionPane.showMessageDialog(null, "Input is not valid. Please enter text only and make sure the fields are not empty.");
            }
        }
        if(ae.getSource() == update) {
            String categoryName = categorynametextfield.getText();
            String categoryDescription = descriptiontextfield.getText();
            if (isValidInput(categoryName) && isValidInput(categoryDescription)) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int categoryId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    updateCategory(categoryName, categoryDescription, categoryId);
                    loadCategories(table);  // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a category to update.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Input is not valid. Please enter text only and make sure the fields are not empty.");
            }
        }
        if(ae.getSource() == delete) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int categoryId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                deleteCategory(categoryId);
                loadCategories(table);  // Refresh the table
            } else {
                JOptionPane.showMessageDialog(null, "Please select a category to delete.");
            }
        }
        if(ae.getSource() == clear){
            categorynametextfield.setText("");
            descriptiontextfield.setText("");
        }
        if(ae.getSource() == back){
            this.setVisible(false);
            new AdminDashboard().setVisible(true);
        }
    }
    public static void main(String[] args) {
        new Category().setVisible(true);

    }
}