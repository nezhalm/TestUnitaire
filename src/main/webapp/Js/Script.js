
    const slideValue = document.querySelector(".range .sliderValue span");
    const inputSlider = document.querySelector("#range1");
    inputSlider.oninput = (() => {
    let value = inputSlider.value;
    slideValue.textContent = value;
    slideValue.style.left = ((value - 5000) * 100 / (600000 - 5000)) + "%";
    slideValue.classList.add("show");
});
    inputSlider.onblur = (() => {
    slideValue.classList.remove("show");
});
    const slideValue2 = document.querySelector(".range .sliderValue2 span");
    const inputSlider2 = document.querySelector("#range2");
    inputSlider2.oninput = (() => {
    let value = inputSlider2.value;
    slideValue2.textContent = value;
    slideValue2.style.left = ((value - 12) * 100 / (120 - 12)) + "%";
    slideValue2.classList.add("show");
});
    inputSlider2.onblur = (() => {
    slideValue2.classList.remove("show");
});
    //hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
    const range1 = document.querySelector("#range1");
    const range2 = document.querySelector("#range2");
    const output = document.querySelector("#output");
    const dureet = document.querySelector("#dureet");
    const mon = document.querySelector("#mon");

    // Fonction pour mettre à jour la valeur dans le paragraphe
    function updateValue() {
    const montant = parseInt(range1.value);
    const duree = parseInt(range2.value);
    const tauxInteret = 0.05; // Taux d'intérêt hypothétique (5%)
    const mensualite = (montant * tauxInteret) / (12 * (1 - Math.pow(1 + (tauxInteret / 12), -duree)));
    output.textContent =  mensualite.toFixed(2)+` DH`;
    dureet.textContent = duree+` Mois`;
    mon.textContent = montant+` DH`;

}

    // Ajouter un écouteur d'événements pour les deux plages
    range1.addEventListener("input", updateValue);
    range2.addEventListener("input", updateValue);

    // Appel initial pour afficher les valeurs par défaut
    updateValue();

