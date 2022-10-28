package ru.academits.smolenskaya.temperature_converter.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TemperatureConverterModel implements TemperatureConverter {
    @Override
    public double getTemperature(String scaleFromName, String scaleToName, double degrees) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        TemperatureScale scaleFrom = getTemperatureScale(scaleFromName);

        double degreesInCelsiusScale = scaleFrom.getDegreesInCelsiusScale(degrees);

        TemperatureScale scaleTo = getTemperatureScale(scaleToName);

        return scaleTo.getDegreesFromCelsiusScale(degreesInCelsiusScale);
    }

    private TemperatureScale getTemperatureScale(String scaleName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String scaleClassNamePrefix = "ru.academits.smolenskaya.temperature_converter.model.";
        String scaleClassNamePostfix = "Scale";

        String scaleClassName = scaleClassNamePrefix + scaleName + scaleClassNamePostfix;

        Class<?> scale = Class.forName(scaleClassName);

        Constructor<?> constructor = scale.getDeclaredConstructor();

        return (TemperatureScale) constructor.newInstance();
    }
}