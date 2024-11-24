const form = document.getElementById("search-form");
const country = document.getElementById("location");
const region = document.getElementById("region");
const sportType = document.getElementById("sport-type");
const startDate = document.getElementById("date-from");
const endDate = document.getElementById("date-to");
const ageAndSex = document.getElementById("age-group")
const compType = document.getElementById("competition-type");




form.addEventListener("submit", function(e) {
    e.preventDefault();


    const data = {
            country: country.value.trim() || null,
            region: region.value || null,
            sportType: sportType.value || null,
            startDate: startDate.value ? new Date(startDate.value).toISOString() : null,
            endDate: endDate.value ? new Date(endDate.value).toISOString() : null,
            ageAndSex: ageAndSex.value || null,
            compType: compType.value || null
        };



    fetch("http://194.87.201.253:8082/search", {
        method: "POST",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json" 
        },
        body: JSON.stringify(data) 
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); 
        })
        .then((responseData) => {
            console.log("Response received:", responseData);
            addEventsToUI(responseData);
           
        })
        .catch((error) => {
            console.error("Error:", error);
            alert("An error occurred while sending the search data.");
        });





})

function addEventsToUI(events) {
    const ulElement = document.querySelector("ul");


    ulElement.innerHTML = "";


    events.forEach(event => {
        const liElement = document.createElement("li");
        liElement.className = "card";


        liElement.innerHTML = `
                <h1 class="card-title">${event.type}</h1>
                <p class="card-subtitle">${event.categoryName || "Неизвестно"}</p>
                <p class="event-identificator">${event.id}</p>
                <div>
                    <div class="card-participants">${event.participants}</div>
                    <div class="card-participants-label">участников</div>
                </div>
                <div class="card-info">
                    <span>${event.sexAndAge || "Не указано"}</span>
                    <span>${event.country || "Неизвестно"}, ${event.region || "Неизвестно"}</span> <!-- Updated line -->
                    <br>
                    <p>(Нажмите, чтобы добавить в избранное)</p>
                </div>
                <div class="card-dates">
                    ${formatDate(event.startDate)} - ${formatDate(event.endDate)}
                </div>
            `;
        liElement.addEventListener("click", function() {

            subscribeUser(event.id);
        });

        ulElement.appendChild(liElement);
    });
}

function formatDate(dateString) {
    if (!dateString) return "Не указано";
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString("ru-RU", options);
}

function subscribeUser(id) {
    console.log("sent req")
    const url = `http://194.87.201.253:8082/subscribe-event?eventid=${id}`;

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