import Data.Char (toLower)

najduza :: [String] -> String
najduza = foldl1 (\a b -> if length a >= length b then a else b)

umanji :: String -> String
umanji = map toLower

razdvoj :: Char -> String -> [String]
razdvoj _ "" = [""]
razdvoj d (x:xs)
  -- Delilac otvara novu rec; obican karakter se dodaje na pocetak prve dobijene reci.
  | x == d    = "" : rest
  | otherwise = (x : head rest) : tail rest
  where
    rest = razdvoj d xs

spoj :: String -> [String] -> String
spoj _ []     = ""
spoj _ [x]    = x
-- Separator dodajemo samo izmedju elemenata, zato jednoclana lista ima poseban slucaj.
spoj sep (x:xs) = x ++ sep ++ spoj sep xs
