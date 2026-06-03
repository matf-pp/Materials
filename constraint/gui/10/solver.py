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
    domains = [(slot, room) for slot in slots for room in rooms]
    schedule = [None] * len(classes)
    order = sorted(
        range(len(classes)),
        key=lambda index: conflict_degree(index, classes),
        reverse=True,
    )

    def can_assign(class_index, slot, room):
        current = classes[class_index]
        for other_index, assigned in enumerate(schedule):
            if assigned is None:
                continue
            other_slot, other_room = assigned
            if other_slot != slot:
                continue
            other = classes[other_index]
            if other_room == room:
                return False
            if other["teacher"] == current["teacher"]:
                return False
            if other["group"] == current["group"]:
                return False
        return True

    def search(order_index):
        if order_index == len(order):
            return True

        class_index = order[order_index]
        for slot, room in domains:
            if not can_assign(class_index, slot, room):
                continue
            schedule[class_index] = (slot, room)
            if search(order_index + 1):
                return True
            schedule[class_index] = None
        return False

    if not search(0):
        return None

    return [
        {
            "subject": classes[index]["subject"],
            "teacher": classes[index]["teacher"],
            "group": classes[index]["group"],
            "slot": schedule[index][0],
            "room": schedule[index][1],
        }
        for index in range(len(classes))
    ]


def conflict_degree(index, classes):
    current = classes[index]
    degree = 0
    for other_index, other in enumerate(classes):
        if other_index == index:
            continue
        if other["teacher"] == current["teacher"] or other["group"] == current["group"]:
            degree += 1
    return degree
