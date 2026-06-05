import sys, random
from PyQt5.QtWidgets import QApplication, QMainWindow, QMessageBox
from PyQt5 import uic

class HangmanGame(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("hangman.ui", self)

        # Lista reči
        self.words = ["python", "programiranje", "kompjuter", "aplikacija", "tehnologija"]
        self.word = random.choice(self.words).lower()
        self.guessed_letters = set()
        self.errors = 0
        self.max_errors = 6

        # Inicijalni prikaz reči
        self.update_display()

        # Povezivanje dugmeta
        self.btn_guess.clicked.connect(self.guess_letter)

    def update_display(self):
        display_word = " ".join([c if c in self.guessed_letters else "_" for c in self.word])
        self.lbl_word.setText(display_word)
        self.lbl_errors.setText(f"Greške: {self.errors}")

    def guess_letter(self):
        letter = self.le_letter.text().strip().lower()
        self.le_letter.clear()

        if not letter or len(letter) != 1 or not letter.isalpha():
            QMessageBox.warning(self, "Greška", "Unesite jedno slovo")
            return

        if letter in self.guessed_letters:
            QMessageBox.information(self, "Info", "Već ste pogodili ovo slovo")
            return

        self.guessed_letters.add(letter)

        if letter not in self.word:
            self.errors += 1

        self.update_display()
        self.check_game_over()

    def check_game_over(self):
        if all(c in self.guessed_letters for c in self.word):
            QMessageBox.information(self, "Pobeda", f"Čestitamo! Pogodili ste reč: {self.word}")
            self.reset_game()
        elif self.errors >= self.max_errors:
            QMessageBox.information(self, "Poraz", f"Igra je završena! Reč je bila: {self.word}")
            self.reset_game()

    def reset_game(self):
        self.word = random.choice(self.words).lower()
        self.guessed_letters = set()
        self.errors = 0
        self.update_display()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = HangmanGame()
    window.show()
    sys.exit(app.exec_())

