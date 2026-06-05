import sys
from pathlib import Path

from PyQt5 import uic
from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QApplication, QHeaderView, QTableWidgetItem, QWidget

from solver import board_from_solution, solve_n_queens


class QueensApp(QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi(str(Path(__file__).with_name("main.ui")), self)

        self.solutions = []
        self.current_index = 0
        self.buttonSolve.clicked.connect(self.solve)
        self.buttonPrevious.clicked.connect(self.previous_solution)
        self.buttonNext.clicked.connect(self.next_solution)
        self.solve()

    def solve(self):
        n = self.spinSize.value()
        self.solutions = solve_n_queens(n)
        self.current_index = 0
        self.show_solution()

    def previous_solution(self):
        if not self.solutions:
            return
        self.current_index = (self.current_index - 1) % len(self.solutions)
        self.show_solution()

    def next_solution(self):
        if not self.solutions:
            return
        self.current_index = (self.current_index + 1) % len(self.solutions)
        self.show_solution()

    def show_solution(self):
        n = self.spinSize.value()
        self.tableBoard.setRowCount(n)
        self.tableBoard.setColumnCount(n)
        self.tableBoard.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableBoard.verticalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableBoard.horizontalHeader().setVisible(False)
        self.tableBoard.verticalHeader().setVisible(False)

        if not self.solutions:
            self.tableBoard.clear()
            self.labelStatus.setText("Nema resenja.")
            return

        board = board_from_solution(self.solutions[self.current_index])
        for row in range(n):
            for column in range(n):
                item = QTableWidgetItem(board[row][column])
                item.setTextAlignment(Qt.AlignCenter)
                self.tableBoard.setItem(row, column, item)

        self.labelStatus.setText(
            f"Resenje {self.current_index + 1}/{len(self.solutions)}"
        )


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = QueensApp()
    window.show()
    sys.exit(app.exec_())
