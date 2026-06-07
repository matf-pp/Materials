from constraint import Problem


COINS = (1, 2, 5, 10, 20)


def coin_combinations(amount, coins=COINS):
    if amount < 0:
        raise ValueError("Iznos mora biti nenegativan.")

    coins = tuple(sorted(coins))
    variables = [f"coin_{coin}" for coin in coins]

    problem = Problem()
    for variable, coin in zip(variables, coins):
        problem.addVariable(variable, range(amount // coin + 1))

    problem.addConstraint(
        lambda *counts: sum(
            coin * count for coin, count in zip(coins, counts)
        )
        == amount,
        variables,
    )

    solutions = problem.getSolutions()
    solutions.sort(
        key=lambda solution: tuple(solution[variable] for variable in variables)
    )
    return [
        {coin: solution[variable] for coin, variable in zip(coins, variables)}
        for solution in solutions
    ]
