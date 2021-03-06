package richard.eldridge.networking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javax.swing.BoxLayout.Y_AXIS;

/**
 * Initial credit must go to:
 *  Do-It-Yourself Multiplayer Java Games: An Introduction to Java Sockets and Internet-Based Games (Do-It-Yourself Java Games Book 4)
 *   by Annette Godtland (Author)
 */
public class LogInDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    public static final String FILE_NAME = "Login.log";
    public static final String LOG_IN_DIALOG_CANCEL_BTN = "LogInDialog_cancelBtn_" + UUID.randomUUID();
    public static final String LOG_IN_DIALOG_OK_BTN = "LogInDialog_okBtn_" + UUID.randomUUID();
    private boolean canceled = false;
    private final JTextField ipAddressField = new JTextField(null, 2);
    private final JTextField userNameField = new JTextField(null, 2);

    private Map<String, Component> componentMap;

    public LogInDialog(String appName) {
        setTitle(appName);
        initGUI();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        try (BufferedReader in = new BufferedReader(new FileReader(new File(FILE_NAME)))) {
            String ipAddress = in.readLine();
            ipAddressField.setText(ipAddress);
            String userName = in.readLine();
            userNameField.setText(userName);
        } catch(FileNotFoundException e) {
            //do nothing
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error when reading from: " + FILE_NAME);
        }
    }

    private void initGUI() {
        //main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setName("LogInDialog");
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
        okButton.setName(LOG_IN_DIALOG_OK_BTN);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });
        buttonPanel.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setName(LOG_IN_DIALOG_CANCEL_BTN);
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
        createComponentMap();
    }

    //TODO move to reusable component for part of all jframe object composition

    /**
     * Courtesy of stackoverflow
     * https://stackoverflow.com/questions/4958600/get-a-swing-component-by-name
     */
    private void createComponentMap() {
        componentMap = new HashMap<String,Component>();
        Component[] components = this.getContentPane().getComponents();
        for (Component component : components) {
            componentMap.put(component.getName(), component);
        }
    }

    //TODO also move to reusable component for part of all jframe object composition
    /**
     * Courtesy of stackoverflow
     * https://stackoverflow.com/questions/4958600/get-a-swing-component-by-name
     * @return
     */
    public Component getComponent(String name){
        if (componentMap.containsKey(name)) {
            return (Component) componentMap.get(name);
        }
        else return null;
    }

    private void cancel() {
        canceled = true;
        setVisible(false);
    }

    private void ok() {
        if(getIpAddress().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You must enter an IP address.");
        } else if(getUserName().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You must enter a user name.");
        } else {
            canceled = false;
            setVisible(false);
            try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(FILE_NAME)))){
                out.write(getIpAddress()+"\n");
                out.write(getUserName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error encountered when writing to: " + FILE_NAME);
            }
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
