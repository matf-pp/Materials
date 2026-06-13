# 2.2.1: TWO + TWO = FOUR
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['F', 'O', 'R', 'T', 'U', 'W']
    leading = ['F', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['TWO', 'TWO']) == sum(value(word, assignment) for word in ['FOUR'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('TWO + TWO = FOUR', solution)


if __name__ == "__main__":
    main()
