import sys
import random
import string
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5 import uic


class PasswordGenerator(QWidget):
    def __init__(self):
        super().__init__()

        uic.loadUi("password_generator.ui", self)

        self.buttonGenerate.clicked.connect(self.generate_password)

    def generate_password(self):
        characters = string.ascii_letters + string.digits
        password = "".join(random.choice(characters) for _ in range(8))
        self.labelPassword.setText(password)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = PasswordGenerator()
    window.show()
    sys.exit(app.exec_())

