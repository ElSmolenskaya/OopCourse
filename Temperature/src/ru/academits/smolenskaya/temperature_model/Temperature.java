package ru.academits.smolenskaya.temperature_model;

public class Temperature {
    private double celsiusDegrees;
    private double kelvinDegrees;
    private double fahrenheitDegrees;

    private static final double CELSIUS_KELVIN_DEGREES_DIFFERENCE = 273.15;
    private static final int CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE = 32;
    private static final double FAHRENHEIT_KELVIN_DEGREES_DIFFERENCE = 459.67;
    private static final double CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT = 1.8;
    private static final double FAHRENHEIT_KELVIN_DEGREES_COEFFICIENT = 5.0 / 9;

    public double getCelsiusDegrees() {
        return celsiusDegrees;
    }

    public double getKelvinDegrees() {
        return kelvinDegrees;
    }

    public double getFahrenheitDegrees() {
        return fahrenheitDegrees;
    }

    public void setCelsiusDegrees(double celsiusDegrees) {
        this.celsiusDegrees = celsiusDegrees;

        fahrenheitDegrees = celsiusDegrees * CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT + CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE;
        kelvinDegrees = celsiusDegrees + CELSIUS_KELVIN_DEGREES_DIFFERENCE;
    }

    public void setFahrenheitDegrees(double fahrenheitDegrees) {
        this.fahrenheitDegrees = fahrenheitDegrees;

        celsiusDegrees = (fahrenheitDegrees - CELSIUS_FAHRENHEIT_DEGREES_DIFFERENCE) / CELSIUS_FAHRENHEIT_DEGREES_COEFFICIENT;

        kelvinDegrees = (fahrenheitDegrees + FAHRENHEIT_KELVIN_DEGREES_DIFFERENCE) * FAHRENHEIT_KELVIN_DEGREES_COEFFICIENT;
    }

    public void setKelvinDegrees(double kelvinDegrees) {
        this.kelvinDegrees = kelvinDegrees;

        celsiusDegrees = kelvinDegrees - CELSIUS_KELVIN_DEGREES_DIFFERENCE;

        fahrenheitDegrees = kelvinDegrees / FAHRENHEIT_KELVIN_DEGREES_COEFFICIENT - FAHRENHEIT_KELVIN_DEGREES_DIFFERENCE;
    }
}
