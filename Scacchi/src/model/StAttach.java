package model;
/**
 *@since struttura controllo scacco 
 *@since type true difesa false attacco       
 *@since sm  sm true end true scacco matto /sm false end true stallo
 *@since end
 *@since auto auto scacco
 *@since king colore re di riferimento
 *@since chAttach indirizzo pezzo che sta attaccando
 *@since levelAttach indirizzo occupato o con pezzo minaccia (valore da cntl)
 */
public class StAttach {
public boolean type;
public boolean sm;
public boolean end;
public boolean auto;
public boolean king;
public byte chAttach;
public byte levelAttach;
}
