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
    neighbors = {region: set() for region in REGIONS}
    for first, second in EDGES:
        neighbors[first].add(second)
        neighbors[second].add(first)

    order = sorted(REGIONS, key=lambda region: len(neighbors[region]), reverse=True)
    assignment = {}
    solutions = []

    def search(index):
        if index == len(order):
            solutions.append({region: assignment[region] for region in REGIONS})
            return

        region = order[index]
        used_neighbor_colors = {
            assignment[neighbor]
            for neighbor in neighbors[region]
            if neighbor in assignment
        }
        for color in colors:
            if color in used_neighbor_colors:
                continue
            assignment[region] = color
            search(index + 1)
            del assignment[region]

    search(0)
    return solutions
