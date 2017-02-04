package org.usfirst.frc.team3291.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Ultrasonic Sensor. testing time

public class Robot extends SampleRobot {
	RobotDrive myRobot = new RobotDrive(3, 2, 1, 0);
	Joystick stick = new Joystick(0);
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	SendableChooser<String> chooser = new SendableChooser<>();

	
	public Robot() {
		myRobot.setExpiration(0.1);
	}

	@Override
	public void robotInit() {
		myRobot.setInvertedMotor(MotorType.kFrontRight, true);
		myRobot.setInvertedMotor(MotorType.kFrontLeft, true);
		myRobot.setInvertedMotor(MotorType.kRearRight, true);
		myRobot.setInvertedMotor(MotorType.kRearLeft, true);
		//AnalogInput input = new AnalogInput(3);
				//input.setOversampleBits(4);
				//int bits = input.getOversampleBits();
				//int raw = input.getValue();
		//double volts = input.getVoltage();
				//int averageRaw = input.getAverageValue();
				//double averageVolts = input.getAverageVoltage();
		//System.out.println(volts);
				//input.setAverageBits(2);
				//bits = input.getAverageBits();
				//AnalogInput.setGlobalSampleRate(62500);
		    	//ultra = new Ultrasonic(Ultrasonic.kAnalogInputChannels, Ultrasonic.kAnalogOutputChannels);
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto modes", chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * if-else structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomous() {
		myRobot.setSafetyEnabled(false);
		String autoSelected = chooser.getSelected();
		// String autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		AnalogInput input = new AnalogInput(3);
		double volts = input.getVoltage();
		double distance = (volts/(5/1024))*5; //((5/1024)*1000)

		switch (autoSelected) {
		case customAuto:
			myRobot.setSafetyEnabled(false);
			myRobot.drive(-0.5, 1.0); // spin at half speed
			Timer.delay(2.0); // for 2 seconds
			myRobot.drive(0.0, 0.0); // stop robot
			break;
		case defaultAuto:
		default:
			myRobot.setSafetyEnabled(false);
			/*myRobot.drive(-0.5, 0.0);
			Timer.delay(1.89);
			myRobot.drive(-0.5, 1.0);
			Timer.delay(1.73); //110 degrees, need 120
			myRobot.drive(-0.5, 0.0);
			Timer.delay(0.405);*/
			//myRobot.drive(0.0, 0.0);
			boolean respawn=false;
			while(!respawn){
				if(distance > 40){
					myRobot.drive(-0.5, 0);
					Timer.delay(0.5);
				}else if (distance < 30){
					myRobot.drive(0.0, 0);
					Timer.delay(2);
				}
			}
			
		}
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		
		while (isOperatorControl() && isEnabled()) {
			myRobot.arcadeDrive(stick); // drive with arcade style (use right
										// stick)
			Timer.delay(0.005); // wait for a motor update time
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
