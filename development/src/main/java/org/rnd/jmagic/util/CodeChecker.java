package org.rnd.jmagic.util;

import java.io.*;
import java.lang.reflect.*;

import org.rnd.jmagic.engine.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.core.type.*;
import org.springframework.core.type.classreading.*;
import org.springframework.core.type.filter.*;

public class CodeChecker
{
	public static final boolean CHECK_CREATABLES = true;
	public static final boolean CHECK_GENERATORS = true;

	public static void main(String[] args)
	{
		if(CHECK_CREATABLES)
		{
			ClassLoader classLoader = org.rnd.jmagic.CardLoader.getInstance();

			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
			scanner.setResourceLoader(new org.springframework.core.io.support.PathMatchingResourcePatternResolver(classLoader));
			TypeFilter filter = new CreatableFilter(classLoader);
			scanner.addIncludeFilter(filter);
			java.util.Set<BeanDefinition> components = scanner.findCandidateComponents("org.rnd");
			if(!components.isEmpty())
				System.out.println("Problem components: " + components.size());
			System.out.println("Done");
			System.out.flush();
		}

		if(CHECK_GENERATORS)
		{
			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
			scanner.addIncludeFilter(new GeneratorFilter());
			java.util.Set<BeanDefinition> components = scanner.findCandidateComponents("org.rnd.jmagic");
			if(!components.isEmpty())
				System.out.println("Problem generators: " + components.size());
			System.out.println("Done");
			System.out.flush();
		}
	}

	public static class GeneratorFilter implements TypeFilter
	{
		private final AssignableTypeFilter includeGenerators = new AssignableTypeFilter(org.rnd.jmagic.engine.SetGenerator.class);

		@Override
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException
		{
			if(!this.includeGenerators.match(metadataReader, metadataReaderFactory))
				return false;

			Class<?> clazz;
			ClassMetadata classMetadata = metadataReader.getClassMetadata();
			try
			{
				clazz = Class.forName(classMetadata.getClassName());
			}
			catch(ClassNotFoundException e)
			{
				System.err.println("Can't find class for " + classMetadata.getClassName());
				return true;
			}

			if(clazz == SetGenerator.class)
				return false;

			boolean isPrivate = Modifier.isPrivate(clazz.getModifiers());

			try
			{
				for(Constructor<?> constructor: clazz.getConstructors())
				{
					int modifiers = constructor.getModifiers();
					if(!Modifier.isPrivate(modifiers))
					{
						System.out.println("Non-private constructor for " + classMetadata.getClassName());
						return true;
					}
				}
			}
			catch(RuntimeException ex)
			{
				System.err.println("Can't find constructors for " + classMetadata.getClassName());
				return true;
			}

			try
			{
				Method instanceMethod = null;
				for(Method method: clazz.getMethods())
				{
					if(method.getName().equals("instance"))
					{
						instanceMethod = method;
						break;
					}
				}

				boolean isIndependent = classMetadata.isIndependent();
				if(!isIndependent)
				{
					Class<?> enclosingClass;
					try
					{
						enclosingClass = Class.forName(classMetadata.getEnclosingClassName());
					}
					catch(ClassNotFoundException e)
					{
						System.err.println("Can't find enclosing class: " + classMetadata.getEnclosingClassName());
						return true;
					}

					if(Identified.class.isAssignableFrom(enclosingClass))
					{
						System.out.println("Non-independent generator! (" + classMetadata.getClassName() + ")");
						return true;
					}
				}

				if(instanceMethod == null)
				{
					if(isIndependent && !isPrivate)
					{
						System.out.println("No instance method found for " + classMetadata.getClassName());
						return true;
					}
				}
				else
				{
					int instanceModifiers = instanceMethod.getModifiers();
					if(!Modifier.isPublic(instanceModifiers))
					{
						System.out.println("Non-public instance method for " + classMetadata.getClassName());
						return true;
					}
					if(!Modifier.isStatic(instanceModifiers))
					{
						System.out.println("Non-static instance method for " + classMetadata.getClassName());
						return true;
					}
				}
			}
			catch(RuntimeException ex)
			{
				System.err.println("Can't find methods for " + classMetadata.getClassName());
				return true;
			}

			return false;
		}
	}

	public static class CreatableFilter implements TypeFilter
	{
		private final ClassLoader classLoader;
		private final AssignableTypeFilter includeGameObjects;
		private final AssignableTypeFilter includeStaticAbilities;

		// private final AssignableTypeFilter excludeTokens;

