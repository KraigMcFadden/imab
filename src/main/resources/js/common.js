function newButton(buttonId, buttonText, parentElement) {
    return createElement(parentElement, 'button', {
        id: buttonId,
        text: buttonText
    });
}

function setHiddenInput(inputId, inputValue) {
    document.getElementById(inputId).value = inputValue;
}

function clearAllChildElements(elementId) {
    const element = document.getElementById(elementId);
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

function createElement(parentElement, type, params) {
    const element = document.createElement(type);
    parentElement.appendChild(element);
    if (params.id) { element.id = params.id; }
    if (params.className) { element.className = params.className; }
    if (params.text) {
        const textNode = document.createTextNode(params.text);
        element.appendChild(textNode);
    }
    return element;
}

function toMoneyStr(number) {
    return '$' + number.toFixed(2);
}