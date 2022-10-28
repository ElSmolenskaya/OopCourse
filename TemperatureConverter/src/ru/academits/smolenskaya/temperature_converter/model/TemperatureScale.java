package ru.academits.smolenskaya.temperature_converter.model;

public interface TemperatureScale {
    double getDegreesInCelsiusScale(double degrees);

    double getDegreesFromCelsiusScale(double celsiusDegrees);
}