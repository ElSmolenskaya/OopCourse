package ru.academits.smolenskaya.temperature_view;

import ru.academits.smolenskaya.temperature_model.Temperature;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame temperatureFrame = new JFrame("Temperature conversion");
            temperatureFrame.setSize(270, 130);
            temperatureFrame.setLocationRelativeTo(null);
            temperatureFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            temperatureFrame.setVisible(true);

            GridBagLayout gridBagLayout = new GridBagLayout();
            temperatureFrame.setLayout(gridBagLayout);

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints.fill = GridBagConstraints.BOTH;

            JComboBox temperatureFromComboBox = new JComboBox(Temperature.Scale.values());
            temperatureFromComboBox.setVisible(true);

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;

            temperatureFrame.add(temperatureFromComboBox, gridBagConstraints);

            NumberFormat temperatureNumberFormat = NumberFormat.getNumberInstance();
            temperatureNumberFormat.setMaximumFractionDigits(4);

            JFormattedTextField temperatureFromFormattedTextField = new JFormattedTextField(new NumberFormatter(temperatureNumberFormat));
            temperatureFromFormattedTextField.setPreferredSize(new Dimension(120, temperatureFromComboBox.getPreferredSize().height));
            temperatureFromFormattedTextField.setEditable(true);

            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;

            temperatureFrame.add(temperatureFromFormattedTextField, gridBagConstraints);

            /*JButton conversingButton = new JButton("Convert");
            //conversingButton.setPreferredSize(new Dimension(247, 20));
            temperatureFrame.add(conversingButton);

            temperatureGridBagConstraints.gridwidth = 2;
            temperatureGridBagConstraints.gridx = 0;
            temperatureGridBagConstraints.gridy = 1;
            temperatureGridBagLayout.setConstraints(conversingButton, temperatureGridBagConstraints);
            /////////////////////////////////////////
            JComboBox temperatureToComboBox = new JComboBox(Temperature.Scale.values());
            temperatureToComboBox.setVisible(true);
            temperatureFrame.add(temperatureToComboBox);

            temperatureGridBagConstraints.gridx = 0;
            temperatureGridBagConstraints.gridy = 2;
            //temperatureGridBagConstraints.insets = new Insets(3, 1, 0, 1);
            //temperatureGridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
            temperatureGridBagLayout.setConstraints(temperatureToComboBox, temperatureGridBagConstraints);

            ////////////////
            JFormattedTextField temperatureToFormattedTextField = new JFormattedTextField(new NumberFormatter(temperatureNumberFormat));
            temperatureToFormattedTextField.setEditable(false);
            //temperatureGridBagConstraints.anchor = GridBagConstraints.CENTER;
            temperatureToFormattedTextField.setPreferredSize(new Dimension(temperatureFromFormattedTextField.getPreferredSize().width, temperatureFromFormattedTextField.getPreferredSize().height));
            temperatureFrame.add(temperatureToFormattedTextField);

            temperatureGridBagConstraints.gridx = 1;
            temperatureGridBagConstraints.gridy = 2;
            temperatureGridBagLayout.setConstraints(temperatureToFormattedTextField, temperatureGridBagConstraints);*/
        });
    }
}
