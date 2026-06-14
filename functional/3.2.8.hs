savrseni :: Int -> [Int]
-- Savrsen broj je jednak zbiru svojih pravih delilaca.
savrseni n = [x | x <- [1..n-1], sum (faktori x) == x]
    where faktori x = [i | i <- [1..x-1], x `mod` i == 0]
