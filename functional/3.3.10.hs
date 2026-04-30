infixr 7 :/:
data OList a = End
             | (:/:) a (OList a)
             deriving Eq

-- Da bismo prikazali listu elemenata tipa `a`,
-- neophodno je da znamo kako da prikazemo jedan element
-- tog tipa -- tip `a` mora isto da implementira `Show`
instance Show a => Show (OList a) where
    show End = ""
    show (x :/: End) = show x
    show (x :/: xs)  = show x ++ ", " ++ show xs


-- Funkcija `foldr` prolazi kroz kolekciju (sa desne strane)
-- i akumulira sve elemente u kolekciji
instance Foldable OList where
    foldl f acc End        = acc
    foldl f acc (x :/: xs) =
            let newAcc = f acc x
            in  foldr (flip f) newAcc xs
            -- `foldl` je memorijski skuplji!

    foldr f init End        = init
    foldr f init (x :/: xs) =
            f x (foldr f init xs)

