document.getElementById('form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const data = {
        "username": username,
        "password": password
    };

    fetch('http://194.87.201.253:8081/auth/login', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    .then(response => {
        console.log(response.status)
        if (response.status !== 200) {
            throw new Error('Сетевая ошибка');
        }
        
        
        return response.body
    })

    .then(data => {
        window.location.href = "/web/mainpage";
        console.log("Reg");
    })

    .catch((error) => {
        alert("Ошибка при login. Пожалуйста, попробуйте еще раз.");
    });
});

