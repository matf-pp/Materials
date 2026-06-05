DEFAULT_ITEMS = [
    {"name": "Viski", "size": 4, "value": 15, "max_count": 5},
    {"name": "Parfem", "size": 3, "value": 10, "max_count": 5},
    {"name": "Casa", "size": 2, "value": 7, "max_count": 5},
    {"name": "Knjiga", "size": 1, "value": 2, "max_count": 10},
]


def optimize_knapsack(capacity, items):
    if capacity < 0:
        raise ValueError("Kapacitet mora biti nenegativan.")

    best_value = -1
    best_size = 0
    best_counts = [0] * len(items)
    counts = [0] * len(items)

    def search(index, used_size, value):
        nonlocal best_value, best_size, best_counts

        if index == len(items):
            if value > best_value or (value == best_value and used_size < best_size):
                best_value = value
                best_size = used_size
                best_counts = counts.copy()
            return

        item = items[index]
        size = item["size"]
        max_count = min(item["max_count"], (capacity - used_size) // size)
        for count in range(max_count + 1):
            counts[index] = count
            search(
                index + 1,
                used_size + count * size,
                value + count * item["value"],
            )
        counts[index] = 0

    search(0, 0, 0)
    return {
        "value": best_value,
        "size": best_size,
        "counts": {
            items[index]["name"]: best_counts[index] for index in range(len(items))
        },
    }
