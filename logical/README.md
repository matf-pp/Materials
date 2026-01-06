# Jezik Prolog

Prolog (eng. *PROgramming in LOGic*) je deklarativan programski jezik namenjen rešavanju zadataka simboličke prirode.  
Prolog se temelji na teorijskom modelu logike prvog reda. Početkom 1970-ih godina Alain Kolmerauer (eng. *Alain Colmerauer*) i Filipe Rousel (eng. *Philippe Roussel*) na Univerzitetu u Marselju (eng. *University of Aix-Marseille*), zajedno sa Robertom Kovalskim (eng. *Robert Kowalski*) sa Odeljka Veštačke Inteligencije (eng. *Department of Artificial Intelligence*) na Univerzitetu u Edinburgu (eng. *University of Edinburgh*), razvili su osnovni dizajn jezika Prolog.

## Instalacija BProlog-a

U okviru kursa će biti korišćena distribucija Prologa pod nazivom `BProlog`. `BProlog` se može preuzeti sa zvanične Veb strane:

[http://www.picat-lang.org/bprolog/](http://www.picat-lang.org/bprolog/)

Potrebno je preuzeti adekvatnu verziju za Vaš sistem i otpakovati je. U dobijenom direktorijumu će postojati izvršiva datoteka `bp` kojom se može pokrenuti `BProlog` interpreter. Preporučeno je dodati `bp` u `PATH` kako bi `BProlog` bio dostupan iz komandne linije.

Na primer, pretpostavimo da imamo 64bitni Linux. Potrebno je preuzeti datoteku `bp81_linux64.tar.gz` i smestiti je u direktorijum po izboru, na primer `/home/korisnik/Downloads`. Potom treba izvršiti sledeće naredbe:

```bash
cd ~/Downloads
tar -xvf bp81_linux64.tar.gz
sudo mv BProlog /opt
sudo ln -s /opt/BProlog/bp /usr/bin/bprolog
```

Nakon toga, `BProlog` interpreter se iz konzole može pokrenuti komandom `bprolog`.
