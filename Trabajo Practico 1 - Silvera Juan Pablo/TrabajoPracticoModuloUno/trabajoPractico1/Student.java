package trabajoPractico1;

public class Student
{
	public String DNI;
	public String name;
	public String gender;
	public int firstTestScore;
	public int secondTestScore;
	public float attendancePercent;
	public float averageScore;
	
	
	
    public Student(String DNI, String name, String gender, int firstTestScore, int secondTestScore, float attendancePercent)
    {
        this.DNI = DNI;
        this.name = name;
        this.gender = gender;
        this.firstTestScore = firstTestScore;
        this.secondTestScore = secondTestScore;
        this.attendancePercent = attendancePercent;
    }

	public float getAverageScore() 
	{
		return this.averageScore = (this.firstTestScore + this.secondTestScore) / 2;
	}	
}
