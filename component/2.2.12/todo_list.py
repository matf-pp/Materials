import sys, json
from PyQt5 import QtWidgets, uic
from PyQt5.QtWidgets import QMessageBox

class ToDoList(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi('todo_list.ui', self)

        # Connect buttons
        self.btnAdd.clicked.connect(self.add_task)
        self.btnDelete.clicked.connect(self.delete_task)

        # Load tasks if file exists
        self.file_name = "tasks.json"
        self.load_tasks()

    def add_task(self):
        task_text = self.lineTask.text().strip()
        if task_text:
            self.listTasks.addItem(task_text)
            self.lineTask.clear()
            self.save_tasks()
        else:
            QMessageBox.warning(self, "Warning", "Task cannot be empty!")

    def delete_task(self):
        selected_items = self.listTasks.selectedItems()
        if not selected_items:
            QMessageBox.warning(self, "Warning", "No task selected!")
            return
        for item in selected_items:
            self.listTasks.takeItem(self.listTasks.row(item))
        self.save_tasks()

    def save_tasks(self):
        tasks = [self.listTasks.item(i).text() for i in range(self.listTasks.count())]
        with open(self.file_name, 'w') as f:
            json.dump(tasks, f)

    def load_tasks(self):
        try:
            with open(self.file_name, 'r') as f:
                tasks = json.load(f)
                self.listTasks.addItems(tasks)
        except FileNotFoundError:
            pass

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = ToDoList()
    window.show()
    sys.exit(app.exec_())

