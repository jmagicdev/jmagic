package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class HasPower extends SetGenerator
{
	public static HasPower instance(int what)
	{
		return new HasPower(org.rnd.jmagic.Convenience.numberGenerator(what));
	}

	public static HasPower instance(SetGenerator what)
	{
		return new HasPower(what);
	}

	private SetGenerator what;

	private HasPower(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		Set what = this.what.evaluate(state, thisObject);
		org.rnd.util.NumberRange range = what.getOne(org.rnd.util.NumberRange.class);
		if(range != null)
		{
			for(GameObject object: state.getAllObjects())
				// Only creatures have power
				if(object.getTypes().contains(Type.CREATURE) && range.contains(object.getPower()))
					ret.add(object);
		}
		else
		{
			java.util.Set<Integer> numbers = what.getAll(Integer.class);
			for(GameObject object: state.getAllObjects())
				// Only creatures have power
				if(object.getTypes().contains(Type.CREATURE) && numbers.contains(object.getPower()))
					ret.add(object);
		}
		return ret;
	}

}
