from constraint import AllDifferentConstraint, Problem


LETTERS = ("M", "E", "N", "A", "D", "W", "O")


def number(word, assignment):
    value = 0
    for letter in word:
        value = value * 10 + assignment[letter]
    return value


def solve_man_and_women():
    problem = Problem()
    problem.addVariables(LETTERS, range(10))
    problem.addConstraint(AllDifferentConstraint(), LETTERS)
    problem.addConstraint(lambda m: m != 0, ("M",))
    problem.addConstraint(lambda w: w != 0, ("W",))
    problem.addConstraint(
        lambda m, e, n, a, d, w, o: (100 * m + 10 * e + n) * (100 * a + 10 * n + d)
        == 10000 * w + 1000 * o + 100 * m + 10 * e + n,
        LETTERS,
    )

    solutions = []
    for assignment in sorted(
        problem.getSolutions(),
        key=lambda solution: tuple(solution[letter] for letter in LETTERS),
    ):
        men = number("MEN", assignment)
        et = number("AND", assignment)
        women = number("WOMEN", assignment)
        solutions.append(
            {
                "assignment": assignment,
                "men": men,
                "et": et,
                "women": women,
                "equation": f"{men} * {et} = {women}",
            }
        )

    return solutions
