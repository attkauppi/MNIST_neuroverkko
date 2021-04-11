# MNIST_neuroverkko

Tavoitteena on tehdä yksinkertainen neuroverkkosovellus, joka osaa tunnistaa käsinkirjoitettuja numeroita oikein kohtuullisella tarkkuudella. Joudun projektissa luultavasti myös toteuttamaan ainakin useamman aktivaatiofunktion pystyäkseni optimoimaan käsinkirjoitettujen numeroiden tunnistamistarkuuden riittävälle tasolle.

(Tarkennan määrittelydokumenttia vielä)

## Käytetty kieli

Java (v.11)

## Toteutettavat algoritmit

Tämä edellyttää ainakin seuraavien algoritmien toteuttamista

* Backpropagation algoritmi
* Aktivaatiofunktio (todennäköisesti toteutettava useampi tulosten optimoimiseksi)

## Datasetti

Datasettinä käytetään [MNIST (Modified National Institute of Standards and Technology database)](http://yann.lecun.com/exdb/mnist/) -datasettiä, joka koostuu käsinkirjoitetuista numeroista. 

### Syötteet

Syötteet ovat datasetin kuvia, jotka ovat ubyte-muodossa. Käytännössä algoritmi ottanee syötteikseen kunkin yksittäisen kuvan pikseliä vastaavan RGB-arvon, joka kuvaa kyseisen pikselin väriarvoa.

## Aika- ja tilatilavuudet

Tätä en osannut vielä arvioida. Selvitän asian pikimmiten.

## Dokumentaatiossa käytetty kieli

Suomi

## Opinto-ohjelma

Opiskelen tietojenkäsittelytieteen

## Viikkoraportit

* [Viikkoraportti 1](docs/viikkoraportti1.md)
* [Viikkoraportti 3](docs/viikkoraportti3.md)