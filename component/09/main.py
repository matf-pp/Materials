import sys
from PyQt5 import QtWidgets, uic

# Load the UI
class GridWindow(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi('main.ui', self)  # Load the .ui file

        # Optional: connect buttons to actions
        self.button1.clicked.connect(lambda: print("Button 1 clicked"))
        self.button2.clicked.connect(lambda: print("Button 2 clicked"))
        self.button3.clicked.connect(lambda: print("Button 3 clicked"))
        self.button4.clicked.connect(lambda: print("Button 4 clicked"))
        self.button5.clicked.connect(lambda: print("Button 5 clicked"))

# Run the application
app = QtWidgets.QApplication(sys.argv)
window = GridWindow()
window.show()
sys.exit(app.exec_())

