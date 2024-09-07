let user = document.querySelector(".userInfo");
let userBlock = document.querySelector(".userMenu");

user.onmouseover = function() {
    userBlock.style.display = "block"
}

user.onmouseout = function(event) {
    if (!user.contains(event.relatedTarget)) {
        userBlock.style.display = "none";
    }
}