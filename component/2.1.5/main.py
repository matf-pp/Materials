# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys
from PyQt5 import QtWidgets
from PyQt5.QtCore import Qt
from ui_main import Ui_MainWindow

class MainWindow(QtWidgets.QMainWindow, Ui_MainWindow):
    def __init__(self):
        super().__init__()

        self.setupUi(self)

        # Povezujemo signale cek-boksova
        self.checkBox_A.stateChanged.connect(self.on_state_changed)
        self.checkBox_B.stateChanged.connect(self.on_state_changed)

        # Prikazujemo i signal promene stanja
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
            self.label_info.setText(f"{sender.text()} checked")
        else:
            self.label_info.setText(f"{sender.text()} unchecked")

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
