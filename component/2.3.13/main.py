import sys

from PyQt5.QtWidgets import QApplication, QHeaderView, QTableWidgetItem, QWidget
from ui_main import Ui_MapColoringForm

from solver import REGIONS, color_map


class MapColoringApp(QWidget, Ui_MapColoringForm):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

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
