import sys
from PyQt5.QtWidgets import QApplication, QMainWindow
from PyQt5 import uic


class LoginApp(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("main.ui", self)

        self.loginButton.clicked.connect(self.login)

    def login(self):
        username = self.usernameEdit.text()
        password = self.passwordEdit.text()

        # Simple in-memory check (example only)
        if username == "admin" and password == "1234":
            self.statusLabel.setText("Login successful")
            self.statusLabel.setStyleSheet("color: green;")
        else:
            self.statusLabel.setText("Invalid username or password")
            self.statusLabel.setStyleSheet("color: red;")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    win = LoginApp()
    win.show()
    sys.exit(app.exec_())

