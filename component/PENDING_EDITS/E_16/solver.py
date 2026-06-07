from constraint import Problem


SLOTS = ("Pon 09:00", "Pon 11:00", "Uto 09:00", "Uto 11:00")
ROOMS = ("A1", "A2")
DEFAULT_CLASSES = [
    {"subject": "Matematika", "teacher": "Ana", "group": "G1"},
    {"subject": "Programiranje", "teacher": "Boris", "group": "G1"},
    {"subject": "Fizika", "teacher": "Ana", "group": "G2"},
    {"subject": "Baze", "teacher": "Ceca", "group": "G1"},
    {"subject": "Algoritmi", "teacher": "Boris", "group": "G2"},
    {"subject": "Engleski", "teacher": "Dejan", "group": "G2"},
]


def make_schedule(classes, slots=SLOTS, rooms=ROOMS):
    if not classes:
        return []

    domains = [(slot, room) for slot in slots for room in rooms]
    order = sorted(
        range(len(classes)),
        key=lambda index: conflict_degree(index, classes),
        reverse=True,
    )

    problem = Problem()
    problem.addVariables(range(len(classes)), domains)
    for first_index in range(len(classes)):
        for second_index in range(first_index + 1, len(classes)):
            problem.addConstraint(
                compatible_classes(classes[first_index], classes[second_index]),
                (first_index, second_index),
            )

    solutions = problem.getSolutions()
    if not solutions:
        return None

    domain_index = {assignment: index for index, assignment in enumerate(domains)}
    solution = min(
        solutions,
        key=lambda candidate: tuple(domain_index[candidate[index]] for index in order),
    )
    return [
        {
            "subject": classes[index]["subject"],
            "teacher": classes[index]["teacher"],
            "group": classes[index]["group"],
            "slot": solution[index][0],
            "room": solution[index][1],
        }
        for index in range(len(classes))
    ]


def compatible_classes(first_class, second_class):
    def compatible(first_assignment, second_assignment):
        first_slot, first_room = first_assignment
        second_slot, second_room = second_assignment
        if first_slot != second_slot:
            return True
        return (
            first_room != second_room
            and first_class["teacher"] != second_class["teacher"]
            and first_class["group"] != second_class["group"]
        )

    return compatible


def conflict_degree(index, classes):
    current = classes[index]
    degree = 0
    for other_index, other in enumerate(classes):
        if other_index == index:
            continue
        if other["teacher"] == current["teacher"] or other["group"] == current["group"]:
            degree += 1
    return degree
