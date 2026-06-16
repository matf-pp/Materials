from constraint import AllDifferentConstraint, Problem


DIGITS = range(1, 10)


def solve_sudoku(grid):
    problem = Problem()

    for row in range(9):
        for column in range(9):
            value = grid[row][column]
            if value < 1 or value > 9:
                if value != 0:
                    return None
                problem.addVariable((row, column), DIGITS)
            else:
                problem.addVariable((row, column), [value])

    for index in range(9):
        problem.addConstraint(AllDifferentConstraint(), [(index, column) for column in range(9)])
        problem.addConstraint(AllDifferentConstraint(), [(row, index) for row in range(9)])

    for box_row in range(0, 9, 3):
        for box_column in range(0, 9, 3):
            problem.addConstraint(
                AllDifferentConstraint(),
                [
                    (row, column)
                    for row in range(box_row, box_row + 3)
                    for column in range(box_column, box_column + 3)
                ],
            )

    solution = problem.getSolution()
    if solution is None:
        return None
    return [[solution[(row, column)] for column in range(9)] for row in range(9)]


def box_index(row, column):
    return (row // 3) * 3 + column // 3
