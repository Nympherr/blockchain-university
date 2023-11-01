# Blokų grandinių technologijos
<h1>v0.1 BLOCKCHAIN</h1>
<b>Antra užduotis: supaprastintos blokų grandinės (blockchain) kūrimas</b>

Ši programa leidžia generuoti vartotojus, transakcijas ir kasti naujus blokus. Programa sukuria blokus ir talpina juos į atskirus failus.

### Veikimo Principas

1. **Vartotojų Generavimas**
   - Pirmiausia, programa generuoja atsitiktinius vartotojus.(Arba dirbame su jau sukurtais vartotojais)
   - Šie vartotojai talpinami atskirame faile, esančiame kataloge `blockchain/users`.

2. **Transakcijų Generavimas**
   - Toliau programa sukuria atsitiktines transakcijas tarp vartotojų.
   - Transakcijos yra laikomos atskirame faile `blockchain/transactions` ir vėliau naudojamos blokų kūrimui.

3. **Blokų Kasimas**
   - Programa imasi 100 transakcijų iš transakcijų pool'o ir bando juos įdėti į naują bloką.
   - Tada blokas yra kasamas naudojant Proof-of-Work (PoW) algoritmą.

4. **Blokų Saugojimas**
   - Kiekvienas sėkmingai iškastas blokas yra išsaugomas į failą, esantį kataloge `blockchain/blocks`.

5. **Transakcijų Pašalinimas**
   - Po kiekvieno sėkmingai iškasto bloko, jo transakcijos yra pašalinamos iš transakcijų pool'o.