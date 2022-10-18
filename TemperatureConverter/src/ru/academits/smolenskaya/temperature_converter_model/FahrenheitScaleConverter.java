package ru.academits.smolenskaya.temperature_converter_model;

public class FahrenheitScaleConverter implements TemperatureScaleConverter {
    private static final int CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE = 32;
    private static final double CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT = 1.8;

    @Override
    public double getDegreesInCelsiusScale(double degrees) {
        return (degrees - CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE) / CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT;
    }

    @Override
    public double getDegreesFromCelsiusScale(double celsiusDegrees) {
        return celsiusDegrees * CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT + CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE;
    }
}