data Prozor = MkProzor { indP :: Int, naziv :: String }

type Prozori = [Prozor]

instance Eq Prozor where
  (MkProzor i1 _) == (MkProzor i2 _) = i1 == i2

instance Ord Prozor where
  compare (MkProzor i1 _) (MkProzor i2 _) = compare i1 i2

instance Show Prozor where
  show (MkProzor i n) = "[" ++ show i ++ "]: " ++ n

otvori :: String -> Prozori -> Prozori
otvori str lst =
  let ids = map indP lst
      newId = prviSlobodan 1 ids
  in MkProzor newId str : lst

prviSlobodan :: Int -> [Int] -> Int
prviSlobodan n ids
  | n `elem` ids = prviSlobodan (n + 1) ids
  | otherwise    = n
