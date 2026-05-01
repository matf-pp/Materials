type Stek a = [a]

push :: Stek a -> a -> Stek a
push s x = x : s

top :: Stek a -> Maybe a
top [] = Nothing
top (x:_) = Just x

pop :: Stek a -> (Maybe a, Stek a)
pop [] = (Nothing, [])
pop (x:xs) = (Just x, xs)

stMap :: Stek a -> (a -> b) -> Stek b
stMap s f = map f s

proredi :: Stek a -> Stek a
proredi s = map snd $ filter (even . fst) (zip [0..] s)