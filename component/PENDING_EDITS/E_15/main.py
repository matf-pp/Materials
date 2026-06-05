import sys
from pathlib import Path

from PyQt5 import uic
from PyQt5.QtWidgets import QApplication, QHeaderView, QTableWidgetItem, QWidget

from solver import REGIONS, color_map


class MapColoringApp(QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi(str(Path(__file__).with_name("main.ui")), self)

        self.tableSolutions.setColumnCount(len(REGIONS))
        self.tableSolutions.setHorizontalHeaderLabels(REGIONS)
        self.tableSolutions.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableSolutions.verticalHeader().setVisible(False)
        self.tableSolutions.verticalHeader().setDefaultSectionSize(20)
        self.tableSolutions.setAlternatingRowColors(True)

        self.buttonSolve.clicked.connect(self.solve)
        self.solve()

    def solve(self):
        solutions = color_map(self.spinColors.value())
        self.tableSolutions.setRowCount(len(solutions))
        for row, solution in enumerate(solutions):
            for column, region in enumerate(REGIONS):
                self.tableSolutions.setItem(
                    row, column, QTableWidgetItem(solution[region])
                )

        self.labelStatus.setText(f"Broj bojenja: {len(solutions)}")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MapColoringApp()
    window.show()
    sys.exit(app.exec_())
