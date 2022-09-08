package ru.academits.smolenskaya.csv_main;

import java.io.*;

public class Main {
    public static String getHtmlCellFromCsvCell(String csvString) {
        String tempString = csvString;

        tempString = tempString.replaceAll("<", "&lt");
        tempString = tempString.replaceAll(">", "&gt");
        tempString = tempString.replaceAll("&", "&amp");
        tempString = tempString.replaceAll("\"\"", "\"");

        return tempString;
    }

    public static void getHtmlTableFromCsvTable(String inputFileName, String outputFileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName))) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName))) {
                bufferedWriter.write("<table>");

                String cell;
                String inputRow;

                int rowQueuesCount = 0;

                boolean isRowCompleted = true;

                while ((inputRow = bufferedReader.readLine()) != null) {
                    if (isRowCompleted) {
                        bufferedWriter.write("<tr>");
                    }

                    if (inputRow.isEmpty()) {
                        if (isRowCompleted) {
                            bufferedWriter.write("<td></td></tr>");
                        } else {
                            bufferedWriter.write("</br>");
                        }
                    }

                    int startCellIndex = 0;

                    while (startCellIndex < inputRow.length()) {
                        if (isRowCompleted) {
                            bufferedWriter.write("<td>");
                        }

                        int endCellIndex = -1;

                        if (isRowCompleted && inputRow.charAt(startCellIndex) != '"') {
                            endCellIndex = inputRow.indexOf(',', startCellIndex);

                            if (endCellIndex < 0) {
                                endCellIndex = inputRow.length();

                                cell = inputRow.substring(startCellIndex, endCellIndex);

                                bufferedWriter.write(getHtmlCellFromCsvCell(cell));
                                bufferedWriter.write("</td></tr>");

                                break;
                            }

                            cell = inputRow.substring(startCellIndex, endCellIndex);
                        } else {
                            int index = startCellIndex;

                            if (isRowCompleted) {
                                ++startCellIndex;
                            }

                            for (; index < inputRow.length(); index++) {
                                if (inputRow.charAt(index) == '"') {
                                    ++rowQueuesCount;
                                }

                                if (((inputRow.charAt(index) == ',') || index == inputRow.length() - 1) && (rowQueuesCount % 2 == 0)) {
                                    endCellIndex = index;

                                    isRowCompleted = true;
                                    rowQueuesCount = 0;

                                    break;
                                }
                            }

                            if (endCellIndex < 0) {
                                isRowCompleted = false;

                                cell = inputRow.substring(startCellIndex);
                                bufferedWriter.write(getHtmlCellFromCsvCell(cell));

                                bufferedWriter.write("</br>");

                                break;
                            }

                            if (inputRow.charAt(index) == ',') {
                                cell = inputRow.substring(startCellIndex, endCellIndex - 1);
                            } else {
                                cell = inputRow.substring(startCellIndex, endCellIndex);
                            }
                        }

                        bufferedWriter.write(getHtmlCellFromCsvCell(cell));
                        bufferedWriter.write("</td>");

                        startCellIndex = endCellIndex + 1;

                        if (startCellIndex >= inputRow.length()) {
                            if (inputRow.charAt(endCellIndex) == ',') {
                                bufferedWriter.write("<td></td>");
                            }

                            bufferedWriter.write("</tr>");
                        }
                    }
                }

                bufferedWriter.write("</table>");
            } catch (FileNotFoundException e) {
                System.out.printf("Файл %s не найден", inputFileName);
            } catch (IOException e) {
                System.out.printf("При попытке работы с файлом %s возникла ошибка", inputFileName);
            }
        } catch (IOException e) {
            System.out.printf("Ошибка при попытке записи в файл %s: %s", outputFileName, e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filePath = "CSV/src/ru/academits/smolenskaya/files/";
        String inputFileName = filePath + "input.csv";
        String outputFileName = filePath + "output.html";

        getHtmlTableFromCsvTable(inputFileName, outputFileName);
    }
}
