async function verificarReserva() {
    const token = document.getElementById('token').value;
    const res = await fetch(`/api/reservations/${token}`);

    const resultado = document.getElementById('resultado');
    if (res.ok) {
      const data = await res.json();

      let html = `<p><strong>Restaurant:</strong> ${data.restaurant}</p>`;
      html += `<p><strong>Date:</strong> ${data.date}</p>`;
      html += `<p><strong>Meal:</strong> ${data.description}</p>`;
      html += `<p><strong>Used?</strong> <span id="used-status"> ${data.used ? 'Yes' : 'No'}</span></p>`;

      if (!data.used) {
          html += `  
              <div id="checkin-section"> 
                  <button onclick="marcarComoUsada('${token}')">Mark as used</button>
              </div>
          `;
      }

      resultado.innerHTML = html;
    } else {
      resultado.innerHTML = '<p style="color:red;">Reservation not found</p>';
    }
  }

async function marcarComoUsada(token) {
    const res = await fetch(`/api/reservations/${token}/checkin`, {
      method: 'POST'
    });

    const checkinSection = document.getElementById('checkin-section');

    if (res.ok) {
      document.getElementById("used-status").textContent = "Yes";
      checkinSection.innerHTML = '<p style="color:green;">Reservation marked as used</p>';
      
    } else {
      checkinSection.innerHTML = '<p style="color:red;">Error marking as used</p>';
    }
  }