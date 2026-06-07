from constraint import Problem


DEFAULT_ITEMS = [
    {"name": "Viski", "size": 4, "value": 15, "max_count": 5},
    {"name": "Parfem", "size": 3, "value": 10, "max_count": 5},
    {"name": "Casa", "size": 2, "value": 7, "max_count": 5},
    {"name": "Knjiga", "size": 1, "value": 2, "max_count": 10},
]


def optimize_knapsack(capacity, items):
    if capacity < 0:
        raise ValueError("Kapacitet mora biti nenegativan.")

    variables = list(range(len(items)))
    if not variables:
        return {"value": 0, "size": 0, "counts": {}}

    problem = Problem()
    for index, item in enumerate(items):
        max_count = min(item["max_count"], capacity // item["size"])
        problem.addVariable(index, range(max_count + 1))

    problem.addConstraint(
        lambda *counts: sum(
            count * item["size"] for count, item in zip(counts, items)
        )
        <= capacity,
        variables,
    )

    best_value = -1
    best_size = 0
    best_counts = [0] * len(items)
    solutions = sorted(
        problem.getSolutions(),
        key=lambda solution: tuple(solution[index] for index in variables),
    )
    for solution in solutions:
        counts = [solution[index] for index in variables]
        used_size = sum(count * item["size"] for count, item in zip(counts, items))
        value = sum(count * item["value"] for count, item in zip(counts, items))
        if value > best_value or (value == best_value and used_size < best_size):
            best_value = value
            best_size = used_size
            best_counts = counts

    return {
        "value": best_value,
        "size": best_size,
        "counts": {
            items[index]["name"]: best_counts[index] for index in range(len(items))
        },
    }
