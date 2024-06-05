package admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Product extends JFrame implements ActionListener {

    JLabel search, productname, category, quantity, price, imagetext;
    JTextField searchtextfield, productnametextfield, quantitytextfield,pricetextfield;
    JButton save,update,delete,clear,back;
    JTextArea dropTargetArea;
    JTable table;
    JComboBox<String> categoryComboBox;


    private void loadProducts(JTable table) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

            String sql = "SELECT pr.*, c.cname AS category_name FROM product pr " +
                    "JOIN category c ON pr.cname = c.cname";

            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("pid");
                row[1] = rs.getString("pname");
                row[2] = rs.getString("category_name");
                row[3] = rs.getInt("pqty");
                row[4] = rs.getFloat("pprice");
                row[5] = rs.getString("pimage");
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    Product(){

        setLayout(null);
        setTitle("PRODUCT");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/lightgrey.png"));
        Image i2 = i1.getImage().getScaledInstance(980, 650, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,980,650);
        add(image);

        search = new JLabel("Search Product:");
        search.setFont(new Font("Montserrat",Font.BOLD,12));
        search.setBounds(600,10,100,30);
        image.add(search);
        searchtextfield = new JTextField();
        searchtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        searchtextfield.setBounds(700,15,250,25);
        image.add(searchtextfield);


        productname = new JLabel("Product Name");
        productname.setFont(new Font("Montserrat",Font.BOLD,14));
        productname.setBounds(20,50,200,30);
        image.add(productname);
        productnametextfield = new JTextField();
        productnametextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        productnametextfield.setBounds(20,75,250,30);
        image.add(productnametextfield);

        category = new JLabel("Category");
        category.setFont(new Font("Montserrat",Font.BOLD,14));
        category.setBounds(20,110,200,30);
        image.add(category);
        categoryComboBox = new JComboBox<String>();
        categoryComboBox.setBounds(20,135,250,30);
        image.add(categoryComboBox);
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true", "sa", "data123");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT cname FROM category");
            while (rs.next()) {
                categoryComboBox.addItem(rs.getString("cname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        quantity = new JLabel("Quantity");
        quantity.setFont(new Font("Montserrat",Font.BOLD,14));
        quantity.setBounds(20,170,200,30);
        image.add(quantity);
        quantitytextfield = new JTextField();
        quantitytextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        quantitytextfield.setBounds(20,195,250,30);
        image.add(quantitytextfield);

        price = new JLabel("Price");
        price.setFont(new Font("Montserrat",Font.BOLD,14));
        price.setBounds(20,230,200,30);
        image.add(price);
        pricetextfield = new JTextField();
        pricetextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        pricetextfield.setBounds(20,255,250,30);
        image.add(pricetextfield);

        imagetext = new JLabel("Image");
        imagetext.setFont(new Font("Montserrat",Font.BOLD,14));
        imagetext.setBounds(20,290,200,30);
        image.add(imagetext);
        dropTargetArea = new JTextArea("Drop file here");
        dropTargetArea.setBounds(20,315,250,30);
        dropTargetArea.setEditable(false);
        dropTargetArea.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add black border
        image.add(dropTargetArea);



        Color color = Color.decode("#495057");

        save = new JButton("Save");
        save.setBounds(20,450,100,30);
        save.setFont(new Font("Montserrat", Font.BOLD, 16));
        save.setBackground(color);
        save.setForeground(Color.white);
        save.addActionListener(this);
        image.add(save);

        update = new JButton("Update");
        update.setBounds(170,450,100,30);
        update.setFont(new Font("Montserrat", Font.BOLD, 16));
        update.setBackground(color);
        update.setForeground(Color.white);
        update.addActionListener(this);
        image.add(update);

        delete = new JButton("Delete");
        delete.setBounds(20,500,100,30);
        delete.setFont(new Font("Montserrat", Font.BOLD, 16));
        delete.setBackground(color);
        delete.setForeground(Color.white);
        delete.addActionListener(this);
        image.add(delete);

        clear = new JButton("Clear");
        clear.setBounds(170,500,100,30);
        clear.setFont(new Font("Montserrat", Font.BOLD, 16));
        clear.setBackground(color);
        clear.setForeground(Color.white);
        clear.addActionListener(this);
        image.add(clear);

        back = new JButton("Back");
        back.setBounds(95,550,100,30);
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

        clear.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                clear.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // Column names for the first table
        String[] columnNames1 = {"Product ID", "Product Name", "Category Name", "Quantity", "Price", "Image"};

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Product ID", "Product Name", "Category Name", "Quantity", "Price", "Image", "View Image"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "View Image" column is editable
                return column == 6;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300, 70, 650, 530);
        image.add(scrollPane);

        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel("View Image");
                label.setForeground(Color.BLACK);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return label;
            }
        });


