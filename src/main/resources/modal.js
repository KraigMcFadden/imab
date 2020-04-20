function setupModal(modalId, openButtonId, closeSpanId) {
    const modal = document.getElementById(modalId);
    const openButton = document.getElementById(openButtonId);
    const closeSpan = document.getElementById(closeSpanId);

    // When the user clicks on the button, open the modal
    openButton.addEventListener('click', () => modal.style.display = "block");

    // When the user clicks on <span> (x), close the modal
    closeSpan.addEventListener('click',() => modal.style.display = "none");

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
}