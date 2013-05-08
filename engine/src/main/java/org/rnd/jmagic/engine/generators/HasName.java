package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject with the given name
 */
public class HasName extends SetGenerator
{
	public static class HasNameModifier
	{
		public final String effectName;
		public final int objectID;
		public final String additionalName;

		public HasNameModifier(String effectName, GameObject object, String additionalName)
		{
			this.effectName = effectName;
			this.objectID = object.ID;
			this.additionalName = additionalName;
		}
	}

	public static HasName instance(String what)
	{
		return new HasName(Identity.instance(what));
	}

	public static HasName instance(SetGenerator what)
	{
		return new HasName(what);
	}

	private final SetGenerator name;

	private HasName(SetGenerator name)
	{
		this.name = name;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<String> name = this.name.evaluate(state, thisObject).getAll(String.class);

		// #376: HasName(empty string) should evaluate to nothing
		name.remove("");

		java.util.Set<HasNameModifier> modifiers = new java.util.HashSet<HasNameModifier>(state.hasNameModifiers);
		java.util.Iterator<HasNameModifier> iter = modifiers.iterator();
		while(iter.hasNext())
		{
			HasNameModifier modifier = iter.next();

			// If the object looking isn't named according to the modification,
			// or if the name the object adds isn't cared about, ignore this
			// modifier.
			if(!modifier.effectName.equals(thisObject.getName()) || !name.contains(modifier.additionalName))
				iter.remove();
		}

		Set ret = new Set();
		for(GameObject card: state.getAllObjects())
		{
			if(name.contains(card.getName()))
				ret.add(card);
			else
				for(HasNameModifier modifier: modifiers)
					if(card.ID == modifier.objectID)
						ret.add(card);
		}
		return ret;
	}
}
