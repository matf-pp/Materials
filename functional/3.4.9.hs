data Point2D = MkPoint Float Float deriving (Show, Eq)

x :: Point2D -> Float
x (MkPoint a _) = a

y :: Point2D -> Float
y (MkPoint _ b) = b

point :: (Float, Float) -> Point2D
point (a, b) = MkPoint a b

line :: Point2D -> Point2D -> (Float, Float)
line p1 p2 =
  let k = (y p2 - y p1) / (x p2 - x p1)
      n = y p1 - k * x p1
  in (k, n)

dist :: Point2D -> Point2D -> Float
dist p1 p2 =
  sqrt ((x p2 - x p1)^2 + (y p2 - y p1)^2)

maxP :: (Point2D -> Float) -> [Point2D] -> Float
maxP f lst = maximum (map f lst)