brojDelilaca :: Int -> Int
-- Listom obuhvatamo samo unutrasnje delioce, bez 1 i samog broja n.
brojDelilaca n = length [x | x <- [2..n-1], mod n x == 0]
