from constraint import AllDifferentConstraint, Problem


def solve_n_queens(n):
    if n < 1:
        raise ValueError("Dimenzija table mora biti pozitivna.")

    rows = list(range(n))
    problem = Problem()
    problem.addVariables(rows, range(n))
    problem.addConstraint(AllDifferentConstraint(), rows)

    for first_row in rows:
        for second_row in range(first_row + 1, n):
            problem.addConstraint(
                lambda first_column, second_column, row_delta=second_row
                - first_row: abs(first_column - second_column) != row_delta,
                (first_row, second_row),
            )

    solutions = problem.getSolutions()
    return sorted(tuple(solution[row] for row in rows) for solution in solutions)


def board_from_solution(solution):
    n = len(solution)
    return [
        ["D" if solution[row] == column else "" for column in range(n)]
        for row in range(n)
    ]
