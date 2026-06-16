import json 
import os
import sys

"""
Napisati program koji racuna n-ti Fibonacijev ili kvazi-Lukasov broj u zavisnosti od izabrane opcije. 
U slucaju racunanja kvazi-Lukasovog broja, ucitati i odgovarajuci konfiguracioni .json fajl.

Kreirati GUI deo kao na slici. Gui deo sastoji se od tekst labele sa nazivom programa, radio dugmadi kojima se selektuje algoritam, 
zatim dela kojim se ucitavaju parametri N i konfiguracioni fajl u slucaju kvazi-Lukasovih brojeva. 
Dugme koje trigeruje racunanje kao i polje u kome se ispisuje rezultat.

Fibonacijevi brojevi racunaju se prema formuli:
F0 = 0
F1 = 1
Fn = Fn-1 + Fn-2

Kvazi-Lukasovi brojevi su brojevi koje dobijamo po formuli:
L0 = c0
L1 = c1
Ln = a*Ln-1 + b*Ln-2, gde su c0, c1, a i b proizvoljne realne konstante.

Zadatak uradjen bez grafickog korisnickog okruzenja nosi maksimum 50% poena.
"""

def quasi_lucas(c0, c1, a, b):
    x, y = c0, c1
    while True:
        yield x
        x, y = y, c0*x + c1*y

def fib():
    x, y = 0, 1
    while True:
        yield x
        x, y = y, x + y

def iterate(iter, n):
    for _ in range(n-1):
        iter.__next__()
    return iter.__next__()

if __name__=="__main__":
    mode = input("Insert mode [lucas, fibonacci]: ")
    assert mode in ["lucas", "fibonacci"]

    n = int(input("insert n: "))
    assert n>0, "invalid n"

    out = None
    if mode == "fibonacci":
        f = fib()
        out = iterate(f, n)
    elif mode == "lucas":
        config_path = input("json config path: ")
        with open(config_path, 'r') as f:
            conf = json.load(f)
            c0, c1, a, b = conf['c0'], conf['c1'], conf['a'], conf['b']
        l = quasi_lucas(c0, c1, a, b)
        out = iterate(l, n)

    print(out)

