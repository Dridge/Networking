package richard.eldridge.networking;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.BoxLayout.Y_AXIS;

public class LogInDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private boolean canceled = false;
    private JTextField ipAddressField = new JTextField("", 2);
    private JTextField userNameField = new JTextField("", 2);

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
        JLabel ipAddressLabel = new JLabel("IP Address:");
        mainPanel.add(ipAddressLabel);
        mainPanel.add(ipAddressField);
        JLabel userNameLabel = new JLabel("User Name:");
        mainPanel.add(userNameLabel);
        mainPanel.add(userNameField);

        //button panel
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });
        mainPanel.add(buttonPanel);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        mainPanel.add(cancelButton);

        //listeners
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
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
        setVisible(false);
    }
}
