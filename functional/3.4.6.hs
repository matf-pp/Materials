class Serializable a where
  -- Svaki tip koji je Serializable definise kako se zapisuje kao String.
  ser :: a -> String

data Point = MkPoint Float Float

data WeatherInfo = MkWeatherInfo Point Float

instance Serializable Float where
  ser = show

instance Serializable Point where
  ser (MkPoint x y) = "(" ++ show x ++ "," ++ show y ++ ")"

instance Serializable WeatherInfo where
  ser (MkWeatherInfo p t) =
    -- Ugnjezdeni podatak Point serijalizujemo pozivom iste funkcije ser.
    "{ 'loc': " ++ ser p ++ ", 'temp': " ++ show t ++ " }"
