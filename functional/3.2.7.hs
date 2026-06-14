fibLista :: Int -> [Int]
fibLista n = take n $ fibs
    -- fibs je beskonacna lista: zipWith sabira listu sa njenim repom i pravi sledece clanove.
    where fibs = 1 : 1 : zipWith (+) fibs (tail fibs)
