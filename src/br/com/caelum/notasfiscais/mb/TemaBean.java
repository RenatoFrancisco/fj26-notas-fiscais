package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class TemaBean implements Serializable {

	private String tema = "aristo";

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public List<String> getTemas() {
		return Arrays.asList("aristo", "afternoon", "black-tie", "blitzer", "bluesky",
				"bootstrap","casablanca", "cupertino", "dark-hive", "dot-luv", "eggplant",
				"excite-bike", "flick", "glass-x", "hot-sneaks", "humanity",
				"le-frog", "midnight", "mint-choc", "overcast",
				"pepper-grinder", "redmond", "rocket", "sam", "smoothness",
				"south-street", "start", "sunny", "swanky-purse", "trontastic",
				"ui-darkness", "ui-lightness", "vader");
	}
}
