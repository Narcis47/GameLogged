document.getElementById('registerForm').addEventListener('submit',function(event){
    event.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if(!username || !email || !password) {
        document.getElementById('message').textContent = 'All fields are required!';
        document.getElementById('message').style.color = 'red';
        return;  // oprește funcția aici
    }

    if(password.length < 6) {
        document.getElementById('message').textContent = 'Password must be at least 6 characters!';
        document.getElementById('message').style.color = 'red';
        return;
    }   

    fetch(`${API_URL}/api/users/register`, {
        method: 'POST',
        headers: {
                'Content-Type': 'application/json',
                'ngrok-skip-browser-warning': 'true'
                },
        body: JSON.stringify({username,email,password})
    })
    .then(response => {
        if(response.ok) {
            document.getElementById('message').textContent = 'Account created successfully!';
            document.getElementById('message').style.color = 'green';
        } else {
            document.getElementById('message').textContent = 'Email or Username already exists!';
            document.getElementById('message').style.color = 'red';
        }
    })
    .catch(error => {
        document.getElementById('message').textContent = 'Server error!';
    })
})