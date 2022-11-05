package ru.academits.smolenskaya.temperature_converter.model;

public class FahrenheitScale implements TemperatureScale {
    private static final int CELSIUS_FAHRENHEIT_DIFFERENCE = 32;
    private static final double CELSIUS_FAHRENHEIT_COEFFICIENT = 1.8;

    @Override
    public double getDegreesInCelsiusScale(double degrees) {
        return (degrees - CELSIUS_FAHRENHEIT_DIFFERENCE) / CELSIUS_FAHRENHEIT_COEFFICIENT;
    }

    @Override
    public double getDegreesFromCelsiusScale(double celsiusDegrees) {
        return celsiusDegrees * CELSIUS_FAHRENHEIT_COEFFICIENT + CELSIUS_FAHRENHEIT_DIFFERENCE;
    }

    @Override
    public String toString() {
        return "Fahrenheit";
    }
}