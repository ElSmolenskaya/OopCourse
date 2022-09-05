package ru.academits.smolenskaya.csv_html;

class HtmlRow {
    private HtmlCell[] cells;

    public HtmlRow(String cswRow) {
        int startCellPosition = 0;
        int cellsCount = 1;

        while (true) {
            startCellPosition = cswRow.indexOf(',', startCellPosition);

            if (startCellPosition < 0) {
                break;
            }

            ++cellsCount;
        }

        cells = new HtmlCell[cellsCount];

        startCellPosition = 0;
        int endCellPosition;

        int i = -1;

        while (startCellPosition < cswRow.length()) {
            endCellPosition = cswRow.indexOf(',', startCellPosition);

            if (endCellPosition < 0) {
                ++i;

                if (i > 0 & HtmlCell.isCompleted(cells[i - 1].getData())) {
                    cells[i] = new HtmlCell(cswRow.substring(startCellPosition));
                } else {
                    cells[i - 1].add(cswRow.substring(startCellPosition));
                }

                break;
            }

            cells[i] = new HtmlCell(cswRow.substring(startCellPosition, endCellPosition));

            startCellPosition = endCellPosition + 1;
        }
    }

    public String getCells() {
        String openingTag = "<tr>";
        String closingTag = "</tr>";

        StringBuilder stringBuilder = new StringBuilder(openingTag);

        for (HtmlCell cell : cells) {
            stringBuilder.append(cell.getData());
        }

        return stringBuilder.append(closingTag).toString();
    }
}
