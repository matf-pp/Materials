import sys

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import (
    QApplication,
    QHeaderView,
    QMessageBox,
    QTableWidgetItem,
    QWidget,
)
from ui_main import Ui_SudokuForm

from solver import solve_sudoku


EXAMPLE_GRID = [
    [6, 3, 8, 7, 0, 0, 0, 0, 0],
    [0, 2, 7, 0, 0, 0, 5, 0, 0],
    [5, 4, 0, 6, 0, 9, 2, 0, 0],
    [0, 1, 0, 5, 9, 0, 3, 0, 0],
    [0, 0, 9, 0, 7, 0, 4, 0, 0],
    [0, 0, 6, 0, 2, 4, 0, 1, 0],
    [0, 0, 5, 3, 0, 1, 0, 2, 7],
    [0, 0, 2, 0, 0, 0, 8, 3, 0],
    [0, 0, 0, 0, 0, 7, 9, 4, 5],
]


class SudokuApp(QWidget, Ui_SudokuForm):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        self.tableGrid.setRowCount(9)
        self.tableGrid.setColumnCount(9)
        self.tableGrid.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableGrid.verticalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableGrid.horizontalHeader().setVisible(False)
        self.tableGrid.verticalHeader().setVisible(False)

        self.buttonExample.clicked.connect(self.load_example)
        self.buttonSolve.clicked.connect(self.solve)
        self.buttonClear.clicked.connect(self.clear_grid)
        self.load_example()

    def load_example(self):
        self.set_grid(EXAMPLE_GRID)
        self.labelStatus.setText("Ucitan je primer.")

    def clear_grid(self):
        self.set_grid([[0] * 9 for _ in range(9)])
        self.labelStatus.setText("Tabela je ociscena.")

    def solve(self):
        try:
            grid = self.read_grid()
        except ValueError as error:
            QMessageBox.warning(self, "Neispravan unos", str(error))
            return

        solution = solve_sudoku(grid)
        if solution is None:
            self.labelStatus.setText("Uneta tabla nema resenje.")
            QMessageBox.information(self, "Sudoku", "Uneta tabla nema resenje.")
            return

        self.set_grid(solution)
        self.labelStatus.setText("Sudoku je resen.")

    def read_grid(self):
        grid = []
        for row in range(9):
            values = []
            for column in range(9):
                item = self.tableGrid.item(row, column)
                text = item.text().strip() if item else ""
                if text == "":
                    values.append(0)
                    continue
                if not text.isdigit():
                    raise ValueError("Polja smeju da sadrze samo cifre od 1 do 9.")
                value = int(text)
                if value < 1 or value > 9:
                    raise ValueError("Polja smeju da sadrze samo cifre od 1 do 9.")
                values.append(value)
            grid.append(values)
        return grid

    def set_grid(self, grid):
        for row in range(9):
            for column in range(9):
                value = grid[row][column]
                item = QTableWidgetItem("" if value == 0 else str(value))
                item.setTextAlignment(Qt.AlignCenter)
                self.tableGrid.setItem(row, column, item)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = SudokuApp()
    window.show()
    sys.exit(app.exec_())
