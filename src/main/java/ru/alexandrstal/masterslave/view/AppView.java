package ru.alexandrstal.masterslave.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alexandrstal.masterslave.controller.AppViewController;
import ru.alexandrstal.masterslave.model.MasterSlaveModel;

import javax.swing.*;
import java.awt.*;

@Component
public class AppView extends JFrame {


    @Autowired
    AppViewController viewController;

    @Autowired
    MasterSlaveModel model;

    private JCheckBox checkBox = new JCheckBox("Главный!");
    private JLabel label = new JLabel("");
    private JButton button = new JButton("Отправить");
    private JTextField textField = new JTextField();

    public AppView() {
        super("АРМ");

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 2));

        container.add(checkBox);
        container.add(label);
        container.add(textField);
        container.add(button);

        button.addActionListener(e -> viewController.sendMessage(textField.getText()));

        checkBox.addActionListener(e -> {
            model.setMessage(textField.getText());
            viewController.setMaster();
        });


    }

    public void render() {

        checkBox.setSelected(model.isMaster());
        button.setEnabled(model.isMaster());
        label.setText(model.getMasterId());

        textField.setText(model.getMessage());

        if (model.getId() != null) {
            setTitle("АРМ - " + model.getId());
        }
    }

    public void showMe() {
        setSize(300, 300);
        render();
        setVisible(true);
    }
}
