let mainButton = document.querySelector(".backButton");
let usernameInput = document.getElementById("username");
let passwordInput = document.getElementById("password");
let usernameError = document.querySelector(".usernameError");
let passwordError = document.querySelector(".passwordError");
let errors = document.querySelectorAll(".error");
let form = document.querySelector(".form");

mainButton.addEventListener("click", function() {
    window.location.href = "/practice/main";
})

document.addEventListener("DOMContentLoaded", function() {
    form.addEventListener("submit", function(event) {
        usernameError.style.display = "none";
        passwordError.style.display = "none";
        let valid = true;

        if (usernameInput.value === "") {
            usernameError.style.display = "block";
            valid = false;
        }
        if (passwordInput.value === "") {
            passwordError.style.display = "block";
            valid = false;
        }
        if (usernameInput.value === "" && passwordInput.value === "") {
            usernameError.style.display = "block";
            passwordError.style.display = "block";
            valid = false;
        }

        if (!valid) {
            event.preventDefault();
        }
    })
})