izbaci :: Int -> [a] -> [a]
izbaci _ [] = []
-- zip dodaje indekse elementima, a foldr preskace element ciji indeks treba izbaciti.
izbaci k lst = foldr (\(i,x) acc -> if i == k then acc else x : acc) [] 
             $ zip [0..] lst

{-    
    izbaci _ [] = []
    izbaci 0 (_:xs) = xs
    izbaci k (x:xs) = x : (izbaci (k-1) xs)
-}
