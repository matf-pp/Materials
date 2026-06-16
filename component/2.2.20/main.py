import sys

from PyQt5.QtWidgets import (
    QApplication,
    QHeaderView,
    QMessageBox,
    QTableWidgetItem,
    QWidget,
)
from ui_main import Ui_KnapsackForm

from solver import DEFAULT_ITEMS, optimize_knapsack


class KnapsackApp(QWidget, Ui_KnapsackForm):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        self.tableItems.setColumnCount(5)
        self.tableItems.setHorizontalHeaderLabels(
            ["Naziv", "Mesta", "Vrednost", "Najvise", "U rancu"]
        )
        self.tableItems.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableItems.verticalHeader().setVisible(False)
        self.tableItems.verticalHeader().setDefaultSectionSize(22)
        self.tableItems.setAlternatingRowColors(True)
        self.load_defaults()

        self.buttonDefaults.clicked.connect(self.load_defaults)
        self.buttonSolve.clicked.connect(self.solve)

    def load_defaults(self):
        self.tableItems.setRowCount(len(DEFAULT_ITEMS))
        for row, item in enumerate(DEFAULT_ITEMS):
            values = [
                item["name"],
                item["size"],
                item["value"],
                item["max_count"],
                "",
            ]
            for column, value in enumerate(values):
                self.tableItems.setItem(row, column, QTableWidgetItem(str(value)))
        self.labelStatus.setText("Ucitan je primer.")

    def solve(self):
        try:
            items = self.read_items()
            result = optimize_knapsack(self.spinCapacity.value(), items)
        except ValueError as error:
            QMessageBox.warning(self, "Neispravan unos", str(error))
            return

        for row, item in enumerate(items):
            count = result["counts"][item["name"]]
            self.tableItems.setItem(row, 4, QTableWidgetItem(str(count)))

        self.labelStatus.setText(
            f"Vrednost: {result['value']}, zauzeto mesta: {result['size']}"
        )

    def read_items(self):
        items = []
        for row in range(self.tableItems.rowCount()):
            name_item = self.tableItems.item(row, 0)
            name = name_item.text().strip() if name_item else ""
            if not name:
                continue

            values = []
            for column in (1, 2, 3):
                item = self.tableItems.item(row, column)
                text = item.text().strip() if item else ""
                if not text.isdigit():
                    raise ValueError("Mesta, vrednost i maksimum moraju biti celi brojevi.")
                values.append(int(text))

            size, value, max_count = values
            if size <= 0 or value < 0 or max_count < 0:
                raise ValueError("Mesta moraju biti pozitivna, a ostale vrednosti nenegativne.")

            items.append(
                {
                    "name": name,
                    "size": size,
                    "value": value,
                    "max_count": max_count,
                }
            )

        if not items:
            raise ValueError("Tabela mora da sadrzi bar jedan predmet.")
        return items


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = KnapsackApp()
    window.show()
    sys.exit(app.exec_())
