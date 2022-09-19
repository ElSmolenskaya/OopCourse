package ru.academits.smolenskaya.csv;

import java.io.*;

public class Main {
    public static String getHtmlCellFromCsvCell(String csvString) {
        String resultString = csvString;

        resultString = resultString.replaceAll("<", "&lt;");
        resultString = resultString.replaceAll(">", "&gt;");
        resultString = resultString.replaceAll("&", "&amp;");
        resultString = resultString.replaceAll("\"\"", "\"");

        return resultString;
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

            String cell;
            String inputLine;

            int rowQueuesCount = 0;

            boolean isRowCompleted = true;

            char[] inputChar = new char[1];

            while (bufferedReader.read(inputChar) > 0) {
                if (isRowCompleted) {
                    bufferedWriter.newLine();
                    bufferedWriter.write("\t\t\t<tr>");
                }

                if (inputChar.length == 0) {
                    if (isRowCompleted) {
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t\t<td></td>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t</tr>");
                    } else {
                        bufferedWriter.write("<br/>");
                    }
                }
            }

            /*while ((inputLine = bufferedReader.readLine()) != null) {
                if (isRowCompleted) {
                    bufferedWriter.newLine();
                    bufferedWriter.write("\t\t\t<tr>");
                }

                if (inputLine.isEmpty()) {
                    if (isRowCompleted) {
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t\t<td></td>");
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t</tr>");
                    } else {
                        bufferedWriter.write("<br/>");
                    }
                }

                int startCellIndex = 0;

                while (startCellIndex < inputLine.length()) {
                    if (isRowCompleted) {
                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t\t<td>");
                    }

                    int endCellIndex = -1;

                    if (isRowCompleted && inputLine.charAt(startCellIndex) != '"') {
                        endCellIndex = inputLine.indexOf(',', startCellIndex);

                        if (endCellIndex < 0) {
                            endCellIndex = inputLine.length();

                            cell = inputLine.substring(startCellIndex, endCellIndex);

                            bufferedWriter.write(getHtmlCellFromCsvCell(cell));
                            bufferedWriter.write("</td>");
                            bufferedWriter.newLine();
                            bufferedWriter.write("\t\t\t</tr>");

                            break;
                        }

                        cell = inputLine.substring(startCellIndex, endCellIndex);
                    } else {
                        int index = startCellIndex;

                        if (isRowCompleted) {
                            ++startCellIndex;
                        }

                        for (; index < inputLine.length(); index++) {
                            if (inputLine.charAt(index) == '"') {
                                ++rowQueuesCount;
                            }

                            if (((inputLine.charAt(index) == ',') || index == inputLine.length() - 1) && (rowQueuesCount % 2 == 0)) {
                                endCellIndex = index;

                                isRowCompleted = true;
                                rowQueuesCount = 0;

                                break;
                            }
                        }

                        if (endCellIndex < 0) {
                            isRowCompleted = false;

                            cell = inputLine.substring(startCellIndex);
                            bufferedWriter.write(getHtmlCellFromCsvCell(cell));

                            bufferedWriter.write("<br/>");

                            break;
                        }

                        if (inputLine.charAt(index) == ',') {
                            cell = inputLine.substring(startCellIndex, endCellIndex - 1);
                        } else {
                            cell = inputLine.substring(startCellIndex, endCellIndex);
                        }
                    }

                    bufferedWriter.write(getHtmlCellFromCsvCell(cell));
                    bufferedWriter.write("</td>");

                    startCellIndex = endCellIndex + 1;

                    if (startCellIndex >= inputLine.length()) {
                        if (inputLine.charAt(endCellIndex) == ',') {
                            bufferedWriter.newLine();
                            bufferedWriter.write("\t\t\t\t<td></td>");
                        }

                        bufferedWriter.newLine();
                        bufferedWriter.write("\t\t\t</tr>");
                    }
                }
            }
*/
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
        String folderPath = "CSV/src/ru/academits/smolenskaya/files/";
        String inputFilePath = folderPath + "input.csv";
        String outputFilePath = folderPath + "output.html";

        gettingHtmlTableFromCsvTable(inputFilePath, outputFilePath);
    }
}
