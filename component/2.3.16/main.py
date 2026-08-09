import sys
from pathlib import Path

from solver import APOENI, kombinacije_novcica, ukupno_novcica


COLUMNS = ["1 din", "2 din", "5 din", "10 din", "Ukupno"]


def print_solutions(solutions):
    # Poravnata tabela, isto kao u grafickom interfejsu.
    sirine = [len(naslov) for naslov in COLUMNS]

    def red(polja):
        return "  ".join(p.rjust(s) for p, s in zip(polja, sirine))

    print(red(COLUMNS))
    for solution in solutions:
        polja = [str(solution[apoen]) for apoen in APOENI]
        polja.append(str(ukupno_novcica(solution)))
        print(red(polja))
    print(f"Broj resenja: {len(solutions)}")


def build_window():
    from PyQt5 import uic
    from PyQt5.QtCore import Qt
    from PyQt5.QtWidgets import (
        QHeaderView,
        QTableWidgetItem,
        QWidget,
    )

    IZNOS = 10

    class NovciciApp(QWidget):
        def __init__(self):
            super().__init__()
            uic.loadUi(str(Path(__file__).with_name("main.ui")), self)

            self.tableSolutions.setColumnCount(len(COLUMNS))
            self.tableSolutions.setHorizontalHeaderLabels(COLUMNS)
            for column in range(len(COLUMNS)):
                self.tableSolutions.horizontalHeader().setSectionResizeMode(
                    column, QHeaderView.Stretch
                )
            self.tableSolutions.verticalHeader().setVisible(False)
            self.tableSolutions.verticalHeader().setDefaultSectionSize(22)
            self.tableSolutions.setAlternatingRowColors(True)
            self.tableSolutions.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOn)

            self.buttonSolve.clicked.connect(self.solve)
            self.solve()

        def solve(self):
            solutions = kombinacije_novcica(IZNOS)
            self.tableSolutions.setRowCount(len(solutions))

            for row, solution in enumerate(solutions):
                for column, apoen in enumerate(APOENI):
                    self.tableSolutions.setItem(
                        row, column, QTableWidgetItem(str(solution[apoen]))
                    )
                self.tableSolutions.setItem(
                    row,
                    len(APOENI),
                    QTableWidgetItem(str(ukupno_novcica(solution))),
                )

            if solutions:
                self.labelStatus.setText(f"Broj resenja: {len(solutions)}")
            else:
                self.labelStatus.setText("Nema resenja.")

    return NovciciApp()


def run_gui():
    from PyQt5.QtWidgets import QApplication

    app = QApplication(sys.argv)
    window = build_window()
    window.show()
    sys.exit(app.exec_())


if __name__ == "__main__":
    if "--console" in sys.argv:
        print_solutions(kombinacije_novcica(10))
    else:
        run_gui()
