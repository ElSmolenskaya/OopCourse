package ru.academits.smolenskaya.temperature_converter.model;

public interface TemperatureConverter {
    double getTemperature(TemperatureScale scaleFrom, TemperatureScale scaleTo, double degrees);
}