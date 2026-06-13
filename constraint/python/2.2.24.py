# 2.2.24: SEVEN + SEVEN + SIX = TWENTY
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'I', 'N', 'S', 'T', 'V', 'W', 'X', 'Y']
    leading = ['S', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['SEVEN', 'SEVEN', 'SIX']) == sum(value(word, assignment) for word in ['TWENTY'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('SEVEN + SEVEN + SIX = TWENTY', solution)


if __name__ == "__main__":
    main()
