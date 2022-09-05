package ru.academits.smolenskaya.csv_html;

public class HtmlTable {
    private HtmlRow[] rows;
    private int size;

    public HtmlTable() {
        rows = new HtmlRow[20];
    }

    public void addRow(String csvString) {
        ++size;

        rows[size - 1] = new HtmlRow(csvString);

/*        int lineSeparatorPosition;

        int startRowPosition = 0;

        int quotesPosition = startRowPosition;

        if (csvString == null) {
            return;
        }

        while (true) {
            lineSeparatorPosition = csvString.indexOf(System.lineSeparator(), startRowPosition);

            if (lineSeparatorPosition < 0) {
                ++size;

                //rows[size - 1] = new HtmlRow(csvString.substring(startRowPosition));
                System.out.println(csvString.substring(startRowPosition));

                break;
            }

            if (lineSeparatorPosition > 0) {
                int quotesCount = 0;

                while (true) {
                    quotesPosition = csvString.indexOf("\"", quotesPosition);

                    if ((quotesPosition < 0) || (quotesPosition >= lineSeparatorPosition)) {
                        break;
                    }

                    ++quotesCount;
                }

                if (quotesCount % 2 == 0) {
                    ++size;

                    //rows[size - 1] = new HtmlRow(csvString.substring(startRowPosition, lineSeparatorPosition));
                    System.out.println(csvString.substring(startRowPosition, lineSeparatorPosition));

                    startRowPosition = lineSeparatorPosition + System.lineSeparator().length() + 1;

                    quotesPosition = startRowPosition;
                }
            }
        }*/
    }

    public String getRows() {
        String openingTag = "<table>";
        String closingTag = "</table>";

        StringBuilder stringBuilder = new StringBuilder(openingTag);

        for (HtmlRow row : rows) {
            stringBuilder.append(row.getCells());
        }

        return stringBuilder.append(closingTag).toString();
    }
}
