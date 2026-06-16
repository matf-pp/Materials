from constraint import Problem


def is_magic_sequence(sequence):
    return all(sequence.count(i) == sequence[i] for i in range(len(sequence)))


def magic_sequences(n):
    if n <= 0:
        raise ValueError("Duzina sekvence mora biti pozitivna.")

    variables = list(range(n))
    problem = Problem()
    problem.addVariables(variables, range(n))

    def magic_constraint(*values):
        return (
            sum(values) == n
            and sum(index * value for index, value in enumerate(values)) == n
            and is_magic_sequence(values)
        )

    problem.addConstraint(magic_constraint, variables)
    solutions = problem.getSolutions()
    return [
        list(values)
        for values in sorted(
            tuple(solution[index] for index in variables)
            for solution in solutions
        )
    ]
