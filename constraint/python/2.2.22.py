# 2.2.22: ONE + THREE + FOUR = EIGHT
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'F', 'G', 'H', 'I', 'N', 'O', 'R', 'T', 'U']
    leading = ['E', 'F', 'O', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['ONE', 'THREE', 'FOUR']) == sum(value(word, assignment) for word in ['EIGHT'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('ONE + THREE + FOUR = EIGHT', solution)


if __name__ == "__main__":
    main()
