function newButton(buttonId, buttonText, parentElement) {
    const button = document.createElement('button');
    button.id = buttonId;
    const buttonTextNode = document.createTextNode(buttonText);
    button.appendChild(buttonTextNode);
    parentElement.appendChild(button);
    return button;
}

function setHiddenInput(inputId, inputValue) {
    document.getElementById(inputId).value = inputValue;
}