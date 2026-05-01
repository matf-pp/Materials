data Komponenta = R | G | B deriving (Show, Eq)

data Boja = MkBoja { r :: Int, g :: Int, b :: Int }
  deriving (Show, Eq)

komponenta :: Komponenta -> Boja -> Int
komponenta R = r
komponenta G = g
komponenta B = b

ponisti :: Komponenta -> Boja -> Boja
ponisti R (MkBoja _ g b) = MkBoja 0 g b
ponisti G (MkBoja r _ b) = MkBoja r 0 b
ponisti B (MkBoja r g _) = MkBoja r g 0

pomesaj :: Boja -> Boja -> Maybe Boja
pomesaj (MkBoja r1 g1 b1) (MkBoja r2 g2 b2)
  | any (>255) [r1+r2, g1+g2, b1+b2] = Nothing
  | otherwise = Just (MkBoja (r1+r2) (g1+g2) (b1+b2))

maska :: [Komponenta] -> Boja -> Boja
maska ks (MkBoja r g b) =
  MkBoja (if R `elem` ks then r else 0)
         (if G `elem` ks then g else 0)
         (if B `elem` ks then b else 0)