package ru.academits.smolenskaya.temperature_view;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame temperatureFrame = new JFrame("Temperature conversion");
            temperatureFrame.setSize(250, 110);
            temperatureFrame.setResizable(false);
            temperatureFrame.setLocationRelativeTo(null);
            temperatureFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            temperatureFrame.setVisible(true);

            GridBagLayout temperatureGridBagLayout = new GridBagLayout();
            temperatureFrame.setLayout(temperatureGridBagLayout);

            GridBagConstraints temperatureGridBagConstraints = new GridBagConstraints();
            temperatureGridBagConstraints.anchor = GridBagConstraints.CENTER;

            String[] items = {
                    "Celsius",
                    "Kelvin",
                    "Fahrenheit"
            };
            //////////////////////////////////////////
            JComboBox temperatureFromComboBox = new JComboBox(items);
            temperatureFromComboBox.setVisible(true);
            temperatureFrame.add(temperatureFromComboBox);

            temperatureGridBagConstraints.insets = new Insets(1, 1, 0, 1);
            temperatureGridBagConstraints.gridx = 0;
            temperatureGridBagConstraints.gridy = 0;
            temperatureGridBagLayout.setConstraints(temperatureFromComboBox, temperatureGridBagConstraints);
            //////////////////////////////////////////
            NumberFormat temperatureNumberFormat = NumberFormat.getNumberInstance();
            temperatureNumberFormat.setMaximumFractionDigits(4);

            JFormattedTextField temperatureFromFormattedTextField = new JFormattedTextField(new NumberFormatter(temperatureNumberFormat));
            temperatureFromFormattedTextField.setEditable(true);
            temperatureGridBagConstraints.anchor = GridBagConstraints.CENTER;
            temperatureFromFormattedTextField.setPreferredSize(new Dimension(120, temperatureFromComboBox.getPreferredSize().height));
            temperatureFrame.add(temperatureFromFormattedTextField);

            temperatureGridBagConstraints.gridx = 1;
            temperatureGridBagConstraints.gridy = 0;
            temperatureGridBagLayout.setConstraints(temperatureFromFormattedTextField, temperatureGridBagConstraints);
            //////////////////////////////////////////
            JButton conversingButton = new JButton("Convert");
            conversingButton.setPreferredSize(new Dimension(237, 20));
            temperatureFrame.add(conversingButton);

            temperatureGridBagConstraints.gridwidth = 2;
            temperatureGridBagConstraints.gridx = 0;
            temperatureGridBagConstraints.gridy = 1;
            temperatureGridBagLayout.setConstraints(conversingButton, temperatureGridBagConstraints);
            /////////////////////////////////////////
            JComboBox temperatureToComboBox = new JComboBox(items);
            temperatureToComboBox.setVisible(true);
            temperatureFrame.add(temperatureToComboBox);

            temperatureGridBagConstraints.gridx = 0;
            temperatureGridBagConstraints.gridy = 2;
            temperatureGridBagConstraints.insets = new Insets(3, 1, 0, 1);
            temperatureGridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
            temperatureGridBagLayout.setConstraints(temperatureToComboBox, temperatureGridBagConstraints);

            ////////////////
            JFormattedTextField temperatureToFormattedTextField = new JFormattedTextField(new NumberFormatter(temperatureNumberFormat));
            temperatureToFormattedTextField.setEditable(false);
            temperatureGridBagConstraints.anchor = GridBagConstraints.CENTER;
            temperatureToFormattedTextField.setPreferredSize(new Dimension(temperatureFromFormattedTextField.getPreferredSize().width, temperatureFromFormattedTextField.getPreferredSize().height));
            temperatureFrame.add(temperatureToFormattedTextField);

            temperatureGridBagConstraints.gridx = 1;
            temperatureGridBagConstraints.gridy = 2;
            temperatureGridBagLayout.setConstraints(temperatureToFormattedTextField, temperatureGridBagConstraints);
        });
    }
}
