import sys
from PyQt5 import QtWidgets, uic, QtCore, QtMultimedia, QtGui
from PyQt5.QtWidgets import QFileDialog

class MusicPlayer(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi('music_player.ui', self)

        # Initialize QMediaPlayer
        self.player = QtMultimedia.QMediaPlayer()

        # Set button icons
        self.btnPlay.setIcon(self.style().standardIcon(QtWidgets.QStyle.SP_MediaPlay))
        self.btnPause.setIcon(self.style().standardIcon(QtWidgets.QStyle.SP_MediaPause))
        self.btnStop.setIcon(self.style().standardIcon(QtWidgets.QStyle.SP_MediaStop))

        # Make icons fill the buttons
        self.btnPlay.setIconSize(QtCore.QSize(40, 40))
        self.btnPause.setIconSize(QtCore.QSize(40, 40))
        self.btnStop.setIconSize(QtCore.QSize(40, 40))

        # Optional: make buttons square
        self.btnPlay.setFixedSize(50, 50)
        self.btnPause.setFixedSize(50, 50)
        self.btnStop.setFixedSize(50, 50)

        # Connect buttons
        self.btnLoad.clicked.connect(self.load_music)
        self.btnPlay.clicked.connect(self.play_music)
        self.btnPause.clicked.connect(self.pause_music)
        self.btnStop.clicked.connect(self.stop_music)

        # Slider connections
        self.slider.sliderMoved.connect(self.set_position)
        self.player.positionChanged.connect(self.update_slider)
        self.player.durationChanged.connect(self.set_slider_range)

    def load_music(self):
        file, _ = QFileDialog.getOpenFileName(self, "Open Music File", "", "Audio Files (*.mp3 *.wav)")
        if file:
            self.player.setMedia(QtMultimedia.QMediaContent(QtCore.QUrl.fromLocalFile(file)))
            self.label.setText(file.split('/')[-1])

    def play_music(self):
        self.player.play()

    def pause_music(self):
        self.player.pause()

    def stop_music(self):
        self.player.stop()

    def set_position(self, position):
        self.player.setPosition(position)

    def update_slider(self, position):
        self.slider.setValue(position)

    def set_slider_range(self, duration):
        self.slider.setRange(0, duration)

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MusicPlayer()
    window.show()
    sys.exit(app.exec_())

