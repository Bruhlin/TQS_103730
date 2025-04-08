async function loadMeals() {
    const restaurant = document.getElementById("restaurant").value;
    const response = await fetch(`/api/meals?restaurant=${restaurant}`);
    const data = await response.json();

    const container = document.getElementById("meals");
    container.innerHTML = "";

    if (data.length === 0) {
      container.innerHTML = "<p>No meals available</p>";
      return;
    }

    data.forEach(meal => {
      const div = document.createElement("div");
      div.className = "meal";
      div.innerHTML = `
        <strong>Date:</strong> ${meal.date}<br>
        <strong>Meal:</strong> ${meal.description}<br>
        <strong>Weather:</strong> ${meal.weather || "N/A"}<br>
        <button onclick="bookMeal('${meal.restaurant}', '${meal.date}')">Make a reservation</button>
      `;
      container.appendChild(div);
    });
  }

async function bookMeal(restaurant, date) {
    const response = await fetch("/api/reservations", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ restaurant, date })
    });
    const data = await response.json();
    alert(`Reservation successful. Token : ${data.token}`);
  }