# Blokų grandinių technologijos
<h1>v0.1</h1>
<b>Pirma užduotis buvo sukurti savo maišos("hash") funkciją, kuri atitiktų reikalavimus:</b>

* Būtų deterministinė

* Su tuo pačiu input visada toks pat output

* Output visada turi būti vienodo dydžio

* Maišos funkcija apskaičiuojama greitai/efektyviai

* Iš hash'o funkcijos praktiškai neįmanoma atgaminti pradinių duomenų

* Atspari kolizijai

* Bent minimaliai pakeitus įvedimą turi smarkiai keistis output
<br>


*Hash'avimo pavyzdys per komandinę eilutę (Vartotojas pats įveda žinutę)*
![Hash function example](./images/userInput.png)

*Hash'avimo pavyzdys per komandinę eilutę (Nuskaitomas failas)*
![Hash function example](./images/readFile.png)

<h2>Hash funkcijos pseudo kodas:</h2>

```

1. Sukuriamas tuščias 256bit'ų String'as (visos reikšmės nuliukai)

2. Gaunama vartotojo įvestis(Nuskaitomas failas arba įvedama programos vykdymo metu)

3. Tikrinama ar įvesties simboliai yra ASCII lentelėje. Jeigu nėra - supaprastina į artimiausią
   reikšmę, kad priklausytų ASCII lentelėje

4. Išmaišoma pradinė vartotojo įvestis ( gaunama visų simbolių ASCII vertė, ji susumuojama,
padalinama iš 256 ir gaunamas indeksas per kurį visas dvejetainis string yra stumiamas dešinėn)

5. Sukuriamas "Salt":
   - Imamas kiekvienas pradinės įvesties simbolis (iki pirmųjų 10-ies simbolių)
     - Gaunama simbolio ASCII vertė
     - Ši ASCII vertė yra pridedama prie "Salt" reikšmės
     - Iš praeitai gautos ASCII vertės yra imamas kiekvienas simbolis
       - Gaunama to atskiro simbolio ASCII vertė
       - Ši ASCII vertė pridedama vėl prie "Salt" reikšmės
         - Šis procesas pakartojamas gilyn 3-ią kartą

6. "Druska" pridedama prie pradinės įvesties

7. Vykdomas "žinutės" hash'avimas:
   - Imamas kiekvienas "žinutės" simbolis atskirai
   - Gaunama simbolio ASCII vertė
   - Imamas 256bit'ų Stringo indeksas pagal ASCII vertę:
     - Jei bitas yra 0 tai pakeičiamas į 1 ir atvirkščiai
   - Visi dvejetainiai bit'ai yra stumiami/perkeliami į dešinę pagal simbolio ASCII vertę

8. Dvejetainės eilutės String'as konvertuojamas į šešioliktainę

```

<h2> Eksperimentiniai tyrimai</h2>

1. Įsitikinti, kad hash funkcija nepriklausomai nuo Input'o, Output'ai visada yra vienodo dydžio, o to paties failo hash'as yra tas pats (deterministiškumas).
   
   * Bent du failai būtų sudaryti tik iš vieno, tačiau skirtingo, simbolio.
   * Bent du failai būtų sudaryti iš daug (> 1000) atsitiktinai sugeneruotų simbolių.
   * Bent du failai būtų sudaryti iš daug (> 1000) simbolių, bet skirtųsi vienas nuo kito tik vienu (pvz. vidurinėje pozicijoje esančiu) simboliu.
   * Tuščio failo.

<b>Analizė:</b> <i>Maišos funkcija susidorojo su šiomis užduotimis. Vienas skirtingas simbolis pateikė visiškai skirtingą hash. Hash tokiam pačiam įvedimui visada vienodas. Iš pradžių tuščiam failui negeneravo hash, bet ši klaida buvo ištaisyta.</i>

----------------------------------------------------------------------------------

2. Pagal konstitucija.txt failą atlikti spartos analizę:
   
   * Patikrinti kiek laiko hash'uos 2eilutes, vėliau 4eilutes, 8 ir t.t.
  
<b>Analizė:</b> <i>Iš pradžių buvo problema, kad tekste yra lietuviškos raidės, nes jų nėra ASCII lentelėje. Šią problemą sutvarkiau konvertuodamas raides kaip č į c ar ę į e, taip galėdamas vėl naudotis ASCII lentele. Su greičiu problemų nebuvo, maišos funkcija apskaičiuojama greitai, nepriklausomai nuo eilučių kiekio.</i>

![graph](./images/graph.png)

----------------------------------------------------------------------------------

3. Patikrinti ar su dideliu duomenų kiekiu hash'ų reikšmė nesutampa:
   
    * Sugeneruoti 100tūkst. String porų, kur porų String ilgis vienodas, bet pats turinys yra atsitiktinis
  (String ilgiai nuo 10simbolių iki 1000)
  
<b>Analizė:</b> <i>Galutinis rezultatas: 0 pasikartojimų, 100tūkst. nepasikartojimų. Tai reiškia, kad nuskaitant faile String poras nebuvo dviejų vienodų hash reikšmių.</i>

----------------------------------------------------------------------------------

4. Patikrinti ar su dideliu panašių duomenų kiekiu  hash'ų reikšmė nesutampa:
   
   * Eksperimentas panašus kaip ir 3. bet skiriasi, kad visos String poros skiriasi tik 1-u simboliu
   * Taip pat patikrinti kiek procentaliai reikšmės skiriasi bit'ų ir hex'ų lygmenyje
   * Pateikti minimalią, maksimalią ir vidutines procentines reikšmes
  
