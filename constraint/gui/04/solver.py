def solve_n_queens(n):
    if n < 1:
        raise ValueError("Dimenzija table mora biti pozitivna.")

    solutions = []
    queens = []
    used_columns = set()
    used_main_diagonals = set()
    used_side_diagonals = set()

    def search(row):
        if row == n:
            solutions.append(tuple(queens))
            return

        for column in range(n):
            main_diagonal = row - column
            side_diagonal = row + column
            if (
                column in used_columns
                or main_diagonal in used_main_diagonals
                or side_diagonal in used_side_diagonals
            ):
                continue

            queens.append(column)
            used_columns.add(column)
            used_main_diagonals.add(main_diagonal)
            used_side_diagonals.add(side_diagonal)

            search(row + 1)

            queens.pop()
            used_columns.remove(column)
            used_main_diagonals.remove(main_diagonal)
            used_side_diagonals.remove(side_diagonal)

    search(0)
    return solutions


def board_from_solution(solution):
    n = len(solution)
    return [
        ["D" if solution[row] == column else "" for column in range(n)]
        for row in range(n)
    ]
