import sys

from PyQt5.QtWidgets import QApplication, QHeaderView, QTableWidgetItem, QWidget
from ui_main import Ui_CoinChangeForm

from solver import COINS, coin_combinations


class CoinChangeApp(QWidget, Ui_CoinChangeForm):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        self.tableResults.setColumnCount(len(COINS) + 1)
        self.tableResults.setHorizontalHeaderLabels(
            [f"{coin} din" for coin in COINS] + ["Ukupno"]
        )
        self.tableResults.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        self.tableResults.verticalHeader().setVisible(False)
        self.tableResults.verticalHeader().setDefaultSectionSize(20)
        self.tableResults.setAlternatingRowColors(True)

        self.buttonSolve.clicked.connect(self.solve)
        self.solve()

    def solve(self):
        amount = self.spinAmount.value()
        combinations = coin_combinations(amount)

        self.tableResults.setRowCount(len(combinations))
        for row, combination in enumerate(combinations):
            total = 0
            for column, coin in enumerate(COINS):
                count = combination[coin]
                total += count * coin
                self.tableResults.setItem(row, column, QTableWidgetItem(str(count)))
            self.tableResults.setItem(row, len(COINS), QTableWidgetItem(str(total)))

        self.labelStatus.setText(f"Broj resenja: {len(combinations)}")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = CoinChangeApp()
    window.show()
    sys.exit(app.exec_())
