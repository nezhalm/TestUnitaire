const prevBtns = document.querySelectorAll(".btn-prev");
const nextBtns = document.querySelectorAll(".btn-next");
const progress = document.getElementById("progress");
const formSteps = document.querySelectorAll(".form-step");
const progressSteps = document.querySelectorAll(".progress-step");
const searchClientBtn = document.querySelector("#search-btn");
const clientCodeInput = document.querySelector("#client-code");
const clientInfoContainer = document.querySelector("#client-info");
const searchIcon = document.querySelector("#search-btn i");
const loadingIcon = document.querySelector("#search-btn iconify-icon");
const overview = document.querySelector(".features");

let formStepsNum = 0;
let clientInfoInSideList = "";

nextBtns.forEach((btn) => {
    btn.addEventListener("click", () => {
        formStepsNum++;
        updateFormSteps();
        updateProgressbar();
    });
});

prevBtns.forEach((btn) => {
    btn.addEventListener("click", () => {
        formStepsNum--;
        updateFormSteps();
        updateProgressbar();
    });
});

function updateFormSteps() {
    formSteps.forEach((formStep) => {
        formStep.classList.contains("form-step-active") &&
        formStep.classList.remove("form-step-active");
    });

    formSteps[formStepsNum].classList.add("form-step-active");
}

function updateProgressbar() {
    progressSteps.forEach((progressStep, idx) => {
        if (idx < formStepsNum + 1) {
            progressStep.classList.add("progress-step-active");
        } else {
            progressStep.classList.remove("progress-step-active");
        }
    });

    const progressActive = document.querySelectorAll(".progress-step-active");

    progress.style.width = ((progressActive.length - 1) / (progressSteps.length - 1)) * 100 + "%";
}

searchClientBtn.addEventListener("click", () => {
    const clientCode = clientCodeInput.value;
    const xhr = new XMLHttpRequest();

    clientInfoContainer.innerHTML = "";
    clientInfoInSideList = "";
    nextBtns[1].setAttribute("disabled", "");

    xhr.open("GET", `/get-client?code=${clientCode}`, true);

    xhr.onprogress = function () {
        toggleIcon([searchIcon, loadingIcon]);
    };

    xhr.onload = function () {
        toggleIcon([searchIcon, loadingIcon]);
        const clientInfo = JSON.parse(this.responseText);

        if (this.status === 200) {
            const clientHTML = `
                <h3 class="success">Client found</h3>
                <input type="hidden" name="client-code" value="${clientInfo.code}">
                <p><span>Client code:</span> ${clientInfo.code}</p>
                <p><span>Name:</span> ${clientInfo.nom}</p>
                <p><span>Family name:</span> ${clientInfo.prenom}</p>
            `;

            clientInfoInSideList = `
                <li class="overview-section">
                    <p>Client details</p>
                </li>
                <li>
                    <span class="list-name">Client code:</span>
                    <span class="list-price">${clientInfo.code}</span>
                </li>
                <li>
                    <span class="list-name">Name:</span>
                    <span class="list-price">${clientInfo.nom}</span>
                </li>
                <li>
                    <span class="list-name">Family name:</span>
                    <span class="list-price">${clientInfo.prenom}</span>
                </li>
                <li>
                    <span class="list-name">Address:</span>
                    <span class="list-price">${clientInfo.adresse}</span>
                </li>
                <li>
                    <span class="list-name">Phone:</span>
                    <span class="list-price">${clientInfo.telephone}</span>
                </li>
                <li>
                    <span class="list-name">Birthday:</span>
                    <span class="list-price">${clientInfo.dateNaissance}</span>
                </li>
            `;

            clientInfoContainer.innerHTML = clientHTML;

            nextBtns[1].removeAttribute("disabled");
        } else if (this.status === 404) {
            clientInfoContainer.innerHTML = `
                <h3 class="error">${clientInfo.error}</h3>
                <a href="/addClient" class="btn">Create a new client</a>
            `;
        }
    };

    xhr.send();
});

nextBtns[1].addEventListener("click", () => {
    overview.innerHTML += clientInfoInSideList;
});

prevBtns[1].addEventListener("click", () => {
    const overviewLists = document.querySelectorAll(".features li");

    console.log(overviewLists.length);
    if (overviewLists.length > 4) {
        for (let i = 4; i < overviewLists.length; i++) {
            overview.removeChild(overviewLists[i]);
        }
    }
});

function toggleIcon(icons) {
    icons.forEach(icon => {
        if (icon.style.display == "none") {
            icon.style.display = "inline-block";
        } else {
            icon.style.display = "none";
        }
    });
}
