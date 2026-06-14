# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.13: WHEN + IN + ROME + BE + A = ROMAN
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'B', 'E', 'H', 'I', 'M', 'N', 'O', 'R', 'W']
    leading = ['B', 'I', 'R', 'W']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['WHEN', 'IN', 'ROME', 'BE', 'A']) == sum(value(word, assignment) for word in ['ROMAN'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('WHEN + IN + ROME + BE + A = ROMAN', solution)


if __name__ == "__main__":
    main()
