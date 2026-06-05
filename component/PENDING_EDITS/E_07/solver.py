COINS = (1, 2, 5, 10, 20)


def coin_combinations(amount, coins=COINS):
    if amount < 0:
        raise ValueError("Iznos mora biti nenegativan.")

    coins = tuple(sorted(coins))
    combinations = []
    current = {}

    def search(index, rest):
        if index == len(coins) - 1:
            coin = coins[index]
            if rest % coin == 0:
                current[coin] = rest // coin
                combinations.append({c: current.get(c, 0) for c in coins})
            return

        coin = coins[index]
        for count in range(rest // coin + 1):
            current[coin] = count
            search(index + 1, rest - count * coin)

    search(0, amount)
    return combinations
