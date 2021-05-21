# mnist_neuroneista

# MNIST_neuroverkko

Tavoitteena on tehdä yksinkertainen neuroverkkosovellus, joka osaa tunnistaa käsinkirjoitettuja numeroita oikein kohtuullisella tarkkuudella. Joudun projektissa luultavasti myös toteuttamaan ainakin useamman aktivaatiofunktion pystyäkseni optimoimaan käsinkirjoitettujen numeroiden tunnistamistarkuuden riittävälle tasolle.

(Tarkennan määrittelydokumenttia vielä)

## Käytetty kieli

Java (v.11)

## Toteutettavat algoritmit

Tämä edellyttää ainakin seuraavien algoritmien toteuttamista

* Backpropagation algoritmi
* Aktivaatiofunktio (todennäköisesti toteutettava useampi tulosten optimoimiseksi)
    * Toteutettu:
        * Identity-aktivaatiofunktio (hyödynnetään vain input-layerissa, vastaa 1*kertomista)
        * LeakyReLu
        * ReLu
        * Sigmoid
        * Softplus
        * Softmax (pitäisi soveltua hyvin luokitteluongelman ratkaisuun)
* Kustannus/Loss-funktiot
    * Quadratic eli virheiden neliöitä hyödyntävä kustannusfunktio
    * Half Quadratic
    * MSE - Mean Square Error
    * Cross Entropy
* "Optiomointifunktiot"
    * Gradient descent
    * Momentum

## Datasetti

Datasettinä käytetään [MNIST (Modified National Institute of Standards and Technology database)](http://yann.lecun.com/exdb/mnist/) -datasettiä, joka koostuu käsinkirjoitetuista numeroista. 

### Syötteet

Syötteet ovat datasetin kuvia, jotka ovat ubyte-muodossa. Käytännössä algoritmi ottanee syötteikseen kunkin yksittäisen kuvan pikseliä vastaavan RGB-arvon, joka kuvaa kyseisen pikselin väriarvoa.

## Aika- ja tilatilavuudet

Tätä en osannut vielä arvioida. Selvitän asian pikimmiten.

## Dokumentaatiossa käytetty kieli

Suomi

## Opinto-ohjelma

Opiskelen tietojenkäsittelytieteen kandidaatinohjelmassa.

## Viikkoraportit

* [Viikkoraportti 1](docs/viikkoraportti1.md)
* [Viikkoraportti 3](docs/viikkoraportti3.md)
* [Viikkorapotti 5](docs/viikkoraportti5.md)
* [Viikkorapotti 6](docs/viikkoraportti6.md)

# Matematiikka

## Neuronin input

$$
z= \left ( \sum_{i=0}^{n} w_{i} x_{i} \right ) + b,
$$

jossa

* $x$ koostuu edellisen kerroksen lähettämistä arvoista.
* $b$ on kerroksen "bias"
* $W$ on edellisen kerroksen painot.

Yksinkertaistettuna siis "forward propagointi" etenee seuraavasti:

* input layer lähettää saamansa signaalin
* Ensimmäinen "hidden layer" ottaa signaalin vastaan ja kertoo sen kaarien painoilla (vektori * matriisi), johon lisätään vielä bias
* Sovelletaan aktivaatiofunktiota. Mikäli arvo on yli 0, signaali jatkaa eteenpäin.

## Backpropagaatio

### Output-kerros

Output kerroksessa layerin virhe voidaan laskea seuraavasti (sievennetty merkittävästi):

$$\begin{bmatrix}
a^{L}_{11}-y_{11} & a^{L}_{12}-y_{12}\\ 
a^{L}_{21}-y_{21} & a^{L}_{22}-y_{22}
\end{bmatrix}
\odot  
\begin{bmatrix}
\sigma'{11}(a^{L}_{11}) & \sigma'(a^{L}_{12}) \\ 
\sigma'{22}(a^{L}_{21}) & \sigma'(a^{L}_{22})
\end{bmatrix}
=
\begin{bmatrix}
\delta^{L}_{11} & \delta^{L}_{12} \\ 
\delta^{L}_{21} & \delta^{L}_{22})
\end{bmatrix}$$

jossa $\sigma$ tarkoittaa layerin painoilla ja biasilla painotettuja inputteja, joista otetaan derivaatta, $a$ tarkoittaa aktivaatiota eli edellä mainittua painotettua inputtia, johon on sovellettu kyseisen kerroksen aktivaatiofunktiota. 

Output-kerroksen painojen korjaus tästä saadaan kertomalla lopputulos eli $\sigma_{3}$ edellisen kerroksen outputilla eli:

$$\delta_{L} x^{T}_{2}$$

Muissa piilotetuissa kerroksissa laskutapa hieman muuttuu, sillä edellistä virhettä pyritään nyt viemään taaksepäin verkossa.

Tällöin piilotetun kerroksen virhe muodostuu seuraavasti, jos output-kerros oli kolmannessa kerroksessa.

$\delta_{2} x^{T}_{1}$, jossa $\delta{2}$ muodostuu $W^{T}_{3} \delta{3} \odot \sigma'(W_{2} x_{1})$, jossa sigmaa edeltävä osuus siis itse asiassa sisältää output-kerroksen virheen.

## Viimeinen kerros ja "kustannuksen laskenta"

### MSE

$$
\frac{\partial f}{\partial b}=\frac{1}{n}\sum_{i=1}^{n}-2\left ( y_{i}-\left ( mx_{i}+b \right ) \right ),
$$

# Puutteet

Työtä vaikeutti merkittävästi matemaattisten toimintojen itse implementointi. Periaatteessa algoritmien pitäisi toimia kohtuullisen hyvin, mutta ongelmia laskennassa luultavasti aiheuttavat itsekirjoitettuihin laskutoimenpiteisiin sisältyvät bugit. Kirjoitin vastaavan koodin myös pythonilla ja jo ensimmäisen epochin oikeat tulokset olivat yli 8000 / 10 000 oikein tunnistettua numeroa.

# Aineisto

Aineisto on numeromuotoista tietoa, jota voidaan lukea csv-tiedostoista tai ubyte-tiedostoista. Oletuksena on, että aineisto on 728 kolumnia pitkiä vektoreita, jotka edustavat 28x28 kokoisia kuvia. Kuvia lisäksi tuotetaan ohjelmassa lisää kääntämällä olemassa olevaan aineistoon kuuluvia kuvia hieman ja käyttämällä niitä sitten osana aineistoa.

Aineiston voi ladata halutessaan (täältä)[https://drive.google.com/drive/folders/1U4YwO4NG5DNXB3XwviydyWr1H8AIl4A4?usp=sharing]

# Lähteitä

* http://cs231n.stanford.edu/slides/2018/cs231n_2018_ds02.pdf
* https://sudeepraja.github.io/Neural/#
