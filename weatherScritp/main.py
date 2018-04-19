import csv

def calcE():


def calcZTD():
    staticVar_1 = 0.002277
    staticVar_2 = 1255
    staticVar_3 = 0.05


csvData = []
with open("daneLab02/surface_20150912_00.csv") as csvFile:
    reader = csv.reader(csvFile, delimiter=',', quotechar='|')
    for row in reader:
        csvData.append(row)

for row in csvData:
    if row[3] == 'iterp':
        pass
