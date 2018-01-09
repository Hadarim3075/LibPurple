package LibPurple.systems;


import org.usfirst.frc.team3075.robot.Robot;

import LibPurple.control.PIDvalue;
import LibPurple.sensors.ConsoleJoystick;
import LibPurple.sensors.Encoder3075;
import LibPurple.systems.DriveSystem3075.DrivingState;
import LibPurple.utils.Utils;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.tables.ITable;
/***
 * Initialization example:
 * super.setPIDValues(...);
 * super.setMPValues(...);
 * super.setTurnMPValues(...);
 * super.setVelocityTolerance(...);
 * super.setDistanceTolerance(...);
 * super.distancePerAngle = ...
 * 
 * @author 3075
 *
 */
public abstract class DriveSystem3075 extends Subsystem implements Sendable
{
	public static enum DrivingState
	{
		Voltage, VelocityClosedLoop
	}
	
	DrivingState state = DrivingState.Voltage;
	
	protected SpeedController rightMotor;
	protected SpeedController leftMotor;
	
	private Encoder3075 rightEncoder;
	private Encoder3075 leftEncoder;
	
	private PIDController rightPID;
	protected PIDvalue rightPIDValue;
	
	private PIDController leftPID;
	private PIDvalue leftPIDValue;

	protected double rightMaxV;
	protected double leftMaxV;
	protected double rightTurnMaxV;
	protected double leftTurnMaxV;
	protected double distancePerAngle;
	protected double positionTolerance;
	protected double angleTolerance;
	protected double robotWidth;
	

