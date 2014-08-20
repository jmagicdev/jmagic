package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject which has at least one of the given colors
 * 
 * If you want each GameObject that has all the given colors, instead use
 * Intersect(HasColor(...), HasColor(...)).
 */
public class HasColor extends SetGenerator
{
	public static HasColor instance(Color what)
	{
		return new HasColor(Identity.instance(what));
	}

	public static HasColor instance(Color... what)
	{
		return new HasColor(Identity.instance((Object[])what));
	}

	public static HasColor instance(SetGenerator what)
	{
		return new HasColor(what);
	}

	public static HasColor instance(java.util.Collection<Color> colors)
	{
		return new HasColor(colors);
	}

	private final SetGenerator colorGenerator;

	private HasColor(SetGenerator colors)
	{
		this.colorGenerator = colors;
	}

	private HasColor(java.util.Collection<Color> color)
	{
		Set colors = new Set();
		colors.addAll(color);
		this.colorGenerator = Identity.fromCollection(colors);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		// TODO : change this to use state.getAllObjects() instead.
		Set ret = new Set();
		java.util.Collection<Color> colors = new java.util.LinkedList<Color>();
		colors.addAll(this.colorGenerator.evaluate(state, thisObject).getAll(Color.class));
		for(GameObject item: state.getAll(GameObject.class))
			for(Color c: colors)
				if(null != item.getColors() && item.getColors().contains(c))
				{
					ret.add(item);
					break;
				}
		return ret;
	}
}
