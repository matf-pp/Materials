sufiks :: [a] -> [[a]]
-- scanr gradi sve rezultate rekurzije zdesna, sto ovde daje sve sufikse liste.
sufiks = scanr (:) []
