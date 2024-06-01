    package user;

    import javax.swing.*;
    import javax.swing.filechooser.FileNameExtensionFilter;
    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.DefaultTableCellRenderer;
    import java.sql.*;
    import javax.swing.event.ListSelectionListener;
    import javax.swing.event.ListSelectionEvent;
    import java.io.*;
    import com.itextpdf.text.Document;
    import com.itextpdf.text.Paragraph;
    import com.itextpdf.text.pdf.PdfWriter;


    public class Purchase extends JFrame implements ActionListener {

        JTextField searchtextfield, productnametextfield, totaltextfield;
        JButton add,purchase,print,pay, clear,back;
        JComboBox<Integer> quantityComboBox;
        JTable table1,table2;
        public boolean isPaid = false;
        public boolean isPurchased = false;
        static int userId;

        public void setPaid(boolean isPaid) {
            this.isPaid = isPaid;
        }


        public void printBill(JTable table) {
        StringBuilder bill = new StringBuilder();
        DefaultTableModel model2 = (DefaultTableModel) table.getModel();

        // Print a header
        bill.append("\t\tDigital Trolly\n");
        bill.append("\n");

        // Print rows
        float totalBill = 0;
        for (int i = 0; i < model2.getRowCount(); i++) {
            bill.append("Purchase ID: ").append(model2.getValueAt(i, 0)).append("\n");
            bill.append("Product ID: ").append(model2.getValueAt(i, 1)).append("\n");
            bill.append("Product Name: ").append(model2.getValueAt(i, 2)).append("\n");
            bill.append("Quantity: ").append(model2.getValueAt(i, 3)).append("\n");
            bill.append("Price: ").append(model2.getValueAt(i, 4)).append("\n");
            float total = Float.parseFloat(model2.getValueAt(i, 5).toString());
            bill.append("Total: ").append(total).append("\n");
            bill.append("\n");
            totalBill += total;
        }

        // Print total bill
        bill.append("Total Bill: ").append(totalBill).append("\n");

        // Print a footer
        bill.append("\n");
        bill.append("Date and Time: ");
        bill.append(java.time.LocalDateTime.now());
        bill.append("\n");

        // Create a new JFrame
        JFrame billFrame = new JFrame("Bill");
        billFrame.setSize(400, 500);
        billFrame.setLocationRelativeTo(null);

        // Create a JTextArea
        JTextArea billArea = new JTextArea();
        billArea.setFont(new Font("Montserrat", Font.BOLD, 14));
        billArea.setEditable(false);

        // Add the JTextArea to the JFrame
        billFrame.add(new JScrollPane(billArea));

        // Append the bill to the JTextArea
        billArea.append(bill.toString());

        // Make the JFrame visible
        billFrame.setVisible(true);

        // Create a "Save" button

          JButton save = new JButton("Save");
          save.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  JFileChooser fileChooser = new JFileChooser();
                  fileChooser.setDialogTitle("Specify a file to save");

// Set the file filter
                  FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
                  fileChooser.setFileFilter(filter);

                  int userSelection = fileChooser.showSaveDialog(billFrame);

                  if (userSelection == JFileChooser.APPROVE_OPTION) {
                      File fileToSave = fileChooser.getSelectedFile();
                      // Check if the file has a .pdf extension, if not add it
                      if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                          fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                      }
                      try {
                          Document document = new Document();
                          PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                          document.open();
                          for (String line : bill.toString().split("\n")) {
                              document.add(new Paragraph(line));
                          }
                          document.close();
                          JOptionPane.showMessageDialog(null, "Bill saved successfully!");
                      } catch (Exception ex) {
                          ex.printStackTrace();
                      }
                  }
              }
          });

        // Add the "Save" button to the JFrame
        billFrame.add(save, BorderLayout.SOUTH);
    }
        public void loadProducts() {
            try {
                // Establish a connection
                Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

                // Create a SQL query
                String sql = "SELECT * FROM product";

                // Create a statement
                Statement stmt = con.createStatement();

                // Execute the query
                ResultSet rs = stmt.executeQuery(sql);

                // Get the table model
                DefaultTableModel model = (DefaultTableModel) table1.getModel();

                // Clear the table
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
        Purchase(int userId){

            this.userId = userId;

            setLayout(null);
            setTitle("PURCHASE");

            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/lightgrey.png"));
            Image i2 = i1.getImage().getScaledInstance(1010, 670, Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel image = new JLabel(i3);
            image.setBounds(0,0,1010,670);
            add(image);

            JLabel search = new JLabel("Search Product:");
            search.setFont(new Font("Montserrat",Font.BOLD,14));
            search.setBounds(605,30,150,30);
            image.add(search);
            searchtextfield = new JTextField();
            searchtextfield.setFont(new Font("Montserrat", Font.BOLD, 12));
            searchtextfield.setBounds(720,30,250,30);
            image.add(searchtextfield);
            searchtextfield.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    try {
                        // Establish a connection
                        Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

                        // Get the search text
                        String searchText = searchtextfield.getText();

                        // Create a SQL query
                        String sql = "SELECT * FROM product WHERE pname LIKE ? OR CAST(pid AS VARCHAR(10)) LIKE ?";

                        // Create a statement
                        PreparedStatement stmt = con.prepareStatement(sql);

                        // Set parameters
                        stmt.setString(1, "%" + searchText + "%");
                        stmt.setString(2, "%" + searchText + "%");

                        // Execute the query
                        ResultSet rs = stmt.executeQuery();

                        // Get the table model
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();

                        // Clear the table
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

            JLabel productname = new JLabel("Product Name");
            productname.setFont(new Font("Montserrat",Font.BOLD,14));
            productname.setBounds(20,70,200,30);
            image.add(productname);
            productnametextfield = new JTextField();
            productnametextfield.setEditable(false);
            productnametextfield.setFont(new Font("Montserrat", Font.BOLD, 12));
            productnametextfield.setBounds(20,95,250,30);
            image.add(productnametextfield);

            JLabel quantity = new JLabel("Quantity");
            quantity.setFont(new Font("Montserrat",Font.BOLD,14));
            quantity.setBounds(20,130,200,30);
            image.add(quantity);
            quantityComboBox = new JComboBox<Integer>();
            quantityComboBox.setFont(new Font("Montserrat", Font.BOLD, 12));
            quantityComboBox.setBounds(20,155,250,30);
            quantityComboBox.setMaximumRowCount(5); // Set the maximum row count
            image.add(quantityComboBox);

            Color color = Color.decode("#495057");
            

            add = new JButton("Add to Cart");
            add.setBounds(20,340,100,30);
            add.setBackground(color);
            add.setFont(new Font("Montserrat",Font.BOLD,12));
            add.setForeground(Color.white);
            add.addActionListener(this);
            image.add(add);

            pay = new JButton("Payment");
            pay.setBounds(170,340,100,30);
            pay.setBackground(color);
            pay.setFont(new Font("Montserrat",Font.BOLD,14));
            pay.setForeground(Color.white);
            pay.addActionListener(this);
            image.add(pay);


            purchase = new JButton("Purchase");
            purchase.setBounds(20,400,100,30);
            purchase.setBackground(color);
            purchase.setFont(new Font("Montserrat",Font.BOLD,14));
            purchase.setForeground(Color.white);
            purchase.addActionListener(this);
            image.add(purchase);

            print = new JButton("Print");
            print.setBounds(170,400,100,30);
            print.setBackground(color);
            print.setFont(new Font("Montserrat",Font.BOLD,14));
            print.setForeground(Color.white);
            print.addActionListener(this);
            image.add(print);

            clear = new JButton("Clear");
            clear.setBounds(20,460,100,30);
            clear.setBackground(color);
            clear.setFont(new Font("Montserrat",Font.BOLD,14));
            clear.setForeground(Color.white);
            clear.addActionListener(this);
            image.add(clear);

            back = new JButton("Back");
            back.setBounds(170,460,100,30);
            back.setBackground(color);
            back.setFont(new Font("Montserrat",Font.BOLD,14));
            back.setForeground(Color.white);
            back.addActionListener(this);
            image.add(back);

            add.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                public void mouseExited(MouseEvent e) {
                    add.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });

            purchase.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    purchase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                public void mouseExited(MouseEvent e) {
                    purchase.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });

            print.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                public void mouseExited(MouseEvent e) {
                    print.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
            String[] columnNames1 = {"Product ID", "Product Name", "Category", "Quantity", "Price", "Image", "View Image"};

            DefaultTableModel model = new DefaultTableModel(new Object[0][], columnNames1) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Only the "View Image" column is editable
                    return column == 6;
                }
            };
            table1 = new JTable(model);
            JScrollPane scrollPane1 = new JScrollPane(table1);
            scrollPane1.setBounds(300, 90, 670, 240);
            table1.setFont(new Font("Montserrat", Font.PLAIN, 12));
            image.add(scrollPane1);

            table1.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = new JLabel("View Image");
                    label.setForeground(Color.BLACK);
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    return label;
                }
            });

            table1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
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

    // Hide the "Image" column
            table1.getColumnModel().getColumn(5).setMinWidth(0);
            table1.getColumnModel().getColumn(5).setMaxWidth(0);
            table1.getColumnModel().getColumn(5).setPreferredWidth(0);

            loadProducts();


            // Data for the second table
            Object[][] data2 = {
                    // You can fill this with actual data
            };

           // Column names for the second table
    String[] columnNames2 = {"Purchase ID", "Product ID", "Product Name", "Quantity", "Price", "Total","Image", "View Image"};

    // Create the second table
    DefaultTableModel model2 = new DefaultTableModel(data2, columnNames2);
    table2 = new JTable(model2);
    JScrollPane scrollPane2 = new JScrollPane(table2);
    scrollPane2.setBounds(300, 360, 670, 200);
    table2.setFont(new Font("Montserrat", Font.PLAIN, 12));
    image.add(scrollPane2);

    table2.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel("View Image");
            label.setForeground(Color.BLACK);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return label;
        }
    });

    table2.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(new JTextField()) {
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // When the "View Image" label is clicked, open a new JFrame with the image
            String imagePath = table.getValueAt(row, 6).toString();
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

    // Hide the "Image" column in table2
    table2.getColumnModel().getColumn(6).setMinWidth(0);
    table2.getColumnModel().getColumn(6).setMaxWidth(0);
    table2.getColumnModel().getColumn(6).setPreferredWidth(0);

            JLabel total = new JLabel("Total:");
            total.setFont(new Font("Montserrat",Font.BOLD,18));
            total.setBounds(740,590,150,30);
            image.add(total);
            totaltextfield = new JTextField();
            totaltextfield.setEditable(false);
            totaltextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
            totaltextfield.setBounds(810,590,160,30);
            image.add(totaltextfield);

            table1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            // Check if a row has been selected
            if (table1.getSelectedRow() > -1) {
                // Get the selected row
                int selectedRow = table1.getSelectedRow();

                // Set the productnametextfield with the product name from the selected row
                productnametextfield.setText(table1.getValueAt(selectedRow, 1).toString());

                // Get the quantity of the selected product
                int productQuantity = Integer.parseInt(table1.getValueAt(selectedRow, 3).toString());

                // Clear the quantityComboBox
                quantityComboBox.removeAllItems();

                // Populate the quantityComboBox with numbers from 1 to the quantity of the selected product
                for (int i = 1; i <= productQuantity; i++) {
                    quantityComboBox.addItem(i);
                }
            }
        }
    });

            getContentPane().setBackground(Color.WHITE);

            setResizable(false);
            setSize(1010,670);
            setLocation(100,0);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == add) {
                // Check if a row has been selected in table1
                if (table1.getSelectedRow() > -1) {
                    // Get the selected row
                    int selectedRow = table1.getSelectedRow();

                    // Get the product details from the selected row
                    int productId = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
                    String productName = table1.getValueAt(selectedRow, 1).toString();
                    int quantity = (int) quantityComboBox.getSelectedItem();
                    float price = Float.parseFloat(table1.getValueAt(selectedRow, 4).toString());
                    String image = table1.getValueAt(selectedRow, 5).toString();

                    // Calculate the total
                    float total = quantity * price;

                    // Get the table model for table2
                    DefaultTableModel model2 = (DefaultTableModel) table2.getModel();

                    // Add a new row to table2 with the product details
                    model2.addRow(new Object[]{null, productId, productName, quantity, price, total, image});

                    // Calculate the total bill
                    float totalBill = 0;
                    for (int i = 0; i < model2.getRowCount(); i++) {
                        totalBill += Float.parseFloat(model2.getValueAt(i, 5).toString());
                    }

                    // Update the total text field
                    totaltextfield.setText(String.valueOf(totalBill));

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to add.");
                }
            }
            if (ae.getSource() == pay){

                DefaultTableModel model2 = (DefaultTableModel) table2.getModel();

                if (model2.getRowCount() > 0) {
                    Payment payment = new Payment(userId, isPaid,this);
                    payment.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please add items before Paying.");
                }
            }
            if (ae.getSource() == purchase) {
                if (!isPaid) {
                    JOptionPane.showMessageDialog(null, "Please make a payment before purchasing.");
                    return;
                }
                // Get the table model for table2
                DefaultTableModel model2 = (DefaultTableModel) table2.getModel();

                if (model2.getRowCount() > 0) {
                    // Iterate over the rows in table2
                    for (int i = 0; i < model2.getRowCount(); i++) {
                        // Get the product details from the current row
                        int productId = Integer.parseInt(model2.getValueAt(i, 1).toString());
                        String productName = model2.getValueAt(i, 2).toString();
                        int quantity = Integer.parseInt(model2.getValueAt(i, 3).toString());
                        float price = Float.parseFloat(model2.getValueAt(i, 4).toString());
                        float total = Float.parseFloat(model2.getValueAt(i, 5).toString());
                        String image = model2.getValueAt(i, 6).toString();

                        try {
                            // Establish a connection
                            Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true", "sa", "data123");

                            // Create a SQL query to get the username from the user table
                            String sqlUser = "SELECT uname FROM [user] WHERE uid = ?";
                            PreparedStatement psUser = con.prepareStatement(sqlUser);
                            psUser.setInt(1, this.userId);
                            ResultSet rsUser = psUser.executeQuery();
                            rsUser.next();
                            String username = rsUser.getString("uname");

                            // Create a SQL query
                            String sql = "INSERT INTO purchase (uid, uname, pid, product_name, qty, price, total, pimage) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                            // Create a statement
                            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                            // Set parameters
                            ps.setInt(1, userId);
                            ps.setString(2, username); // Replace with actual username
                            ps.setInt(3, productId);
                            ps.setString(4, productName);
                            ps.setInt(5, quantity);
                            ps.setFloat(6, price);
                            ps.setFloat(7, total);
                            ps.setString(8, image);

                            // Execute the query
                            ps.executeUpdate();

                            // Get the generated purchaseId
                            ResultSet rs = ps.getGeneratedKeys();
                            rs.next();
                            int purchaseId = rs.getInt(1);

                            // Update the purchaseId in table2
                            model2.setValueAt(purchaseId, i, 0);

                            // Create a SQL query to decrease the quantity of the product in the product table
                            String sqlUpdate = "UPDATE product SET pqty = pqty - ? WHERE pid = ? AND pqty >= ?";
                            // Create a statement
                            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
                            // Set parameters
                            psUpdate.setInt(1, quantity);
                            psUpdate.setInt(2, productId);
                            psUpdate.setInt(3, quantity);
                            // Execute the query
                            int rowsAffected = psUpdate.executeUpdate();

                            // If no rows were affected, the product quantity was not enough
                            if (rowsAffected == 0) {
                                JOptionPane.showMessageDialog(null, "Not enough quantity for product: " + productName);
                            } else {
                                // Update the quantity in table1
                                loadProducts();

                                // Remove the row from table1 if the quantity of the product becomes 0
                                DefaultTableModel model1 = (DefaultTableModel) table1.getModel();
                                for (int j = 0; j < model1.getRowCount(); j++) {
                                    if (Integer.parseInt(model1.getValueAt(j, 0).toString()) == productId && Integer.parseInt(model1.getValueAt(j, 3).toString()) == 0) {
                                        model1.removeRow(j);
                                        // Delete the product from the database if the quantity is 0
                                        String sqlDelete = "DELETE FROM product WHERE pid = ? AND pqty = 0";
                                        PreparedStatement psDelete = con.prepareStatement(sqlDelete);
                                        psDelete.setInt(1, productId);
                                        psDelete.executeUpdate();
                                        break;
                                    }
                                }
                            }
                        }catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    JOptionPane.showMessageDialog(null,"Purchase successful");
                    isPurchased = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Please add items before purchasing.");
                }

            }

            if (ae.getSource() == print) {
                // Check if payment has been made and purchase has been done
                if (!isPaid || !isPurchased) {
                    JOptionPane.showMessageDialog(null, "Please make a payment and purchase items before printing.");
                } else {
                    printBill(table2);
                }
            }

            if(ae.getSource() == clear) {
                // Get the table model
                DefaultTableModel model = (DefaultTableModel) table2.getModel();

                // Clear the table
                model.setRowCount(0);
            }

            if(ae.getSource() == back) {
                this.setVisible(false);
                new UserDashboard(userId).setVisible(true);
            }
        }
        public static void main(String[] args) {
            new Purchase(userId).setVisible(true);

        }
    }