import Data.List (group)

delioci :: Int -> [Int]
delioci n = [x | x <- [2..n-1], n `mod` x == 0]

prost :: Int -> Bool
-- Broj je prost ako je veci od 1 i nema unutrasnje delioce.
prost n = n > 1 && null (delioci n)

generisiProste :: Int -> [Int]
generisiProste n = filter prost [2..n]

sumaProstih :: Int -> Int -> Int
sumaProstih a b = sum (filter prost [a..b])

faktori :: Int -> [(Int, Int)]
-- Grupisanjem jednakih prostih faktora dobijamo par (faktor, stepen).
faktori n = map (\xs -> (head xs, length xs)) . group $
            filter prost (factors n)
  where
    factors x = concatMap (\p -> replicate (count p x) p) (generisiProste x)
    count p x
      | x `mod` p /= 0 = 0
      | otherwise = 1 + count p (x `div` p)
