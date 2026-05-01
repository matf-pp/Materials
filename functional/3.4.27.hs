import Data.List (sortBy)

data Student = MkStudent { ime :: String, poeni :: Int }
  deriving (Show, Eq)

instance Ord Student where
  compare s1 s2 = compare (poeni s1) (poeni s2)

rangLista :: [Student] -> [Student]
rangLista = reverse . sortBy compare

poeniStudenata :: [Student] -> [(String, Maybe Int)]
poeniStudenata =
  map (\s ->
    if poeni s >= 50
    then (ime s, Just (poeni s))
    else (ime s, Nothing)
  )