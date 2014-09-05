package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class CantBeCountered extends StaticAbility
{
	private final String spellName;
	private final boolean bySpellsOrAbilities;

	public CantBeCountered(GameState state, String spellName)
	{
		this(state, spellName, false);
	}

	public CantBeCountered(GameState state, String spellName, boolean bySpellsOrAbilities)
	{
		super(state, spellName + " can't be countered" + (bySpellsOrAbilities ? " by spells or abilities" : "") + ".");
		this.spellName = spellName;
		this.bySpellsOrAbilities = bySpellsOrAbilities;

		SimpleEventPattern pattern = new SimpleEventPattern(EventType.COUNTER);
		pattern.put(EventType.Parameter.OBJECT, This.instance());
		if(bySpellsOrAbilities)
			pattern.put(EventType.Parameter.CAUSE, InZone.instance(Stack.instance()));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));

		this.addEffectPart(part);
		this.canApply = THIS_IS_ON_THE_STACK;
	}

	@Override
	public CantBeCountered create(Game game)
	{
		return new CantBeCountered(game.physicalState, this.spellName, this.bySpellsOrAbilities);
	}
}
