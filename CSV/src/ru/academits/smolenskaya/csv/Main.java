package ru.academits.smolenskaya.csv;

import java.io.*;

public class Main {
    public static String getHtmlSymbolsFromCsvSymbol(char csvSymbol) {
        return switch (csvSymbol) {
            case '<' -> "&lt;";
            case '>' -> "&gt;";
            case '&' -> "&amp;";
            default -> Character.toString(csvSymbol);
        };
    }

    public static void convertCsvTableToHtmlTable(BufferedReader bufferedReader, PrintWriter printWriter) throws IOException {
        printWriter.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
        printWriter.println("<html>");
        printWriter.println("\t<head>");
        printWriter.println("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        printWriter.println("\t\t<title>Таблица из csv-документа</title>");
        printWriter.println("\t</head>");
        printWriter.println("\t<body>");
        printWriter.println("\t\t<table>");

        String inputLine;

        boolean isRowCompleted = true;

        while ((inputLine = bufferedReader.readLine()) != null) {
            if (inputLine.isEmpty()) {
                if (isRowCompleted) {
                    printWriter.println("\t\t\t<tr>");
                    printWriter.println("\t\t\t\t<td></td>");
                    printWriter.println("\t\t\t</tr>");
                } else {
                    printWriter.print("<br/>");
                }
            } else {
                if (isRowCompleted) {
                    printWriter.println("\t\t\t<tr>");
                    printWriter.print("\t\t\t\t<td>");
                } else {
                    printWriter.print("<br/>");
                }
            }

            int lineCurrentSymbolIndex = 0;

            while (lineCurrentSymbolIndex < inputLine.length()) {
                char lineCurrentSymbol = inputLine.charAt(lineCurrentSymbolIndex);

                if (isRowCompleted) {
                    if (lineCurrentSymbol != '"') {
                        if (lineCurrentSymbol != ',') {
                            printWriter.print(getHtmlSymbolsFromCsvSymbol(lineCurrentSymbol));
                        } else {
                            printWriter.println("</td>");
                            printWriter.print("\t\t\t\t<td>");
                        }
                    } else {
                        isRowCompleted = false;
                    }
                } else {
                    if (lineCurrentSymbol != '"') {
                        printWriter.print(getHtmlSymbolsFromCsvSymbol(lineCurrentSymbol));
                    } else {
                        char lineNextSymbol = ' ';

                        if (lineCurrentSymbolIndex + 1 < inputLine.length()) {
                            lineNextSymbol = inputLine.charAt(lineCurrentSymbolIndex + 1);
                        }

                        if (lineNextSymbol != '"') {
                            isRowCompleted = true;
                        } else {
                            printWriter.print(getHtmlSymbolsFromCsvSymbol(lineCurrentSymbol));

                            ++lineCurrentSymbolIndex;
                        }
                    }
                }

                if (isRowCompleted && lineCurrentSymbolIndex >= inputLine.length() - 1) {
                    printWriter.println("</td>");
                    printWriter.println("\t\t\t</tr>");
                }

                ++lineCurrentSymbolIndex;
            }
        }

        printWriter.println("\t\t</table>");
        printWriter.println("\t</body>");
        printWriter.print("</html>");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.printf("Arguments count = %s: arguments count must be >= 2.%n", args.length);
            System.out.print("First argument must contain full path to csv-fail. Second argument must contain full path to the folder " +
                    "where a new html-file will be created.");

            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
             PrintWriter printWriter = new PrintWriter(new FileWriter(outputFilePath))) {
            convertCsvTableToHtmlTable(bufferedReader, printWriter);
        } catch (IOException e) {
            System.out.printf("Ошибка при попытке чтения файла %s и записи в файл %s: %s", outputFilePath, inputFilePath, e.getMessage());
        }
    }
}