		public CreatableFilter(ClassLoader classLoader)
		{
			this.classLoader = classLoader;
			this.includeGameObjects = new AssignableTypeFilter(org.rnd.jmagic.engine.GameObject.class);
			this.includeStaticAbilities = new AssignableTypeFilter(org.rnd.jmagic.engine.StaticAbility.class);
			// this.excludeTokens = new
			// AssignableTypeFilter(org.rnd.jmagic.engine.Token.class);
		}

		@Override
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException
		{
			if(!(this.includeGameObjects.match(metadataReader, metadataReaderFactory) || this.includeStaticAbilities.match(metadataReader, metadataReaderFactory)))
				// || this.excludeTokens.match(metadataReader,
				// metadataReaderFactory))
				return false;

			ClassMetadata classMetadata = metadataReader.getClassMetadata();

			String className = classMetadata.getClassName();

			if(className.equals("org.rnd.jmagic.engine.GameObject") || className.equals("org.rnd.jmagic.engine.StaticAbility") || className.equals("org.rnd.jmagic.engine.Card"))
				return false;

			Class<?> clazz = null;
			try
			{
				clazz = this.classLoader.loadClass(className);
			}
			catch(ClassNotFoundException e1)
			{
				System.err.println("Couldn't find class for " + className);
				return false;
			}

			boolean hasGameStateConstructor = false;
			try
			{
				if(clazz.getConstructors().length == 1)
				{
					clazz.getConstructor(new Class[] {GameState.class});
					hasGameStateConstructor = true;
				}
			}
			catch(Exception e)
			{
				// GameState constructor not present
			}

			boolean hasTokenConstructor = false;
			try
			{
				if(clazz.getConstructors().length == 1)
				{
					clazz.getConstructor(new Class[] {GameState.class, GameObject.class});
					hasTokenConstructor = true;
				}
			}
			catch(Exception e)
			{
				// GameState constructor not present
			}

			Method createMethod = null;
			boolean hasCreateMethod = false;
			try
			{
				createMethod = clazz.getMethod("create", new Class[] {Game.class});
				if(createMethod.getDeclaringClass().equals(clazz))
					hasCreateMethod = true;
			}
			catch(Exception e)
			{
				// Create method not present
			}

			boolean hasNameAnnotation = false;
			try
			{
				String name = clazz.getAnnotation(Name.class).value();
				hasNameAnnotation = (name != null && (0 != name.length()));
			}
			catch(Exception e)
			{
				// Name annotation not present
			}
			boolean hasColorIdentityAnnotation = false;
			try
			{
				Color[] colors = clazz.getAnnotation(ColorIdentity.class).value();
				hasColorIdentityAnnotation = (colors != null);
			}
			catch(Exception e)
			{
				// Color Identity annotation not present
			}

			boolean isAbstract = classMetadata.isAbstract();
			boolean isFinal = classMetadata.isFinal();
			boolean isIndependent = classMetadata.isIndependent();
			boolean isCard = Card.class.isAssignableFrom(clazz);
			boolean isToken = Token.class.isAssignableFrom(clazz);

			if(!isIndependent)
			{
				System.out.println("Class is not independent! (" + clazz.getName() + ")");
				return true;
			}
			if(hasCreateMethod && isAbstract)
			{
				System.out.println("Class is abstract and has a create method! (" + clazz.getName() + ")");
				return true;
			}
			if(!isAbstract && !isFinal)
			{
				System.out.println("Class should either be abstract or final! (" + clazz.getName() + ")");
				return true;
			}
			if(!isToken && !isAbstract && (hasGameStateConstructor == hasCreateMethod))
			{
				System.out.println("Class should either be abstract, or should have a create method! (" + clazz.getName() + ")");
				return true;
			}
			if(isToken && (hasTokenConstructor == hasCreateMethod))
			{
				System.out.println("Token should either have a create method or a (GameState, GameObject) constructor! (" + clazz.getName() + ")");
				return true;
			}
			if(isFinal && isCard && !hasNameAnnotation)
			{
				System.out.println("Class is missing @Name annotation! (" + clazz.getName() + ")");
				return true;
			}
			if(hasCreateMethod && createMethod.getReturnType() != clazz)
			{
				System.out.println("Class' create method returns an instance of the wrong type! (" + clazz.getName() + ")");
				return true;
			}
			if(isCard && isFinal && !hasColorIdentityAnnotation)
			{
				System.out.println("Class is missing its ColorIdentity annotation! (" + clazz.getName() + ")");
				return true;
			}

			return false;
		}
	}

}
