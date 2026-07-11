from constraint import AllDifferentConstraint, Problem


POSITIONS = ("P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9")

# Linije cija suma mora biti 15: tri vrste, tri kolone i dve dijagonale.
LINES = (
    ("P1", "P2", "P3"),
    ("P4", "P5", "P6"),
    ("P7", "P8", "P9"),
    ("P1", "P4", "P7"),
    ("P2", "P5", "P8"),
    ("P3", "P6", "P9"),
    ("P1", "P5", "P9"),
    ("P3", "P5", "P7"),
)


def magicni_kvadrati():
    problem = Problem()
    problem.addVariables(POSITIONS, range(1, 10))
    problem.addConstraint(AllDifferentConstraint(), POSITIONS)
    for line in LINES:
        problem.addConstraint(lambda a, b, c: a + b + c == 15, line)

    resenja = sorted(
        problem.getSolutions(),
        key=lambda solution: tuple(solution[p] for p in POSITIONS),
    )

    # Svako resenje predstavljamo kao matricu 3x3 (lista od tri vrste).
    kvadrati = []
    for solution in resenja:
        kvadrat = [
            [solution[POSITIONS[3 * vrsta + kolona]] for kolona in range(3)]
            for vrsta in range(3)
        ]
        kvadrati.append(kvadrat)
    return kvadrati
