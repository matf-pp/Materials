# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.34: SIXTEEN + TWENTY + TWENTY + TEN + TWO + TWO = SEVENTY
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'I', 'N', 'O', 'S', 'T', 'V', 'W', 'X', 'Y']
    leading = ['S', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['SIXTEEN', 'TWENTY', 'TWENTY', 'TEN', 'TWO', 'TWO']) == sum(value(word, assignment) for word in ['SEVENTY'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('SIXTEEN + TWENTY + TWENTY + TEN + TWO + TWO = SEVENTY', solution)


if __name__ == "__main__":
    main()
