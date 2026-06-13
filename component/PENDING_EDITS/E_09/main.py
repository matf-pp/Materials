import sys

from PyQt5.QtWidgets import QApplication, QHeaderView, QTableWidgetItem, QWidget
from ui_main import Ui_CryptarithmForm

from solver import LETTERS, solve_see_you_soon


class CryptarithmApp(QWidget, Ui_CryptarithmForm):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        self.tableSolutions.setColumnCount(len(LETTERS) + 1)
        self.tableSolutions.setHorizontalHeaderLabels(list(LETTERS) + ["Jednacina"])
        for column in range(len(LETTERS)):
            self.tableSolutions.horizontalHeader().setSectionResizeMode(
                column, QHeaderView.ResizeToContents
            )
        self.tableSolutions.horizontalHeader().setSectionResizeMode(
            len(LETTERS), QHeaderView.Stretch
        )
        self.tableSolutions.verticalHeader().setVisible(False)
        self.tableSolutions.verticalHeader().setDefaultSectionSize(22)
        self.tableSolutions.setAlternatingRowColors(True)

        self.buttonSolve.clicked.connect(self.solve)
        self.solve()

    def solve(self):
        solutions = solve_see_you_soon()
        self.tableSolutions.setRowCount(len(solutions))

        for row, solution in enumerate(solutions):
            assignment = solution["assignment"]
            for column, letter in enumerate(LETTERS):
                self.tableSolutions.setItem(
                    row, column, QTableWidgetItem(str(assignment[letter]))
                )
            self.tableSolutions.setItem(
                row, len(LETTERS), QTableWidgetItem(solution["equation"])
            )

        self.labelStatus.setText(f"Broj resenja: {len(solutions)}")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = CryptarithmApp()
    window.show()
    sys.exit(app.exec_())
