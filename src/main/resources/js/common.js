function newButton(buttonId, buttonText, parentElement) {
    const button = document.createElement('button');
    button.id = buttonId;
    const buttonTextNode = document.createTextNode(buttonText);
    button.appendChild(buttonTextNode);
    parentElement.appendChild(button);
    return button;
}