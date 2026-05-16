const userId = localStorage.getItem('userId');
const username = localStorage.getItem('username');
let currentFilter = 'ALL';
let allUserGames = [];

if(!userId) {
    window.location.href = 'index.html';
}

document.getElementById('welcomeUser').textContent = 'Hello, ' + username;

document.getElementById('logoutBtn').addEventListener('click', function() {
    localStorage.clear();
    window.location.href = 'index.html';
});

document.getElementById('searchBtn').addEventListener('click',function(){
    const query = document.getElementById('searchInput').value;

    if(!query) return;

    fetch(`${API_URL}/api/games/search?query=${query}`)
    .then(response => response.json())
    .then(games => {
        const searchResults = document.getElementById('searchResults');
        searchResults.innerHTML = '';

        games.forEach(game => {
            const item = document.createElement('div');
            item.className = 'search-result-item';
            item.innerHTML = `
                <img src="${game['background_image'] || ''}" 
                    alt="${game.name}"    
                <span>${game.name}</span>
                <button onclick="addGame(${game.id})">+ Add</button>
            `;
            searchResults.appendChild(item);
        });
    })
})

function addGame(rawgId) {
    fetch(`${API_URL}/api/games/add`, {
        method: 'POST',
        headers: {
                'Content-Type': 'application/json'},
        body: JSON.stringify({ rawgId: rawgId })
    })
    .then(response => response.json())
    .then(game => {
        return fetch(`${API_URL}/api/library/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'},
            body: JSON.stringify({
                userId: parseInt(userId),
                gameId: game.id,
                status: 'WISHLIST'
            })
        });
    })
    .then(response => {
        if(response.ok) {
            showToast('Game added to library!');
            loadLibrary();
        }
    })
    .catch(error => console.error(error));
}

function loadLibrary() {
    fetch(`${API_URL}/api/library/${userId}`)
    .then(response => response.json())
    .then(userGames => {
        allUserGames = userGames;
        renderGames(userGames);
    });
}

function renderGames(userGames) {
    const library = document.getElementById('library');
    library.innerHTML = '<div class="game-grid"></div>';
    const grid = library.querySelector('.game-grid');

    if(userGames.length === 0) {
        library.innerHTML = '<p>No games found!</p>';
        return;
    }

    userGames.forEach(userGame => {
        fetch(`${API_URL}/api/games/${userGame.gameId}/rawg`)
        .then(response => response.json())
        .then(rawgGame => {
            const card = document.createElement('div');
            card.className = 'game-card';
            card.innerHTML = `
                <img src="${rawgGame['background_image'] || ''}"
                    alt="${rawgGame.name}"
                    onerror="this.style.display='none'">
                <div class="game-card-info">
                    <h3>${rawgGame.name}</h3>
                    <span class="status-badge">${userGame.status}</span>
                    <p class="rating">${userGame.rating ? '⭐ ' + userGame.rating + '/10' : 'No rating'}</p>
                    <button class="btn-delete" onclick="removeGame(${userGame.id})">Remove</button>
                </div>
                <select onchange="updateStatus(${userGame.id}, this.value)">
                    <option ${userGame.status === 'WISHLIST' ? 'selected' : ''}>WISHLIST</option>
                    <option ${userGame.status === 'PLAYING' ? 'selected' : ''}>PLAYING</option>
                    <option ${userGame.status === 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                    <option ${userGame.status === 'DROPPED' ? 'selected' : ''}>DROPPED</option>
                </select>
                <input type="number" min="1" max="10" value="${userGame.rating || ''}"
                    placeholder="Rating 1-10"
                    onchange="updateRating(${userGame.id}, this.value)">
            `;
            grid.appendChild(card);
        });
    });
}

function removeGame(id) {
    fetch(`${API_URL}/api/library/${id}`, {
        method: 'DELETE'
    })
    .then(() => loadLibrary());
}

function updateStatus(id, status) {
    fetch(`${API_URL}/api/library/${id}/status`, {
        method: 'PUT',
        headers: {
                'Content-Type': 'application/json'},
        body: JSON.stringify({ status: status })
    })
    .then(() => loadLibrary());
}

function updateRating(id, rating) {
    fetch(`${API_URL}/api/library/${id}/rating`, {
        method: 'PUT',
        headers: {
                'Content-Type': 'application/json'},
        body: JSON.stringify({ rating: parseInt(rating) })
    })
    .then(() => loadLibrary());
}

function showToast(message) {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.classList.add('show');
    setTimeout(() => toast.classList.remove('show'), 3000);
}

function filterLibrary(status) {
    currentFilter = status;

    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.classList.remove('active');
        if(btn.textContent.toUpperCase() === status ||
           (status === 'ALL' && btn.textContent === 'All')) {
            btn.classList.add('active');
        }
    });

    const filtered = status === 'ALL'
        ? allUserGames
        : allUserGames.filter(g => g.status === status);

    renderGames(filtered);
}

loadLibrary();