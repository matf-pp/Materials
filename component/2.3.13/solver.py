from constraint import Problem


REGIONS = ("Sever", "Zapad", "Centar", "Istok", "Jug", "Obala")
EDGES = (
    ("Sever", "Zapad"),
    ("Sever", "Centar"),
    ("Sever", "Istok"),
    ("Zapad", "Centar"),
    ("Zapad", "Jug"),
    ("Centar", "Istok"),
    ("Centar", "Jug"),
    ("Centar", "Obala"),
    ("Istok", "Obala"),
    ("Jug", "Obala"),
)
COLORS = ("Crvena", "Plava", "Zelena", "Zuta", "Ljubicasta")


def color_map(color_count):
    if color_count < 1 or color_count > len(COLORS):
        raise ValueError("Neispravan broj boja.")

    colors = COLORS[:color_count]
    problem = Problem()
    problem.addVariables(REGIONS, colors)
    for edge in EDGES:
        problem.addConstraint(lambda first, second: first != second, edge)

    solutions = sorted(
        problem.getSolutions(),
        key=lambda solution: tuple(solution[region] for region in REGIONS),
    )
    return [
        {region: solution[region] for region in REGIONS}
        for solution in solutions
    ]
