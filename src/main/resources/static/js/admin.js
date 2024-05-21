document.addEventListener('DOMContentLoaded', function () {


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
            // Находим элемент <tbody>
            const tbody = document.querySelector('tbody');

            // Очищаем <tbody>, если необходимо
            tbody.innerHTML = '';

            // Проходимся по каждому пользователю из данных
            data.forEach(user => {
                // Создаем новую строку <tr>
                const tr = document.createElement('tr');

                // Создаем и заполняем ячейки <td>
                const idTd = document.createElement('td');
                idTd.innerText = user.id;
                tr.appendChild(idTd);

                const nameTd = document.createElement('td');
                nameTd.innerText = user.username;
                tr.appendChild(nameTd);

                const surnameTd = document.createElement('td');
                surnameTd.innerText = user.surname;
                tr.appendChild(surnameTd);

                const sexTd = document.createElement('td');
                sexTd.innerText = user.sex;
                tr.appendChild(sexTd);

                const rolesTd = document.createElement('td');
                rolesTd.innerText = user.roles.map(role => role.role.substring(5)).join(', ');
                tr.appendChild(rolesTd);

                // Создаем ячейки для кнопок Edit и Delete
                const editTd = document.createElement('td');
                const deleteTd = document.createElement('td');

                // Создаем кнопку Edit

                const editButton = document.createElement('button');
                editButton.id = 'edit-user';
                editButton.type = 'button';
                editButton.className = 'btn btn-info edit-btn';
                editButton.setAttribute('data-toggle', 'modal');
                editButton.setAttribute('data-target', '#userEditModal');
                editButton.setAttribute('data-userId', user.id);
                editButton.innerText = 'Edit';
                editTd.appendChild(editButton);

                // Создаем кнопку Delete
                const deleteButton = document.createElement('button');
                deleteButton.id = 'delete-user';
                deleteButton.type = 'button';
                deleteButton.className = 'btn btn-danger';
                deleteButton.setAttribute('data-toggle', 'modal');
                deleteButton.setAttribute('data-target', '#userDeleteModal');
                deleteButton.setAttribute('data-userId', user.id);
                deleteButton.innerText = 'Delete';
                deleteTd.appendChild(deleteButton);

                // Добавляем ячейки с кнопками в строку
                tr.appendChild(editTd);
                tr.appendChild(deleteTd);

                // Добавляем строку <tr> в <tbody>
                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error(error));

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
        const url = '/admin/update';
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

    $('#new-user-form').submit(function (event) {
        event.preventDefault();

        let formData = JSON.stringify({
            username: $('#new-name').val(),
            surname: $('#new-surname').val(),
            sex: $('#new-sex').val(),
            password: $('#new-password').val(),
            roles: [{role: $('#new-role').val()}]
        });
        const url = 'http://localhost:8088/admin/create'
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
            const url = 'http://localhost:8088/api/v1/user/' + userId
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
        }
    })
})