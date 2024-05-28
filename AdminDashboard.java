package admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;

public class AdminDashboard extends JFrame {

    JTextField t1, t2, t3, t4;
    JLabel managecategories, manageproducts, manageusers, transaction, logout;
    DefaultCategoryDataset dataset;
    ChartPanel chartPanel;

    public AdminDashboard() {
        setLayout(null);
        setTitle("Admin Dashboard");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/admindashboard.png"));
        Image i2 = i1.getImage().getScaledInstance(1000, 650, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1000, 650);
        add(image);

        ImageIcon cartIcon = new ImageIcon(ClassLoader.getSystemResource("icons/cart.png"));
        Image cartImg = cartIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        cartIcon = new ImageIcon(cartImg);
        JLabel cartLabel = new JLabel(cartIcon);
        cartLabel.setBounds(300, 35, 60, 60);
        image.setLayout(null);
        image.add(cartLabel);

        JLabel system = new JLabel("Digital Trolley");
        system.setFont(new Font("Montserrat", Font.BOLD, 44));
        system.setForeground(Color.WHITE);
        system.setBounds(380, 40, 400, 50);
        image.add(system);

        ImageIcon dashboardIcon = new ImageIcon(ClassLoader.getSystemResource("icons/dashboard.png"));
        Image dashboardImg = dashboardIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        dashboardIcon = new ImageIcon(dashboardImg);
        JLabel dashboardLabel = new JLabel(dashboardIcon);
        dashboardLabel.setBounds(20, 155, 40, 40);
        image.setLayout(null);
        image.add(dashboardLabel);

        JLabel dashboard = new JLabel("DASHBOARD");
        dashboard.setFont(new Font("Montserrat", Font.BOLD, 26));
        dashboard.setForeground(Color.BLACK);
        dashboard.setBounds(70, 160, 200, 30);
        image.add(dashboard);

        ImageIcon shoppingIcon = new ImageIcon(ClassLoader.getSystemResource("icons/categories.png"));
        Image shoppingImg = shoppingIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        shoppingIcon = new ImageIcon(shoppingImg);
        JLabel shoppingLabel = new JLabel(shoppingIcon);
        shoppingLabel.setBounds(20, 250, 30, 30);
        image.setLayout(null);
        image.add(shoppingLabel);

        managecategories = new JLabel("Manage Categories");
        managecategories.setBounds(70, 240, 235, 50);
        managecategories.setFont(new Font("Montserrat", Font.BOLD, 18));
        managecategories.setForeground(Color.white);
        managecategories.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(managecategories);
        managecategories.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                managecategories.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                managecategories.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                managecategories.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                managecategories.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new Category().setVisible(true);
            }
        });

        ImageIcon notesIcon = new ImageIcon(ClassLoader.getSystemResource("icons/products.png"));
        Image notesImg = notesIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        notesIcon = new ImageIcon(notesImg);
        JLabel notesLabel = new JLabel(notesIcon);
        notesLabel.setBounds(20, 310, 30, 30);
        image.setLayout(null);
        image.add(notesLabel);

        manageproducts = new JLabel("Manage Products");
        manageproducts.setBounds(70, 300, 235, 50);
        manageproducts.setFont(new Font("Montserrat", Font.BOLD, 18));
        manageproducts.setForeground(Color.white);
        manageproducts.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(manageproducts);
        manageproducts.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                manageproducts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                manageproducts.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                manageproducts.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                manageproducts.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new Product().setVisible(true);
            }
        });

        ImageIcon accountIcon = new ImageIcon(ClassLoader.getSystemResource("icons/users.png"));
        Image accountImg = accountIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        accountIcon = new ImageIcon(accountImg);
        JLabel accountLabel = new JLabel(accountIcon);
        accountLabel.setBounds(20, 370, 30, 18);
        image.setLayout(null);
        image.add(accountLabel);

        manageusers = new JLabel("Manage Users");
        manageusers.setBounds(70, 360, 235, 50);
        manageusers.setFont(new Font("Montserrat", Font.BOLD, 18));
        manageusers.setForeground(Color.white);
        manageusers.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(manageusers);
        manageusers.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                manageusers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                manageusers.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                manageusers.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                manageusers.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new ManageUsers().setVisible(true);
            }
        });

        ImageIcon transactionsIcon = new ImageIcon(ClassLoader.getSystemResource("icons/transactions.png"));
        Image transactionsImg = transactionsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        transactionsIcon = new ImageIcon(transactionsImg);
        JLabel transactionsLabel = new JLabel(transactionsIcon);
        transactionsLabel.setBounds(20, 430, 30, 30);
        image.setLayout(null);
        image.add(transactionsLabel);

        transaction = new JLabel("Transaction");
        transaction.setBounds(70, 420, 235, 50);
        transaction.setFont(new Font("Montserrat", Font.BOLD, 18));
        transaction.setForeground(Color.white);
        transaction.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(transaction);
        transaction.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                transaction.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                transaction.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                transaction.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                transaction.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new Transaction().setVisible(true);
            }
        });

        ImageIcon powerIcon = new ImageIcon(ClassLoader.getSystemResource("icons/power.png"));
        Image powerImg = powerIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        powerIcon = new ImageIcon(powerImg);
        JLabel powerLabel = new JLabel(powerIcon);
        powerLabel.setBounds(840, 100, 30, 30);
        image.setLayout(null);
        image.add(powerLabel);

        logout = new JLabel("Logout");
        logout.setBounds(880, 90, 235, 50);
        logout.setFont(new Font("Montserrat", Font.BOLD, 20));
        logout.setForeground(Color.white);
        logout.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(logout);
        logout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                logout.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent e) {
                logout.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                logout.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new user.Login().setVisible(true);
            }
        });

        JLabel heading = new JLabel("Statistics");
        heading.setFont(new Font("Montserrat", Font.BOLD, 26));
        heading.setForeground(Color.BLACK);
        heading.setBounds(550, 160, 200, 30);
        image.add(heading);

        JLabel l1 = new JLabel("Total Categories: ");
        l1.setFont(new Font("Montserrat", Font.BOLD, 20));
        l1.setForeground(Color.BLACK);
        l1.setBounds(340, 200, 200, 30);
        image.add(l1);
        t1 = new JTextField("0");
        t1.setEditable(false);
        t1.setFont(new Font("Montserrat", Font.BOLD, 20));
        t1.setBounds(510, 200, 50, 30);
        image.add(t1);

        JLabel l2 = new JLabel("Total Products: ");
        l2.setFont(new Font("Montserrat", Font.BOLD, 20));
        l2.setForeground(Color.BLACK);
        l2.setBounds(340, 250, 200, 30);
        image.add(l2);
        t2 = new JTextField("0");
        t2.setEditable(false);
        t2.setFont(new Font("Montserrat", Font.BOLD, 20));
        t2.setBounds(510, 250, 50, 30);
        image.add(t2);

        JLabel l3 = new JLabel("Total Users: ");
        l3.setFont(new Font("Montserrat", Font.BOLD, 20));
        l3.setForeground(Color.BLACK);
        l3.setBounds(650, 200, 200, 30);
        image.add(l3);
        t3 = new JTextField("0");
        t3.setEditable(false);
        t3.setFont(new Font("Montserrat", Font.BOLD, 20));
        t3.setBounds(800, 200, 100, 30);
        image.add(t3);

        JLabel l4 = new JLabel("Total Sales: ");
        l4.setFont(new Font("Montserrat", Font.BOLD, 20));
        l4.setForeground(Color.BLACK);
        l4.setBounds(650, 250, 200, 30);
        image.add(l4);
        t4 = new JTextField("0.0");
        t4.setEditable(false);
        t4.setFont(new Font("Montserrat", Font.BOLD, 20));
        t4.setBounds(800, 250, 100, 30);
        image.add(t4);

        dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart3D(
                "",
                "Category",
                "Score",
                dataset);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);


        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, Color.black); // Change the color of the first series to green
        renderer.setSeriesPaint(1, Color.GRAY);
        renderer.setSeriesPaint(2, Color.DARK_GRAY);
        renderer.setSeriesPaint(3, Color.LIGHT_GRAY);



        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(350, 330, 550, 270);
        image.add(chartPanel);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDataset();
            }
        });
        timer.start();

        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(1000, 650);
        setLocation(100, 10);
        setVisible(true);
    }

    private void updateDataset() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

                    String sql = "SELECT COUNT(*) FROM category";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        t1.setText(Integer.toString(rs.getInt(1)));
                    }

                    sql = "SELECT COUNT(*) FROM product";
                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        t2.setText(Integer.toString(rs.getInt(1)));
                    }

                    sql = "SELECT COUNT(*) FROM [user]";
                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        t3.setText(Integer.toString(rs.getInt(1)));
                    }

                    sql = "SELECT SUM(total) FROM purchase";
                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        t4.setText(Float.toString(rs.getFloat(1)));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                dataset.clear();

                int count = Integer.parseInt(t1.getText());
                dataset.addValue(count, "Categories", "Categories");

                count = Integer.parseInt(t2.getText());
                dataset.addValue(count, "Products", "Products");

                count = Integer.parseInt(t3.getText());
                dataset.addValue(count, "Users", "Users");

                float totalSales = Float.parseFloat(t4.getText());
                dataset.addValue(totalSales, "Sales", "Sales");

                chartPanel.repaint();
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        new AdminDashboard().setVisible(true);
    }
}