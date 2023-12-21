# Blokų grandinių technologijos
<h1>V0.3 BLOCKCHAIN</h1>
<b>Ketvirta užduotis: Sukurti išmanią sutartį bei ją atvaizduoti UI aplikacijoje</b>

Ši sutartis veikia kazino / loterijos principu. Skirtingi vartotojai gali užsiregistruoti į žaidimą, valiuta dedama į bendrą fondą. Po paskirto laiko, vienas atsitiktinis laimingas pasirinkimas tampa laimėtoju ir visi, kas jį buvo išsirinkę, pasidalina tarpusavyje prizinį fondą.

### Veikimo Principas

1. **Loterijos pradžia**
   - Ši išmanioji sutartis leidžia visiems vartotojams "Užsiregistruoti" į žaidimą. Per vieną žaidimą galima užsiregistruoti tik vieną kartą. Nustatyta suma - 0.05 ether(testinio)

2. **Tolimesnė eiga**
   - Pati išmanioji sutartis žaidimo niekada neužbaigs. Visos lėšos bus saugomos pačiame kontrakte. Žaidimo baigimas realizuojamas front'end aplikacijoje. Po paskirto laiko programa parenka laimingą atsakymą ir dalyviams išdalina prizus.

3. **Saugumas**
   - Iškviesti atsitiktinio laimingo atsakymo funkciją gali tik vartotojas, kuris "deploy'ino" pačią sutartį. Taipogi tik tas pats vartotojas ir tegali iškviesti funkciją, kuri išdalina prizus
   
4. **Pastebėjimai**
   - Veikimas buvo ištestuotas Sepolia testnet'e. Pilnavertis loterijos žaidimas buvo ištestuotas granache lokaliame tinkle
   
### Kaip naudotis
 Pirmiausiai pagal save susidėliokite truffle-config.js failą, o poto tiek kiek jūsų fantazija leis. Front-end paleidimui užteks paleisti index.html failą naršyklėje.
