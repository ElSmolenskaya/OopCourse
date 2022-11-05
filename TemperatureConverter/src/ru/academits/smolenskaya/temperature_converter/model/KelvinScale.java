package ru.academits.smolenskaya.temperature_converter.model;

public class KelvinScale implements TemperatureScale {
    private static final double CELSIUS_KELVIN_DIFFERENCE = 273.15;

    @Override
    public double getDegreesInCelsiusScale(double degrees) {
        return degrees - CELSIUS_KELVIN_DIFFERENCE;
    }

    @Override
    public double getDegreesFromCelsiusScale(double celsiusDegrees) {
        return celsiusDegrees + CELSIUS_KELVIN_DIFFERENCE;
    }

    @Override
    public String toString() {
        return "Kelvin";
    }
}