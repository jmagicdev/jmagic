package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Blinding Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class BlindingAngel extends Card
{
	public static final class Blind extends EventTriggeredAbility
	{
		public Blind(GameState state)
		{
			super(state, "Whenever Blinding Angel deals combat damage to a player, that player skips his or her next combat phase.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator combat = CombatPhaseOf.instance(thatPlayer);

			SimpleEventPattern nextCombatPhase = new SimpleEventPattern(EventType.BEGIN_PHASE);
			nextCombatPhase.put(EventType.Parameter.PHASE, combat);

			EventReplacementEffect skip = new EventReplacementEffect(state.game, "That player skips his or her next combat phase", nextCombatPhase);
			// skip.addEffect(nothing)

			this.addEffect(createFloatingReplacement(skip, "That player skips his or her next combat phase."));
		}
	}

	public BlindingAngel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Blinding Angel deals combat damage to a player, that player
		// skips his or her next combat phase.
		this.addAbility(new Blind(state));
	}
}
