
function confermaEliminazioneProf() {
	return confirm('Sei sicuro di voler eliminare questo profilo?');
}

function confermaEliminazioneIns() {
  return confirm("Sei sicuro di voler eliminare questa inserzione?");
}
function confermaEliminazioneOrd() {
	return confirm('Sei sicuro di voler eliminare questo ordine?')
}
function confermaEliminazioneRec() {
  return confirm("Sei sicuro di voler eliminare questa recensione?");
}

function validaQuantita() {
    const quantitaInput = document.getElementById("quantita_da_aggiungere");
    const quantita = parseInt(quantitaInput.value, 10);

    if (isNaN(quantita) || quantita < 1) {
        alert("Inserisci un numero valido maggiore di zero.");
        quantitaInput.focus();
        return false; // blocca l'invio del form
    }

    return true; // tutto ok, invia il form
}

function validaRecensione() {
	document.getElementById("recensioneForm").addEventListener("submit", function() {
		
		const votoInput = document.getElementById("voto");
	    const commentoInput = document.getElementById("commento");
		
		const voto = parseInt(votoInput.value, 10);
		const commento = commentoInput.value.trim();
		
			// Controllo voto
	        if (isNaN(voto) || voto < 1 || voto > 5) {
	            alert("Il voto deve essere un numero compreso tra 1 e 5.");
	            e.preventDefault();
	            return;
	        }

	        // Controllo commento
	        if (commento.length === 0) {
	            alert("Il commento non può essere vuoto.");
	            e.preventDefault();
	            return;
	        }
	        if (commento.length < 5) {
	            alert("Il commento deve contenere almeno 5 caratteri.");
	            e.preventDefault();
	            return;
	        }
	});
}