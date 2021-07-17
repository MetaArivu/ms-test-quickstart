package io.fusion.water.order.domainLayer.models;

/**
 * 
 * @author arafkarsh
 *
 */
public class ShippingAddress {

	private String streetName;
	private String addressLine2;
	private String city;
	private String state;
	private String landMark;
	private String country;
	private String zipCode;
	
	/**
	 * Create Shipping address
	 * @param _sn
	 * @param _aline2
	 * @param _city
	 * @param _state
	 * @param _lm
	 * @param _country
	 * @param _zCode
	 */
	public ShippingAddress(String _sn, String _aline2, String _city,
			String _state, String _lm, String _country, String _zCode) {
		
		streetName 		= _sn;
		addressLine2	= _aline2;
		city			= _city;
		state			= _state;
		landMark		= _lm;
		country			= _country;
		zipCode			= _zCode;
	}
	
	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @return the landMark
	 */
	public String getLandMark() {
		return landMark;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	
	
}
