# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys
from PyQt5 import QtWidgets
from ui_main import Ui_MainWindow

class MainWindow(QtWidgets.QMainWindow, Ui_MainWindow):
    def __init__(self):
        super().__init__()

        self.setupUi(self)

        # Povezujemo dugmad sa funkcijama
        self.btn_one.clicked.connect(lambda: self.update_status("Button One clicked"))
        self.btn_two.clicked.connect(lambda: self.update_status("Button Two clicked"))
        self.btn_three.clicked.connect(lambda: self.update_status("Button Three clicked"))

        self.btn_ok.clicked.connect(self.ok_clicked)
        self.btn_cancel.clicked.connect(self.close)

    def update_status(self, text):
        self.label_status.setText(f"Status: {text}")

    def ok_clicked(self):
        self.label_status.setText("Status: OK pressed")

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())
