from constraint import AllDifferentConstraint, Problem


def magic_squares():
    variables = list(range(9))
    problem = Problem()
    problem.addVariables(variables, range(1, 10))
    problem.addConstraint(AllDifferentConstraint(), variables)

    lines = (
        (0, 1, 2),
        (3, 4, 5),
        (6, 7, 8),
        (0, 3, 6),
        (1, 4, 7),
        (2, 5, 8),
        (0, 4, 8),
        (2, 4, 6),
    )
    for line in lines:
        problem.addConstraint(lambda a, b, c: a + b + c == 15, line)

    solutions = sorted(
        tuple(solution[index] for index in variables)
        for solution in problem.getSolutions()
    )
    return [
        [list(values[0:3]), list(values[3:6]), list(values[6:9])]
        for values in solutions
    ]
