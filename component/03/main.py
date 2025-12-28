import sys
from PyQt5 import QtWidgets, uic

class MainWindow(QtWidgets.QMainWindow):
    def __init__(self):
        super().__init__()

        # Load UI
        uic.loadUi("main.ui", self)

        # Connect button
        self.pushButton.clicked.connect(self.show_selected)

    def show_selected(self):
        selected = []

        if self.checkBox_1.isChecked():
            selected.append("Option A")
        if self.checkBox_2.isChecked():
            selected.append("Option B")
        if self.checkBox_3.isChecked():
            selected.append("Option C")

        if selected:
            self.label_result.setText("Selected: " + ", ".join(selected))
        else:
            self.label_result.setText("No options selected")

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())

