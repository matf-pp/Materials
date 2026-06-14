maxl :: Ord a => [a] -> a
maxl = foldl1 max

ind :: Eq a => [a] -> a -> Int
ind lst x = case lookup True (zip (map (== x) lst) [1..]) of
  -- lookup trazi prvu poziciju na kojoj je poredjenje dalo True.
  Just i -> i
  Nothing -> -1

presek :: Eq a => [a] -> [a] -> [a]
-- U preseku ostaju elementi prve liste koji se pojavljuju i u drugoj.
presek lst1 lst2 = filter (`elem` lst2) lst1

umetni :: [a] -> a -> [a]
umetni [] _ = []
umetni [x] _ = [x]
-- Element y se ubacuje izmedju svaka dva susedna elementa.
umetni (x:xs) y = x : y : umetni xs y
