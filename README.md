# Matematiikka

![\Large x=\frac{-b\pm\sqrt{b^2-4ac}}{2a}](https://latex.codecogs.com/svg.latex?x%3D%5Cfrac%7B-b%5Cpm%5Csqrt%7Bb%5E2-4ac%7D%7D%7B2a%7D)

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

## Viimeinen kerros ja "kustannuksen laskenta"

### MSE


$$
\frac{\partial f}{\partial b}=\frac{1}{n}\sum_{i=1}^{n}-2\left ( y_{i}-\left ( mx_{i}+b \right ) \right ),
$$