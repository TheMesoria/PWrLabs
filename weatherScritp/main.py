import csv
import math


def calcPartialSteamPressure(temperature):
    return 6.112 * math.exp((17.67*(temperature-273.15))/(temperature-273.15+243.5))


def calcE(relativeHumidity, partialSteamPressure):
    return relativeHumidity * partialSteamPressure / 100


def calcZTD(partialSteamSteamPressure, atmosphericPressure, temperature):
    return 0.002277 * (atmosphericPressure + (1255 / temperature)+0.05)*partialSteamSteamPressure


csvData = []
with open("daneLab02/surface_20150912_00.csv") as csvFile:
    reader = csv.reader(csvFile, delimiter=',', quotechar='|')
    for row in reader:
        csvData.append(row)

for row in csvData:
    if row[3] == 'iterp':
        pass

print(calcPartialSteamPressure(10))
