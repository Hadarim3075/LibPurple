package LibPurple.actuator;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class Servo3075 extends Servo {

	public Servo3075(int channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Toggle between 2 angles with a joystick button.
	 * Example use: JoystickButton.whenPressed(Servo3075.ButtonToggle(0, 180));
	 * @param angle1 an angle in degrees.
	 * @param angle2 an angle in degrees.
	 * @return a command for the WhenPressed JoystickButton function.
	 */
	public Command ButtonToggle(double angle1, double angle2)
	{
		return new ButtonHandler(this, angle1, angle2);
	}
	
	public Command goToAngle(double angle)
	{
		return new SetAngle(this, angle);
	}
}

class ButtonHandler extends Command
{

	Servo3075 servo;
	double angle1;
	double angle2;
	

	public ButtonHandler(Servo3075 servo, double angle1, double angle2) {
		super();
		this.servo = servo;
		this.angle1 = angle1;
		this.angle2 = angle2;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		servo.setAngle(angle1 == servo.getAngle() ? angle2 : angle1);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
}

class SetAngle extends Command
{
	
	Servo3075 servo;
	double angle;
	
	public SetAngle(Servo3075 servo, double angle) {
		// TODO Auto-generated constructor stub
		this.servo = servo;
		this.angle = angle;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		servo.setAngle(angle);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		servo.setAngle(angle);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
}