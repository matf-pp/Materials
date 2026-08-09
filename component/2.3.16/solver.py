from constraint import Problem


APOENI = (1, 2, 5, 10)


def kombinacije_novcica(iznos):
    problem = Problem()
    for apoen in APOENI:
        problem.addVariable(apoen, range(iznos // apoen + 1))

    problem.addConstraint(
        lambda *broj: sum(b * a for b, a in zip(broj, APOENI)) == iznos,
        APOENI,
    )

    return sorted(
        problem.getSolutions(),
        key=lambda solution: tuple(solution[apoen] for apoen in APOENI),
    )


def ukupno_novcica(solution):
    return sum(solution[apoen] for apoen in APOENI)
