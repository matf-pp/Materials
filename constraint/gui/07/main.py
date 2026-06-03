import sys
from pathlib import Path

from PyQt5 import uic
from PyQt5.QtWidgets import QApplication, QHeaderView, QTableWidgetItem, QWidget

from solver import COURSES, optimize_training


class TrainingPlannerApp(QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi(str(Path(__file__).with_name("main.ui")), self)

        self.tableCourses.setColumnCount(5)
        self.tableCourses.setHorizontalHeaderLabels(
            ["Kurs", "Cena", "Sati", "Dobit/sat", "Broj radnika"]
        )
        self.tableCourses.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableCourses.verticalHeader().setVisible(False)
        self.tableCourses.verticalHeader().setDefaultSectionSize(22)
        self.tableCourses.setAlternatingRowColors(True)
        self.fill_course_table()

        self.buttonSolve.clicked.connect(self.solve)
        self.solve()

    def fill_course_table(self):
        self.tableCourses.setRowCount(len(COURSES))
        for row, (name, data) in enumerate(COURSES.items()):
            values = [
                name,
                data["cost"],
                data["hours"],
                data["profit_per_hour"],
                "",
            ]
            for column, value in enumerate(values):
                self.tableCourses.setItem(row, column, QTableWidgetItem(str(value)))

    def solve(self):
        result = optimize_training(
            self.spinBudget.value(),
            self.spinHours.value(),
            self.spinWorkers.value(),
        )
        if result is None:
            self.labelStatus.setText("Nema dopustivog plana.")
            return

        for row in range(self.tableCourses.rowCount()):
            name = self.tableCourses.item(row, 0).text()
            self.tableCourses.setItem(row, 4, QTableWidgetItem(str(result[name])))

        self.labelStatus.setText(
            "Dobit: {profit}, trosak: {cost}, projektni sati: {project_hours}".format(
                **result
            )
        )


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = TrainingPlannerApp()
    window.show()
    sys.exit(app.exec_())
