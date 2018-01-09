package LibPurple.actuator;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;


public class Solenoid3075 extends DoubleSolenoid{

	public Solenoid3075(int forwardChannel, int reverseChannel) {
		super(forwardChannel, reverseChannel);
		// TODO Auto-generated constructor stub
	}
	
	public Solenoid3075(int moduleNum, int forwardChannel, int reverseChannel) {
		super(moduleNum, forwardChannel, reverseChannel);
		// TODO Auto-generated constructor stub
	}
	
	public Command ToggleCommand()
	{
		return new Toggle(this);
	}
	
	public Command OpenCommand()
	{
		return new OpenClose(this, true);
	}
	
	public Command CloseCommand()
	{
		return new OpenClose(this, false);
	}
	
	public Command OffCommand()
	{
		return new Off(this);
	}
	
	public Command TimedCycle(double timeOut)
	{
		return new TimedCycle(this, timeOut);
	}
}



class Toggle extends Command {

	DoubleSolenoid mySol;
	
	public Toggle(DoubleSolenoid ds)
	{
		mySol = ds;
	}

    protected void initialize() {
    }

    protected void execute() {
    	mySol.set(mySol.get() == DoubleSolenoid.Value.kForward ? 
    			DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}

class OpenClose extends Command {

	DoubleSolenoid mySol;
	boolean open;
	
    public OpenClose(DoubleSolenoid ds, boolean open) {
    	mySol = ds;
    	this.open = open;
    }

    protected void initialize() {
    }

    protected void execute() {
    	mySol.set(open ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}

class TimedCycle extends Command {

	DoubleSolenoid mySol;
	
    public TimedCycle(DoubleSolenoid ds, double timeOut) {
    	mySol = ds;
    	setTimeout(timeOut);
    }

    protected void initialize() {
    	mySol.set(DoubleSolenoid.Value.kForward);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	mySol.set(DoubleSolenoid.Value.kReverse);
    }

    protected void interrupted() {
    }
}

class Off extends Command{
DoubleSolenoid mySol;
	
    public Off(DoubleSolenoid ds) {
    	mySol = ds;
    }

    protected void initialize() {
    	mySol.set(DoubleSolenoid.Value.kOff);
    }

    protected void execute() {
    	
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    	
    }
}