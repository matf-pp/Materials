# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QButtonGroup
from ui_main import Ui_MainWindow

class MainWindow(QtWidgets.QMainWindow, Ui_MainWindow):
    def __init__(self):
        super().__init__()

        self.setupUi(self)

        # Pravimo grupu dugmadi da se izbori medjusobno iskljucuju
        self.group = QButtonGroup(self)
        self.group.addButton(self.radio_A)
        self.group.addButton(self.radio_B)
        self.group.addButton(self.radio_C)

        # Dodeljujemo identifikatore za lakse citanje izbora
        self.group.setId(self.radio_A, 1)
        self.group.setId(self.radio_B, 2)
        self.group.setId(self.radio_C, 3)

        # Povezujemo signal sa funkcijom
        self.group.buttonToggled.connect(self.on_radio_toggled)

    def on_radio_toggled(self, button, checked):
        if checked:
            self.label_result.setText(f"Selected: {button.text()}")

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())
