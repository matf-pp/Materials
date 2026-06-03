DIGITS = set(range(1, 10))


def solve_sudoku(grid):
    board = [row[:] for row in grid]
    rows = [set() for _ in range(9)]
    columns = [set() for _ in range(9)]
    boxes = [set() for _ in range(9)]

    for row in range(9):
        for column in range(9):
            value = board[row][column]
            if value == 0:
                continue
            if value < 1 or value > 9:
                return None
            box = box_index(row, column)
            if value in rows[row] or value in columns[column] or value in boxes[box]:
                return None
            rows[row].add(value)
            columns[column].add(value)
            boxes[box].add(value)

    def candidates(row, column):
        return DIGITS - rows[row] - columns[column] - boxes[box_index(row, column)]

    def find_cell():
        best_cell = None
        best_candidates = None
        for row in range(9):
            for column in range(9):
                if board[row][column] != 0:
                    continue
                cell_candidates = candidates(row, column)
                if not cell_candidates:
                    return (row, column), set()
                if best_candidates is None or len(cell_candidates) < len(best_candidates):
                    best_cell = (row, column)
                    best_candidates = cell_candidates
        return best_cell, best_candidates

    def search():
        cell, cell_candidates = find_cell()
        if cell is None:
            return True

        row, column = cell
        box = box_index(row, column)
        for value in sorted(cell_candidates):
            board[row][column] = value
            rows[row].add(value)
            columns[column].add(value)
            boxes[box].add(value)

            if search():
                return True

            rows[row].remove(value)
            columns[column].remove(value)
            boxes[box].remove(value)
            board[row][column] = 0

        return False

    return board if search() else None


def box_index(row, column):
    return (row // 3) * 3 + column // 3
