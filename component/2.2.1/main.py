# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys
from PyQt5.QtWidgets import QApplication, QMainWindow
from ui_palindrome import Ui_PalindromeChecker

class PalindromeChecker(QMainWindow, Ui_PalindromeChecker):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        # Povezivanje dugmeta sa funkcijom
        self.btn_check.clicked.connect(self.check_palindrome)

    def check_palindrome(self):
        # Uzimanje teksta iz polja za unos i čišćenje
        word = self.le_word.text().strip().lower()
        if word == word[::-1]:
            self.lbl_result.setText("Palindrom")
        else:
            self.lbl_result.setText("Nije palindrom")

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = PalindromeChecker()
    window.show()
    sys.exit(app.exec_())
