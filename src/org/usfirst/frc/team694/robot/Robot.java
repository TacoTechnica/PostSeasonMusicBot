/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team694.robot;

import org.usfirst.frc.team694.robot.musicutil.MotorEmitter;
import org.usfirst.frc.team694.robot.musicutil.Player;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {

	// DESTINY
	private static final int ARM_MOTOR_PORT = 8;
	private static final int SHOOTER_MOTOR_PORT = 7;
	private static final int THIN_ROLLER_MOTOR_PORT = 6;
	private static final int WHEEL_ROLLER_MOTOR_PORT = 5;

	private WPI_TalonSRX shooter, thinRoller, wheelRoller;

	private Joystick testJoystick;

	private Player player;

	@Override
	public void robotInit() {
		shooter = new WPI_TalonSRX(SHOOTER_MOTOR_PORT);
		thinRoller = new WPI_TalonSRX(THIN_ROLLER_MOTOR_PORT);
		wheelRoller = new WPI_TalonSRX(WHEEL_ROLLER_MOTOR_PORT);

		MotorEmitter thinRollerEmitter = new MotorEmitter(thinRoller, 0.32);

		player = new Player("res/moonlight.mid");
		player.addEmitter(thinRollerEmitter);

		testJoystick = new Joystick(1);

		configSmartDashboard();
	}

	private void configSmartDashboard() {
//		SmartDashboard.putNumber("SHOOTER kP", 0);
//		SmartDashboard.putNumber("SHOOTER kI", 0);
//		SmartDashboard.putNumber("SHOOTER kD", 0);
	}

	@Override
	public void autonomousInit() {
//		motor.config_kP(0, SmartDashboard.getNumber("SHOOTER kP", 0), 1000);
//		motor.config_kI(0, SmartDashboard.getNumber("SHOOTER kI", 0), 1000);
//		motor.config_kD(0, SmartDashboard.getNumber("SHOOTER kD", 0), 1000);
	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		double axis = -1 * testJoystick.getRawAxis(1);
		thinRoller.set(ControlMode.PercentOutput, axis);
		System.out.println(axis);
	}

}
