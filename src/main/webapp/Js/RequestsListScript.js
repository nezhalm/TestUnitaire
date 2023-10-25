const updateButton = document.querySelectorAll(".updateDetails");
const favDialog = document.getElementById("favDialog");
const outputBox = document.querySelector("output");
const selectEl = favDialog.querySelector("select");
const confirmBtn = favDialog.querySelector("#confirmBtn");
const demandeStatus = document.querySelector("#demande-status");
const demandeNumber = document.querySelector("#demande-number");
const annulerBtn = document.getElementById("annulerBtn");
const alert = document.querySelector(".alert");
const updateHistory = document.querySelectorAll(".update-history");
const bgModal = document.querySelector("#modal-bg");
const historyModalBtn = document.querySelectorAll(".history-btn");

historyModalBtn.forEach((btn, i) => {
    btn.addEventListener("click", () => {
        updateHistory[i].classList.remove("hidden");
        bgModal.classList.remove("hidden");
    })
});

bgModal.addEventListener("click", () => {
    updateHistory.forEach(modal => {
        modal.classList.add("hidden");
    });
    bgModal.classList.add("hidden");
});

document.addEventListener("click", (evt) => {
    if (evt.target.classList.contains("close-notification-btn")) {
        alert.style.display = "none";
    }
});

annulerBtn.addEventListener("click", () => {
    favDialog.close();
});

if (typeof favDialog.showModal !== "function") {
    favDialog.hidden = true;
}

updateButton.forEach(btn => {
    btn.addEventListener("click", (event) => {
        if (typeof favDialog.showModal === "function") {
            favDialog.showModal();
            demandeStatus.value = event.target.dataset.status;
            demandeNumber.value = event.target.dataset.number;
        }
    });
});
selectEl.addEventListener("change", (e) => {
    confirmBtn.value = selectEl.value;
});