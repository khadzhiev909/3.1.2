$(document).ready(function () {
    //Заполнение заголовка с данными пользователя
    fetch('http://localhost:8088/api/v1/user/current-user', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        return response.json();
    }).then((data) => {
        document.getElementById('header-user-name').innerHTML = data.user.username + " with roles: "
        if(data.user.roles.length > 1) {
            document.getElementById('header-user-roles').innerHTML = data.user.roles[0].role.substring(5) + ", " + data.user.roles[1].role.substring(5)
        }
        document.getElementById('header-user-roles').innerHTML = data.user.roles[0].role.substring(5)
    })

    //Заполнение таблицы
    fetch('http://localhost:8088/api/v1/user/current-user', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        return response.json();
    }).then((data) => {
        document.getElementById('user-id').innerHTML = data.user.id
        document.getElementById('user-name').innerHTML = data.user.username
        document.getElementById('user-surname').innerHTML = data.user.surname
        document.getElementById('user-sex').innerHTML = data.user.sex
        if(data.user.roles.length > 1) {
            document.getElementById('user-roles').innerHTML = data.user.roles[0].role.substring(5) + ", " + data.user.roles[1].role.substring(5)
        }
        document.getElementById('user-roles').innerHTML = data.user.roles[0].role.substring(5)
    })
})