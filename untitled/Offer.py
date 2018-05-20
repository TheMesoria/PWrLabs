class Offer:
    def __init__(self, name, value, author):
        self.value = value
        self.name = name
        self.author = author

    def __str__(self) -> str:
        return self.name + "," + str(self.value) + "," + self.author

    def get(self):
        return [self.name, self.value, self.author]

    @staticmethod
    def make_offer(name, value, author):
        return Offer(name, value, author)
