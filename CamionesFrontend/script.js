const API_URL = 'http://localhost:8080/api/trucks';

let trucksData = [];

async function getTrucks() {
    const response = await fetch(API_URL);
    trucksData = await response.json();
    const truckList = document.getElementById('truck-list');
    const searchInput = document.getElementById('search-input').value.toUpperCase();

    truckList.innerHTML = '';

    const filteredTrucks = trucksData.filter(truck => truck.plate.toUpperCase().includes(searchInput));

    filteredTrucks.forEach(truck => {
        const li = document.createElement('li');
        li.textContent = `Placa: ${truck.plate} - Conductor: ${truck.driver} - Estado: ${truck.status}`;

        const onRouteButton = document.createElement('button');
        onRouteButton.textContent = truck.status === 'En ruta' ? 'En ruta' : 'MARCAR EN RUTA';
        onRouteButton.classList.add('onroute');
        if (truck.status === 'En ruta') {
            onRouteButton.classList.add('active');
        }
        onRouteButton.onclick = () => markTruckAsOnRoute(truck.id, onRouteButton);
        li.appendChild(onRouteButton);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'ELIMINAR';
        deleteButton.classList.add('delete');
        deleteButton.onclick = () => deleteTruck(truck.id);
        li.appendChild(deleteButton);

        truckList.appendChild(li);
    });
}

document.getElementById('truck-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const plate = document.getElementById('plate').value;
    const driver = document.getElementById('driver').value;

    const isDriverOnRoute = trucksData.some(truck => truck.driver === driver && truck.status === 'En ruta');

    if (isDriverOnRoute) {
        alert('Este conductor ya está en ruta con otro camión.');
        return;
    }

    const newTruck = {
        plate: plate,
        driver: driver,
        status: 'Esperando asignación'
    };

    await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newTruck),
    });

    document.getElementById('plate').value = '';
    document.getElementById('driver').value = '';
    getTrucks();
});

async function markTruckAsOnRoute(truckId, button) {
    await fetch(`${API_URL}/${truckId}/onroute`, {
        method: 'PUT',
    });
    button.classList.add('active');
    button.textContent = 'En ruta';
    getTrucks();
}

async function deleteTruck(truckId) {
    if (confirm('¿Estás seguro de eliminar este camión?')) {
        await fetch(`${API_URL}/${truckId}`, {
            method: 'DELETE',
        });
        getTrucks();
    }
}

document.getElementById('search-input').addEventListener('input', getTrucks);

document.addEventListener('DOMContentLoaded', getTrucks);
