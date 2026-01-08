from PyQt5.QtWidgets import (
    QWidget, QVBoxLayout, QTableWidget, QTableWidgetItem, QPushButton
)

class Ui_MainWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.setupUi()

    def setupUi(self):
        self.setWindowTitle("Editable Table Example")
        self.resize(500, 300)

        layout = QVBoxLayout()

        # Create table
        self.table = QTableWidget(5, 3)  # 5 rows, 3 columns
        self.table.setHorizontalHeaderLabels(["Name", "Age", "Grade"])

        # Fill with sample data
        data = [
            ("Alice", "14", "A"),
            ("Bob", "15", "B"),
            ("Charlie", "13", "A"),
            ("Daisy", "14", "C"),
            ("Evan", "15", "B"),
        ]

        for row, row_data in enumerate(data):
            for col, value in enumerate(row_data):
                self.table.setItem(row, col, QTableWidgetItem(value))

        # Button
        self.printButton = QPushButton("Print Table Data")

        layout.addWidget(self.table)
        layout.addWidget(self.printButton)

        self.setLayout(layout)

