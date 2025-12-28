import sys
from PyQt5 import QtWidgets, uic
from PyQt5.QtCore import Qt

class MainWindow(QtWidgets.QMainWindow):
    def __init__(self):
        super().__init__()

        uic.loadUi("main.ui", self)

        # Connect checkbox signals
        self.checkBox_A.stateChanged.connect(self.on_state_changed)
        self.checkBox_B.stateChanged.connect(self.on_state_changed)

        # Also show toggled signal
        self.checkBox_A.toggled.connect(self.on_toggled)
        self.checkBox_B.toggled.connect(self.on_toggled)

    def on_state_changed(self, state):
        """
        state values:
        Qt.Unchecked = 0
        Qt.PartiallyChecked = 1
        Qt.Checked = 2
        """
        sender = self.sender()
        if state == Qt.Checked:
            self.label_info.setText(f"{sender.text()} checked (stateChanged)")
        else:
            self.label_info.setText(f"{sender.text()} unchecked (stateChanged)")

    def on_toggled(self, checked):
        """
        checked values:
        True / False
        """
        sender = self.sender()
        print(f"{sender.text()} toggled: {checked}")

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())