	protected void initialize(SpeedController rightMotor, SpeedController leftMotor,
			Encoder3075 rightEncoder, Encoder3075 leftEncoder)
	{
		this.rightMotor = rightMotor;
		this.leftMotor = leftMotor;
		this.rightEncoder = rightEncoder;
		this.leftEncoder = leftEncoder;
		
		this.rightEncoder.setPIDSourceType(PIDSourceType.kRate);
		this.leftEncoder.setPIDSourceType(PIDSourceType.kRate);
	}
	

	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub
	}
	/**
	 * if the drive system is in velocity state then sets the system's speed
	 * if the drive system is in voltage state then gives the values straight to the engines
	 * @param rightValue - the right engine speed/value
	 * @param leftValue - the left engine speed/value
	 */
	public void set(double rightValue, double leftValue)
	{
		if(state == DrivingState.Voltage)
		{
			rightMotor.set(rightValue);
			leftMotor.set(leftValue);
		}
		else if(state == DrivingState.VelocityClosedLoop)
		{
			double maxV = Math.min(rightMaxV, leftMaxV);
			
			if(rightValue == 0)
			{
				rightPID.disable();
				rightPID.reset();
			}
			else
				rightPID.enable();	
			
			if(leftValue == 0)
			{
				leftPID.disable();
				leftPID.reset();
			}
			else
				leftPID.enable();
			
			rightPID.setSetpoint(rightValue * maxV);
			leftPID.setSetpoint(leftValue * maxV);
		}
	}
	
	/**
	 * switched the drive system state from its current state to a new state 
	 * @param newState - the new state, can get "Voltage", "VelocityClosedLoop","DistanceMotionProfiled" 
	 */
	public void enterState(DrivingState newState)
	{
		this.state = newState;
		
		switch(state)
		{
		case Voltage:
			setPIDEnabled(false);
			break;
		case VelocityClosedLoop:
			rightEncoder.setPIDSourceType(PIDSourceType.kRate);
			leftEncoder.setPIDSourceType(PIDSourceType.kRate);
			setPIDEnabled(true);
			break;
		default:
			setPIDEnabled(false);
			break;
			
		}
	}
	
	/**
	 * enable or disables the PID
	 * @param enabled - boolean that sets the PID
	 */
	private void setPIDEnabled(boolean enabled)
	{
		if(enabled)
		{
			rightPID.enable();
			leftPID.enable();
		}
		else
		{
			rightPID.disable();
			leftPID.disable();
		}
	}
	
	/**
	 * resets the encoders
	 */
	public void reset()
	{
		leftEncoder.reset();
		rightEncoder.reset();
		leftPID.reset();
		rightPID.reset();
	}
	
	public Command arcadeDrive(Joystick joystick)
	{
		return new ArcadeDriveCommand(this, joystick);
	}
	
	public Command xboxArcadeDrive(ConsoleJoystick stick)
	{
		return new XboxArcade(this, stick);
	}
	
	public Command tankDrive(Joystick rightJoystick, Joystick leftJoystick)
	{
		return new TankDriveCommand(this, rightJoystick, leftJoystick);
	}
	
	/**
	 * Toggles the drive system state between the two states given.
	 * 
	 * @param state1 - the first state
	 * @param state2 - the second state
	 */
	public Command stateToggle(DrivingState state1, DrivingState state2)
	{
		return new StateToggle(this, state1, state2);
	}

	/**
	 * sets the PID values
	 * @param leftPIDValue - the values for the left side
	 * @param rightPIDValue - the values for the right side
	 */
	public void setPIDValues(PIDvalue leftPIDValue, PIDvalue rightPIDValue) 
	{
		this.leftPIDValue = leftPIDValue;
		this.rightPIDValue = rightPIDValue;
		
		if(this.leftPID != null)
			leftPID.setPID(leftPIDValue.kP, leftPIDValue.kI, leftPIDValue.kD, leftPIDValue.kF);
		else
			leftPID = new PIDController(leftPIDValue.kP, leftPIDValue.kI, leftPIDValue.kD, leftPIDValue.kF, leftEncoder, leftMotor);

		if(this.rightPID != null)
			rightPID.setPID(rightPIDValue.kP, rightPIDValue.kI, rightPIDValue.kD, rightPIDValue.kF);
		else
			rightPID = new PIDController(rightPIDValue.kP, rightPIDValue.kI, rightPIDValue.kD, rightPIDValue.kF, rightEncoder, rightMotor);
	}

	/**
	 * sets the tolerance for the velocity
	 * @param velocityTolerance - the tolerance to set
	 */
	public void setVelocityTolerance(double velocityTolerance) 
	{
		leftPID.setAbsoluteTolerance(velocityTolerance);
		rightPID.setAbsoluteTolerance(velocityTolerance);
	}
	
	/**
	 * sets the tolerance for the distance
	 * @param tolerance - the tolerance to set
	 */
	public void setTolerance(double tolerance) 
	{
		rightMP.setTolerance(tolerance);
		leftMP.setTolerance(tolerance);
	}
	
	public double getAngle()
	{
		return (leftEncoder.getDistance() - rightEncoder.getDistance()) / (2 * distancePerAngle);
	}
	
	public PIDvalue getLeftPIDValue() 
	{
		return leftPIDValue;
	}

	public PIDvalue getRightPIDValue()
	{
		return rightPIDValue;
	}

	public double getRightMaxV()
	{
		return rightMaxV;
	}

	public double getLeftTurnMaxV()
	{
		return leftTurnMaxV;
	}
	
	public double getRightTurnMaxV()
	{
		return rightTurnMaxV;
	}
	
	public void setMaxV(double rightMaxV, double leftMaxV)
	{
		this.rightMaxV = rightMaxV;
		this.leftMaxV = leftMaxV;
	}
	
	public void setTurnMaxV(double rightTurnMaxV, double leftTurnMaxV)
	{
		this.rightTurnMaxV = rightTurnMaxV;
		this.leftTurnMaxV = leftTurnMaxV;
	}

	public double getLeftMaxV()
	{
		return leftMaxV;
	}

	public double getDistanceTolerance() 
	{
		return rightMP.getTolerance();
	}
	
	public DrivingState getState(){
		return state;
	}

	public double getDistancePerAngle() 
	{
		return distancePerAngle;
	}
	
	public double getRobotWidth()
	{
		return robotWidth;
	}

	public void setDistancePerAngle(double distancePerAngle) 
	{
		this.distancePerAngle = distancePerAngle;
	}

	public double getAverageError() 
	{
		return (rightPID.getError() + leftPID.getError()) / 2;
	}
	
	public double getAngleTolerance()
	{
		return angleTolerance;
	}
	
	private ITable m_table;
	
	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
		
	}

	public ITable getTable() {
		// TODO Auto-generated method stub
		return m_table;
	}

	public String getSmartDashboardType() {
		// TODO Auto-generated method stub
		return "Drive System";
	}
	
	public void updateTable() 
	{
	    if (m_table != null) 
	    {
	      m_table.putNumber("Right Rate", rightEncoder.getRate());
	      m_table.putNumber("Left Rate", leftEncoder.getRate());
	      m_table.putNumber("Right Distance", rightEncoder.getDistance());
	      m_table.putNumber("Left Distance",  leftEncoder.getDistance());
	      m_table.putNumber("Right Rate Setpoint", rightPID.getSetpoint());
	      m_table.putNumber("Left Rate Setpoint", leftPID.getSetpoint());
	      m_table.putNumber("Angle", getAngle());
	      m_table.putString("Driving State", state + "");
	      
	    }
	}
}

