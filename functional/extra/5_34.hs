parni :: Int -> Int -> [Int]
-- List comprehension prolazi kroz ceo interval i zadrzava samo parne brojeve.
parni a b = [x | x <- [a..b], even x]

neparni :: Int -> Int -> [Int]
-- Ista ideja kao za parne brojeve, ali sa predikatom odd.
neparni a b = [x | x <- [a..b], odd x]
