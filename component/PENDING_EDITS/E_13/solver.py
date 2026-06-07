from constraint import Problem


COURSES = {
    "Elixir": {"cost": 100, "hours": 150, "profit_per_hour": 5},
    "Dart": {"cost": 105, "hours": 170, "profit_per_hour": 6},
}


def optimize_training(budget, max_project_hours, worker_count):
    if budget < 0 or max_project_hours < 0 or worker_count < 0:
        raise ValueError("Svi ulazni parametri moraju biti nenegativni.")

    course_names = tuple(COURSES)
    problem = Problem()
    problem.addVariables(course_names, range(worker_count + 1))
    problem.addConstraint(lambda *counts: sum(counts) == worker_count, course_names)
    problem.addConstraint(
        lambda *counts: sum(
            count * COURSES[name]["cost"]
            for count, name in zip(counts, course_names)
        )
        <= budget,
        course_names,
    )
    problem.addConstraint(
        lambda *counts: sum(
            count * COURSES[name]["hours"]
            for count, name in zip(counts, course_names)
        )
        <= max_project_hours,
        course_names,
    )

    best = None
    solutions = sorted(
        problem.getSolutions(),
        key=lambda solution: tuple(solution[name] for name in course_names),
    )
    for solution in solutions:
        cost = sum(
            solution[name] * data["cost"] for name, data in COURSES.items()
        )
        project_hours = sum(
            solution[name] * data["hours"] for name, data in COURSES.items()
        )
        profit = sum(
            solution[name] * data["hours"] * data["profit_per_hour"]
            for name, data in COURSES.items()
        ) - cost
        candidate = {
            "Elixir": solution["Elixir"],
            "Dart": solution["Dart"],
            "cost": cost,
            "project_hours": project_hours,
            "profit": profit,
            "workers": worker_count,
        }
        if best is None or profit > best["profit"]:
            best = candidate

    return best
