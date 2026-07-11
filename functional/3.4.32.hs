import Data.List (sort, sortBy)

data Termin
    = Predavanje String Int
    | Vezbe String Int
    | Kolokvijum String Int
    | Ispit String Int
    deriving (Eq, Ord)

instance Show Termin where
    show (Predavanje sala trajanje) = "[PREDAVANJE] " ++ sala ++ " " ++ show trajanje ++ "h"
    show (Vezbe sala trajanje) = "[VEZBE] " ++ sala ++ " " ++ show trajanje ++ "h"
    show (Kolokvijum sala trajanje) = "[KOLOKVIJUM] " ++ sala ++ " " ++ show trajanje ++ "h"
    show (Ispit sala trajanje) = "[ISPIT] " ++ sala ++ " " ++ show trajanje ++ "h"

trajanjeTermina :: Termin -> Int
trajanjeTermina (Predavanje _ trajanje) = trajanje
trajanjeTermina (Vezbe _ trajanje) = trajanje
trajanjeTermina (Kolokvijum _ trajanje) = trajanje
trajanjeTermina (Ispit _ trajanje) = trajanje

poslednjiIspit :: [Termin] -> Maybe (String, Int)
poslednjiIspit termini =
    case [(sala, trajanje) | Ispit sala trajanje <- sort termini] of
        [] -> Nothing
        ispiti -> Just (last ispiti)

sortirajPoTrajanju :: [Termin] -> [Termin]
sortirajPoTrajanju =
    sortBy (\t1 t2 -> compare (trajanjeTermina t1) (trajanjeTermina t2) <> compare t1 t2)
