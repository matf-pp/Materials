import Data.List (insertBy, delete, maximumBy)
import Data.Ord (comparing)

-- PART 1
compareCard :: (Int, Char) -> (Int, Char) -> Ordering
compareCard (v1, s1) (v2, s2)
  | v1 /= v2 = compare v1 v2
  | otherwise = compare (rank s1) (rank s2)
  where
    rank 'H' = 1
    rank 'K' = 2
    rank 'P' = 3
    rank 'T' = 4
    rank _   = 0

dodaj1 :: [(Int, Char)] -> (Int, Char) -> [(Int, Char)]
dodaj1 r k
  | k `elem` r = r
  | otherwise = insertBy compareCard k r

ukloni1 :: [(Int, Char)] -> (Int, Char) -> [(Int, Char)]
ukloni1 r k = filter (/= k) r

uporedi :: [(Int, Char)] -> [(Int, Char)] -> Ordering
uporedi = compare

-- PART 2
data Znak = Herc | Karo | Pik | Tref deriving (Show, Eq)

data Karta = MkKarta
  { vrednost :: Int
  , znak :: Znak
  } deriving (Show, Eq)

type Ruka = [Karta]

rankZ :: Znak -> Int
rankZ Herc = 1
rankZ Karo = 2
rankZ Pik = 3
rankZ Tref = 4

compareK :: Karta -> Karta -> Ordering
compareK (MkKarta v1 z1) (MkKarta v2 z2)
  | v1 /= v2 = compare v1 v2
  | otherwise = compare (rankZ z1) (rankZ z2)

izlistaj :: Znak -> Ruka -> [Karta]
izlistaj z = filter (\k -> znak k == z)

dodaj2 :: Ruka -> Karta -> Ruka
dodaj2 r k
  | k `elem` r = r
  | otherwise = insertBy compareK k r

najjaca :: Ruka -> Karta
najjaca = maximumBy compareK
