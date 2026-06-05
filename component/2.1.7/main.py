import sys
from PyQt5 import QtWidgets, uic
from PyQt5.QtWidgets import QButtonGroup

class MainWindow(QtWidgets.QMainWindow):
    def __init__(self):
        super().__init__()

        uic.loadUi("main.ui", self)

        # Create a button group (recommended)
        self.group = QButtonGroup(self)
        self.group.addButton(self.radio_A)
        self.group.addButton(self.radio_B)
        self.group.addButton(self.radio_C)

        # Optional: assign IDs
        self.group.setId(self.radio_A, 1)
        self.group.setId(self.radio_B, 2)
        self.group.setId(self.radio_C, 3)

        # Connect signal
        self.group.buttonToggled.connect(self.on_radio_toggled)

    def on_radio_toggled(self, button, checked):
        if checked:
            self.label_result.setText(f"Selected: {button.text()}")

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())

