package ru.academits.smolenskaya.temperature_converter_model;

public interface TemperatureScaleConverter {
    double getDegreesInCelsiusScale(double degrees);

    double getDegreesFromCelsiusScale(double celsiusDegrees);
}