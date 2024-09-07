let stars = document.querySelectorAll(".bxs-star");
let chosenStar = 0;
let form = document.getElementById("review-form");
let filmId = document.getElementById("hiddenFilmId").value;
let logoButton = document.querySelector(".logo");
let reviews = document.querySelectorAll(".review");
let deleteButtons = document.querySelectorAll(".bx-trash-alt");
let updateButtons = document.querySelectorAll(".bx-edit-alt");
let utilities = document.querySelectorAll(".utilities");
let ownersOfTheReviews = document.querySelectorAll("#userOfTheReview");
let formArea = document.querySelector(".review-form");

let watchFilmButton = document.querySelector(".watchButton");
let goToLibraryButton = document.querySelector(".libraryButton");
let goToBankAppButton = document.querySelector(".bankButton");
let price = document.querySelector("#priceHelper").value;
let usernameHelper = document.querySelector(".usernameHelper");

let user = document.querySelector(".userInfo");
let userBlock = document.querySelector(".userMenu");

document.addEventListener("DOMContentLoaded", function () {

    async function checkIfBought(filmId, price) {
        const response = await fetch("/practice/checkIfBought", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                filmId: filmId,
                price: price
            })
        })
        if (response.ok) {
            watchFilmButton.style.display = "none";
            goToBankAppButton.style.display = "none";
            goToLibraryButton.style.display = "block";
        } else {
            watchFilmButton.style.display = "block";
            goToBankAppButton.style.display = "none";
            goToLibraryButton.style.display = "none";
        }
    }

    checkIfBought(filmId, price);

    ownersOfTheReviews.forEach((owner) =>
    {
        async function checkState(username) {

            let response = await fetch("/practice/formState", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({username: username, filmId: filmId})
            });
            if (response.ok) {
                showForm()
            } else {
                hideForm();
            }
        }

        if (owner !== null) {
            checkState(owner.textContent);
        } else {
            showForm();
        }
    })

    if (reviews !== null) {
        reviews.forEach((review, index) => {
            review.onmouseenter = function(element) {
                async function checkUsername(username){
                    try {
                        const response = await fetch("/practice/checking", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({ owner: username })
                        });

                        if (response.ok) {
                            console.log(username);
                            utilities.forEach((utility, indexUtility) => {
                                if (indexUtility === index) {
                                    utility.style.display = "block";
                                }
                            })
                            deleteButtons.forEach((deleteButton, index) => {
                                deleteButton.onclick = async function() {
                                    const responseDelete = await fetch("/practice/delete", {
                                        method: "POST",
                                        headers: {
                                            "Content-Type": "application/json"
                                        },
                                        body: JSON.stringify({ username: username, filmId: filmId })
                                    });
                                    if (responseDelete.ok) {
                                        review.remove();
                                    } else {
                                        console.log("error removing");
                                    }
                                }
                            })
                            updateButtons.forEach((updateButton) => {
                                updateButton.onclick = async function() {
                                    const responseUpdate = await fetch("/practice/update", {
                                        method: "POST",
                                        headers: {
                                            "Content-Type": "application/json"
                                        },
                                        body: JSON.stringify({ username: username, filmId: filmId })
                                    });
                                    if (responseUpdate.ok) {
                                        showForm();
                                        let reviewContentFromTheForm = document.getElementById("reviewOfTheUser");
                                        if (reviewContentFromTheForm !== null && chosenStar !== null) {
                                            let reviewInfo = await responseUpdate.json();
                                            reviewContentFromTheForm.textContent = reviewInfo.reviewData;
                                            chosenStar = reviewInfo.stars;
                                            stars.forEach((star, index) => {
                                                if (index < chosenStar) {
                                                    star.classList.add("selected");
                                                }
                                            })
                                        }
                                    } else {
                                        console.log("error updating");
                                    }
                                }
                            })
                        } else {
                            utilities.forEach((utility, indexUtility) => {
                                if (indexUtility === index) {
                                    utility.style.display = "none";
                                }
                            })
                            console.log(username);
                        }
                    } catch (error) {
                        console.error("Request failed:", error);
                    }
                }

                ownersOfTheReviews.forEach((owner, indexOwner) => {
                    if (indexOwner === index) {
                        checkUsername(owner.textContent);
                    }
                })

                review.onmouseout = function(event) {
                    utilities.forEach((utility, indexUtility) => {
                        if (indexUtility === index && !review.contains(event.relatedTarget)) {
                            utility.style.display = "none";
                        }
                    })
                }}
        })
    }

    logoButton.onclick = function() {
        window.location.href = "/practice/films";
    }

    logoButton.onmouseover = function() {
        logoButton.style.cursor = "pointer";
    }

    function hideForm() {
        if (formArea !== null) {
            formArea.style.display = "none";
        }
    }

    function showForm() {
        if (formArea !== null) {
            formArea.style.display = "block";
        }
    }

    stars.forEach((star) => {
        star.addEventListener("mouseover", function() {
            selectStars(star.dataset.value);
        })
        star.addEventListener("mouseout", function() {
            clearStars();
            if (chosenStar > 0) {
                selectStars(chosenStar);
            }
        })
        star.addEventListener("click", function() {
            chosenStar = parseInt(star.dataset.value);
            selectStars(chosenStar);
        });
    })

    if (form != null) {
        form.addEventListener("submit", function(event) {
            event.preventDefault();
            let reviewContent = document.getElementById("reviewOfTheUser").value;
            let filmIdFromTheForm = 0;
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/practice/review", true);
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200 || xhr.status === 202) {
                        console.log("Rating submitted successfully");
                        form.reset();
                        chosenStar = 0;
                        hideForm();
                    } else {
                        console.log(xhr.status);
                    }
                }
            };

            const data = JSON.stringify({
                stars: chosenStar,
                reviewContent: reviewContent,
                filmId: filmId
            });

            xhr.send(data);
        });
    }

    function selectStars(value) {
        stars.forEach((star, index) => {
            if (index < value) {
                star.classList.add("selected");
            } else {
                star.classList.remove("selected");
            }
        });
    }

    function clearStars() {
        stars.forEach((star) => {
            star.classList.remove("selected");
        })
    }

    watchFilmButton.onclick = function() {
        async function checkAmount(username) {
            let responseBalance = await fetch("/practice/checkBalance", {
                                            method: "POST",
                                            headers: {
                                                "Content-Type": "application/json"
                                            },
                                            body: JSON.stringify({
                                                username: username,
                                                filmId: filmId
                                                }
                                            )
            })
            if (responseBalance.ok) {
                watchFilmButton.style.display = "none";
                goToLibraryButton.style.display = "block";
            } else {
                alert("Not enough balance");
                goToBankAppButton.style.display = "block";
            }
        }
        checkAmount(usernameHelper.textContent);
    }

    user.onmouseover = function() {
        userBlock.style.display = "block"
    }
})