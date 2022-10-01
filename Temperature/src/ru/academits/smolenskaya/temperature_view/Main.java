package ru.academits.smolenskaya.temperature_view;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame temperatureFrame = new JFrame("Temperature conversion");
            temperatureFrame.setSize(300, 200);
            temperatureFrame.setLocationRelativeTo(null);
            temperatureFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            temperatureFrame.setVisible(true);

            JPanel temperaturePanel = new JPanel(new GridBagLayout());
            temperatureFrame.add(temperaturePanel, BorderLayout.CENTER);

            GridBagConstraints c = new GridBagConstraints();

            String[] items = {
                    "Элемент списка 1",
                    "Элемент списка 2",
                    "Элемент списка 3"
            };

            JComboBox temperatureFromComboBox = new JComboBox(items);
            temperatureFromComboBox.setEditable(true);
            temperatureFromComboBox.setVisible(true);
            temperaturePanel.add(temperatureFromComboBox, c);

            JComboBox temperatureToComboBox = new JComboBox(items);
            temperatureToComboBox.setEditable(true);
            temperatureToComboBox.setVisible(true);
            temperaturePanel.add(temperatureToComboBox, c);
        });


    }
}
