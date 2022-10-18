package ru.academits.smolenskaya.temperature_converter_model;

public class CelsiusScaleConverter implements TemperatureScaleConverter {
    @Override
    public double getDegreesInCelsiusScale(double degrees) {
        return degrees;
    }

    @Override
    public double getDegreesFromCelsiusScale(double celsiusDegrees) {
        return celsiusDegrees;
    }
}