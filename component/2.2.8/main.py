import sys
from PyQt5.QtWidgets import QApplication
from ui_main import Ui_MainWindow

class MainApp(Ui_MainWindow):
    def __init__(self):
        super().__init__()
        self.printButton.clicked.connect(self.print_table_data)

    def print_table_data(self):
        rows = self.table.rowCount()
        cols = self.table.columnCount()

        print("Table Data:")
        for row in range(rows):
            row_data = []
            for col in range(cols):
                item = self.table.item(row, col)
                row_data.append(item.text() if item else "")
            print(row_data)

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainApp()
    window.show()
    sys.exit(app.exec_())

