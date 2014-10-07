package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Eidolon of Rhetoric")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class EidolonofRhetoric extends Card
{
	public static final class EidolonofRhetoricAbility0 extends StaticAbility
	{
		public EidolonofRhetoricAbility0(GameState state)
		{
			super(state, "Each player can't cast more than one spell each turn.");

			state.ensureTracker(new CastSpellThisTurn.Tracker());
			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, CastSpellThisTurn.instance());
			castSpell.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(part);
		}
	}

	public EidolonofRhetoric(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Each player can't cast more than one spell each turn.
		this.addAbility(new EidolonofRhetoricAbility0(state));
	}
}
