package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of Exhaustion")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class CurseofExhaustion extends Card
{
	public static final class CurseofExhaustionAbility1 extends StaticAbility
	{
		public CurseofExhaustionAbility1(GameState state)
		{
			super(state, "Enchanted player can't cast more than one spell each turn.");

			state.ensureTracker(new CastSpellThisTurn.Tracker());
			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, Intersect.instance(EnchantedBy.instance(This.instance()), CastSpellThisTurn.instance()));
			castSpell.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(part);
		}
	}

	public CurseofExhaustion(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// Enchanted player can't cast more than one spell each turn.
		this.addAbility(new CurseofExhaustionAbility1(state));
	}
}
