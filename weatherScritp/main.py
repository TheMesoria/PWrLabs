import csv
import math


def calc_partial_steam_pressure(temperature):
    return 6.112 * math.exp((17.67*(temperature-273.15))/(temperature-273.15+243.5))


def calc_e(relative_humidity, partial_steam_pressure):
    return relative_humidity * partial_steam_pressure / 100


def calc_ztd(partial_steam_steam_pressure, atmospheric_pressure, temperature):
    return 0.002277 * (atmospheric_pressure + (1255 / temperature) + 0.05) \
           * partial_steam_steam_pressure


def calc_g(geo_wdth, height, height_wrf):
    return 9.8063 * (1 - (pow(10, -7) * (height_wrf+height) / 2)
                     * 1 - 0.0026373
                     * math.cos(2 * geo_wdth)
                     + 5.9
                     * pow(10, -6)
                     * pow(
                     math.cos(2 * geo_wdth), 2)
                     )


def calc_p_when_not_given(height, pressure, temperature, height_wrf, g):
    return pressure / (pow(((temperature - 0.0065*(height-height_wrf)) / temperature),
                          (g * 0.0289644) / (8.31432 * 0.0065)))


csvData = []
with open("daneLab02/surface_20150912_00.csv") as csvFile:
    reader = csv.reader(csvFile, delimiter=',', quotechar='|')
    for row in reader:
        csvData.append(row)

for row in csvData:

    if row[3] == "interp":
        needed_steam_pressure=row[12]
    elif row[3] == "2m":
        needed_steam_pressure=calc_p_when_not_given(
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

    out=calc_ztd(
        calc_partial_steam_pressure(row[10]),
        needed_steam_pressure,
        calc_e(
            row[11],
            calc_partial_steam_pressure(row[10])
        )
    )
    print(out)

print(calc_partial_steam_pressure(10))
