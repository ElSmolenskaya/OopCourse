package ru.academits.smolenskaya.temperature_converter.model;

public interface TemperatureConverter {
    TemperatureScale[] getTemperatureScales();

    double getTemperature(TemperatureScale scaleFrom, TemperatureScale scaleTo, double degrees);
}