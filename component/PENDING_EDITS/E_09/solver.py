from constraint import AllDifferentConstraint, Problem


LETTERS = ("S", "E", "Y", "O", "U", "N")


def number(word, assignment):
    value = 0
    for letter in word:
        value = value * 10 + assignment[letter]
    return value


def solve_see_you_soon():
    problem = Problem()
    problem.addVariables(LETTERS, range(10))
    problem.addConstraint(AllDifferentConstraint(), LETTERS)
    problem.addConstraint(lambda s: s != 0, ("S",))
    problem.addConstraint(lambda y: y != 0, ("Y",))
    problem.addConstraint(
        lambda s, e, y, o, u, n: 100 * s
        + 11 * e
        + 100 * y
        + 10 * o
        + u
        == 1000 * s + 100 * o + 11 * n,
        LETTERS,
    )

    solutions = []
    for assignment in sorted(
        problem.getSolutions(),
        key=lambda solution: tuple(solution[letter] for letter in LETTERS),
    ):
        see = number("SEE", assignment)
        you = number("YOU", assignment)
        soon = number("SOON", assignment)
        solutions.append(
            {
                "assignment": assignment,
                "see": see,
                "you": you,
                "soon": soon,
                "equation": f"{see} + {you} = {soon}",
            }
        )

    return solutions
