import sys
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5.QtCore import QTimer
from PyQt5 import uic


class TimerApp(QWidget):
    def __init__(self):
        super().__init__()

        uic.loadUi("timer.ui", self)

        self.seconds = 0

        self.timer = QTimer()
        self.timer.setInterval(1000)  # 1 sekunda
        self.timer.timeout.connect(self.update_time)

        self.buttonStart.clicked.connect(self.start_timer)
        self.buttonStop.clicked.connect(self.stop_timer)

    def update_time(self):
        self.seconds += 1
        self.labelTime.setText(f"{self.seconds} s")

    def start_timer(self):
        self.timer.start()

    def stop_timer(self):
        self.timer.stop()


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = TimerApp()
    window.show()
    sys.exit(app.exec_())

