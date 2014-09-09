package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silent Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class SilentSpecter extends Card
{
	public static final class MeleeDiscardTwo extends EventTriggeredAbility
	{
		public MeleeDiscardTwo(GameState state)
		{
			super(state, "Whenever Silent Specter deals combat damage to a player, that player discards two cards.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(discardCards(thatPlayer, 2, "That player discards two cards."));
		}
	}

	public SilentSpecter(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Silent Specter deals combat damage to a player, that player
		// discards two cards.
		this.addAbility(new MeleeDiscardTwo(state));

		// Morph (3)(B)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(B)(B)"));
	}
}
