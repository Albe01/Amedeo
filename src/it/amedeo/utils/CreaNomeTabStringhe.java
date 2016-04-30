package it.amedeo.utils;

public class CreaNomeTabStringhe {
	private String nomeTab;
	public CreaNomeTabStringhe() {
		// TODO Auto-generated constructor stub
	}
	public String CreaNomeTabStringhe(String tipoTab, Integer lenStringa) {
		if (tipoTab.equals("nomi")) {
			switch (lenStringa) {
			case 2:
				nomeTab = "Strnomi03";
				break;
			case 3:
				nomeTab = "Strnomi03";			
				break;
			case 4:
				nomeTab = "Strnomi04";			
				break;
			case 5:
				nomeTab = "Strnomi05";			
				break;
			case 6:
				nomeTab = "Strnomi06";			
				break;
			case 7:
				nomeTab = "Strnomi07";			
				break;
			case 8:
				nomeTab = "Strnomi08";			
				break;
			case 9:
				nomeTab = "Strnomi10";			
				break;
			case 10:
				nomeTab = "Strnomi10";			
				break;
			case 11:
				nomeTab = "Strnomi15";			
				break;
			case 12:
				nomeTab = "Strnomi15";			
				break;
			case 13:
				nomeTab = "Strnomi15";			
				break;
			case 14:
				nomeTab = "Strnomi15";			
				break;
			case 15:
				nomeTab = "Strnomi15";			
				break;
			default:
				nomeTab = "Strnomi20";
				break;
			}
		}

		if (tipoTab.equals("indir")) {
			switch (lenStringa) {
			case 2:
				nomeTab = "Strindir03";
				break;
			case 3:
				nomeTab = "Strindir03";			
				break;
			case 4:
				nomeTab = "Strindir06";			
				break;
			case 5:
				nomeTab = "Strindir06";			
				break;
			case 6:
				nomeTab = "Strindir06";			
				break;
			case 7:
				nomeTab = "Strindir10";			
				break;
			case 8:
				nomeTab = "Strindir10";			
				break;
			case 9:
				nomeTab = "Strindir10";			
				break;
			case 10:
				nomeTab = "Strindir10";			
				break;
			case 11:
				nomeTab = "Strindir15";			
				break;
			case 12:
				nomeTab = "Strindir15";			
				break;
			case 13:
				nomeTab = "Strindir15";			
				break;
			case 14:
				nomeTab = "Strindir15";			
				break;
			case 15:
				nomeTab = "Strindir15";			
				break;
			default:
				nomeTab = "Strindir20";
				break;
			}
		}
		return nomeTab;
	}
}
