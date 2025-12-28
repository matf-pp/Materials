import sys
import random
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5 import uic


class GuessNumber(QWidget):
    def __init__(self):
        super().__init__()

        uic.loadUi("guess_number.ui", self)

        self.secret_number = random.randint(1, 100)
        self.attempts = 0
        self.max_attempts = 10

        self.buttonGuess.clicked.connect(self.check_number)

    def check_number(self):
        if self.attempts >= self.max_attempts:
            return

        try:
            user_number = int(self.lineEditNumber.text())
        except ValueError:
            self.labelResult.setText("Unesi ceo broj!")
            return

        self.attempts += 1
        self.labelAttempts.setText(
            f"Pokušaji: {self.attempts} / {self.max_attempts}"
        )

        if user_number < self.secret_number:
            self.labelResult.setText("Premalo je")
        elif user_number > self.secret_number:
            self.labelResult.setText("Previše je")
        else:
            self.labelResult.setText("Tačno!")
            self.buttonGuess.setEnabled(False)
            return

        if self.attempts == self.max_attempts:
            self.labelResult.setText(
                f"Kraj igre! Broj je bio {self.secret_number}"
            )
            self.buttonGuess.setEnabled(False)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = GuessNumber()
    window.show()
    sys.exit(app.exec_())

