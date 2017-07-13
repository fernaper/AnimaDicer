package animadicer;

import java.util.ArrayList;

public class Dado {
	 ArrayList<Integer> traza;
	 int resultado;
	 Settings settings;
	
	public Dado (Settings s) {
		this.settings = s;
		this.traza = new ArrayList<>();
		this.resultado = 0;
	}
	
	public int getResultado() {
		return resultado;
	}
	
	public void reset() {
		traza.clear();
		resultado = 0;
	}
	
        public void trucarDado(int resultado) {
            this.resultado = resultado;
            traza.add(this.resultado);
        }
        
	public int lanzarDado (boolean abiertas) {
		if (!abiertas) {
			this.resultado = (int)(Math.random()*100) + 1;
			traza.add(this.resultado);
			return this.resultado;
		}
		
		int parcial;
		boolean sigo = false;
		int countAbiertas = 0;
		
		do {
			parcial = (int)(Math.random()*100) + 1;
			traza.add(parcial);
                        this.resultado += parcial;
			
			// Tengo que ver cu�ndo hace abierta y cuando hace pifias
			if (settings.getAbiertas()) {
				sigo = parcial >= (countAbiertas + 90) || parcial == 100;
				countAbiertas++;
			} else {
				sigo = parcial >= 90;
			}
			
			if (settings.getCapicua()) {
				sigo = sigo || ((parcial%10) == ((int)(parcial/10))); 
			}
		} while(sigo);
                
		return this.resultado;
	}
        
        public int lanzarD10() {
            this.resultado = (int)(Math.random()*10) + 1;
            traza.add(this.resultado);
            return this.resultado;
        }
        
        public ArrayList<Integer> getTraza() {
            return this.traza;
        }
}
