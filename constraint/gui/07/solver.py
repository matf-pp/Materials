COURSES = {
    "Elixir": {"cost": 100, "hours": 150, "profit_per_hour": 5},
    "Dart": {"cost": 105, "hours": 170, "profit_per_hour": 6},
}


def optimize_training(budget, max_project_hours, worker_count):
    if budget < 0 or max_project_hours < 0 or worker_count < 0:
        raise ValueError("Svi ulazni parametri moraju biti nenegativni.")

    best = None
    for elixir_count in range(worker_count + 1):
        dart_count = worker_count - elixir_count
        cost = (
            elixir_count * COURSES["Elixir"]["cost"]
            + dart_count * COURSES["Dart"]["cost"]
        )
        project_hours = (
            elixir_count * COURSES["Elixir"]["hours"]
            + dart_count * COURSES["Dart"]["hours"]
        )
        if cost > budget or project_hours > max_project_hours:
            continue

        profit = (
            elixir_count
            * COURSES["Elixir"]["hours"]
            * COURSES["Elixir"]["profit_per_hour"]
            + dart_count
            * COURSES["Dart"]["hours"]
            * COURSES["Dart"]["profit_per_hour"]
            - cost
        )

        candidate = {
            "Elixir": elixir_count,
            "Dart": dart_count,
            "cost": cost,
            "project_hours": project_hours,
            "profit": profit,
            "workers": worker_count,
        }
        if best is None or profit > best["profit"]:
            best = candidate

    return best
