package trabajoPractico1;

import java.io.*;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.util.StringUtils;

public class TrabajoPractico1
{
	public static void main(String [] args) throws IOException 
	{
		List<Student> studentsList = new ArrayList<>(); // Crea una Lista para almacenar los datos de los alumnos
		
		float studentsAverageScore = 0;
		int scoreAboveFourCounter = 0;
		int rankingPosition = 1;

		int maleCounter = 0;
		int femaleCounter = 0;
		float maleTotalAttendance = 0;
		float femaleTotalAttendance = 0;

		boolean dataIsValid = true;
		int unapprovedStudent = 0;

		try
		{  	
			FileReader fileLocation = new FileReader("C:/UTN - TP 1 Java/Archivo de calificaciones.txt");
			BufferedReader br = new BufferedReader(fileLocation);

			String fileRead = br.readLine();


			while (fileRead != null && dataIsValid)
			{
				String[] SplitPosition = fileRead.split(",");

				fileRead = br.readLine();

				String tempDNI = SplitPosition[0].trim();  
				String tempName = SplitPosition[1].trim();
				String tempgender = SplitPosition[2].trim();
				int tempFirstTestScore = Integer.parseInt(SplitPosition[3].trim());
				int tempSecondTestScore = Integer.parseInt(SplitPosition[4].trim());
				float tempAttendancePercent = Float.parseFloat(SplitPosition[5].trim());

				Student tempStudent = new Student(tempDNI, tempName, tempgender, tempFirstTestScore, tempSecondTestScore, tempAttendancePercent);             
				studentsList.add(tempStudent);
				
				//Validador de información
				for(int i = 0; i < studentsList.size(); i++) 
				{	
					Student student = studentsList.get(i);
					String studentWithoutWhitespace = student.name;
					
					studentWithoutWhitespace = studentWithoutWhitespace.replaceAll("[^A-Za-z]+", "");
					
					if(student.DNI.length() == 8 && student.DNI.matches("[0-9]+") && dataIsValid)
					{					
					}else
					{
						System.out.println("Se encontraron errores en la información ingresada.");
						System.out.println("El DNI ingresado tiene más o menos de 8 digitos, o bien, caracteres no numericos.");
						System.out.println("El Nombre del alumno con datos erroneos es: " + student.name);
						System.out.println("El DNI con errores es: " + student.DNI);
						System.out.println("");
						dataIsValid = false;
					}
					
					if(studentWithoutWhitespace.matches("[A-Za-z]+") && dataIsValid)
					{
						//System.out.println(dataIsValid);
						//System.out.println(studentWithoutWhitespace);
					}else
					{
						System.out.println("Se encontraron errores en la información ingresada.");
						System.out.println("El nombre del alumno solo puede contener letras.");
						System.out.println("El nombre que contiene errores es: " + student.name);
						System.out.println("");
						dataIsValid = false;
					}
					
				}
			}
			
			
			studentsList.sort(Comparator.comparing(Student::getAverageScore).reversed());

			
			if (dataIsValid) 
			{				
				tpExerciesCalculations(studentsList, studentsAverageScore, scoreAboveFourCounter, rankingPosition,
						maleCounter, femaleCounter, maleTotalAttendance, femaleTotalAttendance, unapprovedStudent);
			}else {
				System.out.println("Por favor corrija los datos ingresados e intente nuevamente.");
			}
			//Fin de la validación de los datos			
			
			br.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("No se encontró el archivo.");
		}

		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	
	/*3. El programa debe validar que los campos del archivo estén correctos. 
	Si no están correctos debe informarse por pantalla en qué posición del archivo está el problema.
	Validar que todos los campos tengan información, que el género sea M o F, que la nota sea un numero entero
	entre 1 y 10 y que haya un valor en el porcentaje de asistencia.*/
	
	//Metodo donde se calcúlan y muestran todos los ejercicios.
	private static void tpExerciesCalculations(List<Student> studentsList, float studentsAverageScore,
		int scoreAboveFourCounter, int rankingPosition, int maleCounter, int femaleCounter,
		float maleTotalAttendance, float femaleTotalAttendance, int unapprovedStudent)
		{
		float studentCounter;
		
		for(int i = 0; i < 5; i++) //2.1 Los nombres de los 5 mejores promedios (indicar dni, nombre y nota)
		{
			Student student = studentsList.get(i);

			System.out.println("Puesto en el ranking de promedios: " + rankingPosition);
			System.out.println("Promedio general: " + student.averageScore);
			System.out.println("DNI: " + student.DNI + " | Nombre: " + student.name + " | " + "Primera nota: "+ student.firstTestScore + " | " + "Segunda nota: " + student.secondTestScore);
			System.out.println("");
			rankingPosition++;
		}

		//2.2 El promedio general del curso
		//2.4 El promedio de asistencia separado por genero
		for(int i = 0; i < studentsList.size(); i++) 
		{	
			Student student = studentsList.get(i);
			studentCounter = studentsList.size();
			studentsAverageScore += student.averageScore;

			if (student.gender.equals("M") || student.gender.equals("m"))
			{
				maleCounter++;
				maleTotalAttendance +=  student.attendancePercent;
			}

			if (student.gender.equals("F") || student.gender.equals("f"))
			{
				femaleCounter++;
				femaleTotalAttendance += student.attendancePercent;
			}

			//2.3 La cantidad de aprobados (Con ambas notas mayores a 4)
			if (student.firstTestScore >= 4 && student.secondTestScore >= 4)
			{
				scoreAboveFourCounter++;
			}

			//2.5 Cantidad de desaprobados con asistencia mayor al 75%
			if ((student.firstTestScore < 4 || student.secondTestScore < 4)  && student.attendancePercent >= 75)
			{
				unapprovedStudent++;
			}		

			if (i == studentsList.size()-1)
			{					
				femaleTotalAttendance /= femaleCounter;
				maleTotalAttendance /= maleCounter;

				System.out.println("=================================================================================");
				System.out.println("El promedio general del curso es: " + (studentsAverageScore / studentCounter));
				System.out.println("=================================================================================");
				System.out.println("La cantidad de alumnos aprobados con más de 4 en ambas notas es: " + scoreAboveFourCounter);
				System.out.println("=================================================================================");
				System.out.println("El promedio de asistencia por Genero es:");
				System.out.println("Genero Masculino: " + maleTotalAttendance +"%");
				System.out.println("Genero Femenino: " + femaleTotalAttendance +"%");
				System.out.println("=================================================================================");
				System.out.println("La cantidad de alumnos desaprobados con el 75% de asistencia es: " + unapprovedStudent);
			}
		}
	}
}