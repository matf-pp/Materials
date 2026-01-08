import sys
from PyQt5 import QtWidgets, uic

class Calculator(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi('calculator.ui', self)

        # Connect number buttons
        for i in range(10):
            getattr(self, f'btn{i}').clicked.connect(lambda checked, x=i: self.add_to_display(str(x)))

        # Connect operators
        self.btnAdd.clicked.connect(lambda: self.add_to_display('+'))
        self.btnSub.clicked.connect(lambda: self.add_to_display('-'))
        self.btnMul.clicked.connect(lambda: self.add_to_display('*'))
        self.btnDiv.clicked.connect(lambda: self.add_to_display('/'))
        self.btnDot.clicked.connect(lambda: self.add_to_display('.'))

        # Connect equal and clear
        self.btnEq.clicked.connect(self.calculate)
        self.btnClear.clicked.connect(self.clear_display)

    def add_to_display(self, value):
        self.display.setText(self.display.text() + value)

    def calculate(self):
        try:
            result = str(eval(self.display.text()))
            self.display.setText(result)
        except Exception:
            self.display.setText("Error")

    def clear_display(self):
        self.display.clear()

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = Calculator()
    window.show()
    sys.exit(app.exec_())

