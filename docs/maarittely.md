# Määrittelydokumentti

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

Näiden valinta ei ollut kovin suunniteltua vaan käytännössä kaikkia näitä kannattaa olla projektissa useita, jotta verkon toimintaa voi yrittää optimoida kokeilemalla erilaisia yhdistelmiä. Erityisesti Cross Entropyn ja Momentumin yhdistelmän tulisi kuitenkin selviytyä varsin hyvin lukujen tunnistamisesta. Lisäksi Softmax on tällöin varsin sopiva vaihtoehto, sillä se pyrkii valitsemaan output-layerilta suurimman arvon. Tässä tapauksessa siis tunnistamaan todennäköisimmän numeron, jota kuva voisi esittää pikseliarvojen perusteella.

Näiden lisäksi mukana on eräänlainen dropout-implementaatio, joka pudottaa sattumanvaraisesti noin puolet piilotettujen kerrosten neuroneista opetuksen aikana, minkä pitäisi johtaa korkeampilaatuiseen oppimiseen. Tätä ilmiötä en kuitenkaan pystynyt todentamaan sillä verkkoni toiminnassa oli ilmeisesti laskutoimituksiin liittyviä ongelmia.

## Datasetti

Datasettinä käytetään [MNIST (Modified National Institute of Standards and Technology database)](http://yann.lecun.com/exdb/mnist/) -datasettiä, joka koostuu käsinkirjoitetuista numeroista. 

### Syötteet

Syötteet ovat datasetin kuvia, jotka ovat ubyte-muodossa. Käytännössä algoritmi ottanee syötteikseen kunkin yksittäisen kuvan pikseliä vastaavan RGB-arvon, joka kuvaa kyseisen pikselin väriarvoa.

## Dokumentaatiossa käytetty kieli

Suomi

Koodissa olen käyttänyt englantia pääasiallisena dokumentointikielenä, sillä aihepiirin sanasto suomeksi on vieras.

## Aika- ja tilatilavuudet

Molemmat ovat erittäin suuret, sillä ohjelman kehitys ei ehtinyt optimointivaiheeseen.


## Opinto-ohjelma

Opiskelen tietojenkäsittelytieteen kandidaatinohjelmassa.

## Matematiikka - jonkin verran koodista löytyvää matematiikkaa kuvattuna

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

$$
\begin{bmatrix}
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
\end{bmatrix}
$$

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