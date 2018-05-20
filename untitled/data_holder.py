import csv
import Offer

def read_offers_from_csv():
    with open('data.csv', newline='') as File:
        reader = csv.reader(File)
        arr = []
        for row in reader:
            arr.append(Offer.Offer.make_offer(row[0], int(row[1]), row[2]))
        return arr


def save_offers_to_csv(offers):
    writer = csv.writer(open("data.csv", 'w'))
    with open('data.csv', newline='') as File:
        for row in offers:
            print(row)
            writer.writerow(row.get())
