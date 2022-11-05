package ru.academits.smolenskaya.temperature_converter.main;

import ru.academits.smolenskaya.temperature_converter.model.*;
import ru.academits.smolenskaya.temperature_converter.view.TemperatureConverterFrame;

public class Main {
    public static void main(String[] args) {
        try {
            TemperatureConverterModel temperatureConverterModel = new TemperatureConverterModel();

            TemperatureScale[] temperatureScales = temperatureConverterModel.getTemperatureScales();

            //noinspection unused
            TemperatureConverterFrame temperatureFrame = new TemperatureConverterFrame(temperatureConverterModel, temperatureScales);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }
}