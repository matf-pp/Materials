# 2.2.35: SIXTEEN + TWELVE + TWELVE + TWELVE + NINE + NINE = SEVENTY
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'I', 'L', 'N', 'S', 'T', 'V', 'W', 'X', 'Y']
    leading = ['N', 'S', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['SIXTEEN', 'TWELVE', 'TWELVE', 'TWELVE', 'NINE', 'NINE']) == sum(value(word, assignment) for word in ['SEVENTY'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('SIXTEEN + TWELVE + TWELVE + TWELVE + NINE + NINE = SEVENTY', solution)


if __name__ == "__main__":
    main()
