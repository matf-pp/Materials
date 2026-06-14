# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys
from PyQt5 import QtWidgets
from ui_main import Ui_GridWindow

# Ucitavamo generisanu UI klasu
class GridWindow(QtWidgets.QWidget, Ui_GridWindow):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        # Povezujemo dugmad sa akcijama
        self.button1.clicked.connect(lambda: print("Button 1 clicked"))
        self.button2.clicked.connect(lambda: print("Button 2 clicked"))
        self.button3.clicked.connect(lambda: print("Button 3 clicked"))
        self.button4.clicked.connect(lambda: print("Button 4 clicked"))
        self.button5.clicked.connect(lambda: print("Button 5 clicked"))

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = GridWindow()
    window.show()
    sys.exit(app.exec_())
