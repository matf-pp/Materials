from constraint import Problem

def main():
    counts=[2,3,5,4]; weights=[500,7,55,1]; values=[3400,1800,200,10]; capacity=128
    p=Problem(); names=list(range(4))
    for i,c in enumerate(counts): p.addVariable(i, range(c+1))
    p.addConstraint(lambda *xs: sum(x*w for x,w in zip(xs, weights)) <= capacity, names)
    r=max(p.getSolutions(), key=lambda x: sum(x[i]*values[i] for i in names))
    print([r[i] for i in names])
if __name__ == '__main__': main()
