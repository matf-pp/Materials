import sys
import string
import secrets

from PyQt5.QtWidgets import QApplication, QWidget, QMessageBox
from PyQt5.uic import loadUi


class PasswordGenerator(QWidget):
    def __init__(self):
        super().__init__()
        loadUi("password_generator.ui", self)

        self.generateButton.clicked.connect(self.generate_password)

    def generate_password(self):
        length = self.lengthSpinBox.value()
        chars = ""

        if self.lowerCheckBox.isChecked():
            chars += string.ascii_lowercase
        if self.upperCheckBox.isChecked():
            chars += string.ascii_uppercase
        if self.digitsCheckBox.isChecked():
            chars += string.digits
        if self.symbolsCheckBox.isChecked():
            chars += string.punctuation

        if not chars:
            QMessageBox.warning(self, "Error", "Select at least one character type.")
            return

        password = "".join(secrets.choice(chars) for _ in range(length))
        self.passwordLineEdit.setText(password)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = PasswordGenerator()
    window.show()
    sys.exit(app.exec_())

