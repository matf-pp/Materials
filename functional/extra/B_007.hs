brojDelilaca :: Int -> Int
-- Brojimo samo delioce izmedju 1 i n, pa 1 i n nisu ukljuceni.
brojDelilaca n = length [x | x <- [2..n-1], mod n x == 0]
