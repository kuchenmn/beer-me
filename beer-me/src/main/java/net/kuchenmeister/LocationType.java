package net.kuchenmeister;

/**
 * User: nkuchenmeister
 * Date: 3/20/14
 */
public enum LocationType {
	BREWPUB("Brewpub"), BREWERY("Brewery"), BEER_BAR("Beer Bar"), BEER_STORE("Beer Store"), HOMEBREW_STORE("Homebrew Store");
	private String value;

	private LocationType(String value) {
		this.value = value;
	}
}
