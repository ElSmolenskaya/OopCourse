package ru.academits.smolenskaya.csv_main;

import java.io.*;
import java.util.Objects;

public class Main {
    public static String getHtmlCellFromCsv(String csvString) {
        String tempString = csvString;

        tempString = tempString.replaceAll("<", "&lt");
        tempString = tempString.replaceAll(">", "&gt");
        tempString = tempString.replaceAll("&", "&amp");

        if ((tempString.length() > 1) && Objects.equals(tempString.substring(0, 0), "\"") &&
                Objects.equals(tempString.substring(tempString.length() - 1), "\"")) {
            tempString = tempString.substring(1, tempString.length() - 1);

            tempString = tempString.replaceAll("\"\"", "\"");
            tempString = tempString.replaceAll(System.lineSeparator(), "</br>");

            tempString = tempString.substring(1, tempString.length() - 1);
        }

        return "<td>" + tempString + "</td>";
    }

    public static void main(String[] args) {
        String filePath = "CSV/src/ru/academits/smolenskaya/files/";
        String inputFileName = "input.csv";
        String outputFileName = "output.html";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + inputFileName))) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath + outputFileName))) {
                bufferedWriter.write("<table>");

                String cell = null;
                String uncompletedCell = null;

                String inputString;

                while ((inputString = bufferedReader.readLine()) != null) {
                    if (uncompletedCell != null) {
                        inputString = uncompletedCell + System.lineSeparator() + inputString;

                        uncompletedCell = null;
                    }

                    int startCellIndex = 0;
                    int endCellIndex = -1;

                    char[] inputStringArray = inputString.toCharArray();

                    while (startCellIndex < inputString.length()) {
                        if (inputStringArray[startCellIndex] != '"') {
                            endCellIndex = inputString.indexOf(',');

                            if (endCellIndex < 0) {
                                cell = inputString.substring(startCellIndex);

                                bufferedWriter.write(getHtmlCellFromCsv(cell));

                                break;
                            } else {
                                cell = inputString.substring(startCellIndex, endCellIndex);

                                bufferedWriter.write(getHtmlCellFromCsv(cell));

                                startCellIndex = endCellIndex + 1;
                                endCellIndex = -1;
                            }
                        } else {
                            int index = startCellIndex + 1;

                            while (index < inputString.length()) {
                                index = inputString.indexOf("\",", index);

                                if (index < 0) {
                                    if (inputStringArray[inputStringArray.length - 1] == '"' &&
                                            inputStringArray[inputStringArray.length - 2] != '"') {
                                        endCellIndex = inputString.length() - 1;
                                    }

                                    break;
                                }

                                if (inputStringArray[index - 1] != '"') {
                                    endCellIndex = index;

                                    break;
                                }
                            }

                            if (endCellIndex < 0) {
                                uncompletedCell = inputString.substring(startCellIndex);

                                break;
                            }

                            cell = inputString.substring(startCellIndex, endCellIndex);
                            bufferedWriter.write(getHtmlCellFromCsv(cell));

                            startCellIndex = endCellIndex + 1;
                            endCellIndex = -1;
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
            System.out.printf("Ошибка при попытке записи в файл %s: %s", filePath + outputFileName, e.getMessage());
        }
    }
}
