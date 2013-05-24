package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class SelectedModesOf extends SetGenerator
{
	public static SelectedModesOf instance(SetGenerator what)
	{
		return new SelectedModesOf(what);
	}

	private SetGenerator what;

	private SelectedModesOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			ret.addAll(object.getSelectedModes());

		return ret;
	}

}
