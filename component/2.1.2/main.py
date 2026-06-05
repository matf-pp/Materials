import sys
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5.uic import loadUi
from datetime import datetime


# ----------------------------
# Base class
# ----------------------------
class BaseWindow(QWidget):
    def __init__(self):
        super().__init__()
        loadUi("main.ui", self)

        self.click_count = 0  # instance variable

        self.setWindowTitle("Base Window")
        self.button.clicked.connect(self.on_button_clicked)

    # -------- Instance Method --------
    def on_button_clicked(self):
        """
        Instance method:
        - Uses self
        - Modifies object state
        """
        self.click_count += 1
        time_str = self.get_current_time()

        self.label.setText(
            f"Clicked {self.click_count} times\nTime: {time_str}"
        )

    # -------- Static Method --------
    @staticmethod
    def format_time(dt: datetime) -> str:
        """
        Static method:
        - No access to self
        - Pure utility logic
        """
        return dt.strftime("%H:%M:%S")

    # -------- Instance Method using Static Method --------
    def get_current_time(self) -> str:
        """
        Instance method calling static method
        """
        now = datetime.now()
        return self.format_time(now)


# ----------------------------
# Child class (Inheritance)
# ----------------------------
class MainWindow(BaseWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Main Window (Child Class)")

    # Method override
    def on_button_clicked(self):
        self.click_count += 1
        time_str = self.get_current_time()

        self.label.setText(
            f"Clicks: {self.click_count}\nTime: {time_str}"
        )


# ----------------------------
# App entry
# ----------------------------
if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())

