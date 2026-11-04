
package customerorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerOrder extends JFrame {

    JTextField name = new JTextField(15);
    JTextField item = new JTextField(15);
    JTextField quantity = new JTextField(15);
    JTextField price = new JTextField(15);
    JTextField search = new JTextField(10);

    DefaultTableModel model;
    JTable table;

   CustomerOrderBackend backend = new CustomerOrderBackend();

    public CustomerOrder() {

        setTitle("Cashier Order System");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // TITLE
        JLabel title = new JLabel("CUSTOMER ORDER SYSTEM");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        // FORM
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Customer Name:"));
        form.add(name);

        form.add(new JLabel("Food Order:"));
        form.add(item);

        form.add(new JLabel("Quantity:"));
        form.add(quantity);

        form.add(new JLabel("Price:"));
        form.add(price);

        JButton addButton = new JButton("Add Customer Order");
        JButton displayButton = new JButton("Display Pending Orders");

        form.add(addButton);
        form.add(displayButton);

        // BOTTOM PANEL
        JPanel bottom = new JPanel();

        JButton serveButton = new JButton("Serve Next Order");
        JButton searchButton = new JButton("Search");

        bottom.add(serveButton);
        bottom.add(new JLabel("Search Order No."));
        bottom.add(search);
        bottom.add(searchButton);

        // TABLE
        model = new DefaultTableModel(
            new String[]{
                "Order No.",
                "Customer",
                "Food",
                "Quantity",
                "Total"
            }, 0
        );

        table = new JTable(model);
        table.setRowHeight(25);

        // ADD COMPONENTS
        add(form, BorderLayout.WEST);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // BUTTON ACTIONS

        addButton.addActionListener(e -> addOrder());

        displayButton.addActionListener(e -> displayOrders());

        serveButton.addActionListener(e -> serveOrder());

        searchButton.addActionListener(e -> searchOrder());

        setVisible(true);
    }

    // ADD ORDER
    void addOrder() {

        try {

            String customer = name.getText().trim();
            String food = item.getText().trim();

            if (customer.isEmpty() || food.isEmpty()
                    || quantity.getText().trim().isEmpty()
                    || price.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                    "Please fill in all fields!");

                return;
            }

            int qty = Integer.parseInt(quantity.getText().trim());
            double cost = Double.parseDouble(price.getText().trim());

            if (qty <= 0 || cost < 0) {
                JOptionPane.showMessageDialog(this,
                    "Quantity must be greater than zero and price cannot be negative!");
                return;
            }

          String message = backend.addOrder(
                customer, food, qty, cost
            );

            JOptionPane.showMessageDialog(this, message);

            if (message.startsWith("Order successfully added")) {
                clearFields();
                displayOrders();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                "Please enter valid quantity and price!");
        }
    }

    // DISPLAY ORDERS
    void displayOrders() {

        model.setRowCount(0);

        for (String[] order : backend.displayOrders()) {
            model.addRow(order);
        }
    }

    // SERVE ORDER
    void serveOrder() {

        String message = backend.serveOrder();

        JOptionPane.showMessageDialog(this, message);

        displayOrders();
    }

    // SEARCH ORDER
    void searchOrder() {

        String number = search.getText().trim();

        if (number.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter an Order Number!");
            return;
        }

        String message = backend.searchOrder(number);

        JOptionPane.showMessageDialog(this, message);
    }

    // CLEAR FIELDS
    void clearFields() {

         name.setText("");
         item.setText("");
         quantity.setText("");
         price.setText("");
    }

    // MAIN METHOD
    public static void main(String[] args) {

      SwingUtilities.invokeLater(() -> new CustomerOrder());
    }
}

