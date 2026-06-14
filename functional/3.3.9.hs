data UList a = Empty | Cons a (UList a)

instance Show a => Show (UList a) where
  show xs = "<" ++ show (toList xs) ++ ">"
    where
      -- Prikaz pravimo tako sto nasu rekurzivnu listu prvo pretvorimo u obicnu Haskell listu.
      toList Empty = []
      toList (Cons x xs) = x : toList xs

instance Eq a => Eq (UList a) where
  Empty == Empty = True
  Cons x xs == Cons y ys = x == y && xs == ys
  _ == _ = False

instance Foldable UList where
  foldr _ z Empty = z
  -- foldr na nasoj listi ima isti oblik kao rekurzija nad Cons konstruktorom.
  foldr f z (Cons x xs) = f x (foldr f z xs)
