parni :: Int -> [Int]
-- Lenjo filtriranje beskonacne liste radi zato sto take zahteva samo prvih n rezultata.
parni n = take n $ filter (\x -> x `mod` 2 == 0) [1..]
