package ru.academits.smolenskaya.temperature_converter.main;

import ru.academits.smolenskaya.temperature_converter.model.*;
import ru.academits.smolenskaya.temperature_converter.view.TemperatureConverterFrame;

public class Main {
    public static void main(String[] args) {
        try {
            TemperatureScale[] temperatureScales = {new CelsiusScale(), new KelvinScale(), new FahrenheitScale()};

            TemperatureConverterModel temperatureConverterModel = new TemperatureConverterModel();

            //noinspection unused
            TemperatureConverterFrame temperatureFrame = new TemperatureConverterFrame(temperatureConverterModel, temperatureScales);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }
}