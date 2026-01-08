import sys
from PyQt5 import QtWidgets, uic

class MainWindow(QtWidgets.QMainWindow):
    def __init__(self):
        super().__init__()

        uic.loadUi("main.ui", self)

        # Connect buttons
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