class XboxArcade extends Command
{
	
	DriveSystem3075 driveSystem;
	ConsoleJoystick stick;
	
	private double leftValue;
    private double rightValue;
    private double last = 0;
	
	public XboxArcade(DriveSystem3075 driveSystem, ConsoleJoystick stick) {
		requires(driveSystem);
		this.driveSystem = driveSystem;
		this.stick = stick;
	}
	
	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() 
	{
		double throttle = stick.yGet();
		double turn = Math.pow(stick.xGet(), 2) * Math.signum(stick.xGet());
		
//		double throttle = stick.getDrivingY();
//		double turn = stick.getRawAxis(0);
		throttle = Utils.deadband(throttle, 0.01);
//		turn = Utils.deadband(turn, 0.3);
		
		throttle = Utils.accellimit(throttle, last, 0.2);
        leftValue = (throttle + turn);
        rightValue = (throttle - turn);

        driveSystem.set(rightValue, leftValue);
        
        last = throttle;
        
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	protected void end() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		
	}

	
}

class ArcadeDriveCommand extends Command
{
	DriveSystem3075 driveSystem;
	Joystick stick;
	
	private double leftValue;
    private double rightValue;
    
	public ArcadeDriveCommand(DriveSystem3075 driveSystem, Joystick stick) 
	{
		requires(driveSystem);
		this.driveSystem = driveSystem;
		this.stick = stick;
	}

	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() 
	{
		double throttle = stick.getRawAxis(AxisType.kY.value);
		double turn = stick.getRawAxis(AxisType.kX.value);
		
        leftValue = throttle + turn;
        rightValue = throttle - turn;

        driveSystem.set(rightValue, leftValue);
        
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	protected void end() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		
	}

}

class TankDriveCommand extends Command
{
	DriveSystem3075 driveSystem;
	Joystick rightStick;
	Joystick leftStick;
    
	public TankDriveCommand(DriveSystem3075 driveSystem, Joystick rightStick, Joystick leftStick) 
	{
		requires(driveSystem);
		this.driveSystem = driveSystem;
		this.rightStick = rightStick;
		this.leftStick = leftStick;
	}

	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() 
	{
        driveSystem.set(rightStick.getY(), leftStick.getY());
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	protected void end() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		
	}

}


class StateToggle extends InstantCommand
{
	DriveSystem3075.DrivingState state1, state2;
	DriveSystem3075 driveSystem;
	

	public StateToggle(DriveSystem3075 driveSystem, DrivingState state1, DrivingState state2) 
	{
		this.driveSystem = driveSystem;
		this.state1 = state1;
		this.state2 = state2;
	}

	@Override
	protected void initialize() 
	{	
		if(driveSystem.getState().equals(state1))
		{
			driveSystem.enterState(state2);
		}
		else
		{
			driveSystem.enterState(state1);
		}
	}
}

