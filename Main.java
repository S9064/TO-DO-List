import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private JFrame frame;
    private JTextField taskInputField;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;

    public Main() {
        // Set up the main frame of the application
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout(10, 10)); // Add a small gap

        // Create the task input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        taskInputField = new JTextField();
        JButton addButton = new JButton("Add Task");

        // Add color to the "Add Task" button
        addButton.setBackground(new Color(51, 153, 255)); // A pleasant blue color
        addButton.setForeground(Color.WHITE); // White text for contrast
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);

        inputPanel.add(taskInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Create the list to display tasks
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Add a custom renderer to show completed tasks with a strikethrough
        taskList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String task = (String) value;
                if (task.startsWith("✓ ")) { // Check if the task is marked as complete
                    setFont(getFont().deriveFont(Font.ITALIC));
                    setForeground(Color.GRAY);
                    // Use strikethrough HTML to visually indicate completion
                    setText("<html><strike>" + task.substring(2) + "</strike></html>");
                } else {
                    setFont(getFont().deriveFont(Font.PLAIN));
                    setForeground(list.getForeground());
                    setText(task);
                }
                return c;
            }
        });

        // Create the action buttons panel
        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton("Remove Task");
        JButton completeButton = new JButton("Mark Complete");

        // Add color to the "Remove Task" button
        removeButton.setBackground(new Color(255, 102, 102)); // A soft red color
        removeButton.setForeground(Color.WHITE);
        removeButton.setOpaque(true);
        removeButton.setBorderPainted(false);

        // Add color to the "Mark Complete" button
        completeButton.setBackground(new Color(102, 204, 0)); // A soft green color
        completeButton.setForeground(Color.WHITE);
        completeButton.setOpaque(true);
        completeButton.setBorderPainted(false);

        buttonPanel.add(completeButton);
        buttonPanel.add(removeButton);

        // Add components to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskInputField.getText().trim();
                if (!task.isEmpty()) {
                    listModel.addElement(task);
                    taskInputField.setText("");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String task = listModel.getElementAt(selectedIndex);
                    if (!task.startsWith("✓ ")) { // Check if the task is not already complete
                        listModel.setElementAt("✓ " + task, selectedIndex);
                    }
                }
            }
        });

        // Make the "add" button responsive to the Enter key
        frame.getRootPane().setDefaultButton(addButton);

        // Display the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}