# main.py
import sys
from PyQt5.QtWidgets import QApplication
from PyQt5.QtSvg import QSvgWidget
from PyQt5.QtCore import Qt
from ui_main import GameUI


class BulletGame(GameUI):
    def __init__(self):
        super().__init__()

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            self.shoot(event.x(), event.y())

    def shoot(self, x, y):
        hole = QSvgWidget("hole.svg", self)

        size = 40
        hole.setFixedSize(size, size)

        # Centriranje rupe na mjesto klika
        hole.move(x - size // 2, y - size // 2)
        hole.show()


if __name__ == "__main__":
    app = QApplication(sys.argv)
    game = BulletGame()
    game.show()
    sys.exit(app.exec_())

