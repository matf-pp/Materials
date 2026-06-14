# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys
from PyQt5.QtWidgets import QApplication, QWidget
from ui_main import Ui_Form

def fibonacci_generator():
    a, b = 0, 1
    while True:
        yield a
        a, b = b, a + b

class FibonacciApp(QWidget, Ui_Form):
    def __init__(self):
        super().__init__()

        self.setupUi(self)

        # Pravimo generator nasumicnih brojeva
        self.fib_gen = fibonacci_generator()

        # Povezujemo dugme sa funkcijom
        self.btnNext.clicked.connect(self.next_fibonacci)

    def next_fibonacci(self):
        value = next(self.fib_gen)
        self.textOutput.append(str(value))

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = FibonacciApp()
    window.show()
    sys.exit(app.exec_())
