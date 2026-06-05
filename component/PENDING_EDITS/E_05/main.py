import sys
from PyQt5.QtWidgets import (
    QApplication, QMainWindow, QLineEdit, QMessageBox
)
from PyQt5 import uic


class SudokuApp(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("main.ui", self)

        self.cells = []  # 9x9 QLineEdit grid
        self.init_grid()

        self.clearButton.clicked.connect(self.clear_grid)
        self.validateButton.clicked.connect(self.validate_grid)

    def init_grid(self):
        layout = self.gridWidget.layout()
        for row in range(9):
            row_cells = []
            for col in range(9):
                cell = QLineEdit()
                cell.setMaxLength(1)
                cell.setFixedSize(50, 50)
                cell.setStyleSheet("font-size: 20px;")
                layout.addWidget(cell, row, col)
                row_cells.append(cell)
            self.cells.append(row_cells)

    def clear_grid(self):
        for row in self.cells:
            for cell in row:
                cell.clear()

    def validate_grid(self):
        """Validate the Sudoku grid according to Sudoku rules."""
        board = [[cell.text() for cell in row] for row in self.cells]

        # Check cells for valid digits
        for i in range(9):
            for j in range(9):
                val = board[i][j]
                if val and (not val.isdigit() or int(val) < 1 or int(val) > 9):
                    QMessageBox.warning(
                            self, "Invalid Input", f"Cell ({i+1},{j+1}) must be 1-9"
                            )
                    return

        # Check rows
        for i in range(9):
            nums = [v for v in board[i] if v != ""]
            if len(nums) != len(set(nums)):
                QMessageBox.warning(self, "Invalid Sudoku", f"Duplicate in row {i+1}")
                return

        # Check columns
        for j in range(9):
            nums = [board[i][j] for i in range(9) if board[i][j] != ""]
            if len(nums) != len(set(nums)):
                QMessageBox.warning(self, "Invalid Sudoku", f"Duplicate in column {j+1}")
                return

        # Check 3x3 blocks
        for block_row in range(3):
            for block_col in range(3):
                nums = []
                for i in range(block_row*3, block_row*3 + 3):
                    for j in range(block_col*3, block_col*3 + 3):
                        val = board[i][j]
                        if val != "":
                            nums.append(val)
                if len(nums) != len(set(nums)):
                    QMessageBox.warning(
                            self, "Invalid Sudoku",
                            f"Duplicate in 3x3 block starting at row {block_row*3+1}, col {block_col*3+1}"
                            )
                    return

        QMessageBox.information(self, "Valid Sudoku", "No duplicates found. Grid is valid!")


if __name__ == "__main__":
    from PyQt5.QtCore import Qt

    app = QApplication(sys.argv)
    win = SudokuApp()
    win.show()
    sys.exit(app.exec_())

