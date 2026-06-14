import Data.List (sortBy)

data Student = MkStudent { ime :: String, poeni :: Int }
  deriving (Show, Eq)

instance Ord Student where
  -- Poredjenje studenata zasniva se samo na broju poena.
  compare s1 s2 = compare (poeni s1) (poeni s2)

rangLista :: [Student] -> [Student]
-- sortBy compare sortira rastuce, pa reverse daje rang listu od najboljeg ka najlosijem.
rangLista = reverse . sortBy compare

poeniStudenata :: [Student] -> [(String, Maybe Int)]
poeniStudenata =
  map (\s ->
    -- Maybe sakriva poene studenata koji nisu presli prag.
    if poeni s >= 50
    then (ime s, Just (poeni s))
    else (ime s, Nothing)
  )
