import csv
import os
from math import exp, cos


def dane():
    licznik = 0
    ktoryPlik = 0
    ktoraGodzina = 0
    dataGodzina = ""
    licznikZapisuDaty = 0
    for filename in os.listdir(os.path.join(os.path.dirname(_file_),
                                            'Dane/')):
        licznik += 1
    lista = [[0 for x in range(24)] for y in range(licznik)]
    for filename in os.listdir(os.path.join(os.path.dirname(_file_),
                                            'Dane/')):
        licznikZapisuDaty = 0
        with open(os.path.join(os.path.dirname(_file_),
                               'Dane/', filename), newline='') as csvfile:
            spamreader = csv.reader(csvfile, delimiter=' ', quotechar='|')
            for row in spamreader:
                splited = [x.strip() for x in row[0].split(',')]
                if splited[2] == "GDAN":
                    if ktoryPlik != 0:
                        if licznikZapisuDaty >= 18:
                            dataGodzina = dataGodzina + "," + str(splited[0]) + " " + str(splited[1])
                    else:
                        if licznikZapisuDaty == 0:
                            dataGodzina = str(splited[0]) + " " + str(splited[1])
                        else:
                            dataGodzina = dataGodzina + "," + str(splited[0]) + " " + str(splited[1])
                    licznikZapisuDaty += 1
                    if splited[3] == "interp":
                        P = float(splited[12])
                    else:
                        H = float(splited[8])
                        Pi = float(splited[12])
                        Ti = float(splited[10])
                        Hi = float(splited[9])
                        gamma = 0.0065
                        M = 0.0289644
                        R = 8.31432
                        sigma = 2
                        G1 = (cos(2 * sigma)) ** 2
                        G2 = 1 - 0.0026373 * cos(2 * sigma)
                        G3 = G2 + 5.9 * (10 ** (-6)) * G1
                        G = 9.8063 * (1 - (10 ** (-7)) * ((Hi + H) / 2) * G3)
                        P1 = (Ti - gamma * (H - Hi)) / Ti
                        P = Pi * (P1 ** ((G * M) / (R * gamma)))
                    T = float(splited[10])
                    Esat = 6.112 * exp((17.67 * (T - 273.15)) /
                                       ((T - 273.15) + 243.5))
                    RH = float(splited[11])
                    E = RH * Esat / 100
                    ZTD = 0.002277 * (P + ((1255 / T) + 0.05) * E) * 1000
                    lista[ktoryPlik][ktoraGodzina] = ZTD
                    ktoraGodzina += 1
        ktoryPlik = ktoryPlik + 1
        ktoraGodzina = 0

    tabDanych = []
    for i in range(24+6*(ktoryPlik-1)):
        suma = 0
        licznik = 0
        prawd = 1
        j = i + 1
        dziel = 0
        while j > 24:
            licznik += 1
            j -= 6
        while j > 0:
            if licznik <= ktoryPlik-1:
                suma += lista[licznik][j - 1] * (1/prawd)
                prawd += 1
                j -= 6
                licznik += 1
            else:
                break
        while prawd > 1:
            prawd -= 1
            dziel += 1 / prawd
        tabDanych.append(suma / dziel)
    f = open("file.csv", "w", encoding="utf8")
    danetxt = ""
    for i in range(24 + 6 * (ktoryPlik - 1)):
        danetxt = danetxt + str(tabDanych[i])
        if i != 23 + 6 * (ktoryPlik - 1):
            danetxt = danetxt + ','
    f.write(danetxt)
    f.write("\n")
    f.write(dataGodzina)
    return tabDanych


tab = dane()
print(tab)