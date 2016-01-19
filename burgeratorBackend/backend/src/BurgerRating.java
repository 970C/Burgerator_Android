//package burgeratorBackend;

public class BurgerRating {
	
	private double rating;
	
	public BurgerRating() {
		setRating(-999);
	}
	public BurgerRating(double _rating) {
		setRating(-999);
	}
	
	public double getRating(){
		
		return rating;
	}
	
	public void setRating(double _rating){
		this.rating = _rating;
	}
	
}
