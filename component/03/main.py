import sys
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5 import uic

def fibonacci_generator():
    a, b = 0, 1
    while True:
        yield a
        a, b = b, a + b

class FibonacciApp(QWidget):
    def __init__(self):
        super().__init__()

        # Load UI
        uic.loadUi("main.ui", self)

        # Create generator
        self.fib_gen = fibonacci_generator()

        # Connect button
        self.btnNext.clicked.connect(self.next_fibonacci)

    def next_fibonacci(self):
        value = next(self.fib_gen)
        self.textOutput.append(str(value))

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = FibonacciApp()
    window.show()
    sys.exit(app.exec_())

