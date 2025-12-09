package ui;

// ServiceGUI.java


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ServiceGUI extends JFrame {
    private final JTextField txtID = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextArea txtDesc = new JTextArea(3, 20);
    private final JTextField txtPrice = new JTextField();
    private final JTextArea txtOutput = new JTextArea();
    private final ServiceManager manager = new ServiceManager();

    public ServiceGUI() {
        setTitle("Service Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Service ID:"), c);
        c.gridx = 1; c.gridy = 0; form.add(txtID, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Name:"), c);
        c.gridx = 1; c.gridy = 1; form.add(txtName, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Description:"), c);
        c.gridx = 1; c.gridy = 2;
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        form.add(new JScrollPane(txtDesc), c);

        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Price:"), c);
        c.gridx = 1; c.gridy = 3; form.add(txtPrice, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");
        JButton btnReport = new JButton("Report");
        JButton btnClear = new JButton("Clear");
        buttons.add(btnAdd);
        buttons.add(btnDelete);
        buttons.add(btnUpdate);
        buttons.add(btnReport);
        buttons.add(btnClear);

        txtOutput.setEditable(false);
        txtOutput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane outputScroll = new JScrollPane(txtOutput);
        outputScroll.setPreferredSize(new Dimension(500, 200));

        btnAdd.addActionListener((ActionEvent e) -> onAdd());
        btnDelete.addActionListener((ActionEvent e) -> onDelete());
        btnUpdate.addActionListener((ActionEvent e) -> onUpdate());
        btnReport.addActionListener((ActionEvent e) -> onReport());
        btnClear.addActionListener((ActionEvent e) -> clearForm());

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(10, 10));
        cp.add(form, BorderLayout.NORTH);
        cp.add(buttons, BorderLayout.CENTER);
        cp.add(outputScroll, BorderLayout.SOUTH);
    }

    private void onAdd() {
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            String name = txtName.getText().trim();
            String desc = txtDesc.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            if (name.isEmpty()) {
                showError("Name cannot be empty");
                return;
            }
            if (manager.findServiceById(id) != null) {
                showError("Service ID already exists");
                return;
            }
            manager.addService(new Service(id, name, desc, price));
            showServices();
            clearForm();
        } catch (NumberFormatException ex) {
            showError("ID must be integer and Price must be a number");
        }
    }

    private void onDelete() {
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            boolean removed = manager.deleteService(id);
            if (!removed) showError("Service ID not found");
            showServices();
            clearForm();
        } catch (NumberFormatException ex) {
            showError("Enter a valid integer ID");
        }
    }

    private void onUpdate() {
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            String name = txtName.getText().trim();
            String desc = txtDesc.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            boolean updated = manager.updateService(id, name, desc, price);
            if (!updated) showError("Service ID not found");
            showServices();
            clearForm();
        } catch (NumberFormatException ex) {
            showError("ID must be integer and Price must be a number");
        }
    }

    private void onReport() {
        double total = manager.generateServiceUsageReport();
        JOptionPane.showMessageDialog(this, "Total Services Price = " + total, "Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showServices() {
        txtOutput.setText("");
        for (Service s : manager.getAllServices()) {
            txtOutput.append(s.getServiceDetails() + System.lineSeparator());
        }
    }

    private void clearForm() {
        txtID.setText("");
        txtName.setText("");
        txtDesc.setText("");
        txtPrice.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
