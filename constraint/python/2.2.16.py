# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.16: OSAKA + HAIKU + SUSHI = JAPAN
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'H', 'I', 'J', 'K', 'N', 'O', 'P', 'S', 'U']
    leading = ['H', 'J', 'O', 'S']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['OSAKA', 'HAIKU', 'SUSHI']) == sum(value(word, assignment) for word in ['JAPAN'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('OSAKA + HAIKU + SUSHI = JAPAN', solution)


if __name__ == "__main__":
    main()
