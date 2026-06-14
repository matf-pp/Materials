# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.12: SATURN + URANUS + NEPTUNE + PLUTO = PLANETS
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'E', 'L', 'N', 'O', 'P', 'R', 'S', 'T', 'U']
    leading = ['N', 'P', 'S', 'U']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['SATURN', 'URANUS', 'NEPTUNE', 'PLUTO']) == sum(value(word, assignment) for word in ['PLANETS'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('SATURN + URANUS + NEPTUNE + PLUTO = PLANETS', solution)


if __name__ == "__main__":
    main()
