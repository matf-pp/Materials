import sys
from PyQt5.QtWidgets import QApplication, QMainWindow
from PyQt5 import uic

class PalindromeChecker(QMainWindow):
    def __init__(self):
        super().__init__()
        # Učitavanje UI fajla
        uic.loadUi("palindrome.ui", self)

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

