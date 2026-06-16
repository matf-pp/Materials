import sys

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QApplication, QHeaderView, QTableWidgetItem, QWidget
from ui_main import Ui_MagicSquareForm

from solver import magic_squares


class MagicSquareApp(QWidget, Ui_MagicSquareForm):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        self.tableSquare.setRowCount(3)
        self.tableSquare.setColumnCount(3)
        self.tableSquare.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableSquare.verticalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableSquare.horizontalHeader().setVisible(False)
        self.tableSquare.verticalHeader().setVisible(False)

        self.solutions = []
        self.current_index = 0
        self.buttonSolve.clicked.connect(self.solve)
        self.buttonPrevious.clicked.connect(self.previous_solution)
        self.buttonNext.clicked.connect(self.next_solution)
        self.solve()

    def solve(self):
        self.solutions = magic_squares()
        self.current_index = 0
        self.show_solution()

    def previous_solution(self):
        self.current_index = (self.current_index - 1) % len(self.solutions)
        self.show_solution()

    def next_solution(self):
        self.current_index = (self.current_index + 1) % len(self.solutions)
        self.show_solution()

    def show_solution(self):
        square = self.solutions[self.current_index]
        for row in range(3):
            for column in range(3):
                item = QTableWidgetItem(str(square[row][column]))
                item.setTextAlignment(Qt.AlignCenter)
                self.tableSquare.setItem(row, column, item)

        self.labelStatus.setText(
            f"Resenje {self.current_index + 1}/{len(self.solutions)}"
        )


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MagicSquareApp()
    window.show()
    sys.exit(app.exec_())
