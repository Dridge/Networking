package richard.eldridge.networking;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.BoxLayout.Y_AXIS;

public class LogInDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private boolean canceled = false;
    private JTextField ipAddressField = new JTextField(null, 2);
    private JTextField userNameField = new JTextField(null, 2);

    public LogInDialog(String appName) {
        setTitle(appName);
        initGUI();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initGUI() {
        //main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);
        JLabel ipAddressLabel = new JLabel("IP Address:");
        mainPanel.add(ipAddressLabel);
        mainPanel.add(ipAddressField);
        JLabel userNameLabel = new JLabel("User Name:");
        mainPanel.add(userNameLabel);
        mainPanel.add(userNameField);

        //button panel
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.PAGE_END);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });
        buttonPanel.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        buttonPanel.add(cancelButton);
        getRootPane().setDefaultButton(okButton);
        //listeners
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });

    }

    private void cancel() {
        canceled = true;
        setVisible(false);
    }

    private void ok() {
        canceled = false;
        if(getIpAddress().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You must enter an IP address.");
        } else if(getUserName().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You must enter a user name.");
        } else {
            setVisible(false);
        }
    }

    public void login() {
        setVisible(true);
    }

    public boolean isCanceled() {
        return canceled;
    }

    public String getIpAddress() {
        return ipAddressField.getText().trim();
    }

    public String getUserName() {
        return userNameField.getText().trim();
    }
}
