package ru.academits.smolenskaya.temperature_converter_model;

public class KelvinScaleConverter implements TemperatureScaleConverter {
    private static final double CELSIUS_KELVIN_DEGREES_DIFFERENCE = 273.15;

    @Override
    public double getDegreesInCelsiusScale(double degrees) {
        return degrees - CELSIUS_KELVIN_DEGREES_DIFFERENCE;
    }

    @Override
    public double getDegreesFromCelsiusScale(double celsiusDegrees) {
        return celsiusDegrees + CELSIUS_KELVIN_DEGREES_DIFFERENCE;
    }
}