let balanceBlock = document.querySelector(".balance");
let hiddenBlock = document.querySelector(".hiddenBalance");
let pfp = document.querySelector(".pfp");
let editBlock = document.querySelector(".editBlock");
let fileBlock = document.querySelector(".fileForm");
let edit = document.querySelector("#edit");

balanceBlock.onmouseover = function() {
    hiddenBlock.style.display = "block";
}

balanceBlock.onmouseout = function(event) {
    if (!balanceBlock.contains(event.relatedTarget)) {
        hiddenBlock.style.display = "none";
    }
}

pfp.onmouseover = function() {
    editBlock.style.display = "block";
}

pfp.onmouseout = function() {
    editBlock.style.display = "none";
}

edit.onclick = function() {
    fileBlock.style.display = "block";
    setTimeout(function() {
        document.addEventListener("click", function(event) {
            if (fileBlock.style.display === "block" && !fileBlock.contains(event.target) && !edit.contains(event.target)) {
                fileBlock.style.display = "none";
            }
        });
    }, 0);
}

function openTab(event, blockClass) {
    let buttons = document.querySelectorAll(".tabButton");
    let tabs = document.querySelectorAll(".bodyBlock");

    for (let i = 0; i < tabs.length; i++) {
        tabs[i].style.display = "none";
    }

    for (let i = 0; i < buttons.length; i++) {
        buttons[i].classList.remove("active");
    }

    document.querySelector(blockClass).style.display = "block";
    event.currentTarget.classList.add("active");
}

document.querySelector(".tabButton").click();

function addFriendBlock() {
    document.querySelector(".hiddenSearch").style.display = "block";
}

function sendFriendInfo(idUser) {
    let username = document.getElementById(idUser).value;
    console.log(username);
}