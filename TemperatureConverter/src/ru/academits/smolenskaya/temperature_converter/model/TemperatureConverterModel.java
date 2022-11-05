package ru.academits.smolenskaya.temperature_converter.model;

public class TemperatureConverterModel implements TemperatureConverter {
    @Override
    public TemperatureScale[] getTemperatureScales() {
        return new TemperatureScale[]{new CelsiusScale(), new KelvinScale(), new FahrenheitScale()};
    }

    @Override
    public double getTemperature(TemperatureScale scaleFrom, TemperatureScale scaleTo, double degrees) {
        double degreesInCelsiusScale = scaleFrom.getDegreesInCelsiusScale(degrees);

        return scaleTo.getDegreesFromCelsiusScale(degreesInCelsiusScale);
    }
}