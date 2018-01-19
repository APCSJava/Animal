
/***
 * Creates an instance of Animal subtypes and calls methods on them to demonstrate 
 * polymorphism applied through inheritance.
 * 
 * 
 **/
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnimalRunner {

	// a mapping of letters to lists of names starting with those letters
	private static Map<String, List<String>> names = initializeNamesMap();

	public static void main(String[] args) {
		// an empty array list of type Animal
		List<Animal> animals = new ArrayList<Animal>();

		List<Class<? extends Animal>> classList = collectAnimalClasses();
		for (Class<? extends Animal> c : classList) {
			animals.add(createAnimalOfClass(c));
		}
		System.out.println("Who do we have?");
		for (Animal a : animals) {
			System.out.println(a);
		}
		System.out.println("\nHow do we sound?");
		for (Animal a : animals) {
			System.out.println(a.makeSound());
		}
		System.out.println("\nHow do we look?");
		for (Animal a : animals) {
			System.out.println(a.describe());
		}
		System.out.println("\nWho is a dog?");
		for (Animal a : animals) {
			if (a instanceof Dog) {
				System.out.println(a+" is a Dog.");
			}
		}

	} 

	/**
	 * Attempts to return a reference to an instance of a class extending Animal. A
	 * random name is selected from the name database having the same first
	 * character as the name of the class provided. The random name is used as an
	 * argument during instantiation. If no suitable constructor is found, an error
	 * is thrown.
	 * 
	 * @param c
	 *            a class that extends Animal
	 * @return a new instance of the class provided
	 */
	private static Animal createAnimalOfClass(
			Class<? extends Animal> c) {
		String firstLetter = c.getName().substring(0, 1);
		List<String> appropriateNames = names.get(firstLetter);
		int randIndex = (int) (Math.random()
				* appropriateNames.size());
		String randName = appropriateNames.get(randIndex);
		Constructor<?> ctor;
		Animal a = null;
		try {
			ctor = c.getDeclaredConstructor(String.class);
			a = (Animal) ctor.newInstance(randName);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	private static Map<String, List<String>> initializeNamesMap() {
		try {
			List<String> lines = Files
					.readAllLines(Paths.get("yob1964.txt"));
			return lines.stream()
					.map(a -> a.substring(0, a.indexOf(",")))
					.collect(Collectors
							.groupingBy(s -> s.substring(0, 1)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Collects from the working directory all files that extend Animal
	 * 
	 * @return a list holding all classes that extend Animal
	 * @throws ClassNotFoundException
	 *             if the requested class is not found
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class<? extends Animal>> collectAnimalClasses() {
		List<Class<? extends Animal>> animals = new ArrayList<Class<? extends Animal>>();
		File[] classes = findClassFilesInPackage();
		for (File f : classes) {
			String nameWithExtension = f.getName();
			int idx = nameWithExtension.lastIndexOf(".class");
			String searchString = nameWithExtension.substring(0,
					idx);
			Class classObj;
			try {
				classObj = Class.forName(searchString);
				if (Animal.class.isAssignableFrom(classObj)
						&& !Modifier.isAbstract(
								classObj.getModifiers())) {
					animals.add(classObj);
				}
			} catch (ClassNotFoundException e) {
				System.out.println(
						"Error getting class from file " + f);
			}
		}
		return animals;
	}

	/**
	 * Looks inside the current working directory and collects all file names having
	 * the extension .class
	 * 
	 * @return an array of files
	 */
	private static File[] findClassFilesInPackage() {
		File directory = new File(
				AnimalRunner.class.getResource("/").getFile());
		File[] files = directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("class");
			}
		});
		return files;
	}

}
