data Krug = Krug Double
data Prav = Prav Double Double
data Kvadrat = Kvadrat Double

class Oblik a where
  povrsina :: a -> Double
  obim :: a -> Double

instance Oblik Krug where
  povrsina (Krug r) = pi * r ^ 2
  obim (Krug r) = 2 * pi * r

instance Oblik Prav where
  povrsina (Prav a b) = a * b
  obim (Prav a b) = 2 * (a + b)

instance Oblik Kvadrat where
  povrsina (Kvadrat a) = a * a
  obim (Kvadrat a) = 4 * a
