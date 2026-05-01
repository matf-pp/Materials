type Stek a = [a]

maxl :: Ord a => [a] -> a
maxl = foldl1 max

ind :: Eq a => [a] -> a -> Int
ind lst x = case lookup True (zip (map (== x) lst) [1..]) of
  Just i -> i
  Nothing -> -1

presek :: Ord a => [a] -> [a] -> [a]
presek [] _ = []
presek _ [] = []
presek (x:xs) (y:ys)
  | x == y = x : presek xs ys
  | x < y = presek xs (y:ys)
  | otherwise = presek (x:xs) ys

push :: Stek a -> a -> Stek a
push s x = x : s

top :: Stek a -> Maybe a
top [] = Nothing
top (x:_) = Just x

pushMP :: Ord a => [a] -> [a] -> Stek a -> Stek a
pushMP l1 l2 s =
  let p = presek l1 l2
  in if null p then s else push s (maximum p)
