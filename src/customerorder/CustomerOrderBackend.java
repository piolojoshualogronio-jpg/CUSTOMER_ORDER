/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customerorder;



import java.util.LinkedList;
import java.util.Queue;
public class CustomerOrderBackend {
    Queue<String[]> orders = new LinkedList<>();
    int orderNumber = 1001;

    // ADD ORDER
    public String addOrder(String customer, String food,
                           int qty, double cost) {

        if (customer.isEmpty() || food.isEmpty()
                || qty <= 0 || cost < 0) {
            return "Please enter valid order details!";
        }

        double total = qty * cost;

        String[] order = {
            String.valueOf(orderNumber),
            customer,
            food,
            String.valueOf(qty),
            String.valueOf(total)
        };

        orders.add(order);
        orderNumber++;

        return "Order successfully added!\nTotal: P" + total;
    }

    // DISPLAY ORDERS
    public Queue<String[]> displayOrders() {
        return new LinkedList<>(orders);
    }

    // SERVE ORDER
    public String serveOrder() {

        if (orders.isEmpty()) {
            return "No pending orders!";
        }

        String[] served = orders.poll();

        return "Now Serving Order No. " + served[0]
                + "\nCustomer: " + served[1]
                + "\nFood: " + served[2];
    }

    // SEARCH ORDER
    public String searchOrder(String number) {

        if (number.isEmpty()) {
            return "Enter an order number!";
        }

        for (String[] order : orders) {

            if (order[0].equals(number)) {
                return """
                       ORDER FOUND!
                       Order Number: """ + order[0]
                        + "\nCustomer: " + order[1]
                        + "\nFood: " + order[2]
                        + "\nQuantity: " + order[3]
                        + "\nTotal: P" + order[4];
            }
        }

        return "Order not found in pending orders!";
    }
}
    