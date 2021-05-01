# Viikkoraportti 6

## Ohjelman edistyminen

Projekti on jäänyt pahasti jumiin backpropagaation implementointiin matriisien ja vektorien kokoihin liittyvien virheiden vuoksi. Nyt uskon vihdoni löytäneeni kaavat, joilla saan laskettua tuloksia luotettavammin, jotta oppimista alkaa tapahtumaan koodissa.

Aloin jo epäilemään valitsemaani lähestymistapaani jättää neuronit mallintamatta ohjelmassani ja aloin koodaamaan neuroneitakin mukaan. Niistä onkin siinä mielessä ollut hyötyä, että matriisien koot on helpompi saada eri vaiheissa oikein, kun kaarien määrät voi oikeasti tarkistaa jostakin. Valitettavasti tämä lähestymistapa ei ollenkaan sovellu esim. mini-batchien käsittelyyn mallia opetettaessa.

Ilmeisesti osittain tämän viikon ongelmat ovat johtuneet siitä, että olen ymmärtänyt väärin, missä vaiheessa virheen laskemista välittäminen takaisin kohti verkon alkupäätä tapahtuu.

## Mitä teen seuraavaksi

Parannan projektin dokumentaatiota sekä pyrin saamaan työni loppuun tänä viikonloppuna ainakin pääosin. Tämä pätee edelleen sillä koko viikko on mennyt backpropagaation selvittelyyn.

## Aikaa käytetty tällä viikolla

Noin 30-35 tuntia.