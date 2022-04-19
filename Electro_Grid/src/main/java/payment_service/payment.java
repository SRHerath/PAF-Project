package payment_service;

public class payment {
	
	int payID;
	String cardName;
	String cardNo;
	String exMonth;
	String exYr;
	String amount;
	String cv;
	
	public int getPaymentID() {
		return payID;
	}

	public void setPaymentID(int payID) {
		this.payID = payID;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getItemName() {
		return cardNo;
	}

	public void setItemName(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpMonth() {
		return exMonth;
	}

	public void setExpMonth(String exMonth) {
		this.exMonth = exMonth;
	}

	public String getExpYear() {
		return exYr;
	}

	public void setExpYear(String exYr) {
		this.exYr = exYr;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCVC() {
		return cv;
	}

	public void setCVC(String cv) {
		this.cv = cv;
	}
	

}
