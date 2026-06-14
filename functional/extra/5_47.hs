delioci :: Int -> [Int]
-- Delioce trazimo filtriranjem svih kandidata od 1 do n.
delioci n = filter (\d -> n `mod` d == 0) [1..n]

prost :: Int -> Bool
-- Prost broj ima tacno dva pozitivna delioca: 1 i samog sebe.
prost n = length (delioci n) == 2

prosti :: Int -> [Int]
prosti n = [k | k <- [2..n], prost k]
