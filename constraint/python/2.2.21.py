# 2.2.21: NINE + LESS + TWO = SEVEN
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'I', 'L', 'N', 'O', 'S', 'T', 'V', 'W']
    leading = ['L', 'N', 'S', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['NINE', 'LESS', 'TWO']) == sum(value(word, assignment) for word in ['SEVEN'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('NINE + LESS + TWO = SEVEN', solution)


if __name__ == "__main__":
    main()
