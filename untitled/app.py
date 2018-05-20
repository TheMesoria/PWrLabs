from flask import Flask, render_template, request
import data_holder
import Offer

app = Flask(__name__)

offers = []

@app.route('/')
def index():
    return 'Hello World!'


@app.route('/show')
def show_offers():
    return render_template("show.html", offers=offers)


@app.route('/add', methods=["GET", "POST"])
def add_offer():
    if request.method == "POST":
        name = request.form['name']
        value = request.form['value']
        author = request.form['author']
        global offers
        offers.append(Offer.Offer.make_offer(name, value, author))
        return "OK"
    elif request.method == "GET":
        return render_template("add.html")


@app.route('/save')
def save():
    data_holder.save_offers_to_csv(offers)
    return "SAVED"

@app.route('/load')
def load():
    global offers
    offers = data_holder.read_offers_from_csv()
    return "READY"


if __name__ == '__main__':
    app.run(debug=True)
