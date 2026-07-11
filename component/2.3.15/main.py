import sys
from pathlib import Path

from solver import magicni_kvadrati


KVADRATA_PO_REDU = 4


def print_solutions(kvadrati):
    # Kvadrate prikazujemo jedan pored drugog, isto kao u grafickom interfejsu.
    for pocetak in range(0, len(kvadrati), KVADRATA_PO_REDU):
        grupa = kvadrati[pocetak:pocetak + KVADRATA_PO_REDU]
        for vrsta in range(3):
            print("   ".join(
                " ".join(str(broj) for broj in kvadrat[vrsta]) for kvadrat in grupa
            ))
        print()
    print(f"Broj resenja: {len(kvadrati)}")


def build_window():
    from PyQt5 import uic
    from PyQt5.QtCore import Qt
    from PyQt5.QtWidgets import (
        QAbstractItemView,
        QTableWidget,
        QTableWidgetItem,
        QWidget,
    )

    CELIJA = 32

    def napravi_kvadrat(kvadrat):
        tabela = QTableWidget(3, 3)
        tabela.horizontalHeader().setVisible(False)
        tabela.verticalHeader().setVisible(False)
        tabela.setEditTriggers(QAbstractItemView.NoEditTriggers)
        tabela.setSelectionMode(QAbstractItemView.NoSelection)
        tabela.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        tabela.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        for i in range(3):
            tabela.setRowHeight(i, CELIJA)
            tabela.setColumnWidth(i, CELIJA)
        for vrsta in range(3):
            for kolona in range(3):
                stavka = QTableWidgetItem(str(kvadrat[vrsta][kolona]))
                stavka.setTextAlignment(Qt.AlignCenter)
                tabela.setItem(vrsta, kolona, stavka)
        tabela.setFixedSize(3 * CELIJA + 2, 3 * CELIJA + 2)
        return tabela

    class MagicniKvadratApp(QWidget):
        def __init__(self):
            super().__init__()
            uic.loadUi(str(Path(__file__).with_name("main.ui")), self)
            self.scrollArea.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOn)
            self.buttonSolve.clicked.connect(self.solve)
            self.solve()

        def solve(self):
            while self.gridLayout.count():
                stavka = self.gridLayout.takeAt(0)
                widget = stavka.widget()
                if widget is not None:
                    widget.deleteLater()

            kvadrati = magicni_kvadrati()
            for indeks, kvadrat in enumerate(kvadrati):
                red, kolona = divmod(indeks, KVADRATA_PO_REDU)
                self.gridLayout.addWidget(
                    napravi_kvadrat(kvadrat), red, kolona, Qt.AlignCenter
                )

            self.labelStatus.setText(f"Broj resenja: {len(kvadrati)}")

    return MagicniKvadratApp()


def run_gui():
    from PyQt5.QtWidgets import QApplication

    app = QApplication(sys.argv)
    window = build_window()
    window.show()
    sys.exit(app.exec_())


if __name__ == "__main__":
    if "--console" in sys.argv:
        print_solutions(magicni_kvadrati())
    else:
        run_gui()
