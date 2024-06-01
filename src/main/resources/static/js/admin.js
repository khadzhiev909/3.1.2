document.addEventListener('DOMContentLoaded', function () {


    $(document).ready(function () {
        const urlForGet = 'http://localhost:8088/api/v1/user/current-user'
        //Заполнение заголовка с данными пользователя
        fetch(urlForGet, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            return response.json();
        }).then((data) => {
            document.getElementById('header-user-name').innerHTML = data.user.username + " with roles: "
            if (data.user.roles.length > 1) {
                document.getElementById('header-user-roles').innerHTML = data.user.roles[0].role.substring(5) + ", " + data.user.roles[1].role.substring(5)
            }
            document.getElementById('header-user-roles').innerHTML = data.user.roles[0].role.substring(5)
        })


        //запрос на получение всех
        fetch('http://localhost:8088/api/admin', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
        .then(data => {
            const table = document.getElementById('admin-panel-tbody');
            data.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.surname}</td>
                    <td>${user.sex}</td>
                    <td>${user.roles.map(role => role.role.substring(5))}</td>
                    <td>
                        <button
                            id="edit-user"
                            type="button"
                            class="btn btn-info edit-btn"
                            data-toggle="modal"
                            data-target="#userEditModal"
                            data-userId="${user.id}"
                            >Edit
                        </button>
                    </td>
                    <td>
                        <button
                            id="delete-user"
                            type="button"
                            class="btn btn-danger"
                            data-toggle="modal"
                            data-target="#userDeleteModal"
                            data-userId="${user.id}"
                        >Delete</button>
                    </td>
                
                `
                table.appendChild(row)
            })

        })



        //заполнение модального окна для редактирования
        $('#userEditModal').on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget);
            let userId = button.data('userid');

            if (userId) {
                const url = 'http://localhost:8088/api/v1/user/' + userId;
                const headers = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                fetch(url, headers)
                    .then(response => {
                        if (!response.ok) {
                            alert("Error: " + response.status + " " + response.statusText);
                        }
                        return response.json();
                    })
                    .then(data => {
                        document.getElementById('id').value = data.id
                        document.getElementById('user-id').value = data.id
                        document.getElementById('name').value = data.username
                        document.getElementById('surname').value = data.surname
                        document.getElementById('sex').value = data.sex
                        if (data.roles.length > 1) {
                            document.getElementById('roles').value = data.roles[0].role + ", " + data.roles[1].role
                        } else {
                            document.getElementById('roles').value = data.roles[0].role
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }
        })
    });

    // Обработчик события отправки формы
    $('#editForm').submit(function (event) {
        // Отмена стандартного поведения формы
        event.preventDefault();

        // Сериализация данных формы в формат JSON
        let formData = JSON.stringify({
            id: $('#id').val(),
            username: $('#name').val(),
            surname: $('#surname').val(),
            sex: $('#sex').val(),
            password: $('#password').val(),
            roles: [{role: $('#roles').val()}] // Преобразование роли в объект
        });
        // Отправка данных на сервер методом PATCH
        const url = 'http://localhost:8088/api/admin';
        const headers = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: formData
        }
        fetch(url, headers)
            .then(response => {
                if (response.ok) {
                    $('#userEditModal').modal('hide');
                    // Перезагрузка страницы или выполнение других действий
                    location.reload();
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });

    $('#edit-user').click((event) => {
       const modal = document.getElementById("userEditModal")
        modal.style.display = "block";
       modal.className = "modal fade show";

    })

    //отправка формы для создания
    $('#new-user-form').submit(function (event) {
        event.preventDefault();

        let formData = JSON.stringify({
            username: $('#new-name').val(),
            surname: $('#new-surname').val(),
            sex: $('#new-sex').val(),
            password: $('#new-password').val(),
            roles: [{role: $('#new-role').val()}]
        });
        const url = 'http://localhost:8088/api/admin'
        const headers = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: formData
        }
        fetch(url, headers)
            .then(response => {
                if (response.ok) {
                    location.reload()
                }
                return response.json()
            })
            .catch(error => console.error("Error:", error))
    });

    //FORM for delete
    $('#userDeleteModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget)
        let userId = button.data('userid')
        if (userId) {
            const urlForEdit = 'http://localhost:8088/api/v1/user/' + userId
            const headers = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            }
            fetch(urlForEdit, headers)
                .then(response => {
                    if (!response.ok) {
                        alert("Error: " + response.status + " " + response.statusText);
                    }
                    return response.json();
                })
                .then(data => {
                    document.getElementById('delete-name').value = data.username
                    document.getElementById('delete-surname').value = data.surname
                    document.getElementById('delete-sex').value = data.sex
                    if (data.roles.length > 1) {
                        document.getElementById('delete-role').value = data.roles[0].role.substring(5) + ", " + data.roles[1].role.substring(5)
                    } else {
                        document.getElementById('delete-role').value = data.roles[0].role.substring(5)
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            $("#delete-btn").click(event => {
                const url = 'http://localhost:8088/api/admin/' + userId;
                const headers = {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                fetch(url, headers)
                    .then(response => {
                        if (response.ok) {
                            $('#userDeleteModal').modal('hide');
                            // Перезагрузка страницы или выполнение других действий
                            location.reload();
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            })
        }
    })
})