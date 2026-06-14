fibLista :: Int -> [Int]
fibLista n = take n $ fibs
    -- Beskonacna lista fibs se definise sama preko sebe, a Haskell je racuna lenjo.
    where fibs = 1 : 1 : zipWith (+) fibs (tail fibs)
