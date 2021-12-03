package frc.robot;

//import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX; //imports the correct victor API
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;



// the robot class create all the parts of the robot, ie the motors and sensors
public class Robot extends TimedRobot 
{


   
  private Joystick m_stick;

   //sets up the ports of motors
   private static final int kMotorPortLeftFront = 10;
   private static final int kMotorPortLeftRear = 11; 
   private static final int kMotorPortRightFront = 20;
   private static final int kMotorPortRightRear = 21;
   private static final int kMotorPortIntake = 30; 
   private static final int kMotorPortShooter = 31;
   private static final int kJoystickChannel = 0;
   
   UsbCamera Front;
   UsbCamera back;
   UsbCamera shooter;
   VideoSink server;

 
   // the doubles control the left and right drive train values, speed
   Double LDTMotor; //Left Drive Train
   Double RDTMotor; //Right Drive Train
   Double scaleFactor; // this is the value that scales the speed of the motors
   WPI_VictorSPX m_LeftFront = new WPI_VictorSPX(kMotorPortLeftFront); //identifys VictorSPX to Motors 
   WPI_VictorSPX m_LeftRear = new WPI_VictorSPX(kMotorPortLeftRear);
   WPI_VictorSPX m_RightFront = new WPI_VictorSPX(kMotorPortRightFront);
   WPI_VictorSPX m_RightRear = new WPI_VictorSPX(kMotorPortRightRear); 
   SpeedControllerGroup m_LeftGroup = new SpeedControllerGroup(m_LeftFront, m_LeftRear);
   SpeedControllerGroup m_RightGroup = new SpeedControllerGroup(m_RightFront, m_RightRear);
   WPI_VictorSPX m_Intake = new WPI_VictorSPX(kMotorPortIntake);
   WPI_VictorSPX m_Shooter = new WPI_VictorSPX(kMotorPortShooter);
   DifferentialDrive m_drive = new DifferentialDrive(m_LeftGroup, m_RightGroup);
   double stick1val,stick2val;
   Compressor compressor = new Compressor(0);
   private Solenoid s1 , s2, s3, s4; 

// im joto


  private final Timer m_timer = new Timer(); //identifys a timer 



@Override
public void robotInit(){
    

  WPI_VictorSPX m_Shooter = new WPI_VictorSPX(kMotorPortShooter); 
  compressor = new Compressor(1);
   Front = CameraServer.getInstance().startAutomaticCapture();
   back = CameraServer.getInstance().startAutomaticCapture();
   shooter = CameraServer.getInstance().startAutomaticCapture();
    server = CameraServer.getInstance().getServer();
    
    s1 = new Solenoid(0);
    s2 = new Solenoid(1); //logic error port is incorrectS
    s3 = new Solenoid(2);
    s4 = new Solenoid(3);
  
    m_Shooter.setInverted(true);
    m_RightFront.setInverted(true);
    m_LeftRear.setInverted(true);

    m_stick = new Joystick(kJoystickChannel);

scaleFactor = 0.5;
} 

@Override
public void robotPeriodic(){
}


@Override
public void autonomousInit(){

}


@Override 
public void autonomousPeriodic() {

  /* for(int i = 0; i<=50; i++ )
  {
    m_Intake.set(0.75);
  }
  m_Intake.set(0);

  int s = 0;

  for(s=0; s<=25; s++)
  {
      m_Shooter.set(0.75);
  }

  if (m_timer.get() <= 5)
  {
    
  } 
  else { 
    
  }
  */

}
   


//runs teleoperated 
@Override
  public void teleopPeriodic() {
    m_drive.arcadeDrive(m_stick.getX(), m_stick.getY());
    //m_drive.arcadeDrive(m_driverController.getY(Hand.kLeft), m_driverController.getY(Hand.kRight));

    compressor.start();

        if (m_stick.getRawButtonPressed(6)) 
        {
          System.out.println("Setting Back camera");
          server.setSource(back);
         } 
      else if (m_stick.getRawButtonReleased(7)) 
      {
          System.out.println("Setting Front camera");
          server.setSource(Front);
      }
      if (m_stick.getRawButtonPressed(2))
      {
        System.out.println("Setting camera 1");
          server.setSource(shooter);
      }

  
    m_drive.setSafetyEnabled(false);

    //triggers on and off Front Solenoid
   if (m_stick.getRawButtonPressed(4))
   {
      s1.set(true);
   }
   else if(m_stick.getRawButtonReleased(4))
   {
      s1.set(false);
   }
   
   //triggers on and off Front Solenoid
   if (m_stick.getRawButtonPressed(5))
   {
     s2.set(true);
   }

   else if (m_stick.getRawButtonReleased(5))
   {
     s2.set(false);
   }

   if (m_stick.getRawButtonPressed(10))
   {
     s3.set(true);
   }

   else if (m_stick.getRawButtonReleased(10))
   {
     s3.set(false);
   }

   if(m_stick.getRawButtonPressed(11))
   {
     s4.set(true);
   }

   else if (m_stick.getRawButtonReleased(11))
   {
     s4.set(false);
   }


}


@Override
public void testPeriodic() {
   
  
    m_drive.setSafetyEnabled(false);
    
    
    m_LeftFront.set(stick1val);
    m_LeftRear.set(stick1val);
    m_RightFront.set(stick2val);
    m_RightRear.set(stick2val);
    
    if(Timer.getMatchTime() > 10.1 && Timer.getMatchTime() < 10.9)
    {
      //frontSol.set(DoubleSolenoid.Value.kReverse);
      //rearSol.set(DoubleSolenoid.Value.kReverse);
    }  
    //System.out.println(timer.getMatchTime());

    
     
    


}

}



