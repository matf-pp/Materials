# 2.2.4: NO + GUN + NO = HUNT
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['G', 'H', 'N', 'O', 'T', 'U']
    leading = ['G', 'H', 'N']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['NO', 'GUN', 'NO']) == sum(value(word, assignment) for word in ['HUNT'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('NO + GUN + NO = HUNT', solution)


if __name__ == "__main__":
    main()
