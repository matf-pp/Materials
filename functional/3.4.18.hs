komponenta :: Char -> (Int, Int, Int) -> Int
komponenta 'r' (r,_,_) = r
komponenta 'g' (_,g,_) = g
komponenta 'b' (_,_,b) = b
komponenta _ _ = 0

ponisti :: Char -> (Int, Int, Int) -> (Int, Int, Int)
ponisti 'r' (_,g,b) = (0,g,b)
ponisti 'g' (r,_,b) = (r,0,b)
ponisti 'b' (r,g,_) = (r,g,0)
ponisti _ x = x

clamp :: Int -> Int
clamp x = min 255 x

pomesaj :: (Int, Int, Int) -> (Int, Int, Int) -> (Int, Int, Int)
pomesaj (r1,g1,b1) (r2,g2,b2) =
  (clamp (r1+r2), clamp (g1+g2), clamp (b1+b2))

dodaj :: Char -> Int -> (Int, Int, Int) -> (Int, Int, Int)
dodaj 'r' v (r,g,b) = (clamp (r+v), g, b)
dodaj 'g' v (r,g,b) = (r, clamp (g+v), b)
dodaj 'b' v (r,g,b) = (r, g, clamp (b+v))
dodaj _ _ x = x

lbFilter :: Char -> Int -> [(Int, Int, Int)] -> [(Int, Int, Int)]
lbFilter k v lst =
  filter (\c -> komponenta k c >= v) lst