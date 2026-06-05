def is_magic_sequence(sequence):
    return all(sequence.count(i) == sequence[i] for i in range(len(sequence)))


def magic_sequences(n):
    if n <= 0:
        raise ValueError("Duzina sekvence mora biti pozitivna.")

    sequence = [0] * n
    solutions = []

    def search(position, total_count, weighted_count):
        if total_count > n or weighted_count > n:
            return

        if position == n:
            if total_count == n and weighted_count == n and is_magic_sequence(sequence):
                solutions.append(sequence.copy())
            return

        for value in range(n):
            sequence[position] = value
            search(position + 1, total_count + value, weighted_count + position * value)

    search(0, 0, 0)
    return solutions
