package ru.academits.smolenskaya.csv_html;

import java.util.Objects;

class HtmlCell {
    private String data;

    public HtmlCell() {
    }

    public HtmlCell(String data) {
        setData(data);
    }

    public String getData() {
        return data;
    }

    public void setData(String csvData) {
        data = convertCsvDataToHtml(csvData);
    }

    public void add(String csvData) {
        String breakingTag = "</br>";

        data = data + breakingTag + convertCsvDataToHtml(csvData);

        if (!isCompleted(data) && !isCompleted(csvData) &&
                Objects.equals(data.substring(0, 0), "\"") &&
                Objects.equals(data.substring(data.length() - 1), "\"")) {
            data = data.substring(1, data.length() - 1);
        }
    }

    public static String convertCsvDataToHtml(String csvData) {
        String tempString = csvData;
        //boolean isCsvDataInQuotes = false;

        if ((tempString.length() > 1) && Objects.equals(tempString.substring(0, 0), "\"") &&
                Objects.equals(tempString.substring(tempString.length() - 1), "\"")) {
            tempString = tempString.substring(1, tempString.length() - 1);

            tempString = tempString.replaceAll("\"\"", "\"");

            //isCsvDataInQuotes = true;
        }

        tempString = tempString.replaceAll("<", "&lt");
        tempString = tempString.replaceAll(">", "&gt");
        tempString = tempString.replaceAll("&", "&amp");

        /*if (isCsvDataInQuotes) {
            String breakTag = "</br>";

            tempString = tempString.replaceAll(System.lineSeparator(), breakTag);
        }*/

        return tempString;
    }

    public static boolean isCompleted(String data) {
        int quotesCount = 0;
        int quotesPosition = -1;

        while (true) {
            quotesPosition = data.indexOf("\"", quotesPosition);

            if (quotesPosition < 0) {
                break;
            }

            ++quotesCount;
        }

        return quotesCount % 2 == 0;
    }

    @Override
    public String toString() {
        String openingTag = "<td>";
        String closingTag = "</td>";

        return openingTag + data + closingTag;
    }
}

