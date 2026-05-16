document.getElementById('loginForm').addEventListener('submit',function(event){
    event.preventDefault()

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    
    if(!email || !password) {
        document.getElementById('message').textContent = 'All fields are required!';
        document.getElementById('message').style.color = 'red';
        return;
    }

    fetch(`${API_URL}/api/users/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'},
        body: JSON.stringify({email, password})
    })
    .then(response => {
        if(!response.ok) {
            document.getElementById('message').textContent = 'Email or password is wrong!';
            document.getElementById('message').style.color = 'red';
            return;
        }
        return response.json();
    })
    .then(user => {
        if(!user) return;
        localStorage.setItem('userId', user.id);
        localStorage.setItem('username', user.username);
        window.location.href = 'library.html';
    })
    .catch(error => {
        document.getElementById('message').textContent = 'Server error!';
    })
})