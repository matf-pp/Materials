type Stek a = [a]

push :: Stek a -> a -> Stek a
push s x = x : s

top :: Stek a -> Maybe a
top [] = Nothing
-- Maybe koristimo zato sto prazan stek nema element na vrhu.
top (x:_) = Just x

pop :: Stek a -> (Maybe a, Stek a)
pop [] = (Nothing, [])
-- Rezultat vraca skinuti element i novi stek bez tog elementa.
pop (x:xs) = (Just x, xs)

stMap :: Stek a -> (a -> b) -> Stek b
stMap s f = map f s

proredi :: Stek a -> Stek a
-- zip dodaje indekse, pa zadrzavamo elemente na parnim pozicijama.
proredi s = map snd $ filter (even . fst) (zip [0..] s)
