import sys
from PyQt5.QtWidgets import QApplication, QWidget
from ui_click_counter import Ui_ClickCounter


class ClickCounter(QWidget, Ui_ClickCounter):
    def __init__(self):
        super().__init__()

        self.setupUi(self)

        self.count = 0

        # Povezivanje dugmeta sa funkcijom
        self.buttonClick.clicked.connect(self.button_clicked)

    def button_clicked(self):
        self.count += 1
        self.labelCount.setText(f"Klikovi: {self.count}")

        if self.count >= 20:
            self.labelMessage.setText("Dovoljno!")
            self.buttonClick.setEnabled(False)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = ClickCounter()
    window.show()
    sys.exit(app.exec_())
