import sys
from PyQt5 import QtWidgets
from ui_main import Ui_MainWindow

class MainWindow(QtWidgets.QMainWindow, Ui_MainWindow):
    def __init__(self):
        super().__init__()

        self.setupUi(self)

        # Connect button click to function
        self.pushButton.clicked.connect(self.on_button_click)

    def on_button_click(self):
        self.label.setText("Button clicked!")

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())
