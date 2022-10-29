package ru.academits.smolenskaya.temperature_converter.model;

public class TemperatureConverterModel implements TemperatureConverter {
    @Override
    public double getTemperature(TemperatureScale scaleFrom, TemperatureScale scaleTo, double degrees) {
        double degreesInCelsiusScale = scaleFrom.getDegreesInCelsiusScale(degrees);

        return scaleTo.getDegreesFromCelsiusScale(degreesInCelsiusScale);
    }
}