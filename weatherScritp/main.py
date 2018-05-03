import csv
import math
from os import walk

import matplotlib
import numpy
import matplotlib.pyplot as pyplot


def calc_partial_steam_pressure(temperature):
    return 6.112 * math.exp(
        (17.67 *
         (float(temperature) - 273.15))
        / (float(temperature) - 273.15 + 243.5))


def calc_e(relative_humidity, partial_steam_pressure):
    return float(relative_humidity) * float(partial_steam_pressure) / 100


def calc_ztd(partial_steam_steam_pressure, atmospheric_pressure, temperature):
    return 0.002277 \
           * (float(atmospheric_pressure) + (1255 / float(temperature)) + 0.05) \
           * float(partial_steam_steam_pressure)


def calc_g(geo_wdth, height, height_wrf):
    return float(9.8063) * (1 - (pow(10, -7) * (float(height_wrf)+float(height)) / 2)
                     * 1 - 0.0026373
                     * math.cos(2 * float(geo_wdth))
                     + 5.9
                     * pow(10, -6)
                     * pow(math.cos(2 * float(geo_wdth)), 2))


def calc_p_when_not_given(height, pressure, temperature, height_wrf, g):
    return float(pressure) \
           / (
               pow(
                   ((float(temperature) - 0.0065 * (float(height) - float(height_wrf)))
                    / float(temperature)),
                   (g * 0.0289644) / (8.31432 * 0.0065))
           )


def calc_for_file(file):
    csv_data = []
    csv_results = []
    with open(file) as csvFile:
        reader = csv.reader(csvFile, delimiter=',', quotechar='|')
        for row in reader:
            csv_data.append(row)

    for row in csv_data:
        needed_steam_pressure = 0
        if row[3] == "interp":
            needed_steam_pressure = row[12]
        elif row[3] == "2m":
            needed_steam_pressure = calc_p_when_not_given(
                row[8],
                row[12],
                row[10],
                row[9],
                calc_g(
                    row[4],
                    row[8],
                    row[9]
                )
            )

        out = calc_ztd(
            calc_partial_steam_pressure(row[10]),
            needed_steam_pressure,
            calc_e(
                row[11],
                calc_partial_steam_pressure(row[10])
            )
        )
        csv_results.append(out)

    return csv_results

f = []
for (dirpath, dirnames, filenames) in walk("daneLab02/"):
    f.extend(filenames)
    break

known_res = []
for var in f:
    known_res.append(calc_for_file("daneLab02/"+var))

counter = 0
for res in known_res:
    for x in range(0, 6 * counter):
        res.insert(0, 0.0)

length = known_res.__len__()
size = known_res.__sizeof__()

average = []
for x in range(0, size):
    val = 0
    counter = 0
    for y in range(0, length):
        test_elm=known_res[y][x]
        if test_elm == 0:
            break
        val += test_elm
        counter += 1

    if counter == 0:
        average.append(0)
        continue
    average.append(val/counter)

print(average)

x = numpy.linspace(0, 48, 500)
pyplot.plot(average)
pyplot.title('Tmp chart')
pyplot.show()