import sys, random
from PyQt5.QtWidgets import QApplication, QMainWindow, QMessageBox
from PyQt5 import uic
from PyQt5.QtCore import QTimer

class QuickReflexGame(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("quick_reflex.ui", self)

        self.score = 0
        self.time_left = 10  # 10 sekundi igre

        # Timer za igru (svakih 0.5 s menja poziciju dugmeta)
        self.move_timer = QTimer()
        self.move_timer.timeout.connect(self.move_button)

        # Timer za odbrojavanje vremena
        self.countdown_timer = QTimer()
        self.countdown_timer.timeout.connect(self.update_time)
        self.countdown_timer.start(1000)  # svaka sekunda

        self.btn_click.clicked.connect(self.button_clicked)

        # Start igre odmah
        self.move_timer.start(500)

    def move_button(self):
        # Dobavljanje dimenzija prozora
        w = self.centralWidget().width() - self.btn_click.width()
        h = self.centralWidget().height() - self.btn_click.height()

        x = random.randint(0, max(0, w))
        y = random.randint(0, max(0, h))
        self.btn_click.move(x, y)

    def button_clicked(self):
        self.score += 1
        self.lbl_score.setText(f"Rezultat: {self.score}")

    def update_time(self):
        self.time_left -= 1
        self.lbl_time.setText(f"Vreme: {self.time_left}")
        if self.time_left <= 0:
            self.move_timer.stop()
            self.countdown_timer.stop()
            QMessageBox.information(self, "Kraj igre", f"Igra je završena! Rezultat: {self.score}")

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = QuickReflexGame()
    window.show()
    sys.exit(app.exec_())

