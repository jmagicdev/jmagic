package org.rnd.jmagic;

import org.rnd.jmagic.engine.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.*;

public class ListCards
{
	public static void main(String[] args)
	{
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AssignableTypeFilter(Card.class));
		scanner.addExcludeFilter(new AssignableTypeFilter(AlternateCard.class));

		java.util.Set<BeanDefinition> components = scanner.findCandidateComponents("org.rnd.jmagic.cards");
		for(BeanDefinition bean: components)
			try
			{
				System.out.println(Class.forName(bean.getBeanClassName()).getAnnotation(Name.class).value());
			}
			catch(ClassNotFoundException e)
			{
				//
			}
		System.out.println("---------------");
		System.out.println("Count: " + components.size());
	}
}
