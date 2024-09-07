let paymentButton = document.querySelector("#payButton");
let number = document.querySelector(".number");
let cvc = document.querySelector(".cvc");
let payment = document.querySelector(".date");
let price = document.querySelector("#priceHelper");
let id = document.querySelector("#idHelper");

paymentButton.onclick = function() {
    async function transaction(number, cvc, date, price, id) {
        const response = await fetch("http://localhost:8080/practice/bridge", {
                                                method: "POST",
                                                headers: {"Content-Type": "application/json"},
                                                body: JSON.stringify({
                                                    cardNumber: number,
                                                    cvc: cvc,
                                                    date: date,
                                                    price: price,
                                                    id: id
                                                })
        })
        if (response.ok) {
            window.location.href = `/practice/films/${id}`;
        }
    }

    transaction(number.value, cvc.value, payment.value, price.value, id.value);
}