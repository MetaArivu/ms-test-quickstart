package io.fusion.water.order.domainLayer.models;

/**
 * Order Item
 * 
 * @author arafkarsh
 *
 */
public class OrderItem {

	private String itemId;
	private String itemName;
	private int itemValue;
	private String itemCurrency;
	private int quantity;
	
	/**
	 * Create an Order Item
	 * 
	 * @param _itemId
	 * @param _iName
	 * @param _value
	 * @param _currency
	 * @param _qty
	 */
	public OrderItem(String _itemId, String _iName,
			int _value, String _currency, int _qty) {
		
		itemId			= _itemId;
		itemName		= _iName;
		itemValue		= _value;
		itemCurrency	= _currency;
		quantity		= _qty;
		
	}
	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @return the itemValue
	 */
	public int getItemValue() {
		return itemValue;
	}
	/**
	 * @return the itemCurrency
	 */
	public String getItemCurrency() {
		return itemCurrency;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Returns Hashcode of the Item ID
	 */
	public int hashCode()  {
		return itemId.hashCode();
	}
	
	/**
	 * Returns Item Name
	 */
	public String toString() {
		return itemName;
	}
}
