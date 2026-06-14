safeHead :: [a] -> Maybe a
-- Nothing oznacava da lista nema prvi element.
safeHead [] = Nothing
safeHead (x:_) = Just x

safeTail :: [a] -> Either [a] String
safeTail [] = Right "Empty"
safeTail (_:xs) = Left xs

find :: Eq a => a -> [a] -> Either Int String
find e lst =
  case lookup True (zip (map (== e) lst) [1..]) of
    -- Left nosi uspesan indeks, a Right tekst greske u ovom primeru.
    Just i -> Left i
    Nothing -> Right "404"

foldf :: b -> [(b -> a -> b)] -> [a] -> b
foldf acc fs lst =
  -- Svaki element liste se obradjuje odgovarajucom funkcijom iz liste funkcija.
  foldl (\a (f, x) -> f a x) acc (zip fs lst)