<b>Analizė:</b> <i>Maišos funkcijai ši užduotis sekėsi sunkiau. Buvo labai daug hash'ų pasikartojimų ( net 66proc. )

Bit'ų lygmenyje rezultatai: MIN - 0proc. MAX - 69proc. AVG - 15.09proc.

Hex'ų lygmenyje rezultatai: MIN - 0proc. MAX - 100proc. AVG - 27.67proc.</i>

 * MIN 0 - reiškia, kad du hash'ai yra vienodi

 * MAX 100 - reiškia, kad du hash'ai yra visiškai skirtingi, nėra nei vieno vienodo elemento

 * AVG - kuo mažesnis skaičius, tuo reiškias panašesni yra hash'ai

<h2>Galutinės išvados</h2>

<i> Maišos funkcija yra veikianti ir gan neblogai atlieka savo darbą. Bet yra vietų, kur reikia patobulinti. Tai ypač pasireiškia, kai imamas didelis duomenų kiekis ir poros skiriasi tik vienu simboliu. Vieną silpnumą įvardinčiau, kad bandant kurti šią funkciją nebuvo pasitelktos įvairios matematinės formulės. Buvo bandoma "maišyti" viską kiek galima daugiau, kad tas hash gautųsi kuo labiau atsitiktinesnis</i>


<h1>v0.2</h1>

<i>Buvo nuspręsta patobulinti versiją dėl vienos priežasties: atlikus eksperimentą su 100k String porų, kur pora tesiskiria tik vienu simboliu (Patį string sudaro 1000simbolių) pasitaikė labai didelis atvejis, kai hash reikšmės sutampa (V0.1 versijoje iš 100k net 66k sutapimai buvo).</i>

<b> Kas pasikeitė </b>

<i>Buvo pridėta tik viena papildoma funkcija pačioje pradžioje. Prieš pridedant "salt", dabar input'as yra "pamaišomas", kas suteikia didesnį atsitiktinumo šansą lyginant su panašiais input'ais. Taipogi patobulintos testavimo klasės lengvesniam testavimui</i>

<h2> Eksperimentiniai tyrimai</h2>

1. Įsitikinti, kad hash funkcija nepriklausomai nuo Input'o, Output'ai visada yra vienodo dydžio, o to paties failo hash'as yra tas pats (deterministiškumas).
   
   * Bent du failai būtų sudaryti tik iš vieno, tačiau skirtingo, simbolio.
   * Bent du failai būtų sudaryti iš daug (> 1000) atsitiktinai sugeneruotų simbolių.
   * Bent du failai būtų sudaryti iš daug (> 1000) simbolių, bet skirtųsi vienas nuo kito tik vienu (pvz. vidurinėje pozicijoje esančiu) simboliu.
   * Tuščio failo.

<b>Analizė:</b> <i>Viskas kaip ir pirmoje versijoje, pakitimų nematyti. Maišos funkcija atlieka savo darbą kaip ir pridera.</i>

----------------------------------------------------------------------------------

2. Pagal konstitucija.txt failą atlikti spartos analizę:
   
   * Patikrinti kiek laiko hash'uos 2eilutes, vėliau 4eilutes, 8 ir t.t.
  
<b>Analizė:</b> <i>Pasitaikė įdomus dalykas: per šį eksperimentą pastebėta, kad visus input'us maišos funkcija įvykdo per 1-3 milisekundes. Ir nesvarbu kokio dydžio failas bebūtų. Deja negaliu paaiškinti kodėl taip vyksta, nes funkcionalumo tose vietose nekeičiau. Laikas kaip tik turėtų būti pailgėjęs.</i>

----------------------------------------------------------------------------------
3. Patikrinti ar su dideliu duomenų kiekiu hash'ų reikšmė nesutampa:
   
    * Sugeneruoti 100tūkst. String porų, kur porų String ilgis vienodas, bet pats turinys yra atsitiktinis
  (String ilgiai nuo 10simbolių iki 1000)
  
<b>Analizė:</b> <i>Galutinis rezultatas: 0 pasikartojimų, 100tūkst. nepasikartojimų. Kaip ir pirmoje versijoje.</i>

![graph](./images/hashCompare.png)

----------------------------------------------------------------------------------

4. Patikrinti ar su dideliu panašių duomenų kiekiu  hash'ų reikšmė nesutampa:
   
   * Eksperimentas panašus kaip ir 3. bet skiriasi, kad visos String poros skiriasi tik 1-u simboliu
   * Taip pat patikrinti kiek procentaliai reikšmės skiriasi bit'ų ir hex'ų lygmenyje
   * Pateikti minimalią, maksimalią ir vidutines procentines reikšmes
  
<b>Analizė:</b> <i>Būtent čia šioje versijoje matosi didelė pažanga. Praeitoje versijoje pasikartojimų skaičius buvo 66tūkst, o dabar pavyko sumažinti iki 500. Taipogi skyrėsi ir procentiniai skaičiai bit'ų ir hex'ų lygmenyje:</i>

*Bit'ų lygmenyje rezultatai: MIN - 0proc. MAX - 67proc. AVG - 49.24proc.*

![graph](./images/bitDifference.png)
<br>
*Hex'ų lygmenyje rezultatai: MIN - 0proc. MAX - 100proc. AVG - 92.57proc.</i>*

![graph](./images/hexDifference.png)


<h2>Galutinės išvados</h2>

<i> Versija V0.2 tapo geresnė už V0.1, tačiau tai vistiek nėra idealus variantas. Buvo užčipuota jos viena iš silpnesnių pusių, bet ir to buvo negana, vis dar yra nemažai pasikartojimų, kai tarp string'ų(kurie yra ilgi) skiriasi tik vienas simbolis.</i>