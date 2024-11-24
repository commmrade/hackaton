
function closeEvents() {
    window.location.href = "/web/filter?filterType=close";
}
function weekEvents() {
    window.location.href = "/web/filter?filterType=weekly";
}

function monthEvents() {
    window.location.href = "/web/filter?filterType=monthly";
}

function quarterEvents() {
    window.location.href = "/web/filter?filterType=quarterly";
}

function halfEvents() {
    window.location.href = "/web/filter?filterType=half";
}

function subscribeUser(card) {
    const event_id = card.getAttribute("data-eventid");
    const url = `http://194.87.201.253:8082/subscribe-event?eventid=${event_id}`;

    fetch(url, {
        method: 'PUT',
        credentials: 'include', // Include cookies or other credentials
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.body;
    })
    .then(data => {
        console.log('Success:', data);
    })
    .catch(error => {
        console.error('Error:', error);

        alert("Вы уже добавили это событие!")
    });
}
