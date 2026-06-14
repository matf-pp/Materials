import Data.List (isPrefixOf)

takeCycled :: Int -> [a] -> [a]
takeCycled _ [] = []
takeCycled n lst = take n (cycle lst)

dropUntil :: (a -> Bool) -> [a] -> [a]
dropUntil _ [] = []
dropUntil p (x:xs)
  -- Kada prvi element zadovolji predikat, vracamo ostatak liste od tog mesta.
  | p x = x:xs
  | otherwise = dropUntil p xs

sumLst :: Num a => [a] -> a
sumLst = foldl (+) 0

split :: Char -> String -> [String]
split _ "" = [""]
split sep (c:cs)
  -- Separator pocinje novu rec; inace znak dodajemo na prvu rec iz rekurzivnog rezultata.
  | c == sep = "" : rest
  | otherwise = (c : head rest) : tail rest
  where
    rest = split sep cs

join :: String -> [String] -> String
join _ [] = ""
join _ [x] = x
join sep (x:xs) = x ++ sep ++ join sep xs
