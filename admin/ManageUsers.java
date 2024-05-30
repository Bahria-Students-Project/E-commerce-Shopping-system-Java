package admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import connection.MyConnection;

public class ManageUsers extends JFrame implements ActionListener {

    JTextField idtextfield,usernametextfield,emialtextfield, phonenotextfield, answertextfield, addresstextfield, citytextfield,searchtextfield;
    JPasswordField passwordtextfield;
    JComboBox genderComboBox;
    JButton showPasswordButton,update,delete,back;
    JTable table1;


    ManageUsers(){

        setLayout(null);
        setTitle("Manage Users");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/lightgrey.png"));
        Image i2 = i1.getImage().getScaledInstance(850, 650, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,850,650);
        add(image);


        JLabel id = new JLabel("ID");
        id.setFont(new Font("Montserrat",Font.BOLD,16));
        id.setBounds(50,20,100,30);
        image.add(id);
        idtextfield = new JTextField();
        idtextfield.setEditable(false);
        idtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        idtextfield.setBounds(50,45,350,20);
        image.add(idtextfield);


        JLabel username = new JLabel("Username");
        username.setFont(new Font("Montserrat",Font.BOLD,16));
        username.setBounds(50,70,200,30);
        image.add(username);
        usernametextfield = new JTextField();
        usernametextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        usernametextfield.setBounds(50,95,350,20);
        image.add(usernametextfield);


        JLabel password = new JLabel("Password");
        password.setFont(new Font("Montserrat",Font.BOLD,16));
        password.setBounds(50,120,200,30);
        image.add(password);
        passwordtextfield = new JPasswordField();
        passwordtextfield.setEditable(false);
        passwordtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        passwordtextfield.setBounds(50,145,350,20);
        image.add(passwordtextfield);

        showPasswordButton = new JButton();
        showPasswordButton.setBounds(400, 145, 30, 20); // Adjust the position as needed
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/hide.png")); // Replace with your image path
        showPasswordButton.setIcon(icon);
        showPasswordButton.setBackground(Color.black);
        image.add(showPasswordButton);

        showPasswordButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        if (passwordtextfield.getEchoChar() == (char) 0) {
            passwordtextfield.setEchoChar('*');
            // Optionally, change the button icon to a "hide password" icon
            // showPasswordButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/hide.png")));
        } else {
            passwordtextfield.setEchoChar((char) 0);
            // Optionally, change the button icon back to a "show password" icon
            // showPasswordButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/show.png")));
        }
    }
});


        JLabel email = new JLabel("Email");
        email.setFont(new Font("Montserrat",Font.BOLD,16));
        email.setBounds(50,170,200,30);
        image.add(email);
        emialtextfield = new JTextField();
        emialtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        emialtextfield.setBounds(50,195,350,20);
        image.add(emialtextfield);


        JLabel gender = new JLabel("Gender");
        gender.setFont(new Font("Montserrat",Font.BOLD,16));
        gender.setBounds(50,220,200,30);
        image.add(gender);


        String[] genders = {"Male", "Female"};
        genderComboBox = new JComboBox(genders);
        genderComboBox.setFont(new Font("Montserrat", Font.BOLD, 14));
        genderComboBox.setBounds(50,245,350,20);
        genderComboBox.setBackground(Color.white);
        image.add(genderComboBox);


        JLabel phone = new JLabel("Phone No");
        phone.setFont(new Font("Montserrat",Font.BOLD,16));
        phone.setBounds(450,20,200,30);
        image.add(phone);
        phonenotextfield = new JTextField();
        phonenotextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        phonenotextfield.setBounds(450,45,350,20);
        image.add(phonenotextfield);


        JLabel question = new JLabel("Security Question");
        question.setFont(new Font("Montserrat",Font.BOLD,16));
        question.setBounds(450,70,200,30);
        image.add(question);
        answertextfield = new JTextField();
        answertextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        answertextfield.setBounds(450,95,350,20);
        image.add(answertextfield);


        JLabel city = new JLabel("City");
        city.setFont(new Font("Montserrat",Font.BOLD,16));
        city.setBounds(450,120,200,30);
        image.add(city);
        citytextfield = new JTextField();
        citytextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        citytextfield.setBounds(450,145,350,20);
        image.add(citytextfield);


        JLabel address = new JLabel("Address");
        address.setFont(new Font("Montserrat",Font.BOLD,16));
        address.setBounds(450,170,200,30);
        image.add(address);
        addresstextfield = new JTextField();
        addresstextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        addresstextfield.setBounds(450,195,350,20);
        image.add(addresstextfield);

        Color color = Color.decode("#495057");

        update = new JButton("Update");
        update.setBackground(color);
        update.setForeground(Color.white);
        update.setFont(new Font("Montserrat",Font.BOLD,16));
        update.setBounds(450,230,120,30);
        update.addActionListener(this);
        image.add(update);
        update.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                update.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        delete = new JButton("Delete");
        delete.setBackground(color);
        delete.setForeground(Color.white);
        delete.setFont(new Font("Montserrat",Font.BOLD,16));
        delete.setBounds(680,230,120,30);
        delete.addActionListener(this);
        image.add(delete);
        delete.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                delete.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });


        back = new JButton("Back");
        back.setBackground(color);
        back.setForeground(Color.white);
        back.setFont(new Font("Montserrat",Font.BOLD,16));
        back.setBounds(450,270,352,30);
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

        JSeparator separator = new JSeparator();
        separator.setBounds(50, 320, 750, 1); // Adjust the position and size as needed
        separator.setBackground(Color.WHITE);
        separator.setForeground(Color.WHITE);
        image.add(separator);

        JLabel search = new JLabel("Search User");
        search.setFont(new Font("Montserrat",Font.BOLD,14));
        search.setBounds(50,330,100,30);
        image.add(search);
        searchtextfield = new JTextField();
        searchtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        searchtextfield.setBounds(150,335,250,20);
        image.add(searchtextfield);

        // Data for the first table
        Object[][] data1 = {
                // You can fill this with actual data
        };

        // Column names for the first table
        String[] columnNames1 = {"User ID", "Username", "Password", "Email", "Gender", "Phone no", "Security Question", "City", "Address"};


