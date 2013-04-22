package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rule of Law")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class RuleofLaw extends Card
{
	public static final class Law extends StaticAbility
	{
		public Law(GameState state)
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

	public RuleofLaw(GameState state)
	{
		super(state);

		this.addAbility(new Law(state));
	}
}
