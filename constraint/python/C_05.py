# max(||X||2)?
# A*X <= b
# X in [0,10]^2 (dvomenzion vektori u celobrojnoj 2D resetci dimenzije 10)
import constraint
import functools

problem = constraint.Problem()

problem.addVariable('X1', range(0, 10))
problem.addVariable('X2', range(0, 10))

def ogr(x1, x2, a1, a2, b):
    if a1*x1 + a2*x2 < b:
        return True

# AX <= b
# [2   3 ]*[X1] <= [23]
# [45 -34] [X2]    [12]
A = [[2, 3], [45, -34]]
b = [23, 12]

for _a, _b in zip(A, b):
    print('Adding constr: X1, X2, ', _a[0], _a[1], _b)
    ogr_tmp = functools.partial(ogr, a1=_a[0], a2=_a[1], b=_b)
    problem.addConstraint(ogr_tmp, ['X1', 'X2'])

rs = problem.getSolutions()
rs_sorted = sorted(rs, key=lambda x: x['X1']**2 + x['X2']**2)

print(rs_sorted)