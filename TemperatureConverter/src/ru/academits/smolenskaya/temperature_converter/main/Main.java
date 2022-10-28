package ru.academits.smolenskaya.temperature_converter.main;

import ru.academits.smolenskaya.temperature_converter.model.TemperatureConverterModel;
import ru.academits.smolenskaya.temperature_converter.view.TemperatureConverterFrame;

public class Main {
    public static void main(String[] args) {
        try {
            String[] temperatureScales = {"Celsius", "Kelvin", "Fahrenheit"};

            TemperatureConverterModel temperatureConverterModel = new TemperatureConverterModel();

            //noinspection unused
            TemperatureConverterFrame temperatureFrame = new TemperatureConverterFrame(temperatureConverterModel, temperatureScales);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }
}