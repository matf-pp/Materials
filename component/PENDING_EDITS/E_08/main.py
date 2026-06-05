import sys
from pathlib import Path

from PyQt5 import uic
from PyQt5.QtWidgets import QApplication, QWidget

from solver import magic_sequences


class MagicSequenceApp(QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi(str(Path(__file__).with_name("main.ui")), self)
        self.buttonSolve.clicked.connect(self.solve)
        self.solve()

    def solve(self):
        n = self.spinLength.value()
        solutions = magic_sequences(n)

        self.listSolutions.clear()
        for solution in solutions:
            self.listSolutions.addItem(str(solution))

        if solutions:
            self.labelStatus.setText(f"Broj resenja: {len(solutions)}")
        else:
            self.labelStatus.setText(f"Ne postoji magicna sekvenca duzine {n}.")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MagicSequenceApp()
    window.show()
    sys.exit(app.exec_())
