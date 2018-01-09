package LibPurple.utils;


public class Polynom 
{
	
	double[] polynom;
	
	public Polynom(double[] input)
	{
		this.polynom = input;
	}
	
	public double getValue(double input)
	{
		double output = 0;
		
		for(int i = 0; i < this.polynom.length; i++)
		{
			output += Math.pow(input, this.polynom.length-i-1) * polynom[i];
		}
		return output;
	}
	
	public void setPolynom(double[] newPolynom)
	{
		this.polynom = newPolynom;
	}

	public double[] getPolynom() {
		return polynom;
	}
}
