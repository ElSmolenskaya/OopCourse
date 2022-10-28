package ru.academits.smolenskaya.temperature_converter.model;

import java.lang.reflect.InvocationTargetException;

public interface TemperatureConverter {
    double getTemperature(String scaleFromName, String scaleToName, double degrees) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
