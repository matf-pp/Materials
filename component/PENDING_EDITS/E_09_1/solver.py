from itertools import permutations


LETTERS = ("M", "E", "N", "A", "D", "W", "O")


def number(word, assignment):
    value = 0
    for letter in word:
        value = value * 10 + assignment[letter]
    return value


def solve_man_and_women():
    solutions = []

    for digits in permutations(range(10), len(LETTERS)):
        assignment = dict(zip(LETTERS, digits))
        if assignment["M"] == 0 or assignment["W"] == 0:
            continue

        men = number("MEN", assignment)
        et = number("AND", assignment)
        women = number("WOMEN", assignment)

        if men + et == women:
            solutions.append(
                {
                    "assignment": assignment,
                    "men": men,
                    "et": et,
                    "women": women,
                    "equation": f"{men} + {et} = {women}",
                }
            )

    return solutions
