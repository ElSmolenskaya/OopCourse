package ru.academits.smolenskaya.temperature_converter_view;

import ru.academits.smolenskaya.temperature_converter_model.CelsiusScaleConverter;
import ru.academits.smolenskaya.temperature_converter_model.FahrenheitScaleConverter;
import ru.academits.smolenskaya.temperature_converter_model.KelvinScaleConverter;
import ru.academits.smolenskaya.temperature_converter_model.TemperatureScaleConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class TemperatureConverterFrame {
    private final String[] temperatureScales = {"Celsius", "Kelvin", "Fahrenheit"};

    public TemperatureConverterFrame() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame temperatureConverterFrame = new JFrame("Temperature conversion");
            temperatureConverterFrame.setMinimumSize(new Dimension(315, 150));
            temperatureConverterFrame.setLocationRelativeTo(null);
            temperatureConverterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            temperatureConverterFrame.setVisible(true);

            GridBagLayout gridBagLayout = new GridBagLayout();
            temperatureConverterFrame.setLayout(gridBagLayout);

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints.fill = GridBagConstraints.BOTH;

            JComboBox<String> scaleFromComboBox = new JComboBox<>(temperatureScales);
            scaleFromComboBox.setVisible(true);

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;

            temperatureConverterFrame.add(scaleFromComboBox, gridBagConstraints);

            JTextField degreesFromTextField = new JTextField();
            degreesFromTextField.setPreferredSize(new Dimension(120, scaleFromComboBox.getPreferredSize().height));
            degreesFromTextField.setEditable(true);

            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;

            temperatureConverterFrame.add(degreesFromTextField, gridBagConstraints);

            JButton conversingButton = new JButton("Convert");
            conversingButton.setPreferredSize(new Dimension(250, scaleFromComboBox.getPreferredSize().height));
            conversingButton.setEnabled(false);

            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;

            temperatureConverterFrame.add(conversingButton, gridBagConstraints);

            JComboBox<String> scaleToComboBox = new JComboBox<>(temperatureScales);
            scaleToComboBox.setVisible(true);
            scaleToComboBox.setSelectedIndex(1);

            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;

            temperatureConverterFrame.add(scaleToComboBox, gridBagConstraints);

            JTextField degreesToTextField = new JTextField();
            degreesToTextField.setEditable(false);
            degreesToTextField.setPreferredSize(new Dimension(degreesFromTextField.getPreferredSize().width, degreesFromTextField.getPreferredSize().height));

            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;

            temperatureConverterFrame.add(degreesToTextField, gridBagConstraints);

            conversingButton.addActionListener(e -> {
                try {
                    degreesFromTextField.setText(degreesFromTextField.getText().replace(',', '.'));

                    double degreesFrom = Double.parseDouble(degreesFromTextField.getText());
                    degreesFromTextField.setText(getFormattedNumberString(degreesFromTextField.getText()));

                    TemperatureScaleConverter degreesFromScaleConverter = getTemperatureScaleConverter(Objects.requireNonNull(scaleFromComboBox.getSelectedItem()).toString());
                    double degreesFromInCelsiusScale = degreesFromScaleConverter.getDegreesInCelsiusScale(degreesFrom);

                    TemperatureScaleConverter degreesToScaleConverter = getTemperatureScaleConverter(Objects.requireNonNull(scaleToComboBox.getSelectedItem()).toString());
                    double degreesTo = degreesToScaleConverter.getDegreesFromCelsiusScale(degreesFromInCelsiusScale);

                    degreesToTextField.setText(getFormattedNumberString(String.valueOf(degreesTo)));
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(new JPanel(new FlowLayout()), "Temperature value to convert" +
                            System.lineSeparator() + "contains incorrect symbols!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            scaleFromComboBox.addItemListener(e -> degreesToTextField.setText(null));

            scaleToComboBox.addItemListener(e -> degreesToTextField.setText(null));

            degreesFromTextField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    degreesFromTextField.setText(degreesFromTextField.getText());
                    degreesFromTextField.setCaretPosition(degreesFromTextField.getText().length());
                }
            });

            degreesFromTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    conversingButton.setEnabled(containsDigit(degreesFromTextField.getText() + e.getKeyChar()));

                    degreesToTextField.setText(null);
                }
            });
        });
    }

    private static boolean containsDigit(String string) {
        if (string.isEmpty()) {
            return false;
        }

        for (int i = 0; i < string.length(); i++) {
            char element = string.charAt(i);
            if (element >= '0' && element <= '9') {
                return true;
            }
        }

        return false;
    }

    private static TemperatureScaleConverter getTemperatureScaleConverter(String scaleName) {
        return switch (scaleName) {
            case "Celsius" -> new CelsiusScaleConverter();
            case "Kelvin" -> new KelvinScaleConverter();
            case "Fahrenheit" -> new FahrenheitScaleConverter();
            default -> throw new IllegalArgumentException("Scale name = " + scaleName + ": unknown scale name");
        };
    }

    private static String getFormattedNumberString(String number) {
        BigDecimal bigDecimal = new BigDecimal(number);

        String tempNumber = String.valueOf(bigDecimal.setScale(6, RoundingMode.HALF_UP).doubleValue());

        if (tempNumber.length() > 2 && Objects.equals(tempNumber.substring(tempNumber.length() - 2), ".0")) {
            tempNumber = tempNumber.substring(0, tempNumber.length() - 2);
        }

        return tempNumber;
    }
}