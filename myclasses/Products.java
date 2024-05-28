package myclasses;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Products extends JFrame implements ActionListener {
    // TODO Table data edit and table shift

    private final JTable table;
    private final JTextField productPrice_fld;
    private final JTextField productQuantity_fld;
    private final JTextField productName_fld;
    private final JButton logOut_btn;
    private final JButton back_btn;
    private final JButton add_btn;
    private final JButton del_btn;
    private final JButton update_btn;
    private final JButton search_btn;
    private final JTextField search_fld;
    private final JComboBox<String> productStatus_box;
    private final JComboBox<String> productCategory_box;

    public Products() {
        setResizable(false);
        setTitle("Admin manage product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(82, 145, 145));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        logOut_btn = new JButton("Log Out");
        logOut_btn.setBounds(812, 11, 89, 23);
        logOut_btn.setFocusable(false);
        contentPane.add(logOut_btn);

        back_btn = new JButton("Back");
        back_btn.setBounds(702, 11, 89, 23);
        back_btn.setFocusable(false);
        contentPane.add(back_btn);

        JLabel products_lbl = new JLabel("Products");
        products_lbl.setForeground(Color.LIGHT_GRAY);
        products_lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        products_lbl.setBounds(10, 3, 180, 34);
        contentPane.add(products_lbl);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(22, 130, 536, 366);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        DefaultTableModel model =
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Id", "Name", "Category", "Price", "Status", "Quantity"}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Make all cells non-editable
                    }
                };

        table.getTableHeader().setReorderingAllowed(false);
        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);

        model.setRowCount(0);

        table.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {
                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected, hasFocus, row, column);
                        if (row % 2 == 0) {
                            component.setBackground(new Color(230, 230, 230));
                        } else {
                            component.setBackground(Color.WHITE);
                        }
                        if (isSelected) {
                            component.setBackground(new Color(43, 75, 75));
                        }

                        return component;
                    }
                });

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(150, 150, 150));
        header.setForeground(Color.WHITE);
        Font headerFont = header.getFont();
        header.setFont(headerFont.deriveFont(Font.BOLD));

        try (BufferedReader br = new BufferedReader(new FileReader("files/products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("Products Detail")) {
                    String[] rowData = new String[6];
                    rowData[0] = line;
                    for (int i = 1; i < 6; i++) {
                        rowData[i] = br.readLine();
                    }
                    model.addRow(rowData);
                    br.readLine();
                    br.readLine();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        search_fld = new JTextField();
        search_fld.setBounds(300, 60, 151, 34);
        contentPane.add(search_fld);
        search_fld.setColumns(10);

        JLabel productName_lbl = new JLabel("Product Name");
        productName_lbl.setForeground(Color.BLACK);
        productName_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productName_lbl.setBounds(631, 115, 97, 26);
        contentPane.add(productName_lbl);

        productName_fld = new JTextField();
        productName_fld.setColumns(10);
        productName_fld.setBounds(631, 140, 160, 20);
        contentPane.add(productName_fld);

        JLabel productCategory_lbl = new JLabel("Product Category");
        productCategory_lbl.setForeground(Color.BLACK);
        productCategory_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productCategory_lbl.setBounds(631, 170, 72, 23);
        contentPane.add(productCategory_lbl);

        productCategory_box = new JComboBox<>();
        productCategory_box.setModel(new DefaultComboBoxModel<>(new String[]{"Antibiotics", "Vitamins"}));
        productCategory_box.setBounds(631, 195, 160, 22);
        contentPane.add(productCategory_box);

        JLabel productPrice_lbl = new JLabel("Product Price");
        productPrice_lbl.setForeground(Color.BLACK);
        productPrice_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productPrice_lbl.setBounds(631, 225, 97, 26);
        contentPane.add(productPrice_lbl);

        productPrice_fld = new JTextField();
        productPrice_fld.setColumns(10);
        productPrice_fld.setBounds(631, 250, 160, 20);
        contentPane.add(productPrice_fld);

        JLabel productStatus_lbl = new JLabel("Status");
        productStatus_lbl.setForeground(Color.BLACK);
        productStatus_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productStatus_lbl.setBounds(631, 280, 72, 23);
        contentPane.add(productStatus_lbl);

        productStatus_box = new JComboBox<>();
        productStatus_box.setModel(new DefaultComboBoxModel<>(new String[]{"Have", "Not Have"}));
        productStatus_box.setBounds(631, 305, 160, 22);
        contentPane.add(productStatus_box);

        JLabel productQuantity_lbl = new JLabel("Product Quantity");
        productQuantity_lbl.setForeground(Color.BLACK);
        productQuantity_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productQuantity_lbl.setBounds(631, 335, 97, 26);
        contentPane.add(productQuantity_lbl);

        productQuantity_fld = new JTextField();
        productQuantity_fld.setColumns(10);
        productQuantity_fld.setBounds(631, 360, 160, 20);
        contentPane.add(productQuantity_fld);

        add_btn = new JButton("Add");
        add_btn.setBounds(631, 420, 111, 34);
        add_btn.setFocusable(false);
        contentPane.add(add_btn);

        del_btn = new JButton("Delete");
        del_btn.setBounds(140, 60, 100, 34);
        del_btn.setFocusable(false);
        contentPane.add(del_btn);

        update_btn = new JButton("Update");
        update_btn.setBounds(20, 60, 100, 34);
        update_btn.setFocusable(false);
        contentPane.add(update_btn);

        search_btn = new JButton("Search");
        search_btn.setBounds(460, 60, 100, 34);
        search_btn.setFocusable(false);
        contentPane.add(search_btn);

        logOut_btn.addActionListener(this);
        back_btn.addActionListener(this);
        add_btn.addActionListener(this);
        del_btn.addActionListener(this);
        update_btn.addActionListener(this);
        search_btn.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer productId_fld = null;
        try (BufferedReader br = new BufferedReader(new FileReader("files/products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (Objects.equals(line, "Products Detail")) {
                    productId_fld = Integer.valueOf(br.readLine());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String prodId = String.valueOf(productId_fld + 1);
        String prodName = productName_fld.getText();
        String prodCategory = (String) productCategory_box.getSelectedItem();
        String prodPrice = productPrice_fld.getText().trim();
        String prodStatus = (String) productStatus_box.getSelectedItem();
        String prodCount = productQuantity_fld.getText().trim();

        boolean prodPriceEmpty = productPrice_fld.getText().isEmpty();
        boolean prodQuantityEmpty = productQuantity_fld.getText().isEmpty();

        if (e.getSource() == logOut_btn) {
            int yesORno =
                    JOptionPane.showConfirmDialog(
                            null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new Login();
            }
        } else if (e.getSource() == back_btn) {
            setVisible(false);
            new DashBoard();
        } else if (e.getSource() == add_btn)
        {
            // Check if product price and quantity are not empty
            if (!prodPriceEmpty && !prodQuantityEmpty) {
                if (!prodPrice.matches("\\d+")) {
                    JOptionPane.showMessageDialog(
                            null, "Invalid Price", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        String filePath = "files/products.json";
                        File file = new File(filePath);
                        JSONArray productsArray;

                        // Read the existing JSON data
                        if (!file.exists()) {
                            file.createNewFile();
                            productsArray = new JSONArray();
                        } else {
                            String content = new String(Files.readAllBytes(Paths.get(filePath)));
                            productsArray = new JSONArray(content);
                        }

                        // Create a new product JSON object
                        JSONObject newProduct = new JSONObject();
                        newProduct.put("prodId", prodId);
                        newProduct.put("prodName", prodName);
                        newProduct.put("prodCategory", prodCategory);
                        newProduct.put("prodPrice", prodPrice);
                        newProduct.put("prodStatus", prodStatus);
                        newProduct.put("prodCount", prodCount);

                        // Add the new product to the products array
                        productsArray.put(newProduct);

                        // Write the updated JSON data to the file
                        try (FileWriter fileWriter = new FileWriter(file)) {
                            fileWriter.write(productsArray.toString());
                        }

                        // Clear text fields
                        productPrice_fld.setText(null);
                        productName_fld.setText(null);
                        productQuantity_fld.setText(null);

                        System.out.println("New Product added");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                // Display a warning message if any box is not filled
                JOptionPane.showMessageDialog(
                        null, "Please Fill all the box", "Error", JOptionPane.WARNING_MESSAGE);
            }

            // Update the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            try {
                String content = new String(Files.readAllBytes(Paths.get("files/products.json")));
                JSONArray productsArray = new JSONArray(content);

                for (int i = 0; i < productsArray.length(); i++) {
                    JSONObject product = productsArray.getJSONObject(i);
                    String[] rowData = {
                            product.getString("prodId"),
                            product.getString("prodName"),
                            product.getString("prodCategory"),
                            product.getString("prodPrice"),
                            product.getString("prodStatus"),
                            product.getString("prodCount")
                    };
                    model.addRow(rowData);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == del_btn) {
            if (JOptionPane.showConfirmDialog(
                    null, "Confirmation", "Remove This Room?", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                DefaultTableModel tempTbl = (DefaultTableModel) table.getModel();
                int selectedRow = table.getSelectedRow();

                if (table.getSelectedRow() != -1) {
                    String[] data = new String[6];
                    for (int i = 0; i < 6; i++) {
                        data[i] = tempTbl.getValueAt(selectedRow, i).toString();
                    }
                    System.out.println(data);
                    if (data[4].equals("Not Have")) {
                        try {
                            File inputFile = new File("files/products.txt");
                            File tempFile = new File("./files/products_temp.txt");
                            System.out.println("temp file created");

                            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                            System.out.println("temp file updated");

                            String currentLine;
                            int lineCounter = 0;
                            while ((currentLine = reader.readLine()) != null) {
                                lineCounter++;
                                if (currentLine.contains(data[0])) {
                                    break;
                                }
                            }

                            reader.close();
                            reader = new BufferedReader(new FileReader(inputFile));
                            int k = 0;
                            while ((currentLine = reader.readLine()) != null) {
                                k++;
                                if (k > (lineCounter - 2) && k < (lineCounter + 6)) {
                                } else {
                                    // write all other lines to the temp file
                                    writer.write(currentLine + System.getProperty("line.separator"));
                                }
                            }

                            writer.close();
                            reader.close();

                            inputFile.delete();
                            System.out.println("Original file deleted");
                            tempFile.renameTo(inputFile);
                            System.out.println("temp file renamed as original file");

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        tempTbl.removeRow(table.getSelectedRow());
                    } else {
                        JOptionPane.showMessageDialog(this, "Please check out it first");
                    }

                } else {
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(this, "Table is Empty!");

                    } else {
                        JOptionPane.showMessageDialog(this, "Please select A row to delete ");
                    }
                }
            }
        } else if (e.getSource() == update_btn) {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                String id = table.getValueAt(selectedRow, 0).toString();
                String name = table.getValueAt(selectedRow, 1).toString();
                String category = table.getValueAt(selectedRow, 2).toString();
                String price = table.getValueAt(selectedRow, 3).toString();
                String status = table.getValueAt(selectedRow, 4).toString();
                String quantity = table.getValueAt(selectedRow, 5).toString();

                JTextField idField = new JTextField(id);
                JTextField nameField = new JTextField(name);
                JTextField categoryField = new JTextField(category);
                JTextField priceField = new JTextField(price);
                JTextField quantityField = new JTextField(quantity);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("ID:"));
                panel.add(idField);
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Category:"));
                panel.add(categoryField);
                panel.add(new JLabel("Price:"));
                panel.add(priceField);
                panel.add(new JLabel("Quantity:"));
                panel.add(quantityField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Update Product",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    // Update the data in the table
                    table.setValueAt(idField.getText(), selectedRow, 0);
                    table.setValueAt(nameField.getText(), selectedRow, 1);
                    table.setValueAt(categoryField.getText(), selectedRow, 2);
                    table.setValueAt(priceField.getText(), selectedRow, 3);
                    table.setValueAt(quantityField.getText(), selectedRow, 5);

                    try {
                        File inputFile = new File("files/products.txt");
                        File tempFile = new File("./files/products_temp.txt");

                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            String[] id_f = currentLine.split(",");
                            if (id_f.length > 0 && id_f[0].equals(id)) { // Assuming ID is the first field
                                reader.readLine();
                                reader.readLine();
                                reader.readLine();
                                reader.readLine();
                                reader.readLine();
                                reader.readLine();
                                reader.readLine();
                                continue;
                            }
                            writer.write(currentLine + System.getProperty("line.separator"));
                        }

                        writer.write(System.getProperty("line.separator"));
                        writer.write("Products Detail" + System.getProperty("line.separator"));
                        writer.write(idField.getText() + System.getProperty("line.separator"));
                        writer.write(nameField.getText() + System.getProperty("line.separator"));
                        writer.write(categoryField.getText() + System.getProperty("line.separator"));
                        writer.write(priceField.getText() + System.getProperty("line.separator"));
                        writer.write(status + System.getProperty("line.separator"));
                        writer.write(quantityField.getText() + System.getProperty("line.separator"));
                        writer.write(System.getProperty("line.separator"));

                        writer.close();
                        reader.close();

                        inputFile.delete();
                        tempFile.renameTo(inputFile);

                        JOptionPane.showMessageDialog(this, "Product updated successfully!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to update!");
            }
        } else if (e.getSource() == search_btn) {
            searchProducts(search_fld.getText());
        }
    }

    private void searchProducts(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    public static void main(String args[]) {
        Products manageProducts = new Products();
        manageProducts.setVisible(true);
    }
}
