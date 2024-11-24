
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

function deleteEvent(card) {
    const eventID = card.getAttribute("data-event-id");
    console.log(eventID);
    const url = `http://194.87.201.253:8082/delete-event?event_id=${eventID}`;

    fetch(url, {
        method: 'DELETE',
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
        card.remove();

    })
    .catch(error => {
        console.error('Error:', error);
    });


}

function subscribeToEvent(card) {
    const event_id = card.getAttribute("data-eventid");
    const url = `http://194.87.201.253:8082/subscribe-event?eventid=${event_id}`;

    // Send the PUT request
    fetch(url, {
        method: 'PUT',
        credentials: 'include',
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
        window.location.href = "/web/mainpage";

    })
    .catch(error => {
        console.error('Error:', error);
        alert("Вы уже добавили это событие!");
    });
}

