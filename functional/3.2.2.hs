parni :: Int -> [Int]
-- Beskonacna lista [1..] se lenjo filtrira, pa take uzima samo prvih n parnih brojeva.
parni n = take n $ filter (\x -> x `mod` 2 == 0) [1..]
