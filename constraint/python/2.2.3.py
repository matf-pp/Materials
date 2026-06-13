# 2.2.3: GREEN + ORANGE = COLORS
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'C', 'E', 'G', 'L', 'N', 'O', 'R', 'S']
    leading = ['C', 'G', 'O']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['GREEN', 'ORANGE']) == sum(value(word, assignment) for word in ['COLORS'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('GREEN + ORANGE = COLORS', solution)


if __name__ == "__main__":
    main()
