# mnist_neuroneista

# MNIST_neuroverkko

Tavoitteena on tehdä yksinkertainen neuroverkkosovellus, joka osaa tunnistaa käsinkirjoitettuja numeroita oikein kohtuullisella tarkkuudella. Joudun projektissa luultavasti myös toteuttamaan ainakin useamman aktivaatiofunktion pystyäkseni optimoimaan käsinkirjoitettujen numeroiden tunnistamistarkuuden riittävälle tasolle.

* [Määrittelydokumentti](docs/maarittely.md)

## Viikkoraportit

* [Viikkoraportti 1](docs/viikkoraportti1.md)
* [Viikkoraportti 3](docs/viikkoraportti3.md)
* [Viikkorapotti 5](docs/viikkoraportti5.md)
* [Viikkorapotti 6](docs/viikkoraportti6.md)

# Puutteet

Työtä vaikeutti merkittävästi matemaattisten toimintojen itse implementointi. Periaatteessa algoritmien pitäisi toimia kohtuullisen hyvin, mutta ongelmia laskennassa luultavasti aiheuttavat itsekirjoitettuihin laskutoimenpiteisiin sisältyvät bugit. Kirjoitin vastaavan koodin myös pythonilla ja jo ensimmäisen epochin oikeat tulokset olivat yli 8000 / 10 000 oikein tunnistettua numeroa.

# Aineisto

Aineisto on numeromuotoista tietoa, jota voidaan lukea csv-tiedostoista tai ubyte-tiedostoista. Oletuksena on, että aineisto on 728 kolumnia pitkiä vektoreita, jotka edustavat 28x28 kokoisia kuvia. Kuvia lisäksi tuotetaan ohjelmassa lisää kääntämällä olemassa olevaan aineistoon kuuluvia kuvia hieman ja käyttämällä niitä sitten osana aineistoa.

Aineiston arvot 8-bittisiä pikselin tummuutta ilmentäviä väriarvoja eli väliltä 0-256. Nämä muunnetaan ohjelmassa asteikolle 0-1 yksinkertaisella jakolaskulla.

Aineiston voi ladata halutessaan [täältä](https://drive.google.com/drive/folders/1U4YwO4NG5DNXB3XwviydyWr1H8AIl4A4?usp=sharing)

# Testaus

Ohjelmaan kirjoitettujen testien pitäisi toimia suoraan.

# Muuta

Ohjelman käännösautomaatiotyökaluna on käytetty gradle 6.8.3:sta. Sovellus ei välttämättä ole yhteensopiva kaikkien gradle >= 7.0:ssa käytettyjen ominaisuuksien kanssa.

# Lähteitä

* http://cs231n.stanford.edu/slides/2018/cs231n_2018_ds02.pdf
* https://sudeepraja.github.io/Neural/#
* http://neuralnetworksanddeeplearning.com/#
* Artificial Neural Networks with Java - Tools for Building Neural Network Applications | Igor Livshin | Apress (no date). Available at: https://www.apress.com/gp/book/9781484244203 (Accessed: 22 May 2021).
* Neural Network Programming with Java: Create and unleash the power of neural networks by implementing professional Java code: Souza, Alan M.F., Soares, Fabio M.: 9781785880902: Amazon.com: Books (no date). Available at: https://www.amazon.com/Neural-Network-Programming-Java-Souza/dp/178588090X (Accessed: 22 May 2021).
* Watt, J., Borhani, R. and Katsaggelos, A. K. (2020) Machine Learning Refined: Foundations, Algorithms, and Applications, Higher Education from Cambridge University Press. Cambridge University Press. doi: 10.1017/9781108690935.
