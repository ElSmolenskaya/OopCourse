package ru.academits.smolenskaya.temperature_view;

import ru.academits.smolenskaya.temperature_model.Temperature;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

public class TemperatureFrame {
    private final Temperature temperature;

    public TemperatureFrame() {
        temperature = new Temperature();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame temperatureFrame = new JFrame("Temperature conversion");
            temperatureFrame.setSize(265, 120);
            temperatureFrame.setLocationRelativeTo(null);
            temperatureFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            temperatureFrame.setVisible(true);

            GridBagLayout gridBagLayout = new GridBagLayout();
            temperatureFrame.setLayout(gridBagLayout);

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints.fill = GridBagConstraints.BOTH;

            JComboBox<Temperature.Scale> scaleFromComboBox = new JComboBox<>(Temperature.Scale.values());
            scaleFromComboBox.setVisible(true);

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;

            temperatureFrame.add(scaleFromComboBox, gridBagConstraints);

            NumberFormat degreesNumberFormat = NumberFormat.getNumberInstance();
            degreesNumberFormat.setMaximumFractionDigits(10);

            JFormattedTextField degreesFromFormattedTextField = new JFormattedTextField(new NumberFormatter(degreesNumberFormat));
            degreesFromFormattedTextField.setPreferredSize(new Dimension(120, scaleFromComboBox.getPreferredSize().height));
            degreesFromFormattedTextField.setEditable(true);

            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;

            temperatureFrame.add(degreesFromFormattedTextField, gridBagConstraints);

            JButton conversingButton = new JButton("Convert");
            conversingButton.setPreferredSize(new Dimension(250, scaleFromComboBox.getPreferredSize().height));
            conversingButton.setEnabled(false);

            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;

            temperatureFrame.add(conversingButton, gridBagConstraints);

            JComboBox<Temperature.Scale> scaleToComboBox = new JComboBox<>(Temperature.Scale.values());
            scaleToComboBox.setVisible(true);
            scaleToComboBox.setSelectedIndex(1);

            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;

            temperatureFrame.add(scaleToComboBox, gridBagConstraints);

            JFormattedTextField degreesToFormattedTextField = new JFormattedTextField(new NumberFormatter(degreesNumberFormat));
            degreesToFormattedTextField.setEditable(false);
            degreesToFormattedTextField.setPreferredSize(new Dimension(degreesFromFormattedTextField.getPreferredSize().width, degreesFromFormattedTextField.getPreferredSize().height));
            degreesToFormattedTextField.setFocusable(false);

            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;

            temperatureFrame.add(degreesToFormattedTextField, gridBagConstraints);

            conversingButton.addActionListener(e -> {
                temperature.setDegrees((Double.parseDouble(degreesFromFormattedTextField.getValue().toString())));
                degreesToFormattedTextField.setValue(temperature.getDegrees((Temperature.Scale) scaleToComboBox.getSelectedItem()));
            });

            scaleFromComboBox.addItemListener(e -> {
                temperature.setScale((Temperature.Scale) scaleFromComboBox.getSelectedItem());
                degreesToFormattedTextField.setText(null);
            });

            scaleToComboBox.addItemListener(e -> degreesToFormattedTextField.setText(null));

            degreesFromFormattedTextField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    degreesFromFormattedTextField.setText(degreesFromFormattedTextField.getText());
                    degreesFromFormattedTextField.setCaretPosition(degreesFromFormattedTextField.getText().length());
                }

                @Override
                public void focusLost(FocusEvent e) {
                }
            });

            degreesFromFormattedTextField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    conversingButton.setEnabled(degreesFromFormattedTextField.getText() != null && isStringContainsDigit(degreesFromFormattedTextField.getText()));

                    degreesToFormattedTextField.setText(null);
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        });
    }

    private boolean isStringContainsDigit(String string) {
        char[] charArray = string.toCharArray();

        for (char c : charArray) {
            if (c >= '0' && c <= '9') {
                return true;
            }
        }

        return false;
    }
}
