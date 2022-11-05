package ru.academits.smolenskaya.temperature_converter.model;

public class CelsiusScale implements TemperatureScale {
    @Override
    public double getDegreesInCelsiusScale(double degrees) {
        return degrees;
    }

    @Override
    public double getDegreesFromCelsiusScale(double celsiusDegrees) {
        return celsiusDegrees;
    }

    @Override
    public String toString() {
        return "Celsius";
    }
}