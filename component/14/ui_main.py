# ui_main.py
from PyQt5.QtWidgets import QWidget, QVBoxLayout

class GameUI(QWidget):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("Bullet Hole Game")
        self.setFixedSize(800, 600)

        layout = QVBoxLayout(self)
        self.setLayout(layout)

