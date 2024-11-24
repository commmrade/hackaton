const new_email_field = document.getElementById("new-email");
const cur_pass_field = document.getElementById("current-password")
const new_pass_field = document.getElementById("new-password");

const form = document.getElementById("form");

form.addEventListener("submit", function(e){
    e.preventDefault();
    
    const data = {
        email: "",
        currentPassword: "",
        newPassword: ""
    };

    
    
    if(new_email_field.value.trim() === "" && (cur_pass_field.value.trim() === "" || new_pass_field.value.trim() === "")) {
        new_email_field.focus();
        return;
    }
    data.email = new_email_field.value.trim();
    data.currentPassword = cur_pass_field.value.trim();
    data.newPassword = new_pass_field.value.trim();
    
    const url = "http://194.87.201.253:8081/auth/update-account";

    fetch(url, {
        method: 'PUT',
        credentials: 'include', 
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.body;
    })
    .then(data => {
        window.location.href = "/web/mainpage";
        console.log('Success:', data);
    })
    .catch(error => {
        console.error('Error:', error);

        
    });

})

function logout() {
    const url = "http://194.87.201.253:8081/auth/logout";
    fetch(url, {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.body;
    })
    .then(data => {
        window.location.href = "/web/login";

    })
    .catch(error => {



    });
}