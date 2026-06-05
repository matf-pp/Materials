import sys
from pathlib import Path

from PyQt5 import uic
from PyQt5.QtWidgets import (
    QApplication,
    QHeaderView,
    QMessageBox,
    QTableWidgetItem,
    QWidget,
)

from solver import DEFAULT_CLASSES, make_schedule


class TimetableApp(QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi(str(Path(__file__).with_name("main.ui")), self)

        self.tableInput.setColumnCount(3)
        self.tableInput.setHorizontalHeaderLabels(["Predmet", "Nastavnik", "Grupa"])
        self.tableInput.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableInput.verticalHeader().setVisible(False)
        self.tableInput.verticalHeader().setDefaultSectionSize(22)
        self.tableInput.setAlternatingRowColors(True)

        self.tableSchedule.setColumnCount(5)
        self.tableSchedule.setHorizontalHeaderLabels(
            ["Predmet", "Nastavnik", "Grupa", "Termin", "Sala"]
        )
        self.tableSchedule.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableSchedule.verticalHeader().setVisible(False)
        self.tableSchedule.verticalHeader().setDefaultSectionSize(22)
        self.tableSchedule.setAlternatingRowColors(True)

        self.buttonExample.clicked.connect(self.load_example)
        self.buttonSolve.clicked.connect(self.solve)
        self.load_example()

    def load_example(self):
        self.tableInput.setRowCount(len(DEFAULT_CLASSES))
        for row, class_data in enumerate(DEFAULT_CLASSES):
            values = [
                class_data["subject"],
                class_data["teacher"],
                class_data["group"],
            ]
            for column, value in enumerate(values):
                self.tableInput.setItem(row, column, QTableWidgetItem(value))
        self.tableSchedule.setRowCount(0)
        self.labelStatus.setText("Ucitan je primer.")

    def solve(self):
        try:
            classes = self.read_classes()
        except ValueError as error:
            QMessageBox.warning(self, "Neispravan unos", str(error))
            return

        schedule = make_schedule(classes)
        if schedule is None:
            self.tableSchedule.setRowCount(0)
            self.labelStatus.setText("Nije pronadjen dopustiv raspored.")
            return

        self.tableSchedule.setRowCount(len(schedule))
        for row, class_data in enumerate(schedule):
            values = [
                class_data["subject"],
                class_data["teacher"],
                class_data["group"],
                class_data["slot"],
                class_data["room"],
            ]
            for column, value in enumerate(values):
                self.tableSchedule.setItem(row, column, QTableWidgetItem(value))

        self.labelStatus.setText("Raspored je pronadjen.")

    def read_classes(self):
        classes = []
        for row in range(self.tableInput.rowCount()):
            values = []
            for column in range(3):
                item = self.tableInput.item(row, column)
                values.append(item.text().strip() if item else "")
            if not any(values):
                continue
            if not all(values):
                raise ValueError("Svi podaci u popunjenom redu su obavezni.")
            classes.append(
                {
                    "subject": values[0],
                    "teacher": values[1],
                    "group": values[2],
                }
            )
        return classes


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = TimetableApp()
    window.show()
    sys.exit(app.exec_())
