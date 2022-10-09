package ru.academits.smolenskaya.temperature_model;

public class Temperature {
    private double degrees;
    private Scale scale;

    private static final double CELSIUS_KELVIN_DEGREES_DIFFERENCE = 273.15;
    private static final int CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE = 32;
    private static final double FAHRENHEIT_KELVIN_DEGREES_DIFFERENCE = 459.67;
    private static final double CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT = 1.8;
    private static final double FAHRENHEIT_KELVIN_DEGREES_COEFFICIENT = 5.0 / 9;

    public enum Scale {
        CELSIUS, KELVIN, FAHRENHEIT
    }

    public Temperature() {
        scale = Scale.CELSIUS;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    public double getDegrees(Scale scaleTo) {
        return switch (scale) {
            case CELSIUS -> switch (scaleTo) {
                case CELSIUS -> degrees;
                case KELVIN -> degrees + CELSIUS_KELVIN_DEGREES_DIFFERENCE;
                case FAHRENHEIT ->
                        degrees * CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT + CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE;
            };
            case KELVIN -> switch (scaleTo) {
                case CELSIUS -> degrees - CELSIUS_KELVIN_DEGREES_DIFFERENCE;
                case KELVIN -> degrees;
                case FAHRENHEIT ->
                        degrees / FAHRENHEIT_KELVIN_DEGREES_COEFFICIENT - FAHRENHEIT_KELVIN_DEGREES_DIFFERENCE;
            };
            case FAHRENHEIT -> switch (scaleTo) {
                case CELSIUS ->
                        (degrees - CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE) / CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT;
                case KELVIN -> (degrees + FAHRENHEIT_KELVIN_DEGREES_DIFFERENCE) * FAHRENHEIT_KELVIN_DEGREES_COEFFICIENT;
                case FAHRENHEIT -> degrees;
            };
        };
    }
}
