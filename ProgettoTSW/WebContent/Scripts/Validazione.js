

function validaRegistrazione() {
	document.querySelector("registrazioneForm").addEventListener("submit", function(event) {

	
		let errori = [];
	    const nome = document.querySelector('input[name="nome"]').value.trim();
	    const cognome = document.querySelector('input[name="cognome"]').value.trim();
	    const telefono = document.querySelector('input[name="telefono"]').value.trim();
	    const password = document.querySelector('input[name="password"]').value.trim();
	    const email = document.querySelector('input[name="email"]').value.trim();
	
	    if (!/^[a-zA-ZÀ-ÿ' ]{2,}$/.test(nome)) {
	        errori.push("Il nome deve contenere almeno 2 lettere e solo caratteri validi.");
	    }
	    if (!/^[a-zA-ZÀ-ÿ' ]{2,}$/.test(cognome)) {
	        errori.push("Il cognome deve contenere almeno 2 lettere e solo caratteri validi.");
	    }	
		if (!/^\d{10}$/.test(telefono)) {
		    errori.push("Il numero di telefono deve contenere esattamente 10 cifre.");
		}
	    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
	        errori.push("L'email non è valida.");
	    }
	    if (!/^(?=.*[0-9]).{8,}$/.test(password)) {
	        errori.push("La password deve avere almeno 8 caratteri e almeno un numero.");
	    }
	    if (errori.length > 0) {
	        alert(errori.join("\n"));
	        event.preventDefault();  // Blocca l'invio
	    }
	});
}

function ValidaInserzione() {
	document.getElementById("inserzioneForm").addEventListener("submit", function(event) {
		
	    let errori = [];
		const fileInput = document.querySelector("input[name='immagine']");
		const file = fileInput.files[0];
		const tipo = file.type;
	    const nome = document.querySelector("input[name='nome']").value.trim();
	    const prezzo = parseFloat(document.querySelector("input[name='prezzo']").value);
	    const quantita = parseInt(document.querySelector("input[name='quantita']").value);
	    const condizione = document.querySelector("input[name='condizione']").value.trim();

		// Verifica il tipo del file
		if (!tipo.startsWith("image/")) {
			errori.push("Il file selezionato deve essere un'immagine (jpeg, png, ecc).");
		}

	    if (nome.length < 2) {
	        errori.push("Il nome deve contenere almeno 2 caratteri.");
	    }

	    if (isNaN(prezzo) || prezzo <= 0) {
	        errori.push("Il prezzo deve essere un numero positivo.");
	    }

	    if (isNaN(quantita) || quantita < 1) {
	        errori.push("La quantità deve essere almeno 1.");
	    }

	    if (condizione !== "" && condizione.length < 2) {
	        errori.push("La condizione deve contenere almeno 2 caratteri oppure lasciarla vuota.");
	    }

	    if (errori.length > 0) {
	        alert(errori.join("\n"));
			event.preventDefault();
	    }
	});
}

