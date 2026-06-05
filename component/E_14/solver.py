from itertools import permutations


def magic_squares():
    solutions = []
    for values in permutations(range(1, 10)):
        rows = [values[0:3], values[3:6], values[6:9]]
        sums = [
            sum(rows[0]),
            sum(rows[1]),
            sum(rows[2]),
            rows[0][0] + rows[1][0] + rows[2][0],
            rows[0][1] + rows[1][1] + rows[2][1],
            rows[0][2] + rows[1][2] + rows[2][2],
            rows[0][0] + rows[1][1] + rows[2][2],
            rows[0][2] + rows[1][1] + rows[2][0],
        ]
        if all(total == 15 for total in sums):
            solutions.append([list(row) for row in rows])
    return solutions
