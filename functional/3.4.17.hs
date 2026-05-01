data Oblik = Krug Float | Trougao Float | Prav Float Float | Kvadrat Float
  deriving (Show)

class PlanarniOblik a where
  obim :: a -> Float
  povrsina :: a -> Float

instance PlanarniOblik Oblik where
  obim (Krug r) = 2 * pi * r
  obim (Trougao a) = 3 * a
  obim (Prav a b) = 2 * (a + b)
  obim (Kvadrat a) = 4 * a

  povrsina (Krug r) = pi * r^2
  povrsina (Trougao a) = (sqrt 3 / 4) * a^2
  povrsina (Prav a b) = a * b
  povrsina (Kvadrat a) = a^2

ukupnaP :: (PlanarniOblik a) => [a] -> Float
ukupnaP = sum . map povrsina