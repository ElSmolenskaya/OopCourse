package ru.academits.smolenskaya.csv;

import java.io.*;

public class Main {
    public static String getHtmlSymbolsFromCsvSymbol(char csvSymbol) {
        String htmlSymbols = Character.toString(csvSymbol);

        htmlSymbols = htmlSymbols.replaceAll("<", "&lt;");
        htmlSymbols = htmlSymbols.replaceAll(">", "&gt;");
        htmlSymbols = htmlSymbols.replaceAll("&", "&amp;");

        return htmlSymbols;
    }

    public static void gettingHtmlTableFromCsvTable(String inputFilePath, String outputFilePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath))) {
            bufferedWriter.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
            bufferedWriter.newLine();
            bufferedWriter.write("<html>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t<head>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t<title>Таблица из csv-документа</title>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t</head>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t<body>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t<table>");

            String inputLine;

            boolean isRowCompleted = true;

            while ((inputLine = bufferedReader.readLine()) != null) {
                if (inputLine.isEmpty()) {
                    if (isRowCompleted) {
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t<tr>");

                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t\t<td></td>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t</tr>");
                    } else {
                        bufferedWriter.write("<br/>");
                    }
                } else {
                    if (isRowCompleted) {
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t<tr>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t\t<td>");
                    } else {
                        bufferedWriter.write("<br/>");
                    }
                }

                int lineCurrentSymbolIndex = 0;

                while (lineCurrentSymbolIndex < inputLine.length()) {
                    char lineCurrentSymbol = inputLine.charAt(lineCurrentSymbolIndex);

                    if (isRowCompleted) {
                        if (lineCurrentSymbol != '"') {
                            if (lineCurrentSymbol != ',') {
                                bufferedWriter.write(getHtmlSymbolsFromCsvSymbol(lineCurrentSymbol));
                            } else {
                                bufferedWriter.write("</td>");
                                bufferedWriter.newLine();
                                bufferedWriter.write("\t\t\t\t<td>");
                            }
                        } else {
                            isRowCompleted = false;
                        }
                    } else {
                        if (lineCurrentSymbol != '"') {
                            bufferedWriter.write(getHtmlSymbolsFromCsvSymbol(lineCurrentSymbol));
                        } else {
                            char lineNextSymbol = ' ';

                            if (lineCurrentSymbolIndex + 1 < inputLine.length()) {
                                lineNextSymbol = inputLine.charAt(lineCurrentSymbolIndex + 1);
                            }

                            if (lineNextSymbol != '"') {
                                isRowCompleted = true;
                            } else {
                                bufferedWriter.write(getHtmlSymbolsFromCsvSymbol(lineCurrentSymbol));

                                ++lineCurrentSymbolIndex;
                            }
                        }
                    }

                    if (isRowCompleted && lineCurrentSymbolIndex >= inputLine.length() - 1) {
                        bufferedWriter.write("</td>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t</tr>");
                    }

                    ++lineCurrentSymbolIndex;
                }
            }

            bufferedWriter.newLine();
            bufferedWriter.write("\t\t</table>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t</body>");
            bufferedWriter.newLine();
            bufferedWriter.write("</html>");
        } catch (IOException e) {
            System.out.printf("Ошибка при попытке чтения файла %s и записи в файл %s: %s", outputFilePath, inputFilePath, e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Arguments count = " + args.length + ": arguments count must be = 3");
        }

        String folderPath = args[0];
        String inputFilePath = folderPath + args[1];
        String outputFilePath = folderPath + args[2];

        gettingHtmlTableFromCsvTable(inputFilePath, outputFilePath);
    }
}