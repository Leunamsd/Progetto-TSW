
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

function validaVoto() {
	const votoInput = document.getElementById("voto");
	    const voto = parseInt(votoInput.value, 10);
		
		if (isNaN(voto) || voto < 1) {
		        alert("Inserisci un numero valido compreso tra 1 e 5");
		        votoInput.focus();
		        return false;
		    }

		    return true;
}