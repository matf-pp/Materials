import sys

from PyQt5.QtWidgets import QApplication, QWidget
from ui_main import Ui_MagicSequenceForm

from solver import magic_sequences


class MagicSequenceApp(QWidget, Ui_MagicSequenceForm):
    def __init__(self):
        super().__init__()
        self.setupUi(self)
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