// Create a DefaultTableModel
        final DefaultTableModel model = new DefaultTableModel(data1, columnNames1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are non-editable
                return false;
            }
        };

        // Create the first table
        table1 = new JTable(model);
        JScrollPane scrollPane1 = new JScrollPane(table1);
        scrollPane1.setBounds(50, 370, 750, 260);
        table1.setFont(new Font("Montserrat", Font.PLAIN, 12));
        table1.setBackground(Color.white);
        image.add(scrollPane1);


        searchtextfield.addKeyListener(new KeyAdapter(){
            public void keyReleased(KeyEvent e) {
                try {
                    // Establish a connection
                    String url = "jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true";
                    Connection conn = DriverManager.getConnection(url);

                    // Get the search text
                    String searchText = searchtextfield.getText();

                    // Create a SQL query
                    String sql = "SELECT * FROM [user] WHERE uname LIKE ? OR CAST(uid AS VARCHAR(10)) LIKE ?";

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
                        Object[] row = new Object[9];
                        row[0] = rs.getInt("uid");
                        row[1] = rs.getString("uname");
                        row[2] = rs.getString("upassword");
                        row[3] = rs.getString("uemail");
                        row[4] = rs.getString("ugender");
                        row[5] = rs.getString("uphone");
                        row[6] = rs.getString("uans");
                        row[7] = rs.getString("ucity");
                        row[8] = rs.getString("uaddress");
                        model.addRow(row);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // Check if a row has been selected
                if (table1.getSelectedRow() > -1) {
                    // Get the selected row
                    int selectedRow = table1.getSelectedRow();

                    // Set the text fields with the data from the selected row
                    idtextfield.setText(table1.getValueAt(selectedRow, 0).toString());
                    usernametextfield.setText(table1.getValueAt(selectedRow, 1).toString());
                    passwordtextfield.setText(table1.getValueAt(selectedRow, 2).toString());
                    emialtextfield.setText(table1.getValueAt(selectedRow, 3).toString());
                    genderComboBox.setSelectedItem(table1.getValueAt(selectedRow, 4).toString());
                    phonenotextfield.setText(table1.getValueAt(selectedRow, 5).toString());
                    answertextfield.setText(table1.getValueAt(selectedRow, 6).toString());
                    citytextfield.setText(table1.getValueAt(selectedRow, 7).toString());
                    addresstextfield.setText(table1.getValueAt(selectedRow, 8).toString());
                }
            }
        });
        getContentPane().setBackground(Color.white);

        populateTable();

        setResizable(false);
        setSize(850,650);
        setLocation(200,20);
        setVisible(true);
    }





    public void populateTable() {
        try {
            // Establish a connection
            String url = "jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true";
            Connection conn = DriverManager.getConnection(url);

            // Create a SQL query
            String sql = "SELECT * FROM [user]";

            // Create a statement
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(sql);

            // Get the table model
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            // Remove all existing rows
            model.setRowCount(0);

            // Column names
            String[] columnNames = {"User ID", "Username", "Password", "Email", "Gender", "Phone no", "Security Question", "City", "Address"};

            // Set column identifiers
            model.setColumnIdentifiers(columnNames);

            // Loop through the result set and add rows to the table model
            while (rs.next()) {
                Object[] row = new Object[9];
                row[0] = rs.getInt("uid");
                row[1] = rs.getString("uname");
                row[2] = rs.getString("upassword");
                row[3] = rs.getString("uemail");
                row[4] = rs.getString("ugender");
                row[5] = rs.getString("uphone");
                row[6] = rs.getString("uans");
                row[7] = rs.getString("ucity");
                row[8] = rs.getString("uaddress");
                model.addRow(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateUserData(int userId) {
        try {
            Connection conn = MyConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE [user] SET uname = ?, upassword = ?, uemail = ?, uphone = ?, ugender = ?, uans = ?, ucity = ?, uaddress = ? WHERE uid = ?");
            ps.setString(1, usernametextfield.getText());
            ps.setString(2, new String(passwordtextfield.getPassword()));
            ps.setString(3, emialtextfield.getText());
            ps.setString(4, phonenotextfield.getText());
            ps.setString(5, (String) genderComboBox.getSelectedItem());
            ps.setString(6, answertextfield.getText());
            ps.setString(7, citytextfield.getText());
            ps.setString(8, addresstextfield.getText());
            ps.setInt(9, userId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserData(int userId) {
        try {
            Connection conn = MyConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM [user] WHERE uid = ?");
            ps.setInt(1, userId);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public boolean isValidInput(String input, String type) {
    // Check if the input length is less than 3 or greater than 25
    if(input.length() < 3 || input.length() > 25) {
        return false;
    }

    switch (type) {
        case "text":
            // Check if the input is not empty and does not contain any numbers or special characters
            return !input.isEmpty() && input.matches("^[a-zA-Z ]*$");
        case "email":
            // Check if the input is not empty and is a valid email address
            return !input.isEmpty() && input.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        case "phone":
            // Check if the input is not empty and is exactly 11 digits
            return !input.isEmpty() && input.matches("^\\d{11}$");
        default:
            return false;
    }
}


   public void actionPerformed(ActionEvent ae){
    if(ae.getSource() == back){
        this.setVisible(false);
        new AdminDashboard().setVisible(true);
    } else if (ae.getSource() == update){
        String username = usernametextfield.getText();
        String email = emialtextfield.getText();
        String phone = phonenotextfield.getText();
        String answer = answertextfield.getText();
        String city = citytextfield.getText();
        String address = addresstextfield.getText();
        if (isValidInput(username, "text") && isValidInput(email, "email") && isValidInput(phone, "phone") && isValidInput(answer, "text") && isValidInput(city, "text") && isValidInput(address, "text")) {
            int userId = Integer.parseInt(idtextfield.getText());
            updateUserData(userId);
            populateTable();
        }
        else {
            JOptionPane.showMessageDialog(null, "Ensure 3-25 char, no numbers/specials for username, password, color, city, address. Valid email, 11-digit phone.");
        }
        } else if (ae.getSource() == delete){
            int userId = Integer.parseInt(idtextfield.getText());
            deleteUserData(userId);
            populateTable();
    }
}

    public static void main(String[] args) {
        new ManageUsers().setVisible(true);

    }
}