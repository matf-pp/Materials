import Data.Char (isDigit, isLower)

cifre :: String -> Int
cifre s = length (filter isDigit s)

mala :: String -> Int
mala s = length (filter isLower s)

desifruj :: [String] -> [String]
desifruj = map obradi
  where
    obradi [] = []
    obradi s@(x:_)
      | isDigit x = drop (cifre s) s
      | isLower x = drop (mala s) s
      | otherwise = s
