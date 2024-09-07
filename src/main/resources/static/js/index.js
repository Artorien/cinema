let registerButton = document.querySelector(".registerButton");
let loginButton = document.querySelector(".loginButton");

registerButton.addEventListener("click", function() {
    window.location.href = "/practice/registration";
})

loginButton.addEventListener("click", function() {
    window.location.href = "/practice/login";
})