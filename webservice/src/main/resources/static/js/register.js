let form = document.querySelector("form");
let email = document.getElementById("email");
let password = document.getElementById("password");
let login = document.getElementById("login");


form.addEventListener("submit", function(e){
    e.preventDefault();
    let reg = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(!reg.test(email.value)) {
        alert("Email введен не правильно");
        email.focus();
        return;
    }

    reg = /^.{4,32}$/;
    if(!reg.test(password.value)) {
        alert("Пароль введен не правильно");
        password.focus();
        return;
    }

    reg = /^.{4,16}$/;
    if(!reg.test(login.value)) {
        alert("Логин введен неправильно");
        login.focus();
        return;
    }

    const data = {
        email: email.value,
        password: password.value,
        username: login.value
    };

    fetch('http://194.87.201.253:8081/auth/register', {
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


        return response.body;
    })

    .then(data => {
        window.location.href = "/web/mainpage";
        console.log("Reg");
    })

    .catch((error) => {
        alert("Ошибка при регистрации. Пожалуйста, попробуйте еще раз.");
    });
})