table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // When the "View Image" label is clicked, open a new JFrame with the image
        String imagePath = table.getValueAt(row, 5).toString();
        JFrame imageFrame = new JFrame();
        imageFrame.setLayout(new BorderLayout());

        // Set a fixed size for the image
        int imageWidth = 500;
        int imageHeight = 500;

        // Create an ImageIcon and scale it to the fixed size
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));

        JLabel imageLabel = new JLabel(imageIcon);
        imageFrame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        imageFrame.setSize(imageWidth, imageHeight);
        imageFrame.setLocationRelativeTo(null);
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imageFrame.setVisible(true);
        // Return a label with a hand cursor
        JLabel label = new JLabel("View Image");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }
});


        loadProducts(table);

        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setPreferredWidth(0);


        new DropTarget(dropTargetArea, new DropTargetAdapter() {
            public void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable t = dtde.getTransferable();
                List<File> files;
                try {
                    files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : files) {
                        String filePath = file.getAbsolutePath();
                        String extension = filePath.substring(filePath.lastIndexOf("."));
                        if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpeg")) {
                            dropTargetArea.setText(filePath);
                            dropTargetArea.setText(filePath); // Display the file path in the JTextArea
                        } else {
                            JOptionPane.showMessageDialog(null, "Please drop an image file (.jpg, .png, .jpeg)");
                        }
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    searchtextfield.addKeyListener(new KeyAdapter(){
    public void keyReleased(KeyEvent e) {
        try {
            // Establish a connection
            String url = "jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true";
            Connection conn = DriverManager.getConnection(url);

            // Get the search text
            String searchText = searchtextfield.getText();

            // Create a SQL query
            String sql = "SELECT * FROM product WHERE pname LIKE ? OR CAST(pid AS VARCHAR(10)) LIKE ?";

            // Create a statement
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Get the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Remove all existing rows
            model.setRowCount(0);

            // Loop through the result set and add rows to the table model
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("pid");
                row[1] = rs.getString("pname");
                row[2] = rs.getString("cname");
                row[3] = rs.getInt("pqty");
                row[4] = rs.getFloat("pprice");
                row[5] = rs.getString("pimage");
                model.addRow(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
});

    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
    public void valueChanged(ListSelectionEvent event) {
        // Check if a row has been selected
        if (table.getSelectedRow() > -1) {
            // Get the selected row
            int selectedRow = table.getSelectedRow();

            // Set the text fields with the data from the selected row
            productnametextfield.setText(table.getValueAt(selectedRow, 1).toString());
            categoryComboBox.setSelectedItem(table.getValueAt(selectedRow, 2).toString());
            quantitytextfield.setText(table.getValueAt(selectedRow, 3).toString());
            pricetextfield.setText(table.getValueAt(selectedRow, 4).toString());
            dropTargetArea.setText(table.getValueAt(selectedRow, 5).toString());
        }
    }
});

        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(980,650);
        setLocation(100,10);
        setVisible(true);
    }

public boolean isValidInput() {
    String productName = productnametextfield.getText();
    String quantity = quantitytextfield.getText();
    String price = pricetextfield.getText();
    String image = dropTargetArea.getText();

    if (productName.isEmpty() || quantity.isEmpty() || price.isEmpty() || image.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Fields cannot be empty");
        return false;
    }

    if (!productName.matches("^[a-zA-Z0-9 ]{3,25}$")) {
        JOptionPane.showMessageDialog(null, "Product name must be 3-25 characters long and contain no special characters");
        return false;
    }

    if (!quantity.matches("\\d+") || !price.matches("\\d+(\\.\\d+)?")) {
        JOptionPane.showMessageDialog(null, "Quantity and price must be valid numbers");
        return false;
    }

    return true;
}

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == save){
            if (isValidInput()) {
                String pname = productnametextfield.getText();
                String cname = (String) categoryComboBox.getSelectedItem();
                String pqty = quantitytextfield.getText();
                String pprice = pricetextfield.getText();
                String pimage = dropTargetArea.getText();

                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true", "sa", "data123");
                    PreparedStatement ps = con.prepareStatement("INSERT INTO product (pname, cname, pqty, pprice, pimage) VALUES (?, ?, ?, ?, ?)");
                    ps.setString(1, pname);
                    ps.setString(2, cname);
                    ps.setInt(3, Integer.parseInt(pqty));
                    ps.setFloat(4, Float.parseFloat(pprice));
                    ps.setString(5, pimage);
                    ps.executeUpdate();

                    // Create a new notification
                    String notificationContent = "A new product has been added: " + pname;
                    PreparedStatement psNotification = con.prepareStatement("INSERT INTO notifications (content, seen) VALUES (?, ?)");
                    psNotification.setString(1, notificationContent);
                    psNotification.setBoolean(2, false); // The notification is not seen yet
                    psNotification.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Product added successfully");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                loadProducts(table);
            }
        }
        if(ae.getSource() == update){

            if (isValidInput()) {
                // Get the selected row
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    // Get the product ID from the selected row
                    int pid = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                    // Get the updated values from the text fields
                    String pname = productnametextfield.getText();
                    String cname = (String) categoryComboBox.getSelectedItem();
                    int pqty = Integer.parseInt(quantitytextfield.getText());
                    float pprice = Float.parseFloat(pricetextfield.getText());
                    String pimage = dropTargetArea.getText();

                    try {
                        // Establish a connection
                        Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true", "sa", "data123");

                        // Create a SQL query
                        String sql = "UPDATE product SET pname = ?, cname = ?, pqty = ?, pprice = ?, pimage = ? WHERE pid = ?";

                        // Create a statement
                        PreparedStatement ps = con.prepareStatement(sql);

                        // Set parameters
                        ps.setString(1, pname);
                        ps.setString(2, cname);
                        ps.setInt(3, pqty);
                        ps.setFloat(4, pprice);
                        ps.setString(5, pimage);
                        ps.setInt(6, pid);

                        // Execute the query
                        int rowsUpdated = ps.executeUpdate();
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Product updated successfully!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    // Refresh the table
                    loadProducts(table);
            }

            } else {
                JOptionPane.showMessageDialog(null, "Please select a product to update.");
            }
        }
        if(ae.getSource() == delete){

            if (isValidInput()) {
                // Get the selected row
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    // Get the product ID from the selected row
                    int pid = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                    try {
                        // Establish a connection
                        Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true", "sa", "data123");

                        // Create a SQL query
                        String sql = "DELETE FROM product WHERE pid = ?";

                        // Create a statement
                        PreparedStatement ps = con.prepareStatement(sql);

                        // Set parameters
                        ps.setInt(1, pid);

                        // Execute the query
                        int rowsDeleted = ps.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(null, "Product deleted successfully!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    // Refresh the table
                    loadProducts(table);
            }

            } else {
                JOptionPane.showMessageDialog(null, "Please select a product to delete.");
            }
        }
        if(ae.getSource() == clear){
            productnametextfield.setText("");
            quantitytextfield.setText("");
            pricetextfield.setText("");
            dropTargetArea.setText("");

        }
        if(ae.getSource() == back){
            this.setVisible(false);
            new AdminDashboard().setVisible(true);
        }

    }

    public static void main(String[] args) {
        new Product().setVisible(true);

    }
}
