from itertools import permutations


LETTERS = ("S", "E", "Y", "O", "U", "N")


def number(word, assignment):
    value = 0
    for letter in word:
        value = value * 10 + assignment[letter]
    return value


def solve_see_you_soon():
    solutions = []

    for digits in permutations(range(10), len(LETTERS)):
        assignment = dict(zip(LETTERS, digits))
        if assignment["S"] == 0 or assignment["Y"] == 0:
            continue

        see = number("SEE", assignment)
        you = number("YOU", assignment)
        soon = number("SOON", assignment)

        if see + you == soon:
            solutions.append(
                {
                    "assignment": assignment,
                    "see": see,
                    "you": you,
                    "soon": soon,
                    "equation": f"{see} + {you} = {soon}",
                }
            )

    return solutions
